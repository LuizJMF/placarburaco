package com.gmail.luizjmfilho.buraco.data

import com.gmail.luizjmfilho.buraco.model.DoubleMaoHistory
import com.gmail.luizjmfilho.buraco.model.DoubleMatchPlayers
import com.gmail.luizjmfilho.buraco.model.SingleMaoHistory
import com.gmail.luizjmfilho.buraco.model.SingleMatchPlayers
import com.gmail.luizjmfilho.buraco.ui.MaoInfo
import javax.inject.Inject

class MaoRepository @Inject constructor(
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

    suspend fun viewAllDoubleMaosFromPernaId(doublePernaId: Int): List<MaoInfo> {
        val allMaos = doubleMaoHistoryDao.viewAllDoubleMaosFromPernaId(doublePernaId)
        val resultList: MutableList<MaoInfo> = mutableListOf()
        for (mao in allMaos) {
            resultList.add(
                MaoInfo(
                    id = mao.doubleMaoId,
                    pts1 = mao.ptsTeam1,
                    pts2 = mao.ptsTeam2,
                    pts3 = 0,
                )
            )
        }

        return resultList
    }

    suspend fun viewAllSingleMaosFromPernaId(singlePernaId: Int): List<MaoInfo> {
        val allMaos = singleMaoHistoryDao.viewAllSingleMaosFromPernaId(singlePernaId)
        val resultList: MutableList<MaoInfo> = mutableListOf()
        for (mao in allMaos) {
            resultList.add(
                MaoInfo(
                    id = mao.singleMaoId,
                    pts1 = mao.ptsP1,
                    pts2 = mao.ptsP2,
                    pts3 = mao.ptsP3,
                )
            )
        }

        return resultList
    }

    suspend fun getSinglePernaNumberFromPernaId(singlePernaId: Int): Int {
        return singlePernaHistoryDao.getSinglePernaNumberFromPernaId(singlePernaId)
    }

    suspend fun getDoublePernaNumberFromPernaId(doublePernaId: Int): Int {
        return doublePernaHistoryDao.getDoublePernaNumberFromPernaId(doublePernaId)
    }

    suspend fun getDoubleNegaIdFromPernaId(doublePernaId: Int): Int {
        return doublePernaHistoryDao.getDoubleNegaIdFromPernaId(doublePernaId)
    }

    suspend fun getSingleNegaIdFromPernaId(singlePernaId: Int): Int {
        return singlePernaHistoryDao.getSingleNegaIdFromPernaId(singlePernaId)
    }

    suspend fun getSingleGroupIdFromNegaId(negaId: Int): Int {
        return singleNegaHistoryDao.getSingleGroupIdFromNegaId(negaId)
    }

    suspend fun getDoubleGroupIdFromNegaId(negaId: Int): Int {
        return doubleNegaHistoryDao.getDoubleGroupIdFromNegaId(negaId)
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

    suspend fun isP2AfterP1FromNegaId(singleNegaId: Int): Boolean {
        return singleNegaHistoryDao.isP2AfterP1FromNegaId(singleNegaId)
    }

    suspend fun isP3AfterP1FromNegaId(doubleNegaId: Int): Boolean {
        return doubleNegaHistoryDao.isP3AfterP1FromNegaId(doubleNegaId)
    }

    suspend fun getSingleStartingPlayerIdFromLastActiveMao(singlePernaId: Int): Int {
        return singleMaoHistoryDao.getSingleStartingPlayerIdFromLastActiveMao(singlePernaId)
    }

    suspend fun getDoubleStartingPlayerIdFromLastActiveMao(doublePernaId: Int): Int {
        return doubleMaoHistoryDao.getDoubleStartingPlayerIdFromLastActiveMao(doublePernaId)
    }

    suspend fun getMaxSingleMaoNumber(singlePernaId: Int): Int {
        return singleMaoHistoryDao.getMaxSingleMaoNumber(singlePernaId)
    }

    suspend fun getMaxDoubleMaoNumber(doublePernaId: Int): Int {
        return doubleMaoHistoryDao.getMaxDoubleMaoNumber(doublePernaId)
    }

    suspend fun addSingleMao(singleMaoHistory: SingleMaoHistory) {
        singleMaoHistoryDao.addSingleMao(singleMaoHistory)
    }

    suspend fun addDoubleMao(doubleMaoHistory: DoubleMaoHistory) {
        doubleMaoHistoryDao.addDoubleMao(doubleMaoHistory)
    }

    suspend fun updatePtsP1ForSingleMaoId(pernaId: Int, newPtsP1: Int, maoNumber: Int) {
        singleMaoHistoryDao.updatePtsP1ForSingleMaoId(pernaId, newPtsP1, maoNumber)
    }

    suspend fun updatePtsP2ForSingleMaoId(pernaId: Int, newPtsP2: Int, maoNumber: Int) {
        singleMaoHistoryDao.updatePtsP2ForSingleMaoId(pernaId, newPtsP2, maoNumber)
    }

    suspend fun updatePtsP3ForSingleMaoId(pernaId: Int, newPtsP3: Int, maoNumber: Int) {
        singleMaoHistoryDao.updatePtsP3ForSingleMaoId(pernaId, newPtsP3, maoNumber)
    }

    suspend fun updatePtsTeam1ForSingleMaoId(pernaId: Int, newPtsTeam1: Int, maoNumber: Int) {
        doubleMaoHistoryDao.updatePtsTeam1ForSingleMaoId(pernaId, newPtsTeam1, maoNumber)
    }

    suspend fun updatePtsTeam2ForSingleMaoId(pernaId: Int, newPtsTeam2: Int, maoNumber: Int) {
        doubleMaoHistoryDao.updatePtsTeam2ForSingleMaoId(pernaId, newPtsTeam2, maoNumber)
    }

    suspend fun getPlayerIdFromName(name: String) : Int {
        return playerDao.getPlayerIdFromName(name)
    }

    suspend fun deleteLastSingleMaoFromPernaId(singlePernaId: Int) {
        return singleMaoHistoryDao.deleteLastSingleMaoFromPernaId(singlePernaId)
    }

    suspend fun deleteLastDoubleMaoFromPernaId(doublePernaId: Int) {
        return doubleMaoHistoryDao.deleteLastDoubleMaoFromPernaId(doublePernaId)
    }

}