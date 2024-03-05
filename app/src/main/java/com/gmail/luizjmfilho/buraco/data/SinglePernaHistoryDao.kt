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

}