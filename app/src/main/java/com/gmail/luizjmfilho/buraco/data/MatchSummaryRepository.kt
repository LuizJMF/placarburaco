package com.gmail.luizjmfilho.buraco.data

import com.gmail.luizjmfilho.buraco.model.DoubleMaoHistory
import com.gmail.luizjmfilho.buraco.model.DoubleMatchPlayers
import com.gmail.luizjmfilho.buraco.model.DoubleNegaHistory
import com.gmail.luizjmfilho.buraco.model.DoublePernaHistory
import com.gmail.luizjmfilho.buraco.model.SingleMaoHistory
import com.gmail.luizjmfilho.buraco.model.SingleMatchPlayers
import com.gmail.luizjmfilho.buraco.model.SingleNegaHistory
import com.gmail.luizjmfilho.buraco.model.SinglePernaHistory
import com.gmail.luizjmfilho.buraco.ui.PlayerPoints
import com.gmail.luizjmfilho.buraco.ui.TeamPoints
import javax.inject.Inject

class MatchSummaryRepository @Inject constructor(
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

    suspend fun listDoublePernasIdOfNegaWhoseIdIs(negaId: Int): List<Int> {
        return doublePernaHistoryDao.listDoublePernasIdOfNegaWhoseIdIs(negaId)
    }

    suspend fun listSinglePernasIdOfNegaWhoseIdIs(negaId: Int): List<Int> {
        return singlePernaHistoryDao.listSinglePernasIdOfNegaWhoseIdIs(negaId)
    }

    suspend fun listDoubleNegasIdOfGroupWhoseIdIs(doubleGroupId: Int): List<Int> {
        return doubleNegaHistoryDao.listDoubleNegasIdOfGroupWhoseIdIs(doubleGroupId)
    }

    suspend fun listSingleNegasIdOfGroupWhoseIdIs(singleGroupId: Int): List<Int> {
        return singleNegaHistoryDao.listSingleNegasIdOfGroupWhoseIdIs(singleGroupId)
    }

    suspend fun sumTeamPointsForPernaWhoseIdIs(doublePernaId: Int): TeamPoints {
        return doubleMaoHistoryDao.sumTeamPointsForPernaWhoseIdIs(doublePernaId)
    }

    suspend fun sumPlayerPointsForPernaWhoseIdIs(singlePernaId: Int): PlayerPoints {
        return singleMaoHistoryDao.sumPlayerPointsForPernaWhoseIdIs(singlePernaId)
    }

    suspend fun getTheSingleGroupWhoseIdIs(singleGroupId: Int) : SingleMatchPlayers {
        return singleMatchPlayersDao.getTheSingleGroupWhoseIdIs(singleGroupId)
    }

    suspend fun getTheDoubleGroupWhoseIdIs(doubleGroupId: Int) : DoubleMatchPlayers {
        return doubleMatchPlayersDao.getTheDoubleGroupWhoseIdIs(doubleGroupId)
    }

    suspend fun getPlayerNameFromId(id: Int) : String {
        return playerDao.getPlayerNameFromId(id)
    }

    suspend fun addDoubleNega(groupId: Int, isNormalOrder: Boolean, firstPlayerToPlay: String, dateAndTime: String,) {
        val lastNegaNumber = doubleNegaHistoryDao.getMaxDoubleNegaNumber(groupId) ?: 0
        val doubleNegaMatch = DoubleNegaHistory(
            doubleGroupId = groupId,
            isP3AfterP1 = isNormalOrder,
            doubleNegaNumber = lastNegaNumber + 1
        )
        doubleNegaHistoryDao.addDoubleNega(doubleNegaMatch)
        val lastNegaId = doubleNegaHistoryDao.getMaxDoubleNegaId(groupId)

        val doublePerna = DoublePernaHistory(
            doubleNegaId = lastNegaId!!,
            doublePernaNumber = 1
        )
        doublePernaHistoryDao.addDoublePerna(doublePerna)

        val idOfFirstPlayerToPlay = playerDao.getPlayerIdFromName(firstPlayerToPlay)
        val pernaId = doublePernaHistoryDao.getDoublePernaIdFromNegaCreatedRightNow(lastNegaId)
        val doubleMao = DoubleMaoHistory(
            doublePernaId = pernaId!!,
            doubleMaoNumber = 1,
            ptsTeam1 = 0,
            ptsTeam2 = 0,
            whoStarts = idOfFirstPlayerToPlay,
            initialDate = dateAndTime
        )

        doubleMaoHistoryDao.addDoubleMao(doubleMao)
    }

    suspend fun addSingleNega(groupId: Int, isNormalOrder: Boolean, firstPlayerToPlay: String, dateAndTime: String) {
        val lastNegaNumber = singleNegaHistoryDao.getMaxSingleNegaNumber(groupId) ?: 0
        val singleNegaMatch = SingleNegaHistory(
            singleGroupId = groupId,
            isP2AfterP1 = isNormalOrder,
            singleNegaNumber = lastNegaNumber + 1
        )
        singleNegaHistoryDao.addSingleNega(singleNegaMatch)
        val lastNegaId = singleNegaHistoryDao.getMaxSingleNegaId(groupId)

        val singlePerna = SinglePernaHistory(
            singleNegaId = lastNegaId!!,
            singlePernaNumber = 1
        )
        singlePernaHistoryDao.addSinglePerna(singlePerna)

        val idOfFirstPlayerToPlay = playerDao.getPlayerIdFromName(firstPlayerToPlay)
        val pernaId = singlePernaHistoryDao.getSinglePernaIdFromNegaCreatedRightNow(lastNegaId)
        val singleMao = SingleMaoHistory(
            singlePernaId = pernaId!!,
            singleMaoNumber = 1,
            ptsP1 = 0,
            ptsP2 = 0,
            ptsP3 = 0,
            whoStarts = idOfFirstPlayerToPlay,
            initialDate = dateAndTime
        )

        singleMaoHistoryDao.addSingleMao(singleMao)
    }

}