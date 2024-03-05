package com.gmail.luizjmfilho.buraco.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.gmail.luizjmfilho.buraco.model.DoubleNegaHistory

@Dao
interface DoubleNegaHistoryDao {

    @Insert
    suspend fun addDoubleNega(doubleNegaMatch: DoubleNegaHistory)

    @Query("SELECT COUNT(*) FROM DoubleNegaHistory WHERE doubleGroupId = :doubleGroupId")
    suspend fun countDoubleNegasPlayed(doubleGroupId: Int): Int

    @Query("SELECT doubleNegaId FROM DoubleNegaHistory WHERE doubleGroupId = :doubleGroupId")
    suspend fun listDoubleNegasIdOfGroupWhoseIdIs(doubleGroupId: Int): List<Int>

    @Query("SELECT MAX(doubleNegaNumber) FROM DoubleNegaHistory WHERE doubleGroupId = :doubleGroupId")
    suspend fun getMaxDoubleNegaNumber(doubleGroupId: Int): Int?

    @Query("SELECT MAX(doubleNegaId) FROM DoubleNegaHistory WHERE doubleGroupId = :doubleGroupId")
    suspend fun getMaxDoubleNegaId(doubleGroupId: Int): Int?

    @Query("DELETE FROM DoubleNegaHistory WHERE doubleGroupId = :doubleGroupId")
    suspend fun deleteAllDoubleNegasBasedOnGroupId(doubleGroupId: Int)

}