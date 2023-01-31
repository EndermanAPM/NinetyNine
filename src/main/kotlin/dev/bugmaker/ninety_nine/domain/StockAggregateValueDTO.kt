package dev.bugmaker.ninety_nine.domain

data class StockAggregateValueDTO (
    val date: String,
    val maxValue: Float,
    val averageValue: Float,
    val minValue: Float
)