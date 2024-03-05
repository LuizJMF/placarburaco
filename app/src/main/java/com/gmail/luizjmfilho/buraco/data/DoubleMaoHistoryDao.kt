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
}