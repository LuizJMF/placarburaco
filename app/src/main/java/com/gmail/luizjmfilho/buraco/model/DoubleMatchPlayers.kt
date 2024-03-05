package com.gmail.luizjmfilho.buraco.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class DoubleMatchPlayers(
    @PrimaryKey(autoGenerate = true) val doubleGroupId: Int = 0,
    val player1id: Int,
    val player2id: Int,
    val player3id: Int,
    val player4id: Int,
)
