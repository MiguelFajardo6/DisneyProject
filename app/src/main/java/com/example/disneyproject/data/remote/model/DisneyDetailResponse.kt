package com.example.disneyproject.data.remote.model

import com.google.gson.annotations.SerializedName

data class DisneyDetailResponse(
    @SerializedName("info")
    val info: DetailInfo,
    @SerializedName("data")
    val data: DisneyDetail
)

data class DetailInfo(
    val count: Int,
    val totalPages: Int,
    val previousPage: String?,
    val nextPage: String?
)
