package com.example.rickandmortyapplication.DATA

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.rickandmortyapplication.POJO.Character
import com.example.rickandmortyapplication.POJO.Episode

@Database(entities = [Character::class, Episode::class], version = 1, exportSchema = false)
@TypeConverters(com.example.rickandmortyapplication.DATA.TypeConverters::class)
abstract class RickMortyDatabase: RoomDatabase() {
    abstract val rickMortyDao: RickMortyDao
    companion object {
        private const val DB_NAME = "rick_morty.db"
        private var INSTANCE: RickMortyDatabase? = null

        fun getInstance(context: Context): RickMortyDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(context, RickMortyDatabase::class.java, DB_NAME)
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance


                }
                return instance
            }
        }
    }
}