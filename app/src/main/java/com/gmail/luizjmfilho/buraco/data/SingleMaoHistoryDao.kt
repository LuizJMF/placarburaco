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
}