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

    suspend fun deleteDoubleMatch(match: DoubleMatchPlayers) {
        doubleMatchPlayersDao.deleteDoubleMatch(match.player1, match.player2, match.player3, match.player4)
    }

    suspend fun deleteSingleMatch(match: SingleMatchPlayers) {
        singleMatchPlayersDao.deleteSingleMatch(match.player1, match.player2, match.player3)
    }

}