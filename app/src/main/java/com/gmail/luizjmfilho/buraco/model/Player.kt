package com.gmail.luizjmfilho.buraco.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Player(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val playerName: String,
)
