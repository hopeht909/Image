package com.example.paringjsonfile.database

import androidx.room.Entity
import androidx.room.PrimaryKey

//table name
@Entity(tableName = "ImageTable")
data class EntityImage(

    // table contents
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val author: String,
    val url: String)