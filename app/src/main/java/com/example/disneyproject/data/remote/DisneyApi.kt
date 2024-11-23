package com.example.disneyproject.data.remote
import com.example.disneyproject.data.remote.model.Disney
import com.example.disneyproject.data.remote.model.DisneyDetail
import com.example.disneyproject.data.remote.model.DisneyDetailResponse
import com.example.disneyproject.data.remote.model.DisneyResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path


interface DisneyApi {

        @GET("character")
        fun getCharacters(

        ): Call<DisneyResponse>

        @GET("character/{id}")
        fun getCharacterDetail(
                @Path("id") id: String?
        ): Call<DisneyDetailResponse>


}