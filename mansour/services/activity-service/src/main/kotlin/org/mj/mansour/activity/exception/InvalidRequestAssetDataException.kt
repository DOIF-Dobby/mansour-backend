package org.mj.mansour.activity.exception

import org.mj.mansour.system.web.exception.ApiException
import org.springframework.http.HttpStatus

class InvalidRequestAssetDataException : ApiException(
    httpStatus = HttpStatus.BAD_REQUEST,
    code = "INVALID_REQUEST_ASSET_DATA",
    messageProperty = "activity.error.invalid-request-asset-data",
)
