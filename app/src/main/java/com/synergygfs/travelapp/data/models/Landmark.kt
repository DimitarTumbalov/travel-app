package com.synergygfs.travelapp.data.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class Landmark(
    var id: Int = -1,
    var name: String = "",
    var description: String = ""
) : Parcelable