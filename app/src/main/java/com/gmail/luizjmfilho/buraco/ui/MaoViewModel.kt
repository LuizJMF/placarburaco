package com.gmail.luizjmfilho.buraco.ui

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gmail.luizjmfilho.buraco.data.MaoRepository
import com.gmail.luizjmfilho.buraco.model.DoubleMaoHistory
import com.gmail.luizjmfilho.buraco.model.DoubleMatchPlayers
import com.gmail.luizjmfilho.buraco.model.SingleMaoHistory
import com.gmail.luizjmfilho.buraco.model.SingleMatchPlayers
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.text.DateFormat
import java.util.Calendar
import javax.inject.Inject

@HiltViewModel
class MaoViewModel @Inject constructor(
    private val maoRepository: MaoRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val pernaInfo = savedStateHandle.get<String>("pernaInfo")!!.split(",")
    private val matchType = MatchType.valueOf(pernaInfo[0])
    private val pernaId = pernaInfo[1].toInt()

    private val _uiState = MutableStateFlow(MaoUiState())
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            val pernaNum = when(matchType) {
                MatchType.Singles -> maoRepository.getSinglePernaNumberFromPernaId(pernaId)
                MatchType.Doubles -> maoRepository.getDoublePernaNumberFromPernaId(pernaId)
            }

            val playerNames = mutableListOf<String>()
            when (matchType) {
                MatchType.Singles -> {
                    val group = maoRepository.getTheSingleGroupWhoseIdIs(maoRepository.getSingleGroupIdFromNegaId(maoRepository.getSingleNegaIdFromPernaId(pernaId)))
                    playerNames.add(maoRepository.getPlayerNameFromId(group.player1id))
                    playerNames.add(maoRepository.getPlayerNameFromId(group.player2id))
                    playerNames.add(maoRepository.getPlayerNameFromId(group.player3id))
                }
                MatchType.Doubles -> {
                    val group = maoRepository.getTheDoubleGroupWhoseIdIs(maoRepository.getDoubleGroupIdFromNegaId(maoRepository.getDoubleNegaIdFromPernaId(pernaId)))
                    playerNames.add(maoRepository.getPlayerNameFromId(group.player1id))
                    playerNames.add(maoRepository.getPlayerNameFromId(group.player2id))
                    playerNames.add(maoRepository.getPlayerNameFromId(group.player3id))
                    playerNames.add(maoRepository.getPlayerNameFromId(group.player4id))
                }
            }

            _uiState.update {  currentState ->
                currentState.copy(
                    matchType = matchType,
                    pernaNum = pernaNum,
                    playerNames = playerNames,

                )
            }

            refreshMao(matchType, pernaId)
        }
    }

    private fun refreshMao(matchType: MatchType, pernaId: Int) {
        viewModelScope.launch {
            val woPoints = 20000

            val maoInfoList = when (matchType) {
                MatchType.Singles -> {
                    maoRepository.viewAllSingleMaosFromPernaId(pernaId)
                }

                MatchType.Doubles -> {
                    maoRepository.viewAllDoubleMaosFromPernaId(pernaId)
                }
            }

            val totalPoints: MaoInfo = when(matchType) {
                MatchType.Singles -> {
                    MaoInfo(
                        id = null,
                        pts1 = when {
                           maoInfoList.any { it.pts1 == woPoints} -> maoInfoList.sumOf { it.pts1 } - woPoints
                           maoInfoList.any { it.pts1 == -woPoints} -> maoInfoList.sumOf { it.pts1 } + woPoints
                           else -> maoInfoList.sumOf { it.pts1 }
                        },
                        pts2 = when {
                            maoInfoList.any { it.pts2 == woPoints} -> maoInfoList.sumOf { it.pts2 } - woPoints
                            maoInfoList.any { it.pts2 == -woPoints} -> maoInfoList.sumOf { it.pts2 } + woPoints
                            else -> maoInfoList.sumOf { it.pts2 }
                        },
                        pts3 = when {
                            maoInfoList.any { it.pts3 == woPoints} -> maoInfoList.sumOf { it.pts3 } - woPoints
                            maoInfoList.any { it.pts3 == -woPoints} -> maoInfoList.sumOf { it.pts3 } + woPoints
                            else -> maoInfoList.sumOf { it.pts3 }
                        },
                    )
                }

                MatchType.Doubles -> {
                    MaoInfo(
                        id = null,
                        pts1 = when {
                            maoInfoList.any { it.pts1 == woPoints} -> maoInfoList.sumOf { it.pts1 } - woPoints
                            maoInfoList.any { it.pts1 == -woPoints} -> maoInfoList.sumOf { it.pts1 } + woPoints
                            else -> maoInfoList.sumOf { it.pts1 }
                        },
                        pts2 = when {
                            maoInfoList.any { it.pts2 == woPoints} -> maoInfoList.sumOf { it.pts2 } - woPoints
                            maoInfoList.any { it.pts2 == -woPoints} -> maoInfoList.sumOf { it.pts2 } + woPoints
                            else -> maoInfoList.sumOf { it.pts2 }
                        },
                        pts3 = 0,
                    )
                }
            }

            val startingPlayer = when(matchType) {
                MatchType.Singles -> {
                    maoRepository.getPlayerNameFromId(maoRepository.getSingleStartingPlayerIdFromLastActiveMao(pernaId))
                }

                MatchType.Doubles -> {
                    maoRepository.getPlayerNameFromId(maoRepository.getDoubleStartingPlayerIdFromLastActiveMao(pernaId))
                }
            }

            _uiState.update { currentState ->
                currentState.copy(
                    maoInfoList = maoInfoList,
                    totalPoints = totalPoints,
                    startingPlayer = startingPlayer,
                )
            }
        }
    }

    fun onTypeScore(scoreTyped: String) {
        _uiState.update {  currentState ->
            val newScore = if (scoreTyped.length <= 4) scoreTyped else currentState.scoreTyped
            currentState.copy(
                scoreTyped = newScore
            )
        }
    }

    fun onConfirmTypeScoreDialog(playerOrNumberIndex: Int, maoNumber: Int) {
        viewModelScope.launch {
            _uiState.update {  currentState ->
                when (matchType) {
                    MatchType.Singles -> {
                        when (playerOrNumberIndex) {
                            0 -> maoRepository.updatePtsP1ForSingleMaoId(pernaId, currentState.scoreTyped.toInt(), maoNumber)
                            1 -> maoRepository.updatePtsP2ForSingleMaoId(pernaId, currentState.scoreTyped.toInt(), maoNumber)
                            else -> maoRepository.updatePtsP3ForSingleMaoId(pernaId, currentState.scoreTyped.toInt(), maoNumber)
                        }
                    }

                    MatchType.Doubles -> {
                        when (playerOrNumberIndex) {
                            0 -> maoRepository.updatePtsTeam1ForSingleMaoId(pernaId, currentState.scoreTyped.toInt(), maoNumber)
                            else -> maoRepository.updatePtsTeam2ForSingleMaoId(pernaId, currentState.scoreTyped.toInt(), maoNumber)

                        }
                    }
                }
                currentState.copy(
                    scoreTyped = ""
                )
            }
            refreshMao(matchType, pernaId)
        }
    }

    fun onDismissTypeScoreDialog() {
        _uiState.update {  currentState ->
            currentState.copy(
                scoreTyped = ""
            )
        }
    }

    fun getDateAndTime(): String {
        val calendar = Calendar.getInstance().time
        val dateFormat = DateFormat.getDateInstance(DateFormat.SHORT).format(calendar)
        val timeFormat = DateFormat.getTimeInstance(DateFormat.SHORT).format(calendar)

        return "$dateFormat - $timeFormat"

    }

    fun onCreateMao() {
        viewModelScope.launch {
            val lastMaoNumber = when(matchType) {
                MatchType.Singles -> maoRepository.getMaxSingleMaoNumber(pernaId)
                MatchType.Doubles -> maoRepository.getMaxDoubleMaoNumber(pernaId)
            }

            val idOrderList: List<Int> = when(matchType) {
                MatchType.Singles -> {
                    val negaId = maoRepository.getSingleNegaIdFromPernaId(pernaId)
                    val groupId = maoRepository.getSingleGroupIdFromNegaId(negaId)
                    val group = maoRepository.getTheSingleGroupWhoseIdIs(groupId)
                    val isNormalOrder = maoRepository.isP2AfterP1FromNegaId(negaId)
                    if (isNormalOrder) {
                        listOf(
                            group.player1id,
                            group.player2id,
                            group.player3id,
                        )
                    } else {
                        listOf(
                            group.player1id,
                            group.player3id,
                            group.player2id,
                        )
                    }
                }

                MatchType.Doubles -> {
                    val negaId = maoRepository.getDoubleNegaIdFromPernaId(pernaId)
                    val groupId = maoRepository.getDoubleGroupIdFromNegaId(negaId)
                    val group = maoRepository.getTheDoubleGroupWhoseIdIs(groupId)
                    val isNormalOrder = maoRepository.isP3AfterP1FromNegaId(negaId)
                    if (isNormalOrder) {
                        listOf(
                            group.player1id,
                            group.player3id,
                            group.player2id,
                            group.player4id,
                        )
                    } else {
                        listOf(
                            group.player1id,
                            group.player4id,
                            group.player2id,
                            group.player3id,
                        )
                    }
                }
            }

            val lastStartingPlayerId = when (matchType) {
                MatchType.Singles -> maoRepository.getSingleStartingPlayerIdFromLastActiveMao(pernaId)
                MatchType.Doubles -> maoRepository.getDoubleStartingPlayerIdFromLastActiveMao(pernaId)
            }
            val lastStartingPlayerIndex = idOrderList.indexOf(lastStartingPlayerId)
            val nextStartingPlayerIndex = (lastStartingPlayerIndex + 1) % idOrderList.size
            val nextStartingPlayerId = idOrderList[nextStartingPlayerIndex]

            when (matchType) {
                MatchType.Singles -> maoRepository.addSingleMao(
                    SingleMaoHistory(
                        singlePernaId = pernaId,
                        singleMaoNumber = lastMaoNumber + 1,
                        ptsP1 = 0,
                        ptsP2 = 0,
                        ptsP3 = 0,
                        whoStarts = nextStartingPlayerId,
                        initialDate = getDateAndTime()
                    )
                )
                MatchType.Doubles -> maoRepository.addDoubleMao(
                    DoubleMaoHistory(
                        doublePernaId = pernaId,
                        doubleMaoNumber = lastMaoNumber + 1,
                        ptsTeam1 = 0,
                        ptsTeam2 = 0,
                        whoStarts = nextStartingPlayerId,
                        initialDate = getDateAndTime()
                    )
                )
            }

            refreshMao(matchType, pernaId)
        }
    }

    fun onDeleteLastMao() {
        viewModelScope.launch {
            when (matchType) {
                MatchType.Singles -> { maoRepository.deleteLastSingleMaoFromPernaId(pernaId) }
                MatchType.Doubles -> { maoRepository.deleteLastDoubleMaoFromPernaId(pernaId) }
            }
            refreshMao(matchType, pernaId)
        }
    }

    fun onCreateWoMao(winnerPlayerByWo: String) {
        viewModelScope.launch {
            val woPoints = 20000

            val lastMaoNumber = when(matchType) {
                MatchType.Singles -> maoRepository.getMaxSingleMaoNumber(pernaId)
                MatchType.Doubles -> maoRepository.getMaxDoubleMaoNumber(pernaId)
            }

            val woWinnerId = maoRepository.getPlayerIdFromName(winnerPlayerByWo)

            val negaId = when(matchType) {
                MatchType.Singles -> maoRepository.getSingleNegaIdFromPernaId(pernaId)
                MatchType.Doubles -> maoRepository.getDoubleNegaIdFromPernaId(pernaId)
            }

            val groupId = when(matchType) {
                MatchType.Singles -> maoRepository.getSingleGroupIdFromNegaId(negaId)
                MatchType.Doubles -> maoRepository.getDoubleGroupIdFromNegaId(negaId)
            }

            val group = when(matchType) {
                MatchType.Singles -> maoRepository.getTheSingleGroupWhoseIdIs(groupId)
                MatchType.Doubles -> maoRepository.getTheDoubleGroupWhoseIdIs(groupId)
            }

            val isNormalOrder = when(matchType) {
                MatchType.Singles -> maoRepository.isP2AfterP1FromNegaId(negaId)
                MatchType.Doubles -> maoRepository.isP3AfterP1FromNegaId(negaId)
            }

            val idOrderList: List<Int> = when(matchType) {
                MatchType.Singles -> {
                    if (group is SingleMatchPlayers) {
                        if (isNormalOrder) {
                            listOf(
                                group.player1id,
                                group.player2id,
                                group.player3id,
                            )
                        } else {
                            listOf(
                                group.player1id,
                                group.player3id,
                                group.player2id,
                            )
                        }
                    } else listOf()
                }

                MatchType.Doubles -> {
                    if (group is DoubleMatchPlayers) {
                        if (isNormalOrder) {
                            listOf(
                                group.player1id,
                                group.player3id,
                                group.player2id,
                                group.player4id,
                            )
                        } else {
                            listOf(
                                group.player1id,
                                group.player4id,
                                group.player2id,
                                group.player3id,
                            )
                        }
                    } else listOf()
                }
            }

            val lastStartingPlayerId = when (matchType) {
                MatchType.Singles -> maoRepository.getSingleStartingPlayerIdFromLastActiveMao(pernaId)
                MatchType.Doubles -> maoRepository.getDoubleStartingPlayerIdFromLastActiveMao(pernaId)
            }
            val lastStartingPlayerIndex = idOrderList.indexOf(lastStartingPlayerId)
            val nextStartingPlayerIndex = (lastStartingPlayerIndex + 1) % idOrderList.size
            val nextStartingPlayerId = idOrderList[nextStartingPlayerIndex]

            val indexOfWoWinner = when (group) {
                is SingleMatchPlayers -> {
                    when (woWinnerId) {
                        group.player1id -> 0
                        group.player2id -> 1
                        else -> 2
                    }
                }
                is DoubleMatchPlayers -> {
                    when (woWinnerId) {
                        group.player1id -> 0
                        else -> 2
                    }
                }
                else -> 0 // em teoria esse caso não ocorrerá
            }



            when (matchType) {
                MatchType.Singles -> maoRepository.addSingleMao(
                    SingleMaoHistory(
                        singlePernaId = pernaId,
                        singleMaoNumber = lastMaoNumber + 1,
                        ptsP1 = if(indexOfWoWinner == 0) woPoints else -woPoints,
                        ptsP2 = if(indexOfWoWinner == 1) woPoints else -woPoints,
                        ptsP3 = if(indexOfWoWinner == 2) woPoints else -woPoints,
                        whoStarts = nextStartingPlayerId,
                        initialDate = getDateAndTime()
                    )
                )
                MatchType.Doubles -> maoRepository.addDoubleMao(
                    DoubleMaoHistory(
                        doublePernaId = pernaId,
                        doubleMaoNumber = lastMaoNumber + 1,
                        ptsTeam1 = if(indexOfWoWinner == 0) woPoints else -woPoints,
                        ptsTeam2 = if(indexOfWoWinner == 2) woPoints else -woPoints,
                        whoStarts = nextStartingPlayerId,
                        initialDate = getDateAndTime()
                    )
                )
            }

            refreshMao(matchType, pernaId)
        }
    }
}