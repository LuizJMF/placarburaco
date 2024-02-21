package com.gmail.luizjmfilho.buraco.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.gmail.luizjmfilho.buraco.model.DoubleMatchPlayers
import com.gmail.luizjmfilho.buraco.model.Player
import com.gmail.luizjmfilho.buraco.model.SingleMatchPlayers

@Database(
    entities = [Player::class, DoubleMatchPlayers::class, SingleMatchPlayers::class],
    version = 1
)
abstract class PlacarBuracoDatabase : RoomDatabase() {
    abstract fun playerDao(): PlayerDao
    abstract fun singleMatchPlayersDao(): SingleMatchPlayersDao
    abstract fun doubleMatchPlayersDao(): DoubleMatchPlayersDao
}