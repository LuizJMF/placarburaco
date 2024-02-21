package com.gmail.luizjmfilho.buraco.data

import com.gmail.luizjmfilho.buraco.model.DoubleMatchPlayers
import com.gmail.luizjmfilho.buraco.model.SingleMatchPlayers
import javax.inject.Inject

class NewMatchRepository @Inject constructor(
    private val singleMatchPlayersDao: SingleMatchPlayersDao,
    private val doubleMatchPlayersDao: DoubleMatchPlayersDao,
) {

    suspend fun addSinglePlayers(players: List<String>) {
        singleMatchPlayersDao.addPlayers(
            SingleMatchPlayers(
                player1 = players[0],
                player2 = players[1],
                player3 = players[2],
            )
        )
    }

    suspend fun verifyIfSinglePlayersExist(players: List<String>): Boolean {
        val count = singleMatchPlayersDao.verifyIfPlayersExist(players)
        return (count == 1)
    }

    suspend fun addDoublePlayers(players: List<String>) {
        doubleMatchPlayersDao.addPlayers(
            DoubleMatchPlayers(
                player1 = players[0],
                player2 = players[1],
                player3 = players[2],
                player4 = players[3]
            )
        )
    }

    suspend fun verifyIfDoublePlayersExist(players: List<String>): Boolean {
        val count = doubleMatchPlayersDao.verifyIfPlayersExist(players[0], players[1], players[2], players[3], )
        return (count == 1)
    }

}