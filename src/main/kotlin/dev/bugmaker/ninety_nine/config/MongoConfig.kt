package dev.bugmaker.ninety_nine.config

import org.springframework.context.annotation.Configuration
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories


@Configuration
@EnableMongoRepositories("dev.bugmaker.ninety_nine.repositories")
internal class MongoConfig {

}