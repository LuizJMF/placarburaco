package com.gmail.luizjmfilho.buraco.ui

import com.gmail.luizjmfilho.buraco.model.DoubleMatchPlayers
import com.gmail.luizjmfilho.buraco.model.Player
import com.gmail.luizjmfilho.buraco.model.SingleMatchPlayers

data class MatchesListUiState(
    val doubleMatchList: List<DoubleMatchPlayers> = emptyList(),
    val singleMatchList: List<SingleMatchPlayers> = emptyList(),
    val playersList: List<Player> = emptyList()
)

enum class MatchType {
    Doubles,
    Singles,
}