package pl.inpost.recruitmenttask.features.shipments.model

data class OperationsModel(
    val manualArchive: Boolean,
    val delete: Boolean,
    val collect: Boolean,
    val highlight: Boolean,
    val expandAvizo: Boolean,
    val endOfWeekCollection: Boolean
)