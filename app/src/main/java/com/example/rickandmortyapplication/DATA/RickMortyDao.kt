package com.example.rickandmortyapplication.DATA

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.rickandmortyapplication.POJO.Character
import com.example.rickandmortyapplication.POJO.Episode

@Dao
interface RickMortyDao {
    @Query("SELECT * FROM character")
    fun getAllCharacters(): LiveData<List<Character>>

    @Query("SELECT * FROM episode")
    fun getAllEpisodes(): LiveData<List<Episode>>

    @Query("DELETE FROM character")
    fun deleteAllCharacters()

    @Query("DELETE FROM episode")
    fun deleteAllEpisodes()

    @Query("SELECT * FROM character WHERE id==:requiredId")
    fun getCharacterById(requiredId: Int): LiveData<Character>

    @Query("SELECT * FROM episode WHERE characters LIKE '%' || :requiredCharacterUrl || '%'")
    fun getEpisodesByCharacter(requiredCharacterUrl: String): LiveData<List<Episode>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCharacter(character: Character)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertEpisode(episode: Episode)
}