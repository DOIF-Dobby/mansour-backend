package org.mj.mansour.strategy.exception

import org.mj.mansour.system.web.exception.BadRequestApiException

class RequiredParameterMissingException(
    parameterName: String
) : BadRequestApiException(
    code = "REQUIRED_PARAMETER_MISSING",
    messageProperty = "strategy.parameter.missing",
    messageArguments = arrayOf(parameterName)
)
