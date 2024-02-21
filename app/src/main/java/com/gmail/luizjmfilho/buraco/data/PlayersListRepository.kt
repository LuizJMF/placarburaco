package com.gmail.luizjmfilho.buraco.data

import com.gmail.luizjmfilho.buraco.model.Player
import com.gmail.luizjmfilho.buraco.ui.NameError
import javax.inject.Inject

data class AddPlayerResult(
    val nameError: NameError?,
)

class PlayersListRepository @Inject constructor(private val playerDao: PlayerDao) {

    suspend fun addPlayer(playerName: String): AddPlayerResult? {

        val nameWithoutSpace = playerName.trim()
        val nameError: NameError? = if (nameWithoutSpace == "") {
            NameError.Empty
        } else if (playerDao.numberOfPlayersWithThisName(nameWithoutSpace) > 0) {
            NameError.Exists
        } else {
            null
        }

        if (nameError == null) {
            playerDao.addPlayer(Player(playerName = nameWithoutSpace))
            return null
        } else {
            return AddPlayerResult(
                nameError = nameError
            )
        }
    }

    suspend fun readPlayer(): List<String> {
        return playerDao.readPlayer()
    }

//    suspend fun deletePlayer(playerName: String) {
//        playersRegisteredDao.deletePlayer(playerName)
//    }

}