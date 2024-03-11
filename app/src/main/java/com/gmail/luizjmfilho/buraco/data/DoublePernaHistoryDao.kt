package com.gmail.luizjmfilho.buraco.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.gmail.luizjmfilho.buraco.model.DoublePernaHistory

@Dao
interface DoublePernaHistoryDao {

    @Insert
    suspend fun addDoublePerna(doublePerna: DoublePernaHistory)

    @Query("SELECT doublePernaId FROM DoublePernaHistory WHERE doubleNegaId = :negaId")
    suspend fun listDoublePernasIdOfNegaWhoseIdIs(negaId: Int): List<Int>

    @Query("SELECT MAX(doublePernaNumber) FROM DoublePernaHistory WHERE doubleNegaId = :doubleNegaId")
    suspend fun getMaxDoublePernaNumber(doubleNegaId: Int): Int?

    @Query("SELECT doublePernaId FROM DoublePernaHistory WHERE doubleNegaId = :doubleNegaId")
    suspend fun getDoublePernaIdFromNegaCreatedRightNow(doubleNegaId: Int): Int?

    @Query("DELETE FROM DoublePernaHistory WHERE doubleNegaId = :doubleNegaId")
    suspend fun deleteAllDoublePernasBasedOnNegaId(doubleNegaId: Int)

    @Query("SELECT MAX(doublePernaId) FROM DoublePernaHistory WHERE doubleNegaId = :doubleNegaId")
    suspend fun getMaxDoublePernaIdFromNega(doubleNegaId: Int): Int?

    @Query("SELECT doublePernaNumber FROM DoublePernaHistory WHERE doublePernaId = :doublePernaId")
    suspend fun getDoublePernaNumberFromPernaId(doublePernaId: Int): Int

    @Query("SELECT doubleNegaId FROM DoublePernaHistory WHERE doublePernaId = :doublePernaId")
    suspend fun getDoubleNegaIdFromPernaId(doublePernaId: Int): Int
}