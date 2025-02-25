package com.example.m5_hw4;

public class Konspect {
    /*
    Урок №6 Room

    1 Добавляем зависимости в Градл :
   //room
implementation("androidx.room:room-runtime:2.6.1")
kapt("androidx.room:room-compiler:2.6.1")

2 Coздаем папку local в Дата и создаем там  дата класс CharacterEntity
3 Создаем в локал интерфейс  ДАО***
4 CharactersDatabase

     */
}
/*
package com.example.m5_hw4.ui

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.m5_hw4.R
import com.example.m5_hw4.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController


        }
    }

package com.example.m5_hw4.ui.fragments.fragment1

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.m5_hw4.R
import com.example.m5_hw4.data.model.characters.Character
import com.example.m5_hw4.databinding.ItemBinding

class CharacterAdapter(
    private val onItemClick: (Int) -> Unit
): ListAdapter<Character,CharacterAdapter.ViewHolder>(diffUtil) {

    inner class ViewHolder(private val binding: ItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(character: Character) = with(binding) {
            characterName.text = character.name
            characterLocation.text = character.location.name
            characterFirstSeen.text = character.origin.name
            characterStatus.text = character.status
            imgCharacter.load(character.image) {
                crossfade(true)
            }
            colorIndicator.setImageResource(
                when {
                    character.status?.contains("Dead") == true ->R.drawable.ic_circle_red
                    character.status?.contains("Alive") == true -> R.drawable.ic_circle_green
                    else -> R.drawable.ic_circle_grey
                }
            )

            root.setOnClickListener {
                onItemClick(character.id)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(getItem(position))

    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<Character>() {
            override fun areItemsTheSame(oldItem: Character, newItem: Character): Boolean {
                return oldItem.id == newItem.id
            }
            override fun areContentsTheSame(oldItem: Character, newItem: Character): Boolean {
                return oldItem == newItem
            }
        }
    }
}
package com.example.m5_hw4.ui.fragments.fragment1

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.m5_hw4.R
import com.example.m5_hw4.databinding.FragmentCharacterBinding
import dagger.hilt.android.AndroidEntryPoint
import dev.androidbroadcast.vbpd.viewBinding

@AndroidEntryPoint
class CharacterFragment : Fragment(R.layout.fragment_character) {

    private val binding by viewBinding(FragmentCharacterBinding::bind)
    private val viewModel: CharacterViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialize()
        setupObserve()

        binding.pgCharacter.visibility = View.VISIBLE
        viewModel.getAllCharacters()
    }

    private fun initialize() {
        val characterAdapter = CharacterAdapter { characterId ->
            val bundle = Bundle().apply {
                putInt("character_id", characterId)
            }
            findNavController().navigate(R.id.action_characterFragment_to_datailFragment, bundle)
        }

        binding.rvCharacter.apply {
            adapter = characterAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun setupObserve() {
        viewModel.characters.observe(viewLifecycleOwner) { response ->
            response.results?.let { results ->
                (binding.rvCharacter.adapter as CharacterAdapter).submitList(results)

                with(binding) {
                    rvCharacter.visibility = View.VISIBLE
                    pgCharacter.visibility = View.GONE
                }
            }
        }
        viewModel.error.observe(viewLifecycleOwner) { message ->
            Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
            binding.pgCharacter.visibility = View.GONE

        }
    }}
    package com.example.m5_hw4.ui.fragments.fragment1

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.m5_hw4.data.model.characters.BaseResponse
import com.example.m5_hw4.data.retrofit.ApiService
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class CharacterViewModel @Inject constructor
    (private val api: ApiService) : ViewModel() {

    private val _characters = MutableLiveData<BaseResponse>()
    val characters: LiveData<BaseResponse> get() = _characters

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    fun getAllCharacters() {
        api.getAllCharacters().enqueue(object : Callback<BaseResponse> {
            override fun onResponse(call: Call<BaseResponse>, response: Response<BaseResponse>) {

                if (response.isSuccessful) {
                    response.body()?.let {
                        _characters.postValue(it)
                    }
                } else {
                    _error.postValue("Error: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<BaseResponse>, thr: Throwable) {
                _error.postValue(thr.localizedMessage ?: "Unknown error")

            }
        })
    }
}
package com.example.m5_hw4.ui.fragments.fragment2

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.example.m5_hw4.R
import com.example.m5_hw4.databinding.FragmentDatailBinding
import dagger.hilt.android.AndroidEntryPoint
import dev.androidbroadcast.vbpd.viewBinding

@AndroidEntryPoint
class DatailFragment : Fragment(R.layout.fragment_datail) {

    private val binding by viewBinding(FragmentDatailBinding::bind)
    private val viewModel: DatailViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val characterId = arguments?.getInt("character_id") ?: return
        viewModel.getCharacterById(characterId)

        observeViewModel()

        setupExpandableLayouts()

    }

    private fun observeViewModel() {
        viewModel.character.observe(viewLifecycleOwner) { character ->
            with(binding) {
                characterName.text = character.name
                characterStatus.text = character.status
                characterLocation.text = character.location.name
                characterGender.text = character.gender

//            Glide.with(this)
//                .load(character.image)
//                .into(binding.characterImage)
                Glide.with(this@DatailFragment)
                    .load(character.image)
                    .into(characterImage)

//            binding.expandable.secondLayout.findViewById<TextView>(R.id.tv_character_info)?.text =
//                "ID: ${character.id}\nSpecies: ${character.species}\nType: ${character.type}"
                expandable.secondLayout.findViewById<TextView>(R.id.tv_character_info)?.text =
                    "ID: ${character.id}\nSpecies: ${character.species}\nType: ${character.type}"

//            binding.expandable2.secondLayout.findViewById<TextView>(R.id.tv_origin)?.text =
//                "Origin: ${character.origin.name}"
                expandable2.secondLayout.findViewById<TextView>(R.id.tv_origin)?.text =
                    "Origin: ${character.origin.name}"

//            binding.expandable3.secondLayout.findViewById<TextView>(R.id.tv_first_seen)?.text =
//                "First seen in: ${character.episode.firstOrNull() ?: "Unknown"}"
                expandable3.secondLayout.findViewById<TextView>(R.id.tv_first_seen)?.text =
                    "First seen in: ${character.episode.firstOrNull() ?: "Unknown"}"
            }

            viewModel.error.observe(viewLifecycleOwner) { error ->
                Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setupExpandableLayouts() {
        val expandables = listOf(
            binding.expandable,
            binding.expandable2,
            binding.expandable3
        )

        expandables.forEach { expandable ->
            expandable.parentLayout.setOnClickListener {
                if (expandable.isExpanded) {
                    expandable.collapse()
                } else {
                    expandable.expand()
                }
            }
        }
    }
}



package com.example.m5_hw4.ui.fragments.fragment2

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.m5_hw4.data.model.characters.Character
import com.example.m5_hw4.data.retrofit.ApiService
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class DatailViewModel @Inject constructor(private val api: ApiService) : ViewModel() {

    private val _character = MutableLiveData<Character>()
    val character: LiveData<Character> get() = _character

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    fun getCharacterById(id: Int) {
        api.getSingleCharacter(id).enqueue(object : Callback<Character> {
            override fun onResponse(
                call: Call<Character>,
                response: Response<Character>
            ) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        _character.postValue(it)
                    }
                } else {
                    _error.postValue("Error: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<Character>, thr: Throwable) {
                _error.postValue(thr.localizedMessage ?: "Unknown error")
            }
        })
    }
}
package com.example.m5_hw4

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App: Application() {
//Все приложения использующие Hilt должны содержать класс Application c анатацией @HiltAndroidApp
}
package com.example.m5_hw4.data.di

import com.example.m5_hw4.BuildConfig
import com.example.m5_hw4.data.retrofit.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }
}
package com.example.m5_hw4.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "characters")
data class CharacterEntity (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo("id")
    val id:Int=0,
    @ColumnInfo("gender")
    val gender:String,
    @ColumnInfo("image")
    val image:String,
   // @ColumnInfo("name")
    val name:String,
    val status:String,


    )
    package com.example.m5_hw4.data.local

import androidx.room.Dao
import androidx.room.Query

@Dao
interface CharactersDao {
    @Query("SELECT*FROM characters WHERE gender='male'")
    fun getMaleCharacters()
//    @Query("SELECT * FROM characters WHERE gender = 'male'")
//    fun getMaleCharacters(): List<CharacterEntity>              //?
    @Query("SELECT*FROM characters WHERE gender='female'")
    fun getFemaleCharacters()
    @Query("SELECT*FROM characters WHERE gender='unknown'")
    fun getUnknownCharacters()

    @Query("SELECT*FROM characters WHERE gender=:gender")
    fun getCharactersByGender(gender:String)

// Получить мужских персонажей и отсортировать по алфавитному порядкубпо имени
    @Query("SELECT*FROM characters WHERE gender='male' ORDER BY name ASC")
   fun getMaleCharactersOrderedByAscending()

    @Query("SELECT*FROM characters WHERE gender=:gender ORDER BY name ASC") //Динамичный способ
    fun getCharactersByGenderAndOrderByAscending(gender: String)

    @Query("SELECT*FROM characters ORDER BY LENGTH(name) DESC LIMIT 1")
    fun getCharactersOrderedByLengthName():List<CharacterEntity>

    @Query("SELECT*FROM characters WHERE id BETWEEN 1 AND 15")
    fun getTopFifteenCharacters()

    @Query("SELECT*FROM characters WHERE name LIKE  '%' AND :text || '%' ORDER BY name ASC")
        fun searchCharacterByName(text:String):List<CharacterEntity>
    //@Query("SELECT * FROM characters WHERE name LIKE '%' || :text || '%' ORDER BY name ASC")
    //fun searchCharacterByName(text: String): List<CharacterEntity>                               //?

}
package com.example.m5_hw4.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [CharacterEntity::class], version = 1)
abstract class CharactersDatabase:RoomDatabase() {

     abstract  fun dao():CharactersDao
}
package com.example.m5_hw4.data.model.characters

import com.google.gson.annotations.SerializedName


data class BaseResponse(
    @SerializedName("info")
    val info: Info,
    @SerializedName("results")
    val results: List<Character>
)
package com.example.m5_hw4.data.model.characters

import com.google.gson.annotations.SerializedName

data class Character(
    @SerializedName("created")
    val created: String,
    @SerializedName("episode")
    val episode: List<String>,
    @SerializedName("gender")
    val gender: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("image")
    val image: String,
    @SerializedName("location")
    val location: Location,
    @SerializedName("name")
    val name: String,
    @SerializedName("origin")
    val origin: Origin,
    @SerializedName("species")
    val species: String,
    @SerializedName("status")
    val status: String,
    @SerializedName("type")
    val type: String,
    @SerializedName("url")
    val url: String
)
package com.example.m5_hw4.data.model.characters

import com.google.gson.annotations.SerializedName

data class Info(
    @SerializedName("count")
    val count: Int,
    @SerializedName("next")
    val next: String,
    @SerializedName("pages")
    val pages: Int,
    @SerializedName("prev")
    val prev: String
)
package com.example.m5_hw4.data.model.characters

import com.google.gson.annotations.SerializedName

data class Location(
    @SerializedName("name")
    val name: String,
    @SerializedName("url")
    val url: String
)
package com.example.m5_hw4.data.model.characters

import com.google.gson.annotations.SerializedName

data class Origin(
    @SerializedName("name")
    val name: String,
    @SerializedName("url")
    val url: String
)
package com.example.m5_hw4.data.model.deteil

import com.google.gson.annotations.SerializedName

data class BaseDetailResponse(

    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("status")
    val status: String,
    @SerializedName("species")
    val species: String,
    @SerializedName("type")
    val type: String,
    @SerializedName("gender")
    val gender: String,
    @SerializedName("origin")
    val origin: Origin,
    @SerializedName("location")
    val location: Location,
    @SerializedName("image")
    val image: String,
    @SerializedName("episode")
    val episode: List<String>,
    @SerializedName("url")
    val url: String,
    @SerializedName("created")
    val created: String
)
package com.example.m5_hw4.data.model.deteil

import com.google.gson.annotations.SerializedName

data class Location(
    @SerializedName("name")
    val name: String,
    @SerializedName("url")
    val url: String
)
package com.example.m5_hw4.data.model.deteil

import com.google.gson.annotations.SerializedName

data class Origin(
    @SerializedName("name")
    val name: String,
    @SerializedName("url")
    val url: String
)
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
plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    id ("kotlin-kapt")
    id("androidx.navigation.safeargs.kotlin")
    id ("com.google.dagger.hilt.android")

}

android {
    namespace = "com.example.m5_hw4"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.m5_hw4"
        minSdk = 28
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        debug {
            buildConfigField("String", "BASE_URL", "\"https://rickandmortyapi.com/api/\"")
        }
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        viewBinding = true
        buildConfig = true
    }

}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    implementation(libs.expandablelayout)

    //retrofit
    implementation (libs.retrofit)
    implementation (libs.converter.gson)
    //coil
    implementation (libs.coil)

    //ViewBinding Delegate
    implementation(libs.vbpd)  //нужно про нее почитать подробнее


    //navigation
    implementation (libs.androidx.navigation.fragment)
    implementation (libs.androidx.navigation.ui)

    //Glide
    implementation (libs.glide)
    //D-Hilt
    implementation(libs.hilt.android)
    kapt(libs.hilt.android.compiler)
    //room
    implementation(libs.androidx.room.runtime)
    kapt(libs.androidx.room.compiler)



}
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.jetbrains.kotlin.android) apply false
    id("androidx.navigation.safeargs.kotlin") version "2.8.7" apply false
    id ("com.google.dagger.hilt.android") version "2.54" apply false // Hilt
}
<?xml version="1.0" encoding="utf-8"?>
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools">
    <uses-permission android:name="android.permission.INTERNET"/>

    <application
        android:name=".App"
        android:allowBackup="true"
        android:dataExtractionRules="@xml/data_extraction_rules"
        android:fullBackupContent="@xml/backup_rules"
        android:icon="@mipmap/ic_launcher"
        android:label="@string/app_name"
        android:roundIcon="@mipmap/ic_launcher_round"
        android:supportsRtl="true"
        android:theme="@style/Theme.M5_hw4"
        tools:targetApi="31">
        <activity
            android:name=".ui.MainActivity"
            android:exported="true">
            <intent-filter>
                <action android:name="android.intent.action.MAIN" />

                <category android:name="android.intent.category.LAUNCHER" />
            </intent-filter>
        </activity>
    </application>

</manifest>

<?xml version="1.0" encoding="utf-8"?>
<FrameLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".ui.fragments.fragment1.CharacterFragment"
    android:background="@color/dark_gray">

    <androidx.recyclerview.widget.RecyclerView
        android:id="@+id/rv_character"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        tools:listitem="@layout/item" />

    <ProgressBar
        android:id="@+id/pg_character"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_gravity="center"
        android:visibility="gone"
        tools:visibility="visible" />


</FrameLayout>



 */

