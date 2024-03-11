package com.gmail.luizjmfilho.buraco.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.gmail.luizjmfilho.buraco.model.DoubleMaoHistory
import com.gmail.luizjmfilho.buraco.model.SingleMaoHistory
import com.gmail.luizjmfilho.buraco.ui.PlayerPoints

@Dao
interface SingleMaoHistoryDao {

    @Insert
    suspend fun addSingleMao(singleMao: SingleMaoHistory)

    @Query("SELECT SUM(ptsP1) AS ptsP1, SUM(ptsP2) AS ptsP2, SUM(ptsP3) AS ptsP3 FROM SingleMaoHistory WHERE singlePernaId = :singlePernaId")
    suspend fun sumPlayerPointsForPernaWhoseIdIs(singlePernaId: Int): PlayerPoints

    @Query("DELETE FROM SingleMaoHistory WHERE singlePernaId = :singlePernaId")
    suspend fun deleteAllSingleMaoBasedOnPernaId(singlePernaId: Int)

    @Query("SELECT * FROM SingleMaoHistory WHERE singlePernaId = :singlePernaId")
    suspend fun viewAllSingleMaosFromPernaId(singlePernaId: Int): List<SingleMaoHistory>

    @Query("SELECT whoStarts FROM SingleMaoHistory WHERE singlePernaId = :singlePernaId AND singleMaoNumber = (SELECT MAX(singleMaoNumber) FROM SingleMaoHistory WHERE singlePernaId = :singlePernaId)")
    suspend fun getSingleStartingPlayerIdFromLastActiveMao(singlePernaId: Int): Int

    @Query("SELECT MAX(singleMaoNumber) FROM SingleMaoHistory WHERE singlePernaId = :singlePernaId")
    suspend fun getMaxSingleMaoNumber(singlePernaId: Int): Int

    @Query("UPDATE SingleMaoHistory SET ptsP1 = :newPtsP1 WHERE singlePernaId = :pernaId AND singleMaoNumber = :maoNumber")
    suspend fun updatePtsP1ForSingleMaoId(pernaId: Int, newPtsP1: Int, maoNumber: Int)

    @Query("UPDATE SingleMaoHistory SET ptsP2 = :newPtsP2 WHERE singlePernaId = :pernaId AND singleMaoNumber = :maoNumber")
    suspend fun updatePtsP2ForSingleMaoId(pernaId: Int, newPtsP2: Int, maoNumber: Int)

    @Query("UPDATE SingleMaoHistory SET ptsP3 = :newPtsP3 WHERE singlePernaId = :pernaId AND singleMaoNumber = :maoNumber")
    suspend fun updatePtsP3ForSingleMaoId(pernaId: Int, newPtsP3: Int, maoNumber: Int)

    @Query("DELETE FROM SingleMaoHistory WHERE singlePernaId = :singlePernaId AND singleMaoNumber = (SELECT MAX(singleMaoNumber) FROM SingleMaoHistory WHERE singlePernaId = :singlePernaId)")
    suspend fun deleteLastSingleMaoFromPernaId(singlePernaId: Int)
}
