package dev.bugmaker.ninety_nine.domain

import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.TimeSeries
import org.springframework.format.annotation.DateTimeFormat

import java.util.*

@TimeSeries(collection = "stock", timeField = "timestamp", metaField = "companyName")
data class StockEntity (
    @field:DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    val timestamp: Date,
    @field:Indexed
    val companyName: String,
    val value: Float,
)