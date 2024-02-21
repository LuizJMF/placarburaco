package com.gmail.luizjmfilho.buraco.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class DoubleMatchPlayers(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val player1: String,
    val player2: String,
    val player3: String,
    val player4: String,
)
