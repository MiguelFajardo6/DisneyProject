package com.example.disneyproject.data.remote.model

import com.google.gson.annotations.SerializedName

data class DisneyResponse(
    @SerializedName("info")
    val info: Info,
    @SerializedName("data")
    val data: List<Disney>
)

data class Info(
    @SerializedName("count")
    val count: Int,
    @SerializedName("totalPages")
    val totalPages: Int,
    @SerializedName("previousPage")
    val previousPage: String?,
    @SerializedName("nextPage")
    val nextPage: String?
)
