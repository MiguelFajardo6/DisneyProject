package com.example.disneyproject.data.remote.model
import com.google.gson.annotations.SerializedName
data class Disney(
    @SerializedName("_id")
    val id: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("imageUrl")
    val imageUrl: String,

)
