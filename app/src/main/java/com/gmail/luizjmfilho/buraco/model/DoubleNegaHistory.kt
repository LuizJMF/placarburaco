package com.gmail.luizjmfilho.buraco.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity
data class DoubleNegaHistory(
    @PrimaryKey(autoGenerate = true) val doubleNegaId: Int = 0,
    val doubleGroupId: Int,
    val doubleNegaNumber: Int,
    val isP3AfterP1: Boolean, // se True, a ordem é P1, P3, P2, P4; se False, é P1, P4, P2, P3
)
