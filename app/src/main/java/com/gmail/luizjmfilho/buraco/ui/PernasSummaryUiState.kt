package com.gmail.luizjmfilho.buraco.ui

data class PernasSummaryUiState(
    val matchType: MatchType = MatchType.Singles,
    val negaNum: Int = 0,
    val playerNames: List<String> = emptyList(),
    val pernaInfoList: List<PernaInfo> = emptyList(),
    val visualizationMode: VisualizationMode = VisualizationMode.InfoList,
    val playersOrder: List<String> = listOf()
)

data class PernaInfo(
    val id: Int,
    val num: Int,
    val status: MatchStatus,
    val winner: String?,
)

enum class VisualizationMode {
    Statistics,
    InfoList,
}
