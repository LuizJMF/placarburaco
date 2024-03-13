package com.gmail.luizjmfilho.buraco.data

import com.gmail.luizjmfilho.buraco.model.DoubleMaoHistory
import com.gmail.luizjmfilho.buraco.model.DoubleMatchPlayers
import com.gmail.luizjmfilho.buraco.model.DoublePernaHistory
import com.gmail.luizjmfilho.buraco.model.SingleMaoHistory
import com.gmail.luizjmfilho.buraco.model.SingleMatchPlayers
import com.gmail.luizjmfilho.buraco.model.SinglePernaHistory
import com.gmail.luizjmfilho.buraco.ui.PlayerPoints
import com.gmail.luizjmfilho.buraco.ui.TeamPoints
import javax.inject.Inject

class PernasSummaryRepository @Inject constructor(
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

    suspend fun getDoubleNegaNumberFromId(negaId: Int): Int {
        return doubleNegaHistoryDao.getDoubleNegaNumberFromId(negaId)
    }

    suspend fun getSingleNegaNumberFromId(negaId: Int): Int {
        return singleNegaHistoryDao.getSingleNegaNumberFromId(negaId)
    }

    suspend fun listDoublePernasIdOfNegaWhoseIdIs(negaId: Int): List<Int> {
        return doublePernaHistoryDao.listDoublePernasIdOfNegaWhoseIdIs(negaId)
    }

    suspend fun listSinglePernasIdOfNegaWhoseIdIs(negaId: Int): List<Int> {
        return singlePernaHistoryDao.listSinglePernasIdOfNegaWhoseIdIs(negaId)
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

    suspend fun getSingleGroupIdFromNegaId(negaId: Int): Int {
        return singleNegaHistoryDao.getSingleGroupIdFromNegaId(negaId)
    }

    suspend fun getDoubleGroupIdFromNegaId(negaId: Int): Int {
        return doubleNegaHistoryDao.getDoubleGroupIdFromNegaId(negaId)
    }

    suspend fun addSinglePerna(firstPlayerToPlay: String, negaId: Int, dateAndTime: String) {
        val pernaNum = singlePernaHistoryDao.getMaxSinglePernaNumber(negaId)!! + 1
        val newPerna = SinglePernaHistory(
            singleNegaId = negaId,
            singlePernaNumber = pernaNum
        )
        singlePernaHistoryDao.addSinglePerna(newPerna)

        val newMao = SingleMaoHistory(
            singlePernaId = singlePernaHistoryDao.getMaxSinglePernaIdFromNega(negaId)!!,
            singleMaoNumber = 1,
            ptsP1 = 0,
            ptsP2 = 0,
            ptsP3 = 0,
            whoStarts = playerDao.getPlayerIdFromName(firstPlayerToPlay),
            initialDate = dateAndTime,
        )
        singleMaoHistoryDao.addSingleMao(newMao)
    }

    suspend fun addDoublePerna(firstPlayerToPlay: String, negaId: Int, dateAndTime: String) {
        val pernaNum = doublePernaHistoryDao.getMaxDoublePernaNumber(negaId)!! + 1
        val newPerna = DoublePernaHistory(
            doubleNegaId = negaId,
            doublePernaNumber = pernaNum
        )
        doublePernaHistoryDao.addDoublePerna(newPerna)

        val newMao = DoubleMaoHistory(
            doublePernaId = doublePernaHistoryDao.getMaxDoublePernaIdFromNega(negaId)!!,
            doubleMaoNumber = 1,
            ptsTeam1 = 0,
            ptsTeam2 = 0,
            whoStarts = playerDao.getPlayerIdFromName(firstPlayerToPlay),
            initialDate = dateAndTime,
        )
        doubleMaoHistoryDao.addDoubleMao(newMao)
    }

    suspend fun isP2AfterP1FromNegaId(singleNegaId: Int): Boolean {
        return singleNegaHistoryDao.isP2AfterP1FromNegaId(singleNegaId)
    }

    suspend fun isP3AfterP1FromNegaId(singleNegaId: Int): Boolean {
        return doubleNegaHistoryDao.isP3AfterP1FromNegaId(singleNegaId)
    }


}