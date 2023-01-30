package dev.bugmaker.ninety_nine.it

import dev.bugmaker.ninety_nine.Fixtures
import dev.bugmaker.ninety_nine.repositories.StockRepository
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.HttpStatus


@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
class StockITTest {

    @Autowired
    lateinit var restTemplate: TestRestTemplate

    @Autowired
    lateinit var stockRepository: StockRepository



    @AfterEach
    fun truncateCollections() {
        stockRepository.deleteAll()
    }

    @Test
    fun `WHEN Get is called THEN should return company names`() {
        stockRepository.insert(Fixtures().mockedStocks())

        val result = restTemplate.getForEntity("/companies", List::class.java)

        assertNotNull(result)
        assertEquals(HttpStatus.OK, result?.statusCode)
        assertEquals(result.body, listOf("Apple", "Microsoft", "Tesla"))
    }
}