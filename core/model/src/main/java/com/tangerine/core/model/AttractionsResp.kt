package com.tangerine.core.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class AttractionsResp(
    @field:SerializedName("total")
    val total: Int,

    @field:SerializedName("data")
    val data: List<Attraction> ?= emptyList()
) : Parcelable