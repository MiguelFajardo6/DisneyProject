package com.example.disneyproject.data.remote.model

import com.google.gson.annotations.SerializedName

data class DisneyDetail(
    @SerializedName("name")
    val name: String,
    @SerializedName("imageUrl")
    val imageUrl: String,
    @SerializedName("sourceUrl")
    val sourceUrl: String
)
