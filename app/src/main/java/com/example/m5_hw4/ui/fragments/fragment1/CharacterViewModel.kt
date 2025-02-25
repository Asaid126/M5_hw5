package com.example.m5_hw4.ui.fragments.fragment1

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.m5_hw4.data.local.CharacterEntity
import com.example.m5_hw4.data.local.CharacterRepository
import com.example.m5_hw4.data.model.characters.BaseResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject




@HiltViewModel
class CharacterViewModel @Inject constructor(
    private val repository: CharacterRepository
) : ViewModel() {

    private val _characters = MutableLiveData<BaseResponse>()
    val characters: LiveData<BaseResponse> get() = _characters

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    fun getAllCharacters() {
        repository.getAllCharactersFromApi().enqueue(object : Callback<BaseResponse> {
            override fun onResponse(call: Call<BaseResponse>, response: Response<BaseResponse>) {
                if (response.isSuccessful) {
                    _characters.postValue(response.body())
                } else {
                    _error.postValue("Error: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<BaseResponse>, t: Throwable) {
                _error.postValue(t.localizedMessage ?: "Unknown error")
            }
        })
    }


    fun searchCharacterByName(text: String) {
        val characters = repository.searchCharacterByName(text)

    }


    fun getAliveCharactersFromDb() {
        val aliveCharacters = repository.getAliveCharactersFromDb()

    }
    fun getCharactersOrderedByShortestName(){
        val ShortName =repository.getCharactersOrderedByShortestName()
    }
    fun getCharactersByStatus(status: String){
        val ByStatus=repository.getCharactersByStatus(status)
    }

}
