data class TicketmasterResponse(
    val _embedded: Embedded?
)

data class Embedded(
    val events: List<Event>?
)

data class Event(
    val name: String,
    val dates: EventDates,
    val info: String?,
    val classifications: List<Classification>?,
    val images: List<Image>?
)

data class EventDates(
    val start: Start
)

data class Start(
    val localDate: String,
    val localTime: String?
)

data class Classification(
    val segment: Segment
)

data class Segment(
    val name: String
)

data class Image(
    val url: String
)
