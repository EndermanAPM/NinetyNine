package dev.bugmaker.ninety_nine.controllers

import dev.bugmaker.ninety_nine.domain.StockAggregateValueDTO
import dev.bugmaker.ninety_nine.domain.TimeFilterEnum
import dev.bugmaker.ninety_nine.services.StockService
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping
class StockController(
    private val stockService: StockService
) {
    @GetMapping("companies")
    fun companies(): List<String> {

        return stockService.findCompanyNames()
    }

    @GetMapping("/companies/{id}")
    fun companyDetail(
        @PathVariable id: String,
        @RequestParam(defaultValue = "HOURLY") timePeriod: TimeFilterEnum
    ): List<StockAggregateValueDTO> {

        return stockService.findCompanyValues(id, timePeriod)
    }

}