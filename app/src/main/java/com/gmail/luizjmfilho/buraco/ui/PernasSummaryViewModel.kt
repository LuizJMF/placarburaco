package com.gmail.luizjmfilho.buraco.ui

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gmail.luizjmfilho.buraco.data.PernasSummaryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.text.DateFormat
import java.util.Calendar
import javax.inject.Inject

@HiltViewModel
class PernasSummaryViewModel @Inject constructor(
    private val pernasSummaryRepository: PernasSummaryRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val negaInfo = savedStateHandle.get<String>("negaInfo")!!.split(",")
    private val matchType = MatchType.valueOf(negaInfo[0])
    private val negaId = negaInfo[1].toInt()

    private val _uiState = MutableStateFlow(PernasSummaryUiState())
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            _uiState.update { currentState ->
                val playerNames = mutableListOf<String>()
                when (matchType) {
                    MatchType.Singles -> {
                        val group = pernasSummaryRepository.getTheSingleGroupWhoseIdIs(pernasSummaryRepository.getSingleGroupIdFromNegaId(negaId))
                        playerNames.add(pernasSummaryRepository.getPlayerNameFromId(group.player1id))
                        playerNames.add(pernasSummaryRepository.getPlayerNameFromId(group.player2id))
                        playerNames.add(pernasSummaryRepository.getPlayerNameFromId(group.player3id))
                    }
                    MatchType.Doubles -> {
                        val group = pernasSummaryRepository.getTheDoubleGroupWhoseIdIs(pernasSummaryRepository.getDoubleGroupIdFromNegaId(negaId))
                        playerNames.add(pernasSummaryRepository.getPlayerNameFromId(group.player1id))
                        playerNames.add(pernasSummaryRepository.getPlayerNameFromId(group.player2id))
                        playerNames.add(pernasSummaryRepository.getPlayerNameFromId(group.player3id))
                        playerNames.add(pernasSummaryRepository.getPlayerNameFromId(group.player4id))
                    }
                }
                currentState.copy(
                    negaNum = when(matchType) {
                        MatchType.Singles -> pernasSummaryRepository.getSingleNegaNumberFromId(negaId)
                        MatchType.Doubles -> pernasSummaryRepository.getDoubleNegaNumberFromId(negaId)
                    },
                    playerNames = playerNames
                )
            }

            refreshPernaSummary(matchType, negaId)

        }
    }

    private fun refreshPernaSummary(matchType: MatchType, negaId: Int) {
        viewModelScope.launch {
            val pernaInfoList: MutableList<PernaInfo> = mutableListOf()

            when (matchType) {
                MatchType.Singles -> {
                    val pernasIdPlayed = pernasSummaryRepository.listSinglePernasIdOfNegaWhoseIdIs(negaId)
                    for (pernaId in pernasIdPlayed) {
                        var pernaInfo = PernaInfo(pernaId,pernasIdPlayed.indexOf(negaId) + 1, MatchStatus.Underway, null)
                        val playerPoints = pernasSummaryRepository.sumPlayerPointsForPernaWhoseIdIs(pernaId)
                        val pernaStatus = if (with(playerPoints) { ptsP1 >= 3000 || ptsP2 >= 3000 || ptsP3 >= 3000 }) MatchStatus.Finished else MatchStatus.Underway
                        val pernaWinnerOrder = when(pernaStatus) {
                            MatchStatus.Finished -> {
                                when {
                                    playerPoints.ptsP1 > playerPoints.ptsP2 && playerPoints.ptsP1 > playerPoints.ptsP3 -> 0
                                    playerPoints.ptsP2 > playerPoints.ptsP1 && playerPoints.ptsP2 > playerPoints.ptsP3 -> 1
                                    else -> 2
                                }
                            }
                            MatchStatus.Underway -> null
                        }
                        val player =  if(pernaStatus == MatchStatus.Underway) null else {
                            val aux = pernasSummaryRepository.getTheSingleGroupWhoseIdIs(pernasSummaryRepository.getSingleGroupIdFromNegaId(negaId))
                            when (pernaWinnerOrder) {
                                0 -> pernasSummaryRepository.getPlayerNameFromId(aux.player1id)
                                1 -> pernasSummaryRepository.getPlayerNameFromId(aux.player2id)
                                else -> pernasSummaryRepository.getPlayerNameFromId(aux.player3id)
                            }
                        }
                        pernaInfo = pernaInfo.copy(status = pernaStatus, winner = player)
                        pernaInfoList.add(pernaInfo)
                    }
                }

                MatchType.Doubles -> {
                    val pernasIdPlayed = pernasSummaryRepository.listDoublePernasIdOfNegaWhoseIdIs(negaId)
                    for (pernaId in pernasIdPlayed) {
                        var pernaInfo = PernaInfo(pernaId,pernasIdPlayed.indexOf(negaId) + 1, MatchStatus.Underway, null)
                        val playerPoints = pernasSummaryRepository.sumTeamPointsForPernaWhoseIdIs(pernaId)
                        val pernaStatus = if (with(playerPoints) { ptsTeam1 >= 3000 || ptsTeam2 >= 3000}) MatchStatus.Finished else MatchStatus.Underway
                        val pernaWinnerOrder = when(pernaStatus) {
                            MatchStatus.Finished -> {
                                when {
                                    playerPoints.ptsTeam1 > playerPoints.ptsTeam2 -> 0
                                    else -> 1
                                }
                            }
                            MatchStatus.Underway -> null
                        }
                        val player =  if(pernaStatus == MatchStatus.Underway) null else {
                            val aux = pernasSummaryRepository.getTheDoubleGroupWhoseIdIs(pernasSummaryRepository.getDoubleGroupIdFromNegaId(negaId))
                            when (pernaWinnerOrder) {
                                0 -> "${
                                    pernasSummaryRepository.getPlayerNameFromId(aux.player1id)
                                } / ${
                                    pernasSummaryRepository.getPlayerNameFromId(aux.player2id)
                                }"
                                else -> "${
                                    pernasSummaryRepository.getPlayerNameFromId(aux.player3id)
                                } / ${
                                    pernasSummaryRepository.getPlayerNameFromId(aux.player4id)
                                }"
                            }
                        }
                        pernaInfo = pernaInfo.copy(status = pernaStatus, winner = player)
                        pernaInfoList.add(pernaInfo)
                    }
                }
            }
            _uiState.update { currentState ->
                currentState.copy(
                    matchType = matchType,
                    pernaInfoList = pernaInfoList,
                )
            }
        }
    }

    fun getDateAndTime(): String {
        val calendar = Calendar.getInstance().time
        val dateFormat = DateFormat.getDateInstance(DateFormat.SHORT).format(calendar)
        val timeFormat = DateFormat.getTimeInstance(DateFormat.SHORT).format(calendar)

        return "$dateFormat - $timeFormat"

    }

    fun onCreatePerna(firstPlayerToPlay: String) {
        viewModelScope.launch {
            val dateAndTime = getDateAndTime()
            when(matchType) {
                MatchType.Singles -> pernasSummaryRepository.addSinglePerna(firstPlayerToPlay, negaId, dateAndTime)
                MatchType.Doubles -> pernasSummaryRepository.addDoublePerna(firstPlayerToPlay, negaId, dateAndTime)
            }
            refreshPernaSummary(matchType, negaId)
        }
    }

}