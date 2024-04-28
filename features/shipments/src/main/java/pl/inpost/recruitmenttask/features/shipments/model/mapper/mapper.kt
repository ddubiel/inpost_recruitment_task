package pl.inpost.recruitmenttask.features.shipments.model.mapper

import pl.inpost.recruitmenttask.data.cache.rook.entities.CustomerEntity
import pl.inpost.recruitmenttask.data.cache.rook.entities.EventLogEntity
import pl.inpost.recruitmenttask.data.cache.rook.entities.OperationsEntity
import pl.inpost.recruitmenttask.data.cache.rook.entities.ShipmentEntity
import pl.inpost.recruitmenttask.data.network.model.CustomerNetwork
import pl.inpost.recruitmenttask.data.network.model.EventLogNetwork
import pl.inpost.recruitmenttask.data.network.model.OperationsNetwork
import pl.inpost.recruitmenttask.data.network.model.ShipmentNetwork
import pl.inpost.recruitmenttask.data.network.model.ShipmentStatus
import pl.inpost.recruitmenttask.features.shipments.model.CustomerModel
import pl.inpost.recruitmenttask.features.shipments.model.EventLogModel
import pl.inpost.recruitmenttask.features.shipments.model.OperationsModel
import pl.inpost.recruitmenttask.features.shipments.model.ShipmentModel


@JvmName("functionOfKotlin2")
fun List<ShipmentNetwork>.asEntity(): List<ShipmentEntity> = map { it.asEntity() }

fun ShipmentNetwork.asEntity() = ShipmentEntity(
    number = number,
    shipmentType = shipmentType,
    status = status,
    eventLog = eventLog.asEntity(),
    openCode = openCode,
    expiryDate = expiryDate,
    storedDate = storedDate,
    pickUpDate = pickUpDate,
    receiver = receiver?.asEntity(),
    sender = sender?.asEntity(),
    operations = operations.asEntity(),
)
private fun  List<EventLogNetwork>.asEntity(): List<EventLogEntity> {
    return map { it.asEntity() }
}
private fun EventLogNetwork.asEntity() = EventLogEntity(
    name = name,
    date = date,
)
@JvmName("functionOfKotlin3")
 fun  List<ShipmentEntity>.asDomainModel(): List<ShipmentModel> {
    return map { it.asDomainModel() }
}

private fun ShipmentEntity.asDomainModel() = ShipmentModel(
    number = number,
    shipmentType = shipmentType,
    status = ShipmentStatus.valueOf(status),
    eventLog = eventLog.asDomainModel(),
    openCode = openCode,
    expiryDate = expiryDate,
    storedDate = storedDate,
    pickUpDate = pickUpDate,
    receiver = receiver?.asDomainModel(),
    sender = sender?.asDomainModel(),
    operations = operations.asDomainModel(),
)


@JvmName("functionOfKotlin4")
private fun List<EventLogEntity>.asDomainModel(): List<EventLogModel> = map { it.asDomainModel() }
private fun EventLogEntity.asDomainModel(): EventLogModel = EventLogModel(
    name = name,
    date = date
)
private fun List<ShipmentNetwork>.asDomainModel(): List<ShipmentModel> = map { it.asDomainModel() }

fun ShipmentNetwork.asDomainModel() = ShipmentModel(
    number = number,
    shipmentType = shipmentType,
    status = ShipmentStatus.valueOf(status),
    eventLog = eventLog.asDomainModel(),
    openCode = openCode,
    expiryDate = expiryDate,
    storedDate = storedDate,
    pickUpDate = pickUpDate,
    receiver = receiver?.asDomainModel(),
    sender = sender?.asDomainModel(),
    operations = operations.asDomainModel(),
)

@JvmName("functionOfKotlin")
private fun List<EventLogNetwork>.asDomainModel() = map { it.asDomainModel() }

private fun EventLogNetwork.asDomainModel() = EventLogModel(
    name = name,
    date = date
)

private fun CustomerNetwork.asDomainModel() = CustomerModel(
    email = email,
    phoneNumber = phoneNumber,
    name = name
)

private fun CustomerNetwork.asEntity() = CustomerEntity(
    email = email,
    phoneNumber = phoneNumber,
    name = name
)


private fun OperationsNetwork.asDomainModel() = OperationsModel(
    manualArchive = manualArchive,
    delete = delete,
    collect = collect,
    highlight = highlight,
    expandAvizo = expandAvizo,
    endOfWeekCollection = endOfWeekCollection,
)

private fun OperationsNetwork.asEntity() = OperationsEntity(
    manualArchive = manualArchive,
    delete = delete,
    collect = collect,
    highlight = highlight,
    expandAvizo = expandAvizo,
    endOfWeekCollection = endOfWeekCollection,
)


private fun CustomerEntity.asDomainModel() = CustomerModel(
    email = email,
    phoneNumber = phoneNumber,
    name = name
)


private fun OperationsEntity.asDomainModel() = OperationsModel(
    manualArchive = manualArchive,
    delete = delete,
    collect = collect,
    highlight = highlight,
    expandAvizo = expandAvizo,
    endOfWeekCollection = endOfWeekCollection,
)