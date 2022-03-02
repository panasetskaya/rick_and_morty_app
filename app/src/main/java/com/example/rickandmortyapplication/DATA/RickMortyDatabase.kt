package com.example.rickandmortyapplication.DATA

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.rickandmortyapplication.POJO.Character
import com.example.rickandmortyapplication.POJO.Episode

@Database(entities = [Character::class, Episode::class], version = 1, exportSchema = false)
abstract class RickMortyDatabase: RoomDatabase() {
    abstract val rickMortyDao: RickMortyDao //ЗДЕСЬ МОЖЕТ БЫТЬ ФУНКЦИЯ!!!
    companion object {
        private const val DB_NAME = "rick_morty.db"
        private var INSTANCE: RickMortyDatabase? = null

        fun getInstance(context: Context): RickMortyDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(context.applicationContext, RickMortyDatabase::class.java, DB_NAME)
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance


                }
                return instance
            }
        }
    }
}