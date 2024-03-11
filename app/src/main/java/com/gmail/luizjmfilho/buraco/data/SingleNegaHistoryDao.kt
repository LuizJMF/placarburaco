package com.gmail.luizjmfilho.buraco.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.gmail.luizjmfilho.buraco.model.SingleNegaHistory

@Dao
interface SingleNegaHistoryDao {

    @Insert
    suspend fun addSingleNega(singleNegaMatch: SingleNegaHistory)

    @Query("SELECT COUNT(*) FROM SingleNegaHistory WHERE singleGroupId = :singleGroupId")
    suspend fun countSingleNegasPlayed(singleGroupId: Int): Int

    @Query("SELECT singleNegaId FROM SingleNegaHistory WHERE singleGroupId = :singleGroupId")
    suspend fun listSingleNegasIdOfGroupWhoseIdIs(singleGroupId: Int): List<Int>

    @Query("SELECT MAX(singleNegaNumber) FROM SingleNegaHistory WHERE singleGroupId = :singleGroupId")
    suspend fun getMaxSingleNegaNumber(singleGroupId: Int): Int?

    @Query("SELECT MAX(singleNegaId) FROM SingleNegaHistory WHERE singleGroupId = :singleGroupId")
    suspend fun getMaxSingleNegaId(singleGroupId: Int): Int?

    @Query("DELETE FROM SingleNegaHistory WHERE singleGroupId = :singleGroupId")
    suspend fun deleteAllSingleNegasBasedOnGroupId(singleGroupId: Int)

    @Query("SELECT singleNegaNumber FROM SingleNegaHistory WHERE singleNegaId = :negaId ")
    suspend fun getSingleNegaNumberFromId(negaId: Int): Int

    @Query("SELECT singleGroupId FROM SingleNegaHistory WHERE singleNegaId = :negaId")
    suspend fun getSingleGroupIdFromNegaId(negaId: Int): Int

    @Query("SELECT isP2AfterP1 FROM SingleNegaHistory WHERE singleNegaId = :singleNegaId")
    suspend fun isP2AfterP1FromNegaId(singleNegaId: Int): Boolean
}