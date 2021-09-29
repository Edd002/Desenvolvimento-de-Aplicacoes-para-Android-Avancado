package com.example.app.trabalhofinal_worldwidepublicholiday.Network

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Holiday (
    @Json(name = "date")
    val date: String,
    @Json(name = "localName")
    val localName: String,
    @Json(name = "name")
    val name: String,
    @Json(name = "countryCode")
    val countryCode: String,
    @Json(name = "fixed")
    val fixed: Boolean,
    @Json(name = "global")
    val global: Boolean,
    @Json(name = "counties")
    val counties: String?,
    @Json(name = "launchYear")
    val launchYear: Int?,
    @Json(name = "types")
    val types: List<String>
): Parcelable