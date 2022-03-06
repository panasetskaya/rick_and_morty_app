package com.example.rickandmortyapplication.DATA

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.rickandmortyapplication.POJO.Character

@Dao
interface RickMortyDao {

    @Query("SELECT * FROM character WHERE id==:requiredId")
    fun getCharacterById(requiredId: Int): LiveData<Character>

//    @Query("SELECT * FROM episode WHERE characters LIKE '%' || :requiredCharacterUrl || '%'")
//    fun getEpisodesByCharacter(requiredCharacterUrl: String): LiveData<List<Episode>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCharacter(character: Character)

}