package com.tangerine.core.model

import android.os.Parcelable
import androidx.compose.runtime.Immutable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
@Immutable
data class Attraction(
    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("introduction")
    val introduction: String,

    @field:SerializedName("address")
    val address: String,

    @field:SerializedName("url")
    val url: String,

    @field:SerializedName("images")
    val images: List<Image> = emptyList(),

    @field:SerializedName("saved")
    var isSaved: Boolean = false
) : Parcelable {
    @Parcelize
    data class Image(
        @field:SerializedName("src")
        val src: String = ""
    ) : Parcelable
}