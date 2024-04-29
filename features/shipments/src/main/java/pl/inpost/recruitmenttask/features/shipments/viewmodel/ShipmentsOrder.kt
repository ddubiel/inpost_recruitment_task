package pl.inpost.recruitmenttask.features.shipments.viewmodel

import androidx.annotation.StringRes
import pl.inpost.recruitmenttask.features.shipments.R

enum class ShipmentsOrder(@StringRes val displayNameRes: Int) {
    STATUS(R.string.status_order_name),
    PICKUP_DATE(R.string.pickup_date),
    EXPIRE_DATE(R.string.expire_date),
    STORED_DATE(R.string.stored_date),
    NUMBER(R.string.number),
}