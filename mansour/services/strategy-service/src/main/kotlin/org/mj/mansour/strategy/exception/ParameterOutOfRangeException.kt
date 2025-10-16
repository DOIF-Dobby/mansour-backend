package org.mj.mansour.strategy.exception

import org.mj.mansour.system.web.exception.BadRequestApiException

class ParameterOutOfRangeException(
    parameterName: String,
    boundaryValue: String,
    isMin: Boolean // 최소값 위반인지, 최대값 위반인지 구분
) : BadRequestApiException(
    code = "PARAMETER_OUT_OF_RANGE",
    messageProperty = if (isMin) "strategy.parameter.below.min" else "strategy.parameter.above.max",
    messageArguments = arrayOf(parameterName, boundaryValue)
)
