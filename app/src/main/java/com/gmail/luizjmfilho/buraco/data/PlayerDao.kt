package com.gmail.luizjmfilho.buraco.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.gmail.luizjmfilho.buraco.model.Player

@Dao
interface PlayerDao {

    @Insert
    suspend fun addPlayer(playerName: Player)

    @Query("DELETE FROM Player WHERE playerName = :playerName")
    suspend fun deletePlayer(playerName: String)

    @Query("SELECT playerName FROM Player ORDER BY playerName")
    suspend fun readPlayer(): List<String>

    @Query("SELECT COUNT(playerName) FROM Player WHERE UPPER(playerName) = UPPER(:searchedName)")
    suspend fun numberOfPlayersWithThisName(searchedName: String): Int

}