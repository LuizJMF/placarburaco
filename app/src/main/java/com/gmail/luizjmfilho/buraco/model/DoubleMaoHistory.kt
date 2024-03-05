package com.gmail.luizjmfilho.buraco.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class DoubleMaoHistory(
    @PrimaryKey(autoGenerate = true) val doubleMaoId: Int = 0,
    val doublePernaId: Int,
    val doubleMaoNumber: Int,
    val ptsTeam1: Int,
    val ptsTeam2: Int,
    val whoStarts: Int,
    val initialDate: String,
)
