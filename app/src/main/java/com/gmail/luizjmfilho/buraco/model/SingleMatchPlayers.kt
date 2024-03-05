package com.gmail.luizjmfilho.buraco.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class SingleMatchPlayers(
    @PrimaryKey(autoGenerate = true) val singleGroupId: Int = 0,
    val player1id: Int,
    val player2id: Int,
    val player3id: Int,
)
