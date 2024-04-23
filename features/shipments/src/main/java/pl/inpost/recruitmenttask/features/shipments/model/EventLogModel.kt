package pl.inpost.recruitmenttask.features.shipments.model

data class EventLogModel(
    val name: String,
    val date: String?,
) {
    /*override fun equals(other: Any?): Boolean {
        if (other !is EventLogModel) return false
        if ((name == other.name) && ((date == null && other.date == null) || date?.equals(other.date) ?: false)) return true
        else return false
    }*/
}