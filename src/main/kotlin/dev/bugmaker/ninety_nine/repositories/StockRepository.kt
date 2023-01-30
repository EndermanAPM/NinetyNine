package dev.bugmaker.ninety_nine.repositories

import dev.bugmaker.ninety_nine.domain.StockEntity
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

interface StockDao {
    fun findDistinctCompanyNames(): List<String>
}

@Repository
private class StockRepositoryImpl(private val mongotemplate: MongoTemplate) : StockDao {

    override fun findDistinctCompanyNames(): List<String> {
        return mongotemplate.findDistinct("companyName", StockEntity::class.java, String::class.java)
    }
}

interface StockRepository : MongoRepository<StockEntity, String>, StockDao {

}