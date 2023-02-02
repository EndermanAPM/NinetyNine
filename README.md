# Ninety Nine code challange


## Thought process

> Here lies my mostly unfiltered thought process while doing the exercise




What persistence layer should I pick?
 - MongoDB it is. It's performant, we don't need complex relationships, great for developer speed, 
and it's already used by Ninety Nine.

Is there anything I can use to get better results on mongo given the task?
 - Yes! [Mongo Time Series collection](https://www.mongodb.com/docs/manual/core/timeseries-collections/) should provide
better performance.

About scheduling:
 - @Scheduled works fine for this challenge but wouldn't scale property. For production a better scheduler would be required
or at the very least a lock to avoid multiple executions.

About the week format:
 - [iso week formatting](https://en.wikipedia.org/wiki/ISO_week_date#:~:text=A%20precise%20date%20is%20specified,Monday%20and%20ending%20with%20Sunday.)
although following the iso date format I will be using the natural year-week as it seems clearer to me

Fun fact:
 - Company detail with 25M documents takes around 17 seconds (3 companies)

## Pending improvements
 - StockClient having its own DTO + mapper
 - Timezone handling / better timestamps (See: [#1](https://xkcd.com/1883/), [#2](https://youtu.be/-5wpm-gesOY), [multyplatform option](https://github.com/Kotlin/kotlinx-datetime/))
 - Add repository cache
 - Better scheduling
 - Add [embedded mongo](https://github.com/flapdoodle-oss/de.flapdoodle.embed.mongo.spring/tree/spring-3.0.x) for ease of
use, portability for integration tests (CI) and remove the need to manually purge old data in the tests.
 - Actually creating a timeseries collection [#1](https://github.com/spring-projects/spring-data-mongodb/issues/4166)
 - Add query pagination and limit in time
 - General observability
 - 404 on non-existent company name
