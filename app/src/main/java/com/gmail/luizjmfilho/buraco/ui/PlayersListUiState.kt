package com.gmail.luizjmfilho.buraco.ui

data class PlayersListUiState(
    val playersList: List<String> = emptyList(),
    val playerBeingAdded: String = "",
    val nameError: NameError? = null,
    val isDialogShown: Boolean = false,
)

enum class NameError {
    Empty,
    Exists,
}