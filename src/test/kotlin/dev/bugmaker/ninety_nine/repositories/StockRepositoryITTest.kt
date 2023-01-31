package dev.bugmaker.ninety_nine.repositories

import dev.bugmaker.ninety_nine.domain.StockAggregateValueDTO
import dev.bugmaker.ninety_nine.domain.StockEntity
import dev.bugmaker.ninety_nine.domain.TimeFilterEnum
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest
import java.util.*


@DataMongoTest
class StockRepositoryITTest {
    @Autowired
    lateinit var stockRepository: StockRepository

    val baseCalendar = GregorianCalendar(2000, 0, 1, 1, 0)
    val calendarAfterOneHour = GregorianCalendar(2000, 0, 1, 2, 0)
    val calendarAfterOneDay = GregorianCalendar(2000, 0, 2, 1, 0)
    val calendarAfterOneWeek = GregorianCalendar(2000, 0, 8, 1, 0)

    @BeforeEach
    fun truncateCollections() {
        stockRepository.deleteAll()
    }


    @Test
    fun `GIVEN hourly dateformat WHEN fetching aggregated stock prices THEN should return hourly stock data`() {
        val companyName = "LimaOscarLima"

        val testdata = listOf(
            StockEntity(Date(baseCalendar.timeInMillis), companyName, 10f),
            StockEntity(Date(calendarAfterOneHour.timeInMillis), companyName, 30f),
            StockEntity(Date(calendarAfterOneHour.timeInMillis), companyName, 50f),
        )
        stockRepository.insert(testdata)

        val expectedResult = listOf(
            StockAggregateValueDTO("2000-01-01T00", 10f, 10f, 10f),
            StockAggregateValueDTO("2000-01-01T01", 50f, 40f, 30f)
        )

        val aggregatedStockData = stockRepository.findAggregatedStockDataByTimeGroup(companyName, TimeFilterEnum.HOURLY.value)
        assertThat(aggregatedStockData).hasSize(2).containsAll(expectedResult)
    }

    @Test
    fun `GIVEN daily dateformat WHEN fetching aggregated stock prices THEN should return daily stock data`() {
        val companyName = "RoyalOilLunarFactory"
        val testdata = listOf(
            StockEntity(Date(baseCalendar.timeInMillis), companyName, 10f),
            StockEntity(Date(calendarAfterOneDay.timeInMillis), companyName, 30f),
            StockEntity(Date(calendarAfterOneDay.timeInMillis), companyName, 50f),
        )
        stockRepository.insert(testdata)

        val expectedResult = listOf(
            StockAggregateValueDTO("2000-01-01", 10f, 10f, 10f),
            StockAggregateValueDTO("2000-01-02", 50f, 40f, 30f)
        )

        val aggregatedStockData = stockRepository.findAggregatedStockDataByTimeGroup(companyName, TimeFilterEnum.DAILY.value)
        assertThat(aggregatedStockData).hasSize(2).containsAll(expectedResult)
    }

    @Test
    fun `GIVEN weekly dateformat WHEN fetching aggregated stock prices THEN should return weekly stock data`() {
        val companyName = "RoyalOilLunarFactory"
        val testdata = listOf(
            StockEntity(Date(baseCalendar.timeInMillis), companyName, 10f),
            StockEntity(Date(calendarAfterOneWeek.timeInMillis), companyName, 30f),
            StockEntity(Date(calendarAfterOneWeek.timeInMillis), companyName, 50f),
        )
        stockRepository.insert(testdata)

        val expectedResult = listOf(
            StockAggregateValueDTO("2000-W00", 10f, 10f, 10f),
            StockAggregateValueDTO("2000-W01", 50f, 40f, 30f)
        )

        val aggregatedStockData = stockRepository.findAggregatedStockDataByTimeGroup(companyName, TimeFilterEnum.WEEKLY.value)
        assertThat(aggregatedStockData).hasSize(2).containsAll(expectedResult)
    }
}