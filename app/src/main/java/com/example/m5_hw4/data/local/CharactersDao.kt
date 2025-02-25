
package com.example.m5_hw4.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query


@Dao
interface CharactersDao {
    @Insert
    suspend fun insertCharacter(character: CharacterEntity) // Метод для вставки в базу

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

    @Query("SELECT * FROM characters WHERE status=:status")
    fun getCharactersByStatus(status: String): List<CharacterEntity>

    @Query("SELECT * FROM characters ORDER BY LENGTH(name) ASC LIMIT 1")
    fun getCharactersOrderedByShortestName(): List<CharacterEntity>

        abstract fun getAliveCharacters(): List<CharacterEntity>
    //@Query("SELECT * FROM characters WHERE name LIKE '%' || :text || '%' ORDER BY name ASC")
    //fun searchCharacterByName(text: String): List<CharacterEntity>                               //?

}