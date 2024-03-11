package com.gmail.luizjmfilho.buraco.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.gmail.luizjmfilho.buraco.model.DoublePernaHistory
import com.gmail.luizjmfilho.buraco.model.SinglePernaHistory

@Dao
interface SinglePernaHistoryDao {

    @Insert
    suspend fun addSinglePerna(singlePerna: SinglePernaHistory)

    @Query("SELECT singlePernaId FROM SinglePernaHistory WHERE singleNegaId = :negaId")
    suspend fun listSinglePernasIdOfNegaWhoseIdIs(negaId: Int): List<Int>

    @Query("SELECT MAX(singlePernaNumber) FROM SinglePernaHistory WHERE singleNegaId = :singleNegaId")
    suspend fun getMaxSinglePernaNumber(singleNegaId: Int): Int?

    @Query("SELECT singlePernaId FROM SinglePernaHistory WHERE singleNegaId = :singleNegaId")
    suspend fun getSinglePernaIdFromNegaCreatedRightNow(singleNegaId: Int): Int?

    @Query("DELETE FROM SinglePernaHistory WHERE singleNegaId = :singleNegaId")
    suspend fun deleteAllSinglePernasBasedOnNegaId(singleNegaId: Int)

    @Query("SELECT MAX(singlePernaId) FROM SinglePernaHistory WHERE singleNegaId = :singleNegaId")
    suspend fun getMaxSinglePernaIdFromNega(singleNegaId: Int): Int?

    @Query("SELECT singlePernaNumber FROM SinglePernaHistory WHERE singlePernaId = :singlePernaId")
    suspend fun getSinglePernaNumberFromPernaId(singlePernaId: Int): Int

    @Query("SELECT singleNegaId FROM SinglePernaHistory WHERE singlePernaId = :singlePernaId")
    suspend fun getSingleNegaIdFromPernaId(singlePernaId: Int): Int

}