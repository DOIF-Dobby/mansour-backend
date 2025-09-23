package org.mj.mansour.activity.exception

import org.mj.mansour.system.web.exception.ApiException
import org.springframework.http.HttpStatus

class DuplicateInterestAssetException : ApiException(
    httpStatus = HttpStatus.BAD_REQUEST,
    code = "DUPLICATE_INTEREST_ASSET",
    messageProperty = "interest.asset.duplicate",
)
