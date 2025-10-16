package org.mj.mansour.strategy.exception

import org.mj.mansour.system.web.exception.BadRequestApiException

class UnknownParameterException(
    parameterName: String
) : BadRequestApiException(
    code = "UNKNOWN_PARAMETER_PROVIDED",
    messageProperty = "strategy.parameter.unknown",
    messageArguments = arrayOf(parameterName)
)
