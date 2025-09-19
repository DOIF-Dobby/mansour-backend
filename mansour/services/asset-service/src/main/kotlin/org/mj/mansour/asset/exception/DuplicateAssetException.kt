package org.mj.mansour.asset.exception

import org.mj.mansour.system.web.exception.ApiException
import org.springframework.http.HttpStatus

class DuplicateAssetException(symbol: String) : ApiException(
    code = "ASSET_DUPLICATE",
    httpStatus = HttpStatus.BAD_REQUEST,
    messageProperty = "asset.duplicate",
    messageArguments = arrayOf(symbol),
)
