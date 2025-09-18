package org.mj.mansour.asset.exception

import org.mj.mansour.system.web.exception.ApiException
import org.springframework.http.HttpStatus

class UnsupportedAssetException : ApiException(
    code = "ASSET_UNSUPPORTED",
    httpStatus = HttpStatus.BAD_REQUEST,
    messageProperty = "asset.unsupported",
)
