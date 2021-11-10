package com.example.paringjsonfile.database

//class that make it easier to reach the methods we created in Doa CLass
class RepositoryImage(private val imageDao: DoaImage) {

    val getNotes: List<EntityImage> = imageDao.getImages()

    // suspend fun so we can reach it in the Main easily and resume it whenever
    suspend fun addImage(item: EntityImage){
        imageDao.addImage(item)
    }

    suspend fun deleteImage(item: EntityImage){
        imageDao.deleteImage(item)
    }

}