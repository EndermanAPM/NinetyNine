package dev.bugmaker.ninety_nine.services

import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service

@Service
class SchedulerService(
    private val stockService: StockService

) {
    @Scheduled(fixedDelay = 20000)
    fun scheduledUpdateOfStocks() {
        stockService.fetchAndPersistLatestStock()
    }
}