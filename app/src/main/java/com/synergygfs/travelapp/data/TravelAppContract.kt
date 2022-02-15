package com.synergygfs.travelapp.data

import android.provider.BaseColumns

object TravelAppContractContract {

    object CityEntity : BaseColumns {
        const val TABLE_NAME = "cities"
        const val COLUMN_NAME_NAME = "name"
        const val COLUMN_NAME_DESCRIPTION = "description"
    }

    object LandmarkEntity : BaseColumns {
        const val TABLE_NAME = "cities"
        const val COLUMN_NAME_CITY_ID = "city_id"
        const val COLUMN_NAME_NAME = "name"
        const val COLUMN_NAME_DESCRIPTION = "description"
    }

}