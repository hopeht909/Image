package com.example.paringjsonfile.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

//The class name must match the one we created before
@Database(entities = [EntityImage::class], version = 1, exportSchema = false)
abstract class DatabaseImage: RoomDatabase() {

    //an object from NoteDoa Class so we can all it in the MainActivity
    abstract fun imageDao(): DoaImage

    // a constant code to activate the database
    companion object{
        @Volatile  // writes to this field are immediately visible to other threads
        private var INSTANCE: DatabaseImage? = null

        fun getDatabase(context: Context): DatabaseImage{
            val tempInstance = INSTANCE
            if(tempInstance != null){
                return tempInstance
            }
            synchronized(this){  // protection from concurrent execution on multiple threads
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    DatabaseImage::class.java,
                    "note_database" // databaseName
                ).fallbackToDestructiveMigration()  // Destroys old database on version change
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }

}