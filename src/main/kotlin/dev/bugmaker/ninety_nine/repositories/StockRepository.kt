package dev.bugmaker.ninety_nine.repositories

import dev.bugmaker.ninety_nine.domain.StockAggregateValueDTO
import dev.bugmaker.ninety_nine.domain.StockEntity
import org.springframework.data.domain.Sort
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.aggregation.*
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository


interface StockDao {
    fun findDistinctCompanyNames(): List<String>
    fun findAggregatedStockDataByTimeGroup(companyName: String, dateFormat: String): List<StockAggregateValueDTO>
}

@Repository
private class StockRepositoryImpl(private val mongoTemplate: MongoTemplate) : StockDao {

    override fun findDistinctCompanyNames(): List<String> {
        return mongoTemplate.findDistinct("companyName", StockEntity::class.java, String::class.java)
    }

    override fun findAggregatedStockDataByTimeGroup(companyName: String, dateFormat: String): List<StockAggregateValueDTO> {

        val matchStage: MatchOperation = Aggregation.match(Criteria(StockEntity::companyName.name).`is`(companyName))

        val projectStage = Aggregation.project(StockEntity::value.name).and(StockEntity::timestamp.name)
            .dateAsFormattedString(dateFormat).`as`(StockAggregateValueDTO::date.name)

        val groupStage: GroupOperation = Aggregation.group(StockAggregateValueDTO::date.name)
            .avg(StockEntity::value.name).`as`(StockAggregateValueDTO::averageValue.name)
            .max(StockEntity::value.name).`as`(StockAggregateValueDTO::maxValue.name)
            .min(StockEntity::value.name).`as`(StockAggregateValueDTO::minValue.name)

        val addFieldsOperationBuilder = AddFieldsOperation(StockAggregateValueDTO::date.name, "\$_id")

        val sortFieldsOperation: SortOperation = Aggregation.sort(Sort.by(StockAggregateValueDTO::date.name))

        val aggregation: Aggregation = Aggregation.newAggregation(
            matchStage,
            projectStage,
            groupStage,
            addFieldsOperationBuilder,
            sortFieldsOperation
        )

        val output: AggregationResults<StockAggregateValueDTO> =
            mongoTemplate.aggregate(aggregation, "stock", StockAggregateValueDTO::class.java)

        return output.mappedResults
    }
}

interface StockRepository : MongoRepository<StockEntity, String>, StockDao