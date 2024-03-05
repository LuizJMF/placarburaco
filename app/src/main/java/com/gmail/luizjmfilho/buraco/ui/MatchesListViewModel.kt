package com.gmail.luizjmfilho.buraco.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gmail.luizjmfilho.buraco.data.MatchesListRepository
import com.gmail.luizjmfilho.buraco.model.DoubleMatchPlayers
import com.gmail.luizjmfilho.buraco.model.SingleMatchPlayers
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MatchesListViewModel @Inject constructor(
    private val matchesListRepository: MatchesListRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(MatchesListUiState())
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            _uiState.update {  currenteState ->
                currenteState.copy(
                    doubleMatchList = matchesListRepository.viewAllDoublesMatches(),
                    singleMatchList = matchesListRepository.viewAllSinglesMatches(),
                    playersList = matchesListRepository.viewAllPlayersRegistered()
                )
            }
        }
    }

    fun onDeleteDoubleMatch(match: DoubleMatchPlayers) {
        viewModelScope.launch {
            _uiState.update {  currentState ->
                matchesListRepository.deleteDoubleMatch(match)
                currentState.copy(
                    doubleMatchList = matchesListRepository.viewAllDoublesMatches()
                )
            }
        }
    }

    fun onDeleteSingleMatch(match: SingleMatchPlayers) {
        viewModelScope.launch {
            _uiState.update {  currentState ->
                matchesListRepository.deleteSingleMatch(match)
                currentState.copy(
                    singleMatchList = matchesListRepository.viewAllSinglesMatches()
                )
            }
        }
    }

    fun onDeleteAllSingleNegaPernaMaoDerivedFromGroupId(groupId: Int) {
        viewModelScope.launch {
            matchesListRepository.onDeleteAllSingleNegaPernaMaoDerivedFromGroupId(groupId)
        }
    }

    fun onDeleteAllDoubleNegaPernaMaoDerivedFromGroupId(groupId: Int) {
        viewModelScope.launch {
            matchesListRepository.onDeleteAllDoubleNegaPernaMaoDerivedFromGroupId(groupId)
        }
    }
}