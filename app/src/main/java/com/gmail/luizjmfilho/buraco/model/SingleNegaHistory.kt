package com.gmail.luizjmfilho.buraco.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class SingleNegaHistory(
    @PrimaryKey(autoGenerate = true) val singleNegaId: Int = 0,
    val singleGroupId: Int,
    val singleNegaNumber: Int,
    val isP2AfterP1: Boolean, // se True, a ordem é P1, P2, P3; se False, é P1, P3, P2
)
