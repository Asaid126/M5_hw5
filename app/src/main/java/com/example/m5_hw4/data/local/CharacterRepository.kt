package com.example.m5_hw4.data.local

import com.example.m5_hw4.data.model.characters.BaseResponse
import com.example.m5_hw4.data.retrofit.ApiService
import retrofit2.Call
import javax.inject.Inject

class CharacterRepository @Inject constructor(
    private val apiService: ApiService,
    private val charactersDao: CharactersDao
) {

    fun getAllCharactersFromApi(): Call<BaseResponse> {
        return apiService.getAllCharacters()
    }


    fun searchCharacterByName(text: String): List<CharacterEntity> {
        return charactersDao.searchCharacterByName(text)
    }


    fun getAliveCharactersFromDb(): List<CharacterEntity> {
        return charactersDao.getAliveCharacters()
    }
    fun getCharactersOrderedByShortestName(): List<CharacterEntity>{
        return  charactersDao.getCharactersOrderedByShortestName()
    }
    fun getCharactersByStatus(status: String): List<CharacterEntity>{
        return charactersDao.getCharactersByStatus(status)
    }

}



