package com.example.integralmed.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class User(
    val id: String = "",
    val firstName: String = "",
    val lastName: String = "",
    val email: String = "",
    val mobile: String = "",
    val image: String = "",
    val profileCompleted: Int = 0,
    val profileAssetsStatus :Int = 0
) : Parcelable