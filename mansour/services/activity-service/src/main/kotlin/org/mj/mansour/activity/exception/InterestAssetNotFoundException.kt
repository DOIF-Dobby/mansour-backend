package org.mj.mansour.activity.exception

import org.mj.mansour.system.web.exception.NotFoundApiException

class InterestAssetNotFoundException : NotFoundApiException(
    code = "INTEREST_ASSET_NOT_FOUND",
    messageProperty = "interest-asset.not-found",
)
