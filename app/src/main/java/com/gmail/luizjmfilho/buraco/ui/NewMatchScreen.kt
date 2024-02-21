package com.gmail.luizjmfilho.buraco.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowRightAlt
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.gmail.luizjmfilho.buraco.R
import com.gmail.luizjmfilho.buraco.ui.theme.PlacarBuracoTheme

@Composable
fun NewMatchScreenPrimaria(
    modifier: Modifier = Modifier,
    onGoToMatchesList: () -> Unit,
    onChoosePlayer: (String) -> Unit,
    newMatchViewModel: NewMatchViewModel = hiltViewModel(),
) {
    val newMatchUiState by newMatchViewModel.uiState.collectAsState()

    if (newMatchUiState.navigateToMatchesList) {
        onGoToMatchesList()
    }

    NewMatchScreenSecundaria(
        newMatchUiState = newMatchUiState,
        onChoosePlayer = onChoosePlayer,
        onCreateMatch = {
           newMatchViewModel.onCreateMatch(it)
        },
        onToggleButtonClick = newMatchViewModel::onToggleButtonClick,
        onCancelClick = onGoToMatchesList,
        modifier = modifier,
    )
}

@Composable
fun NewMatchScreenSecundaria(
    newMatchUiState: NewMatchUiState,
    onChoosePlayer: (String) -> Unit,
    onCancelClick: () -> Unit,
    onCreateMatch: (MatchType) -> Unit,
    onToggleButtonClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        modifier = modifier,
    ) { scaffoldPaddings ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(scaffoldPaddings),
        ) {
            Column(
                modifier = Modifier
                    .padding(top = 20.dp)
                    .fillMaxSize()
            ) {
                ToggleButtonRow(
                    checked = (newMatchUiState.matchType == MatchType.Doubles),
                    onCheckedChange = { onToggleButtonClick() },
                    matchType = newMatchUiState.matchType
                )
                Divider(
                    modifier = Modifier
                        .padding(bottom = 15.dp)
                )
                if (newMatchUiState.matchType == MatchType.Singles) {
                    SinglesLayout(
                        playersList = newMatchUiState.singlePlayersList,
                        modifier = Modifier
                            .padding(start = 10.dp, end = 10.dp),
                        onChoosePlayer = { indexBeingChosen ->
                            onChoosePlayer(indexBeingChosen.toString() + newMatchUiState.matchType.name + newMatchUiState.singlePlayersList.joinToString(","))
                        }
                    )
                } else {
                    DoublesLayout(
                        playersList = newMatchUiState.doublePlayersList,
                        modifier = Modifier
                            .padding(start = 10.dp, end = 10.dp),
                        onChoosePlayer = { indexBeingChosen ->
                            onChoosePlayer(indexBeingChosen.toString() + newMatchUiState.matchType.name + newMatchUiState.doublePlayersList.joinToString(","))
                        }
                    )
                }
                AnimatedVisibility(
                    visible = newMatchUiState.playersCheckedExists
                ) {
                    Text(
                        text = stringResource(R.string.match_already_exists),
                        color = Color.Red,
                        fontStyle = FontStyle.Italic,
                        modifier = Modifier
                            .padding(top = 10.dp, bottom = 10.dp)
                            .fillMaxWidth()
                            .wrapContentWidth()
                    )
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 15.dp)
                ) {
                    Spacer(modifier = Modifier.weight(0.4f))
                    TextButton(
                        onClick = onCancelClick
                    ) {
                        Text(
                            text = stringResource(R.string.cancel),
                            color = Color.Red
                        )
                    }
                    Spacer(modifier = Modifier.weight(0.2f))
                    Button(
                        onClick = {
                            onCreateMatch(newMatchUiState.matchType)
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF021CC5)
                        ),
                        enabled = ((newMatchUiState.matchType == MatchType.Singles && newMatchUiState.singlePlayersList.none { it == null }) || (newMatchUiState.matchType == MatchType.Doubles && newMatchUiState.doublePlayersList.none { it == null }))
                    ) {
                        Text(text = stringResource(R.string.create_match))
                    }
                    Spacer(modifier = Modifier.weight(0.4f))
                }
            }

        }
    }
}

@Composable
fun DoublesLayout(
    playersList: List<String?>,
    onChoosePlayer: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        for ((index, player) in playersList.withIndex()) {
            if (index == 0 || index == 2) {
                DoubleChoosePlayerCard(
                    teamNumber = if (index == 0) 1 else 2,
                    firstPlayer = player,
                    secondPlayer = playersList[index + 1],
                    onChoosePlayer = { onChoosePlayer(index + it) }

                )
            }
        }
    }
}

