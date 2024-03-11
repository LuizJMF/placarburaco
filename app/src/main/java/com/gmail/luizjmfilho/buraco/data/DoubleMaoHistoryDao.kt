package com.gmail.luizjmfilho.buraco.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.gmail.luizjmfilho.buraco.model.DoubleMaoHistory
import com.gmail.luizjmfilho.buraco.ui.TeamPoints

@Dao
interface DoubleMaoHistoryDao {

    @Insert
    suspend fun addDoubleMao(doubleMao: DoubleMaoHistory)

    @Query("SELECT SUM(ptsTeam1) AS ptsTeam1, SUM(ptsTeam2) AS ptsTeam2 FROM DoubleMaoHistory WHERE doublePernaId = :doublePernaId")
    suspend fun sumTeamPointsForPernaWhoseIdIs(doublePernaId: Int): TeamPoints

    @Query("DELETE FROM DoubleMaoHistory WHERE doublePernaId = :doublePernaId")
    suspend fun deleteAllDoubleMaoBasedOnPernaId(doublePernaId: Int)

    @Query("SELECT * FROM DoubleMaoHistory WHERE doublePernaId = :doublePernaId")
    suspend fun viewAllDoubleMaosFromPernaId(doublePernaId: Int): List<DoubleMaoHistory>

    @Query("SELECT whoStarts FROM DoubleMaoHistory WHERE doublePernaId = :doublePernaId AND doubleMaoNumber = (SELECT MAX(doubleMaoNumber) FROM DoubleMaoHistory WHERE doublePernaId = :doublePernaId)")
    suspend fun getDoubleStartingPlayerIdFromLastActiveMao(doublePernaId: Int): Int

    @Query("SELECT MAX(doubleMaoNumber) FROM DoubleMaoHistory WHERE doublePernaId = :doublePernaId")
    suspend fun getMaxDoubleMaoNumber(doublePernaId: Int): Int

    @Query("UPDATE DoubleMaoHistory SET ptsTeam1 = :newPtsTeam1 WHERE doublePernaId = :pernaId AND doubleMaoNumber = :maoNumber")
    suspend fun updatePtsTeam1ForSingleMaoId(pernaId: Int, newPtsTeam1: Int, maoNumber: Int)

    @Query("UPDATE DoubleMaoHistory SET ptsTeam2 = :newPtsTeam2 WHERE doublePernaId = :pernaId AND doubleMaoNumber = :maoNumber")
    suspend fun updatePtsTeam2ForSingleMaoId(pernaId: Int, newPtsTeam2: Int, maoNumber: Int)

    @Query("DELETE FROM DoubleMaoHistory WHERE doublePernaId = :doublePernaId AND doubleMaoNumber = (SELECT MAX(doubleMaoNumber) FROM DoubleMaoHistory WHERE doublePernaId = :doublePernaId)")
    suspend fun deleteLastDoubleMaoFromPernaId(doublePernaId: Int)
}