package com.example.m5_hw4.data.retrofit

import com.example.m5_hw4.data.model.characters.BaseResponse
import com.example.m5_hw4.data.model.characters.Character
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("character")
    fun getAllCharacters(): Call<BaseResponse>

    @GET("character/{id}")
    fun getSingleCharacter(
        @Path("id") id: Int
    ): Call<Character>
}