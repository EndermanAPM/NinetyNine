package dev.bugmaker.ninety_nine.services

import dev.bugmaker.ninety_nine.Fixtures
import dev.bugmaker.ninety_nine.clients.StockClient
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
}