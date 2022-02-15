package com.synergygfs.travelapp.data

import android.provider.BaseColumns

object TravelAppContractContract {

    object City : BaseColumns {
        const val TABLE_NAME = "cities"
        const val COLUMN_NAME_NAME = "name"
        const val COLUMN_NAME_DESCRIPTION = "description"
    }

    object Landmark : BaseColumns {
        const val TABLE_NAME = "cities"
        const val COLUMN_NAME_NAME = "name"
        const val COLUMN_NAME_DESCRIPTION = "description"
    }

}