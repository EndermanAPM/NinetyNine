package dev.bugmaker.ninety_nine.it

import dev.bugmaker.ninety_nine.Fixtures
import dev.bugmaker.ninety_nine.domain.StockAggregateValueDTO
import dev.bugmaker.ninety_nine.repositories.StockRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.HttpStatus


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class StockITTest {

    @Autowired
    lateinit var restTemplate: TestRestTemplate

    @Autowired
    lateinit var stockRepository: StockRepository


    @BeforeAll
    fun truncateCollectionAndAddMockdata() {
        stockRepository.deleteAll()
        stockRepository.insert(Fixtures().mockedStocksByWeeks(2))
    }


    @Test
    fun `WHEN Get is called THEN should return company names`() {

        val result = restTemplate.getForEntity("/companies", List::class.java)

        assertNotNull(result)
        assertEquals(HttpStatus.OK, result?.statusCode)
        assertEquals(result.body, listOf("Apple", "Microsoft", "Tesla"))
    }

    @Test
    fun `GIVEN hourly filter WHEN Get company detail is called THEN should return company hourly prices`() {

        val result = restTemplate.getForEntity("/companies/Apple?timePeriod=HOURLY", Array<StockAggregateValueDTO>::class.java)

        assertNotNull(result)
        assertEquals(HttpStatus.OK, result?.statusCode)
        assertThat(result.body).hasSizeBetween(330, 340)

        assertThat(result.body).allSatisfy {
            assertThat(it).hasNoNullFieldsOrProperties()
                .hasFieldOrProperty("date")
                .hasFieldOrProperty("maxValue")
                .hasFieldOrProperty("averageValue")
                .hasFieldOrProperty("minValue")
        }
    }

    @Test
    fun `GIVEN NO hourly filter WHEN Get company detail is called THEN should return company hourly prices`() {

        val resultWithoutFilters = restTemplate.getForEntity("/companies/Apple", Array<StockAggregateValueDTO>::class.java)
        val result = restTemplate.getForEntity("/companies/Apple?timePeriod=HOURLY", Array<StockAggregateValueDTO>::class.java)

        assertThat(resultWithoutFilters).isEqualTo(result)

    }

    @Test
    fun `GIVEN daily filter WHEN Get company detail is called THEN should return company daily prices`() {

        val result = restTemplate.getForEntity("/companies/Apple?timePeriod=DAILY", Array<StockAggregateValueDTO>::class.java)

        assertNotNull(result)
        assertEquals(HttpStatus.OK, result?.statusCode)
        assertThat(result.body).hasSizeBetween(14, 16)

        assertThat(result.body).allSatisfy {
            assertThat(it).hasNoNullFieldsOrProperties()
                .hasFieldOrProperty("date")
                .hasFieldOrProperty("maxValue")
                .hasFieldOrProperty("averageValue")
                .hasFieldOrProperty("minValue")
        }
    }

    @Test
    fun `GIVEN weekly filter WHEN Get company detail is called THEN should return company weekly prices`() {

        val result = restTemplate.getForEntity("/companies/Apple?timePeriod=WEEKLY", Array<StockAggregateValueDTO>::class.java)

        assertNotNull(result)
        assertEquals(HttpStatus.OK, result?.statusCode)
        assertThat(result.body).hasSize(3)

        assertThat(result.body).allSatisfy {
            assertThat(it).hasNoNullFieldsOrProperties()
                .hasFieldOrProperty("date")
                .hasFieldOrProperty("maxValue")
                .hasFieldOrProperty("averageValue")
                .hasFieldOrProperty("minValue")
        }
    }
}