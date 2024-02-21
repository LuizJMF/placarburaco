package com.gmail.luizjmfilho.buraco

import android.content.Context
import androidx.room.Room
import com.gmail.luizjmfilho.buraco.data.DoubleMatchPlayersDao
import com.gmail.luizjmfilho.buraco.data.PlacarBuracoDatabase
import com.gmail.luizjmfilho.buraco.data.PlayerDao
import com.gmail.luizjmfilho.buraco.data.SingleMatchPlayersDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {
    @Provides
    @Singleton
    fun createDatabase(@ApplicationContext context: Context): PlacarBuracoDatabase {
        return Room.databaseBuilder(context, PlacarBuracoDatabase::class.java, "PlacarBuraco.db").build()
    }

    @Provides
    fun createPlayerDao(database: PlacarBuracoDatabase): PlayerDao {
        return database.playerDao()
    }

    @Provides
    fun createSingleMatchPlayersDao(database: PlacarBuracoDatabase): SingleMatchPlayersDao {
        return database.singleMatchPlayersDao()
    }

    @Provides
    fun createDoubleMatchPlayersDao(database: PlacarBuracoDatabase): DoubleMatchPlayersDao {
        return database.doubleMatchPlayersDao()
    }
}