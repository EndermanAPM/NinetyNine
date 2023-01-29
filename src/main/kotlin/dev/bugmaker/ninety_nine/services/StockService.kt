package dev.bugmaker.ninety_nine.services

import dev.bugmaker.ninety_nine.clients.StockClient
import dev.bugmaker.ninety_nine.repositories.StockRepository
import org.springframework.stereotype.Service

@Service
class StockService(
    private val stockClient: StockClient,
    private val stockRepository: StockRepository
) {
    fun fetchAndPersistLatestStock() {
        val stockList = stockClient.fetchStock()
        stockRepository.insert(stockList)
    }
}