package com.example.m5_hw4.ui.fragments.fragment1
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.example.m5_hw4.data.retrofit.ApiService
import com.example.m5_hw4.data.model.characters.Character
import com.example.m5_hw4.CharactersPagingSource
import com.example.m5_hw4.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject




@HiltViewModel
class CharacterViewModel @Inject constructor(private val api: ApiService) : ViewModel() {

    fun getAllCharacters(): LiveData<Resource<PagingData<Character>>> {
        return liveData {
            emit(Resource.Loading())
            try {
                val pager = Pager(
                    config = PagingConfig(
                        pageSize = 20,
                        initialLoadSize = 15,
                        enablePlaceholders = false
                    ),
                    pagingSourceFactory = { CharactersPagingSource(api) }
                ).liveData

                emitSource(pager.map { Resource.Success(it) })
            } catch (e: Exception) {
                emit(Resource.Error("Failed to load: ${e.message}"))
            }
        }
    }
}