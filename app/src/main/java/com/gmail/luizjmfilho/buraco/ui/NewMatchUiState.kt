package com.gmail.luizjmfilho.buraco.ui

data class NewMatchUiState(
    val singlePlayersList: List<String?> = listOf(null, null, null),
    val doublePlayersList: List<String?> = listOf(null, null, null, null),
    val matchType: MatchType = MatchType.Singles,
    val playersCheckedExists: Boolean = false,
    val navigateToMatchesList: Boolean = false,
)