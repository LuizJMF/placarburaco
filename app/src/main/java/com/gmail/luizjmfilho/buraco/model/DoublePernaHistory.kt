package com.gmail.luizjmfilho.buraco.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity
data class DoublePernaHistory(
    @PrimaryKey(autoGenerate = true) val doublePernaId: Int = 0,
    val doubleNegaId: Int,
    val doublePernaNumber: Int,
)
