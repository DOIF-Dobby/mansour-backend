package org.mj.mansour.strategy.exception

import org.mj.mansour.system.web.exception.BadRequestApiException

class InvalidParameterTypeException(
    parameterName: String,
    invalidValue: String,
    expectedType: String
) : BadRequestApiException(
    code = "INVALID_PARAMETER_TYPE",
    messageProperty = "strategy.parameter.invalid.type",
    messageArguments = arrayOf(parameterName, invalidValue, expectedType)
)
