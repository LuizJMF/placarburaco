package com.gmail.luizjmfilho.buraco.data

import com.gmail.luizjmfilho.buraco.model.DoubleMatchPlayers
import com.gmail.luizjmfilho.buraco.model.SingleMatchPlayers
import javax.inject.Inject

class MatchesListRepository @Inject constructor(
    private val singleMatchPlayersDao: SingleMatchPlayersDao,
    private val doubleMatchPlayersDao: DoubleMatchPlayersDao,
) {

    suspend fun viewAllSinglesMatches() : List<SingleMatchPlayers> {
        return singleMatchPlayersDao.viewAllSingleMatches()
    }

    suspend fun viewAllDoublesMatches() : List<DoubleMatchPlayers> {
        return doubleMatchPlayersDao.viewAllDoubleMatches()
    }

}