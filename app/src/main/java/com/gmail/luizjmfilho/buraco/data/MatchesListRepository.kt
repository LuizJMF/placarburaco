package com.gmail.luizjmfilho.buraco.data

import com.gmail.luizjmfilho.buraco.model.DoubleMatchPlayers
import com.gmail.luizjmfilho.buraco.model.Player
import com.gmail.luizjmfilho.buraco.model.SingleMatchPlayers
import javax.inject.Inject

class MatchesListRepository @Inject constructor(
    private val singleMatchPlayersDao: SingleMatchPlayersDao,
    private val doubleMatchPlayersDao: DoubleMatchPlayersDao,
    private val playerDao: PlayerDao,
    private val doubleNegaHistoryDao: DoubleNegaHistoryDao,
    private val doublePernaHistoryDao: DoublePernaHistoryDao,
    private val doubleMaoHistoryDao: DoubleMaoHistoryDao,
    private val singleNegaHistoryDao: SingleNegaHistoryDao,
    private val singlePernaHistoryDao: SinglePernaHistoryDao,
    private val singleMaoHistoryDao: SingleMaoHistoryDao,
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

    suspend fun onDeleteAllSingleNegaPernaMaoDerivedFromGroupId(groupId: Int) {
        val negasIdBeingDeleted = singleNegaHistoryDao.listSingleNegasIdOfGroupWhoseIdIs(groupId)
        for (negaIdBeingDeleted in negasIdBeingDeleted) {
            val pernasIdBeingDeleted = singlePernaHistoryDao.listSinglePernasIdOfNegaWhoseIdIs(negaIdBeingDeleted)
            for (pernaIdBeingDelete in pernasIdBeingDeleted) {
                singleMaoHistoryDao.deleteAllSingleMaoBasedOnPernaId(pernaIdBeingDelete)
            }
            singlePernaHistoryDao.deleteAllSinglePernasBasedOnNegaId(negaIdBeingDeleted)
        }
        singleNegaHistoryDao.deleteAllSingleNegasBasedOnGroupId(groupId)
    }

    suspend fun onDeleteAllDoubleNegaPernaMaoDerivedFromGroupId(groupId: Int) {
        val negasIdBeingDeleted = doubleNegaHistoryDao.listDoubleNegasIdOfGroupWhoseIdIs(groupId)
        for (negaIdBeingDeleted in negasIdBeingDeleted) {
            val pernasIdBeingDeleted = doublePernaHistoryDao.listDoublePernasIdOfNegaWhoseIdIs(negaIdBeingDeleted)
            for (pernaIdBeingDelete in pernasIdBeingDeleted) {
                doubleMaoHistoryDao.deleteAllDoubleMaoBasedOnPernaId(pernaIdBeingDelete)
            }
            doublePernaHistoryDao.deleteAllDoublePernasBasedOnNegaId(negaIdBeingDeleted)
        }
        doubleNegaHistoryDao.deleteAllDoubleNegasBasedOnGroupId(groupId)
    }
}