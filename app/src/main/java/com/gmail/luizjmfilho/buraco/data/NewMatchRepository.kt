package com.gmail.luizjmfilho.buraco.data

import com.gmail.luizjmfilho.buraco.model.DoubleMatchPlayers
import com.gmail.luizjmfilho.buraco.model.SingleMatchPlayers
import javax.inject.Inject

class NewMatchRepository @Inject constructor(
    private val singleMatchPlayersDao: SingleMatchPlayersDao,
    private val doubleMatchPlayersDao: DoubleMatchPlayersDao,
    private val playerDao: PlayerDao,
) {

    suspend fun addSinglePlayers(players: List<Int>) {
        singleMatchPlayersDao.addPlayers(
            SingleMatchPlayers(
                player1id = players[0],
                player2id = players[1],
                player3id = players[2],
            )
        )
    }

    suspend fun verifyIfSinglePlayersExist(players: List<Int>): Boolean {
        val count = singleMatchPlayersDao.verifyIfPlayersExist(players)
        return (count == 1)
    }

    suspend fun addDoublePlayers(players: List<Int>) {
        doubleMatchPlayersDao.addPlayers(
            DoubleMatchPlayers(
                player1id = players[0],
                player2id = players[1],
                player3id = players[2],
                player4id = players[3]
            )
        )
    }

    suspend fun verifyIfDoublePlayersExist(players: List<Int>): Boolean {
        val count = doubleMatchPlayersDao.verifyIfPlayersExist(players[0], players[1], players[2], players[3], )
        return (count == 1)
    }

    suspend fun getPlayerIdFromName(name: String) : Int {
        return playerDao.getPlayerIdFromName(name)
    }

}