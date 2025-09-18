package org.mj.mansour.asset.service

import org.mj.mansour.asset.domain.AssetRepository
import org.mj.mansour.asset.domain.Stock
import org.mj.mansour.asset.dto.RegisterStockRequest
import org.mj.mansour.asset.exception.AssetNotFoundException
import org.mj.mansour.asset.exception.DuplicateAssetException
import org.mj.mansour.asset.mapper.toResponse
import org.mj.mansour.contract.asset.AssetResponse
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class AssetService(
    private val assetRepository: AssetRepository,
) {

    @Transactional
    fun registerStock(request: RegisterStockRequest) {
        if (assetRepository.findBySymbol(request.symbol) != null) {
            throw DuplicateAssetException(symbol = request.symbol)
        }


        val stock = Stock(
            symbol = request.symbol,
            name = request.name,
            market = request.market
        )

        assetRepository.save(stock)
    }

    @Transactional(readOnly = true)
    fun getAssetBySymbol(symbol: String): AssetResponse {
        val asset = assetRepository.findBySymbol(symbol) ?: throw AssetNotFoundException()
        return asset.toResponse()
    }
}
