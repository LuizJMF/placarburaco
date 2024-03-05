package com.gmail.luizjmfilho.buraco.ui

import com.gmail.luizjmfilho.buraco.model.DoubleMaoHistory
import com.gmail.luizjmfilho.buraco.model.DoubleMatchPlayers
import com.gmail.luizjmfilho.buraco.model.DoubleNegaHistory
import com.gmail.luizjmfilho.buraco.model.DoublePernaHistory
import com.gmail.luizjmfilho.buraco.model.Player
import com.gmail.luizjmfilho.buraco.model.SingleMaoHistory
import com.gmail.luizjmfilho.buraco.model.SingleMatchPlayers
import com.gmail.luizjmfilho.buraco.model.SingleNegaHistory
import com.gmail.luizjmfilho.buraco.model.SinglePernaHistory

data class MatchSummaryUiState(
    val matchType: MatchType = MatchType.Singles,
    val playerNames: List<String> = emptyList(),
    val negaInfoList: List<NegaInfo> = emptyList(),

)

data class NegaInfo(
    val num: Int,
    val status: MatchStatus,
    val winner: String?,
)

data class TeamPoints(
    val ptsTeam1: Int,
    val ptsTeam2: Int,
)

data class PlayerPoints(
    val ptsP1: Int,
    val ptsP2: Int,
    val ptsP3: Int,
)

enum class MatchStatus {
    Underway,
    Finished,
}