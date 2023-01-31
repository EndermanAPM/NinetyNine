package dev.bugmaker.ninety_nine.domain

enum class TimeFilterEnum(val value: String) {
    HOURLY("%Y-%m-%dT%H"),
    DAILY("%Y-%m-%d"),
    WEEKLY("%Y-W%U")
}