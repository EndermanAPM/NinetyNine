package dev.bugmaker.ninety_nine.controllers

import dev.bugmaker.ninety_nine.services.StockService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping
class StockController(
    private val stockService: StockService
) {
    @GetMapping("companies")
    fun companies(): List<String> {
        return stockService.findCompanyNames()
    }
}