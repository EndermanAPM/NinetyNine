package dev.bugmaker.ninety_nine.services

import org.awaitility.Awaitility.await
import org.junit.jupiter.api.Test
import org.mockito.Mockito.atLeast
import org.mockito.Mockito.verify
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.boot.test.mock.mockito.SpyBean
import java.time.Duration


@SpringBootTest
class SchedulerServiceTest {

    @MockBean
    lateinit var stockService: StockService
    @SpyBean
    lateinit var schedulerService :SchedulerService
    @Test
    fun scheduledUpdateOfStocks() {
        val numberOfInvocations = 2
        await().atMost(Duration.ofSeconds(30))
            .untilAsserted { verify(schedulerService, atLeast(numberOfInvocations)).scheduledUpdateOfStocks() }

        verify(stockService, atLeast(numberOfInvocations)).fetchAndPersistLatestStock()

    }
}