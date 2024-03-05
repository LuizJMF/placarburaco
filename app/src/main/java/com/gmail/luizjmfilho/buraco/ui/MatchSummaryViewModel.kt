package com.gmail.luizjmfilho.buraco.ui

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gmail.luizjmfilho.buraco.data.MatchSummaryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.text.DateFormat
import java.util.Calendar
import javax.inject.Inject

@HiltViewModel
class MatchSummaryViewModel @Inject constructor(
    private val matchSummaryRepository: MatchSummaryRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val groupInfo = savedStateHandle.get<String>("groupInfo")!!.split(",")
    private val matchType = MatchType.valueOf(groupInfo[0])
    private val groupId = groupInfo[1].toInt()

    private val _uiState = MutableStateFlow(MatchSummaryUiState())
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            val playerNames = mutableListOf<String>()
            when (matchType) {
                MatchType.Singles -> {
                    val group = matchSummaryRepository.getTheSingleGroupWhoseIdIs(groupId)
                    playerNames.add(matchSummaryRepository.getPlayerNameFromId(group.player1id))
                    playerNames.add(matchSummaryRepository.getPlayerNameFromId(group.player2id))
                    playerNames.add(matchSummaryRepository.getPlayerNameFromId(group.player3id))
                }
                MatchType.Doubles -> {
                    val group = matchSummaryRepository.getTheDoubleGroupWhoseIdIs(groupId)
                    playerNames.add(matchSummaryRepository.getPlayerNameFromId(group.player1id))
                    playerNames.add(matchSummaryRepository.getPlayerNameFromId(group.player2id))
                    playerNames.add(matchSummaryRepository.getPlayerNameFromId(group.player3id))
                    playerNames.add(matchSummaryRepository.getPlayerNameFromId(group.player4id))
                }
            }
            _uiState.update { currentState ->
                currentState.copy(
                    playerNames = playerNames
                )
            }
            refreshNegaSummary(matchType, groupId)

        }
    }

    private fun refreshNegaSummary(matchType: MatchType, groupId: Int) {
        viewModelScope.launch {
            val negaInfoList: MutableList<NegaInfo> = mutableListOf()

            when (matchType) {
                MatchType.Singles -> {
                    val negasIdPlayed = matchSummaryRepository.listSingleNegasIdOfGroupWhoseIdIs(groupId)
                    for (negaId in negasIdPlayed) {
                        var negaInfo = NegaInfo(negasIdPlayed.indexOf(negaId) + 1, MatchStatus.Underway, null)
                        val pernasIdPlayed = matchSummaryRepository.listSinglePernasIdOfNegaWhoseIdIs(negaId)
                        val pairOfPernas = mutableListOf<Pair<MatchStatus, Int?>>()
                        for (pernaId in pernasIdPlayed) {
                            val playerPoints = matchSummaryRepository.sumPlayerPointsForPernaWhoseIdIs(pernaId)

                            val pernaStatus = if (with(playerPoints) { ptsP1 >= 3000 || ptsP2 >= 3000 || ptsP3 >= 3000 }) MatchStatus.Finished else MatchStatus.Underway
                            val pernaWinnerOrder = if (with(playerPoints) { ptsP1 >= 3000 || ptsP2 >= 3000 || ptsP3 >= 3000 }) {
                                when {
                                    playerPoints.ptsP1 > playerPoints.ptsP2 && playerPoints.ptsP1 > playerPoints.ptsP3 -> 0
                                    playerPoints.ptsP2 > playerPoints.ptsP1 && playerPoints.ptsP2 > playerPoints.ptsP3 -> 1
                                    else -> 2
                                }
                            } else null
                            val pernaInfo = Pair(pernaStatus, pernaWinnerOrder)
                            pairOfPernas.add(pernaInfo)
                        }
                        if (pairOfPernas.all { it.first != MatchStatus.Underway } && pairOfPernas.groupBy { it.second }.any { it.value.size == 2 }) {
                            val par = pairOfPernas.groupBy { it.second }.filter { it.value.size == 2 }
                            val player = when (par.keys.first()) {
                                0 -> matchSummaryRepository.getPlayerNameFromId(matchSummaryRepository.getTheSingleGroupWhoseIdIs(groupId).player1id)
                                1 -> matchSummaryRepository.getPlayerNameFromId(matchSummaryRepository.getTheSingleGroupWhoseIdIs(groupId).player2id)
                                else -> matchSummaryRepository.getPlayerNameFromId(matchSummaryRepository.getTheSingleGroupWhoseIdIs(groupId).player3id)
                            }
                            negaInfo = negaInfo.copy(status = MatchStatus.Finished, winner = player)

                        }
                        negaInfoList.add(negaInfo)
                    }
                }

                MatchType.Doubles -> {
                    val negasIdPlayed = matchSummaryRepository.listDoubleNegasIdOfGroupWhoseIdIs(groupId)
                    for (negaId in negasIdPlayed) {
                        var negaInfo = NegaInfo(negasIdPlayed.indexOf(negaId) + 1, MatchStatus.Underway, null)
                        val pernasIdPlayed = matchSummaryRepository.listDoublePernasIdOfNegaWhoseIdIs(negaId)
                        val pairOfPernas = mutableListOf<Pair<MatchStatus, Int?>>()
                        for (pernaId in pernasIdPlayed) {
                            val teamPoints = matchSummaryRepository.sumTeamPointsForPernaWhoseIdIs(pernaId)

                            val pernaStatus = if (with(teamPoints) { ptsTeam1 >= 3000 || ptsTeam2 >= 3000}) MatchStatus.Finished else MatchStatus.Underway
                            val pernaWinnerOrder = if (with(teamPoints) { ptsTeam1 >= 3000 || ptsTeam2 >= 3000}) {
                                when {
                                    teamPoints.ptsTeam1 > teamPoints.ptsTeam2 -> 0
                                    else -> 1
                                }
                            } else null
                            val pernaInfo = Pair(pernaStatus, pernaWinnerOrder)
                            pairOfPernas.add(pernaInfo)
                        }
                        if (pairOfPernas.all { it.first != MatchStatus.Underway } && pairOfPernas.groupBy { it.second }.any { it.value.size == 2 }) {
                            val par = pairOfPernas.groupBy { it.second }.filter { it.value.size == 2 }
                            val player = when (par.keys.first()) {
                                0 -> "${
                                    matchSummaryRepository.getPlayerNameFromId(
                                        matchSummaryRepository.getTheDoubleGroupWhoseIdIs(groupId).player1id
                                    )
                                } / ${
                                    matchSummaryRepository.getPlayerNameFromId(
                                        matchSummaryRepository.getTheDoubleGroupWhoseIdIs(groupId).player2id
                                    )
                                }"
                                else -> "${
                                    matchSummaryRepository.getPlayerNameFromId(
                                        matchSummaryRepository.getTheDoubleGroupWhoseIdIs(groupId).player3id
                                    )
                                } / ${
                                    matchSummaryRepository.getPlayerNameFromId(
                                        matchSummaryRepository.getTheDoubleGroupWhoseIdIs(groupId).player4id
                                    )
                                }"
                            }
                            negaInfo = negaInfo.copy(status = MatchStatus.Finished, winner = player)

                        }
                        negaInfoList.add(negaInfo)
                    }
                }
            }
            _uiState.update { currentState ->
                currentState.copy(
                    matchType = matchType,
                    negaInfoList = negaInfoList,
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

    fun onCreateNega(firstPlayerToPlay: String, isNormalOrder: Boolean) {
        viewModelScope.launch {
            val dateAndTime = getDateAndTime()
            when (matchType) {
                    MatchType.Doubles -> matchSummaryRepository.addDoubleNega(groupId, isNormalOrder, firstPlayerToPlay, dateAndTime)
                    MatchType.Singles -> matchSummaryRepository.addSingleNega(groupId, isNormalOrder, firstPlayerToPlay, dateAndTime)
            }
            refreshNegaSummary(matchType, groupId)
        }
    }

}