package com.tanyayuferova.lifestylenews.data.entity

import com.google.gson.annotations.SerializedName

data class SourceData(
    @SerializedName("id")
    val id: String?,
    @SerializedName("name")
    val name: String?
)