package com.gmail.luizjmfilho.buraco.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.gmail.luizjmfilho.buraco.model.SingleMatchPlayers

@Dao
interface SingleMatchPlayersDao {

    @Insert
    suspend fun addPlayers(playersNames: SingleMatchPlayers)

    @Query("SELECT COUNT(*) FROM SingleMatchPlayers WHERE (player1id IN (:players) AND player2id IN (:players) AND player3id IN (:players))")
    suspend fun verifyIfPlayersExist(players: List<Int>): Int

    @Query("SELECT * FROM SingleMatchPlayers")
    suspend fun viewAllSingleMatches() : List<SingleMatchPlayers>

    @Query("DELETE FROM SingleMatchPlayers WHERE (player1id = :p1 AND player2id = :p2 AND player3id = :p3)")
    suspend fun deleteSingleMatch(p1: Int, p2: Int, p3: Int)

    @Query("SELECT * FROM SingleMatchPlayers WHERE singleGroupId = :singleGroupId")
    suspend fun getTheSingleGroupWhoseIdIs(singleGroupId: Int) : SingleMatchPlayers
}