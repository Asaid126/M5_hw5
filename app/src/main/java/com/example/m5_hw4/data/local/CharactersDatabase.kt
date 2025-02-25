package com.example.m5_hw4.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [CharacterEntity::class], version = 1)
abstract class CharactersDatabase:RoomDatabase() {

     abstract  fun dao():CharactersDao
}