@Composable
fun SinglesLayout(
    playersList: List<String?>,
    onChoosePlayer: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        for ((index, player) in playersList.withIndex()) {
            SingleChoosePlayerCard(
                playerNumber = index + 1,
                playerName = player,
                onChoosePlayer = { onChoosePlayer(index) }
            )
        }
    }
}

@Composable
fun DoubleChoosePlayerCard(
    teamNumber: Int,
    firstPlayer: String?,
    secondPlayer: String?,
    onChoosePlayer: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        elevation = CardDefaults.cardElevation(5.dp),
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFD4DBFF)
        )
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(start = 10.dp)
                .fillMaxWidth()
                .height(IntrinsicSize.Min)
        ) {
            Text(text = "Time $teamNumber")
            Icon(
                imageVector = Icons.Filled.ArrowRightAlt,
                contentDescription = null,
                tint = Color(0xFF021CC5),
                modifier = Modifier
                    .padding(start = 10.dp)
            )
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 20.dp, end = 10.dp)
            ) {
                TextButton(
                    onClick = { onChoosePlayer(0) },
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Text(
                        text = firstPlayer ?: stringResource(R.string.choose),
                        color = if (firstPlayer == null) Color(0xFF021CC5) else Color.Black
                    )
                }
                Divider()
                TextButton(
                    onClick = { onChoosePlayer(1) },
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Text(
                        text = secondPlayer ?: stringResource(R.string.choose),
                        color = if (secondPlayer == null) Color(0xFF021CC5) else Color.Black
                    )
                }
            }

        }
    }
}

@Composable
fun SingleChoosePlayerCard(
    playerNumber: Int,
    playerName: String?,
    onChoosePlayer: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        elevation = CardDefaults.cardElevation(5.dp),
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFD4DBFF)
        )
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(start = 10.dp)
                .fillMaxWidth()
                .height(IntrinsicSize.Min)
        ) {
            Text(text = "Jogador $playerNumber")
            Icon(
                imageVector = Icons.Filled.ArrowRightAlt,
                contentDescription = null,
                tint = Color(0xFF021CC5),
                modifier = Modifier
                    .padding(start = 10.dp)
            )
            TextButton(
                onClick = onChoosePlayer,
                modifier = Modifier
                    .weight(1f)
            ) {
                Text(
                    text = playerName ?: stringResource(R.string.choose),
                    color = if (playerName == null) Color(0xFF021CC5) else Color.Black
                )
            }
        }
    }
}

@Composable
fun ToggleButtonRow(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    matchType: MatchType,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
    ) {
        Text(
            text = stringResource(id = R.string.singles).uppercase(),
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .wrapContentWidth(),
            fontStyle = if (matchType == MatchType.Singles) FontStyle.Normal else FontStyle.Italic,
            fontWeight = if (matchType == MatchType.Singles) FontWeight.ExtraBold else FontWeight.Normal,
            color = if (matchType == MatchType.Singles) Color(0xFF021CC5) else Color.Gray
        )
        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange,
            colors = SwitchDefaults.colors(
                checkedTrackColor = Color(0xFFCACACA),
                checkedThumbColor = Color(0xFF021CC5),
                checkedBorderColor = Color(0xFF444444),
                uncheckedTrackColor = Color(0xFFCACACA),
                uncheckedThumbColor = Color(0xFF021CC5),
                uncheckedBorderColor = Color(0xFF444444),

                ),
            thumbContent = {
                if (matchType == MatchType.Singles) {
                    Icon(
                        imageVector = Icons.Filled.Person,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier
                            .size(15.dp)
                    )
                } else {
                    Icon(
                        imageVector = Icons.Filled.People,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier
                            .size(15.dp)
                    )
                }
            }
        )
        Text(
            text = stringResource(id = R.string.doubles).uppercase(),
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .wrapContentWidth(),
            fontStyle = if (matchType == MatchType.Doubles) FontStyle.Normal else FontStyle.Italic,
            fontWeight = if (matchType == MatchType.Doubles) FontWeight.ExtraBold else FontWeight.Normal,
            color = if (matchType == MatchType.Doubles) Color(0xFF021CC5) else Color.Gray
        )
    }
}

@Preview
@Composable
fun NewMatchScreenPreview() {
    PlacarBuracoTheme {
        NewMatchScreenSecundaria(
            newMatchUiState = NewMatchUiState(
                singlePlayersList = listOf("Gian", null, "Painho"),
                doublePlayersList = listOf("Zinho", "Gian", null, "Mainha")
            ),
            onChoosePlayer = {},
            onCreateMatch = {},
            onToggleButtonClick = {},
            onCancelClick = {}
        )
    }
}