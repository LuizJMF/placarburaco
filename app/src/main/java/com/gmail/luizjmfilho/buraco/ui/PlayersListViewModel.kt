package com.gmail.luizjmfilho.buraco.ui

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gmail.luizjmfilho.buraco.data.PlayersListRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlayersListViewModel @Inject constructor(
    private val playersListRepository: PlayersListRepository,
    private val savedStateHandle: SavedStateHandle,
) : ViewModel()  {

    private val originalInfo = savedStateHandle.get<String>("info")!!
    private val _uiState = MutableStateFlow(PlayersListUiState())
    val uiState: StateFlow<PlayersListUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            _uiState.update { currentState ->
                currentState.copy(
                    playersList = playersListRepository.readPlayer(),
                )
            }
        }
    }

    fun onAddPlayer() {
        viewModelScope.launch {
            _uiState.update { currentState ->
                val addPlayerResult = playersListRepository.addPlayer(currentState.playerBeingAdded)
                if (addPlayerResult == null) {
                    currentState.copy(
                        playerBeingAdded = "",
                        playersList = playersListRepository.readPlayer(),
                        nameError = null,
                    )
                } else {
                    currentState.copy(
                        nameError = addPlayerResult.nameError
                    )
                }
            }
        }
    }

    fun onTypeNewPlayer(playerName: String) {
        viewModelScope.launch {
            _uiState.update { currentState ->
                val newName = if (playerName.length <= 15) playerName else currentState.playerBeingAdded
                currentState.copy(
                    playerBeingAdded = newName,
                )
            }
        }
    }

    fun getInfo() : String {
        return originalInfo
    }

}