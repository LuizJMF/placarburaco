package com.gmail.luizjmfilho.buraco.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class SinglePernaHistory(
    @PrimaryKey(autoGenerate = true) val singlePernaId: Int = 0,
    val singleNegaId: Int,
    val singlePernaNumber: Int,
)
