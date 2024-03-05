package com.gmail.luizjmfilho.buraco

import android.content.Context
import androidx.room.Room
import com.gmail.luizjmfilho.buraco.data.DoubleMaoHistoryDao
import com.gmail.luizjmfilho.buraco.data.DoubleMatchPlayersDao
import com.gmail.luizjmfilho.buraco.data.DoubleNegaHistoryDao
import com.gmail.luizjmfilho.buraco.data.DoublePernaHistoryDao
import com.gmail.luizjmfilho.buraco.data.PlacarBuracoDatabase
import com.gmail.luizjmfilho.buraco.data.PlayerDao
import com.gmail.luizjmfilho.buraco.data.SingleMaoHistoryDao
import com.gmail.luizjmfilho.buraco.data.SingleMatchPlayersDao
import com.gmail.luizjmfilho.buraco.data.SingleNegaHistoryDao
import com.gmail.luizjmfilho.buraco.data.SinglePernaHistoryDao
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

    @Provides
    fun createDoubleNegaHistoryDao(database: PlacarBuracoDatabase): DoubleNegaHistoryDao {
        return database.doubleNegaHistoryDao()
    }

    @Provides
    fun createDoublePernaHistoryDao(database: PlacarBuracoDatabase): DoublePernaHistoryDao {
        return database.doublePernaHistoryDao()
    }

    @Provides
    fun createDoubleMaoHistoryDao(database: PlacarBuracoDatabase): DoubleMaoHistoryDao {
        return database.doubleMaoHistoryDao()
    }

    @Provides
    fun createSingleNegaHistoryDao(database: PlacarBuracoDatabase): SingleNegaHistoryDao {
        return database.singleNegaHistoryDao()
    }

    @Provides
    fun createSinglePernaHistoryDao(database: PlacarBuracoDatabase): SinglePernaHistoryDao {
        return database.singlePernaHistoryDao()
    }

    @Provides
    fun createSingleMaoHistoryDao(database: PlacarBuracoDatabase): SingleMaoHistoryDao {
        return database.singleMaoHistoryDao()
    }
}