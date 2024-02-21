package com.gmail.luizjmfilho.buraco.ui

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gmail.luizjmfilho.buraco.data.NewMatchRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewMatchViewModel @Inject constructor(
    private val newMatchRepository: NewMatchRepository,
    private val savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val playerInfo: String? = savedStateHandle.get<String>("playerInfo")
    private val matchTypeOnSelectMoment = if(playerInfo?.substring(1, 8) == MatchType.Singles.name) MatchType.Singles else MatchType.Doubles
    private val newPlayerList = playerInfo?.substring(8)?.split(",")?.map {
        if (it == "null") null else it
    }
    private val _uiState = MutableStateFlow(NewMatchUiState())
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            _uiState.update {  currentState ->
                if (playerInfo == null) {
                    currentState.copy()
                } else {
                    when (matchTypeOnSelectMoment) {
                        MatchType.Singles -> {
                            currentState.copy(
                                singlePlayersList = newPlayerList!!,
                                matchType = matchTypeOnSelectMoment
                            )
                        }
                        MatchType.Doubles -> {
                            currentState.copy(
                                doublePlayersList = newPlayerList!!,
                                matchType = matchTypeOnSelectMoment
                            )
                        }
                    }

                }
            }
        }
    }

    fun onToggleButtonClick() {
        viewModelScope.launch {
            _uiState.update {  currentState ->
                currentState.copy(
                    matchType = if (currentState.matchType == MatchType.Singles) MatchType.Doubles else MatchType.Singles,
                    singlePlayersList = listOf(null, null, null),
                    doublePlayersList = listOf(null, null, null, null),
                )
            }
        }
    }

    fun onCreateMatch(matchType: MatchType) {
        viewModelScope.launch {
            _uiState.update { currentState ->
                when (matchType) {

                    MatchType.Singles -> {
                        val singlePlayers = listOf(currentState.singlePlayersList[0]!!, currentState.singlePlayersList[1]!!, currentState.singlePlayersList[2]!!)
                        if (newMatchRepository.verifyIfSinglePlayersExist(singlePlayers)) {
                            currentState.copy(
                                playersCheckedExists = true,
                                navigateToMatchesList = false
                            )
                        } else {
                            newMatchRepository.addSinglePlayers(singlePlayers)
                            currentState.copy(
                                singlePlayersList = listOf(null, null, null),
                                playersCheckedExists = false,
                                navigateToMatchesList = true
                            )
                        }
                    }

                    MatchType.Doubles -> {
                        val doublePlayers = listOf(currentState.doublePlayersList[0]!!, currentState.doublePlayersList[1]!!, currentState.doublePlayersList[2]!!, currentState.doublePlayersList[3]!!)
                        if (newMatchRepository.verifyIfDoublePlayersExist(doublePlayers)) {
                            currentState.copy(
                                playersCheckedExists = true,
                                navigateToMatchesList = false
                            )
                        } else {
                            newMatchRepository.addDoublePlayers(doublePlayers)
                            currentState.copy(
                                doublePlayersList = listOf(null, null, null, null),
                                playersCheckedExists = false,
                                navigateToMatchesList = true
                            )
                        }
                    }
                }
            }
        }
    }
}