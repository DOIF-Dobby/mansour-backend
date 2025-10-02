package org.mj.mansour.strategy.service

import org.mansour.contract.strategy.UserStrategyActivatedEvent
import org.mj.mansour.strategy.domain.ParameterType
import org.mj.mansour.strategy.domain.StrategyParameter
import org.mj.mansour.strategy.domain.StrategyParameterRepository
import org.mj.mansour.strategy.domain.TradingStrategyRepository
import org.mj.mansour.strategy.domain.UserStrategy
import org.mj.mansour.strategy.domain.UserStrategyRepository
import org.mj.mansour.strategy.dto.CreateUserStrategyRequest
import org.mj.mansour.strategy.exception.DuplicateActiveStrategyException
import org.mj.mansour.strategy.exception.InvalidParameterTypeException
import org.mj.mansour.strategy.exception.ParameterOutOfRangeException
import org.mj.mansour.strategy.exception.RequiredParameterMissingException
import org.mj.mansour.strategy.exception.TradingStrategyNotFoundException
import org.mj.mansour.strategy.exception.UnknownParameterException
import org.mj.mansour.strategy.exception.UserStrategyNotFoundException
import org.mj.mansour.system.core.logging.log
import org.mj.mansour.system.data.extension.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserStrategyService(
    private val userStrategyRepository: UserStrategyRepository,
    private val tradingStrategyRepository: TradingStrategyRepository,
    private val strategyParameterRepository: StrategyParameterRepository,
    private val outboxService: OutboxService,
) {

    /**
     * 사용자가 새로운 전략을 등록합니다.
     */
    @Transactional
    fun createUserStrategy(userId: Long, request: CreateUserStrategyRequest) {
        // 전략 조회
        val tradingStrategy = tradingStrategyRepository.findByIdOrNull(request.strategyId)
            ?: throw TradingStrategyNotFoundException(request.strategyId)

        // 전략에 설정된 파라미터를 가져옵니다.
        val strategyParameters = strategyParameterRepository.findAllByStrategy(tradingStrategy)

        // 파라미터 검증을 합니다.
        validateParameters(userInput = request.parameters, rules = strategyParameters)

        // 사용자 전략을 저장합니다.
        val userStrategy = UserStrategy(
            userId = userId,
            strategy = tradingStrategy,
            symbol = request.symbol,
            parameters = request.parameters,
            positionSizingType = request.positionSizingType,
            positionSizingValue = request.positionSizingValue,
        )

        userStrategyRepository.save(userStrategy)
    }

    /**
     * 사용자가 등록한 전략을 활성화합니다.
     * - 이미 활성화된 전략이 있다면 예외를 발생시킵니다.
     * - 활성화된 전략이 없다면 해당 전략을 활성화 상태로 변경합니다.
     */
    @Transactional
    fun activateUserStrategy(userId: Long, userStrategyId: Long) {
        // 활성화 할 사용자 전략 조회
        val userStrategyToActivate = userStrategyRepository.findByIdAndUserId(userId = userId, id = userStrategyId)
            ?: throw UserStrategyNotFoundException(userStrategyId)

        // 이미 활성화 되어있으면 종료
        if (userStrategyToActivate.active) {
            log.info { "UserStrategy(id=$userStrategyId) is already active." }
            return
        }

        // 이미 활성화된 다른 전략이 있다면 예외 발생
        if (userStrategyRepository.hasActiveStrategyForSymbol(
                userId = userStrategyToActivate.userId,
                symbol = userStrategyToActivate.symbol,
                id = userStrategyToActivate.id
            )
        ) {
            throw DuplicateActiveStrategyException(
                symbol = userStrategyToActivate.symbol,
                strategyName = userStrategyToActivate.strategy.name
            )
        }

        // 활성화 상태로 변경
        userStrategyToActivate.activate()

        // outbox 레코드 생성
        outboxService.saveOutboxRecord(
            aggregateId = userStrategyToActivate.id.toString(),
            aggregateType = UserStrategyActivatedEvent.AGGREGATE_TYPE,
            eventType = UserStrategyActivatedEvent.EVENT_TYPE,
            payload = UserStrategyActivatedEvent.Payload(
                userStrategyId = userStrategyToActivate.id,
                userId = userId,
                strategyId = userStrategyToActivate.strategy.id,
                symbol = userStrategyToActivate.symbol,
                parameters = userStrategyToActivate.parameters,
            )
        )

    }

    /**
     * 사용자가 등록한 전략을 비활성화합니다.
     * - 이미 비활성화된 전략은 아무 작업도 하지 않습니다.
     */
    @Transactional
    fun deactivateUserStrategy(userId: Long, userStrategyId: Long) {
        // 비활성화 할 사용자 전략 조회
        val userStrategyToDeactivate = userStrategyRepository.findByIdAndUserId(userId = userId, id = userStrategyId)
            ?: throw UserStrategyNotFoundException(userStrategyId)

        // 이미 비활성화 되어있으면 종료
        if (!userStrategyToDeactivate.active) {
            log.info { "UserStrategy(id=$userStrategyId) is already inactive." }
            return
        }

        // 비활성화 상태로 변경
        userStrategyToDeactivate.deactivate()
    }

    /**
     * 사용자 입력 파라미터를 검증합니다.
     * - 필수 파라미터가 누락되었는지 확인합니다.
     * - 각 파라미터의 타입이 올바른지 확인합니다.
     * - 각 파라미터의 값이 정의된 범위 내에 있는지 확인합니다.
     * - 정의되지 않은 파라미터가 있는지 확인합니다.
     */
    private fun validateParameters(userInput: Map<String, String>, rules: List<StrategyParameter>) {
        rules.forEach { rule ->
            val userValueString = userInput[rule.parameterName]

            // 필수 파라미터 검증
            if (rule.required && userValueString.isNullOrBlank()) {
                throw RequiredParameterMissingException(rule.parameterName)
            }

            if (!userValueString.isNullOrBlank()) {
                // 타입 및 범위 검증
                when (rule.parameterType) {
                    ParameterType.INTEGER -> {
                        // 1. String을 Int로 변환 시도. 실패하면 예외 발생
                        val intValue = userValueString.toIntOrNull()
                            ?: throw InvalidParameterTypeException(rule.parameterName, userValueString, "Integer")

                        // 2. 최소/최대값 범위 검증
                        rule.minValue?.let { min ->
                            if (intValue < min.toInt()) throw ParameterOutOfRangeException(rule.parameterName, min, isMin = true)
                        }
                        rule.maxValue?.let { max ->
                            if (intValue > max.toInt()) throw ParameterOutOfRangeException(rule.parameterName, max, isMin = false)
                        }
                    }

                    ParameterType.DOUBLE -> {
                        val doubleValue = userValueString.toDoubleOrNull()
                            ?: throw InvalidParameterTypeException(rule.parameterName, userValueString, "Double")

                        rule.minValue?.let { min ->
                            if (doubleValue < min.toDouble()) throw ParameterOutOfRangeException(rule.parameterName, min, isMin = true)
                        }
                        rule.maxValue?.let { max ->
                            if (doubleValue > max.toDouble()) throw ParameterOutOfRangeException(rule.parameterName, max, isMin = false)
                        }
                    }

                    ParameterType.STRING -> {
                        // String 타입에 대한 검증 (예: 길이, 패턴 등)
                    }
                }
            }
        }

        // 정의되지 않은 파라미터가 있는지 검증
        val ruleNames = rules.map { it.parameterName }.toSet()
        userInput.keys.forEach { userParamName ->
            if (userParamName !in ruleNames) {
                throw UnknownParameterException(userParamName)
            }
        }
    }

}
