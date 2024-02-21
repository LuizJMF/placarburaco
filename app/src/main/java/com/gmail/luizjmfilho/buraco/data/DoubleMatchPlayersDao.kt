package com.gmail.luizjmfilho.buraco.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.gmail.luizjmfilho.buraco.model.DoubleMatchPlayers

@Dao
interface DoubleMatchPlayersDao {

    @Insert
    suspend fun addPlayers(playersNames: DoubleMatchPlayers)

    @Query("" +
            "SELECT COUNT(*) " +
            "FROM DoubleMatchPlayers " +
            "WHERE ((" +
                "(player1 = :nome1 AND player2 = :nome2) OR " +
                "(player1 = :nome2 AND player2 = :nome1)" +
            ") " +
            "AND (" +
                "(player3 = :nome3 AND player4 = :nome4) OR " +
                "(player3 = :nome4 AND player4 = :nome3)" +
            ")) OR ((" +
                "(player1 = :nome3 AND player2 = :nome4) OR " +
                "(player1 = :nome4 AND player2 = :nome3)" +
            ") " +
            "AND (" +
                "(player3 = :nome1 AND player4 = :nome2) OR " +
                "(player3 = :nome2 AND player4 = :nome1)" +
            "))"
    )
    suspend fun verifyIfPlayersExist(nome1: String, nome2: String, nome3: String, nome4: String,): Int

    @Query("SELECT * FROM DoubleMatchPlayers")
    suspend fun viewAllDoubleMatches() : List<DoubleMatchPlayers>

}