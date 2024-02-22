package com.gmail.luizjmfilho.buraco.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.gmail.luizjmfilho.buraco.model.DoubleMatchPlayers
import com.gmail.luizjmfilho.buraco.model.Player
import com.gmail.luizjmfilho.buraco.model.SingleMatchPlayers

@Dao
interface SingleMatchPlayersDao {

    @Insert
    suspend fun addPlayers(playersNames: SingleMatchPlayers)

    @Query("SELECT COUNT(*) FROM SingleMatchPlayers WHERE (player1 IN (:players) AND player2 IN (:players) AND player3 IN (:players))")
    suspend fun verifyIfPlayersExist(players: List<String>): Int

    @Query("SELECT * FROM SingleMatchPlayers")
    suspend fun viewAllSingleMatches() : List<SingleMatchPlayers>

    @Query("DELETE FROM SingleMatchPlayers WHERE (player1 = :p1 AND player2 = :p2 AND player3 = :p3)")
    suspend fun deleteSingleMatch(p1: String, p2: String, p3: String)
}