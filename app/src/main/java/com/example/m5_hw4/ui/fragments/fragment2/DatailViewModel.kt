package com.example.m5_hw4.ui.fragments.fragment2

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.m5_hw4.data.model.characters.Character
import com.example.m5_hw4.data.retrofit.ApiService
import com.example.m5_hw4.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class DatailViewModel @Inject constructor(private val api: ApiService) : ViewModel() {


    fun getCharacterById(id: Int) : LiveData<Resource<Character>> {
        return liveData {
            emit(Resource.Loading())
            val result = runCatching {
                withContext(Dispatchers.IO) {
                    api.getSingleCharacter(id)
                }
            }
            result.onSuccess { response ->
                val body = response.body()
                if (response.isSuccessful && body != null) {
                    emit(Resource.Success(body))
                } else {
                    emit(Resource.Error("Error : ${response.message()}"))
                }
            }.onFailure { exception ->
                emit(Resource.Error("Network error : ${exception.message}"))
            }
        }
    }
}