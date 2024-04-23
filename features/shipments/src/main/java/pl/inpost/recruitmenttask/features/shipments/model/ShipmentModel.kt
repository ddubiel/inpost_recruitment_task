package pl.inpost.recruitmenttask.features.shipments.model

import pl.inpost.recruitmenttask.data.network.model.ShipmentStatus
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

data class ShipmentModel(
    val number: String,
    val shipmentType: String,
    val status: ShipmentStatus,
    val eventLog: List<EventLogModel>,
    val openCode: String?,
    val expiryDate: ZonedDateTime?,
    val storedDate: ZonedDateTime?,
    val pickUpDate: ZonedDateTime?,
    val receiver: CustomerModel?,
    val sender: CustomerModel?,
    val operations: OperationsModel
)

const val DEFAULT_DATE_FORMAT = "EEE | dd.MM.yyyy | HH:mm"
val DEFAULT_LOCALE = Locale("pl", "PL")
fun ZonedDateTime.formatToStringDate(
    format: String = DEFAULT_DATE_FORMAT,
    locale: Locale = DEFAULT_LOCALE
): String {
    val formatter2 = DateTimeFormatter.ofPattern(format, locale)
    return format(formatter2)
}
