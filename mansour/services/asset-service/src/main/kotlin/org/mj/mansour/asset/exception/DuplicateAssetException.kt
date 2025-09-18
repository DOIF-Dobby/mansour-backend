package org.mj.mansour.asset.exception

import org.mj.mansour.system.web.exception.ApiException
import org.springframework.http.HttpStatus.CONFLICT

class DuplicateAssetException(symbol: String) : ApiException(
    code = "ASSET_DUPLICATE",
    httpStatus = CONFLICT,
    messageProperty = "asset.duplicate",
    messageArguments = arrayOf(symbol),
)
