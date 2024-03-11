package com.gmail.luizjmfilho.buraco.ui

data class MaoUiState(
    val matchType: MatchType = MatchType.Singles,
    val pernaNum: Int = 0,
    val playerNames: List<String> = emptyList(),
    val maoInfoList: List<MaoInfo> = emptyList(),
    val totalPoints: MaoInfo = MaoInfo(0,0,0, 0),
    val startingPlayer: String = "",
    val scoreTyped: String = "",
)

data class MaoInfo (
    val id: Int?,
    val pts1: Int,
    val pts2: Int,
    val pts3: Int,
)


