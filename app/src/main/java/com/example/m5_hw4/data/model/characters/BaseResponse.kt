package com.example.m5_hw4.data.model.characters

import com.google.gson.annotations.SerializedName


data class BaseResponse(
    @SerializedName("info")
    val info: Info,
    @SerializedName("results")
    val results: List<Character>
)

