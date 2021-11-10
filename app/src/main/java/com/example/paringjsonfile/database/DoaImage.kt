package com.example.paringjsonfile.database

import androidx.room.*

@Dao
interface DoaImage {


    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addImage(item: EntityImage)

    @Query("SELECT * FROM ImageTable ORDER BY id ASC")
    fun getImages(): List<EntityImage>


    @Delete
    suspend fun deleteImage(item: EntityImage)

}