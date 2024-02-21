package com.gmail.luizjmfilho.buraco.ui

import com.gmail.luizjmfilho.buraco.model.DoubleMatchPlayers
import com.gmail.luizjmfilho.buraco.model.SingleMatchPlayers

data class MatchesListUiState(
    val doubleMatchList: List<DoubleMatchPlayers> = emptyList(),
    val singleMatchList: List<SingleMatchPlayers> = emptyList(),
)

enum class MatchType {
    Doubles,
    Singles,
}