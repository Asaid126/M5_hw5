

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