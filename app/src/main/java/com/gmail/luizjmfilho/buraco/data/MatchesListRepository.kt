package com.gmail.luizjmfilho.buraco.data

import com.gmail.luizjmfilho.buraco.model.DoubleMatchPlayers
import com.gmail.luizjmfilho.buraco.model.Player
import com.gmail.luizjmfilho.buraco.model.SingleMatchPlayers
import javax.inject.Inject

class MatchesListRepository @Inject constructor(
    private val singleMatchPlayersDao: SingleMatchPlayersDao,
    private val doubleMatchPlayersDao: DoubleMatchPlayersDao,
    private val playerDao: PlayerDao,
) {

    suspend fun viewAllSinglesMatches() : List<SingleMatchPlayers> {
        return singleMatchPlayersDao.viewAllSingleMatches()
    }

    suspend fun viewAllDoublesMatches() : List<DoubleMatchPlayers> {
        return doubleMatchPlayersDao.viewAllDoubleMatches()
    }

    suspend fun deleteDoubleMatch(match: DoubleMatchPlayers) {
        doubleMatchPlayersDao.deleteDoubleMatch(match.player1id, match.player2id, match.player3id, match.player4id)
    }

    suspend fun deleteSingleMatch(match: SingleMatchPlayers) {
        singleMatchPlayersDao.deleteSingleMatch(match.player1id, match.player2id, match.player3id)
    }

    suspend fun viewAllPlayersRegistered() : List<Player> {
        return playerDao.viewAllPlayersRegistered()
    }

}