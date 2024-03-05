package com.gmail.luizjmfilho.buraco.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class SingleMaoHistory(
    @PrimaryKey(autoGenerate = true) val singleMaoId: Int = 0,
    val singlePernaId: Int,
    val singleMaoNumber: Int,
    val ptsP1: Int,
    val ptsP2: Int,
    val ptsP3: Int,
    val whoStarts: Int,
    val initialDate: String,
)
