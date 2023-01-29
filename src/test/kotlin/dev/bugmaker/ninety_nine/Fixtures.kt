package dev.bugmaker.ninety_nine

import dev.bugmaker.ninety_nine.domain.StockEntity
import java.util.*
import kotlin.random.Random

class Fixtures {
    fun mockedStocks() = listOf(
        StockEntity(Date(), "Apple", Random.nextFloat()),
        StockEntity(Date(), "Microsoft", Random.nextFloat()),
        StockEntity(Date(), "Tesla", Random.nextFloat()),
    )
}

