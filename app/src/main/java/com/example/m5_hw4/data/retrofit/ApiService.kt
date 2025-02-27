package com.example.m5_hw4.data.retrofit

import com.example.m5_hw4.data.model.characters.BaseResponse
import com.example.m5_hw4.data.model.characters.Character
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("character")
    suspend fun getAllCharacters(
        @Query("page") page: Int,
    ): Response<BaseResponse>

    @GET("character/{id}")
    suspend fun getSingleCharacter(
        @Path("id") id: Int
    ): Response<Character>
}