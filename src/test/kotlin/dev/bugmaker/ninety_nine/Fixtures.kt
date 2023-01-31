package dev.bugmaker.ninety_nine

import dev.bugmaker.ninety_nine.domain.StockEntity
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.time.Instant
import java.time.temporal.ChronoUnit
import java.util.*
import kotlin.random.Random

class Fixtures {
    fun mockedStocks(time: Long = System.currentTimeMillis()) = listOf(
        StockEntity(Date(time), "Apple", Random.nextFloat()),
        StockEntity(Date(time), "Microsoft", Random.nextFloat()),
        StockEntity(Date(time), "Tesla", Random.nextFloat()),
    )




    fun mockedStocksByWeeks(weeks: Int = 2): List<StockEntity> {
        val days = weeks * 7
        val hours = days * 24
        val quarters = hours * 4
        var instant = Instant.now()

        val mockedStocks = buildList(quarters) {
            repeat(quarters) {
                addAll(mockedStocks(instant.toEpochMilli()))
                instant = instant.minus(15, ChronoUnit.MINUTES)
            }
        }
        return mockedStocks
    }

    @Test
    fun testMockedStocksByWeeks() {
        var mockedStocksByWeeks = mockedStocksByWeeks(2)
        assertThat(mockedStocksByWeeks.size).isEqualTo(4032)
        mockedStocksByWeeks = mockedStocksByWeeks(3)
        assertThat(mockedStocksByWeeks.size).isEqualTo(6048)

    }
}

