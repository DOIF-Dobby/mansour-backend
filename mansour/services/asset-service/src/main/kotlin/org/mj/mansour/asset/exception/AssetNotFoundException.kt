package org.mj.mansour.asset.exception

import org.mj.mansour.system.web.exception.NotFoundApiException

class AssetNotFoundException : NotFoundApiException(
    code = "ASSET_NOT_FOUND",
    messageProperty = "asset.not-found",
)
