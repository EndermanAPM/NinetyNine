package dev.bugmaker.ninety_nine.repositories

import dev.bugmaker.ninety_nine.domain.StockEntity
import org.springframework.data.mongodb.repository.MongoRepository


interface StockRepository : MongoRepository<StockEntity, String> {

}