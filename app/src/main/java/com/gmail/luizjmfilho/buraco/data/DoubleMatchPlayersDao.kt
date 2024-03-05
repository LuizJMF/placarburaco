package com.gmail.luizjmfilho.buraco.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.gmail.luizjmfilho.buraco.model.DoubleMatchPlayers
import com.gmail.luizjmfilho.buraco.model.SingleMatchPlayers

@Dao
interface DoubleMatchPlayersDao {

    @Insert
    suspend fun addPlayers(playersNames: DoubleMatchPlayers)

    @Query("" +
            "SELECT COUNT(*) " +
            "FROM DoubleMatchPlayers " +
            "WHERE ((" +
            "(player1id = :nome1id AND player2id = :nome2id) OR " +
            "(player1id = :nome2id AND player2id = :nome1id)" +
            ") " +
            "AND (" +
            "(player3id = :nome3id AND player4id = :nome4id) OR " +
            "(player3id = :nome4id AND player4id = :nome3id)" +
            ")) OR ((" +
            "(player1id = :nome3id AND player2id = :nome4id) OR " +
            "(player1id = :nome4id AND player2id = :nome3id)" +
            ") " +
            "AND (" +
            "(player3id = :nome1id AND player4id = :nome2id) OR " +
            "(player3id = :nome2id AND player4id = :nome1id)" +
            "))"
    )
    suspend fun verifyIfPlayersExist(nome1id: Int, nome2id: Int, nome3id: Int, nome4id: Int,): Int

    @Query("SELECT * FROM DoubleMatchPlayers")
    suspend fun viewAllDoubleMatches() : List<DoubleMatchPlayers>

    @Query("DELETE FROM DoubleMatchPlayers WHERE (player1id = :p1 AND player2id = :p2 AND player3id = :p3 AND player4id = :p4)")
    suspend fun deleteDoubleMatch(p1: Int, p2: Int, p3: Int, p4: Int)

    @Query("SELECT * FROM DoubleMatchPlayers WHERE doubleGroupId = :doubleGroupId")
    suspend fun getTheDoubleGroupWhoseIdIs(doubleGroupId: Int) : DoubleMatchPlayers

}