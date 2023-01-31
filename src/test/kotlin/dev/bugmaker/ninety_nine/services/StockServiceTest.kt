package dev.bugmaker.ninety_nine.services

import dev.bugmaker.ninety_nine.Fixtures
import dev.bugmaker.ninety_nine.clients.StockClient
import dev.bugmaker.ninety_nine.domain.StockAggregateValueDTO
import dev.bugmaker.ninety_nine.domain.TimeFilterEnum
import dev.bugmaker.ninety_nine.repositories.StockRepository
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
@MockKExtension.ConfirmVerification
class StockServiceTest {

    @MockK
    lateinit var stockClient: StockClient

    @MockK
    lateinit var stockRepository: StockRepository

    @InjectMockKs
    lateinit var stockService: StockService

    @Test
    fun fetchLatestStock() {
        val stockFixture = Fixtures().mockedStocks()
        every { stockClient.fetchStock() } returns stockFixture
        every { stockRepository.insert(stockFixture) } returns stockFixture

        stockService.fetchAndPersistLatestStock()

        verify { stockClient.fetchStock() }
        verify { stockRepository.insert(stockFixture) }
    }

    @Test
    fun findCompanyNames() {
        val companyNames = listOf("foo", "bar")
        every { stockRepository.findDistinctCompanyNames() } returns companyNames

        assertEquals(stockService.findCompanyNames(), companyNames)

        verify { stockRepository.findDistinctCompanyNames() }

    }
    @Test
    fun findCompanyValues() {
        every { stockRepository.findAggregatedStockDataByTimeGroup("Apple", TimeFilterEnum.DAILY.value) } returns listOf( StockAggregateValueDTO("2000-01-01T00", 10f, 10f, 10f))

        stockService.findCompanyValues("Apple", TimeFilterEnum.DAILY)

        verify { stockRepository.findAggregatedStockDataByTimeGroup("Apple", TimeFilterEnum.DAILY.value) }


    }

}