package com.gmail.luizjmfilho.buraco.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.gmail.luizjmfilho.buraco.model.DoubleMaoHistory
import com.gmail.luizjmfilho.buraco.model.DoubleMatchPlayers
import com.gmail.luizjmfilho.buraco.model.DoubleNegaHistory
import com.gmail.luizjmfilho.buraco.model.DoublePernaHistory
import com.gmail.luizjmfilho.buraco.model.Player
import com.gmail.luizjmfilho.buraco.model.SingleMaoHistory
import com.gmail.luizjmfilho.buraco.model.SingleMatchPlayers
import com.gmail.luizjmfilho.buraco.model.SingleNegaHistory
import com.gmail.luizjmfilho.buraco.model.SinglePernaHistory

@Database(
    entities = [
        Player::class,
        DoubleMatchPlayers::class,
        SingleMatchPlayers::class,
        DoubleMaoHistory::class,
        DoubleNegaHistory::class,
        DoublePernaHistory::class,
        SingleMaoHistory::class,
        SingleNegaHistory::class,
        SinglePernaHistory::class,
    ],
    version = 3
)
abstract class PlacarBuracoDatabase : RoomDatabase() {
    abstract fun playerDao(): PlayerDao
    abstract fun singleMatchPlayersDao(): SingleMatchPlayersDao
    abstract fun doubleMatchPlayersDao(): DoubleMatchPlayersDao
    abstract fun doubleNegaHistoryDao(): DoubleNegaHistoryDao
    abstract fun doublePernaHistoryDao(): DoublePernaHistoryDao
    abstract fun doubleMaoHistoryDao(): DoubleMaoHistoryDao
    abstract fun singleNegaHistoryDao(): SingleNegaHistoryDao
    abstract fun singlePernaHistoryDao(): SinglePernaHistoryDao
    abstract fun singleMaoHistoryDao(): SingleMaoHistoryDao
}