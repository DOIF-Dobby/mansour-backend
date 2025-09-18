package org.mj.mansour.asset.exception

import org.mj.mansour.system.web.exception.ApiException
import org.springframework.http.HttpStatus

class UnsupportedAssetException : ApiException(
    code = "UNSUPPORTED_ASSET",
    httpStatus = HttpStatus.INTERNAL_SERVER_ERROR,
    messageProperty = "asset.unsupported",
)
