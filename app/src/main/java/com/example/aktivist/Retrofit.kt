package com.example.aktivist

data class ApiResponse(
    val _embedded: Embedded?
)

data class Embedded(
    val events: List<ApiEvent>?
)

data class ApiEvent(
    val name: String,
    val dates: ApiDates,
    val classifications: List<Classification>,
    val images: List<ApiImage>?,
    val info: String?
)

data class ApiDates(
    val start: ApiStart
)

data class ApiStart(
    val localDate: String,
    val localTime: String
)

data class Classification(
    val segment: Segment
)

data class Segment(
    val name: String
)

data class ApiImage(
    val url: String
)
