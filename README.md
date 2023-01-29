# Ninety Nine code challange


## Thought process

> Here lies my mostly unfiltered thought process while doing the exercise




What persistence layer should I pick?
 - MongoDB it is. It's performant, we don't need complex relationships, great for developer speed, 
and it's already used by Ninety Nine.

Is there anything I can use to get better results on mongo given the task.
 - Yes! [Mongo Time Series collection](https://www.mongodb.com/docs/manual/core/timeseries-collections/) should provide
better performance.

## Pending improvements
 - StockClient having its own DTO + mapper
 - Timezone handling / better timestamps (See: [#1](https://xkcd.com/1883/), [#2](https://youtu.be/-5wpm-gesOY), [multyplatform option](https://github.com/Kotlin/kotlinx-datetime/))
 - Add repository cache