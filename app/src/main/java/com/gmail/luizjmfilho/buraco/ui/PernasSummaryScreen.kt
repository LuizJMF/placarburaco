package com.gmail.luizjmfilho.buraco.ui


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowRight
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.FormatListNumbered
import androidx.compose.material.icons.filled.Leaderboard
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.gmail.luizjmfilho.buraco.R
import com.gmail.luizjmfilho.buraco.ui.theme.PlacarBuracoTheme

@Composable
fun PernasSummaryScreenPrimaria(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit,
    onGoToPerna: (MatchType, Int) -> Unit,
    pernasSummaryViewModel: PernasSummaryViewModel = hiltViewModel(),
) {

    val pernasSummaryUiState by pernasSummaryViewModel.uiState.collectAsState()

    PernasSummaryScreenSecundaria(
        pernasSummaryUiState = pernasSummaryUiState,
        onBackClick = onBackClick,
        onCreatePerna = pernasSummaryViewModel::onCreatePerna,
        onGoToPerna = onGoToPerna,
        modifier = modifier,
    )
}

@Composable
fun PernasSummaryScreenSecundaria(
    pernasSummaryUiState: PernasSummaryUiState,
    onCreatePerna: (String) -> Unit,
    onBackClick: () -> Unit,
    onGoToPerna: (MatchType, Int) -> Unit,
    modifier: Modifier = Modifier,
) {

    var isDialogShown by rememberSaveable { mutableStateOf(false) }
    val isFabEnabled = when(pernasSummaryUiState.matchType) {
        MatchType.Singles -> (pernasSummaryUiState.pernaInfoList.size < 4)
        MatchType.Doubles -> (pernasSummaryUiState.pernaInfoList.size < 3)

    }

    Scaffold(
        modifier = modifier,
        topBar = {
            DefaultTopBar(
                onBackClick = onBackClick,
                title = "Nega ${pernasSummaryUiState.negaNum}",
                icon = when(pernasSummaryUiState.matchType) {
                    MatchType.Singles -> Icons.Filled.Person
                    MatchType.Doubles -> Icons.Filled.People
                },
                containerColor = Color(0xFF1B6F1E),
                textColor = Color.White
            )
        },
        floatingActionButtonPosition = FabPosition.Center,
        bottomBar = {
            BottomAppBar(
                containerColor = Color(0xFFA4FF94),
                modifier = Modifier
                    .height(70.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxHeight()
                        .padding(bottom = 5.dp)
                ) {
                    Spacer(modifier = Modifier.weight(1f))
                    FloatingActionButton(
                        onClick = {
                            if (isFabEnabled) {
                                isDialogShown = true
                            }
                        },
                        containerColor = if(isFabEnabled) Color(0xFF1B6F1E) else Color(0xFFB8B8B8),
                        contentColor = Color.White,
                        ) {
                        Icon(
                            imageVector = Icons.Filled.Add,
                            contentDescription = null
                        )
                    }
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
        }
    ) { scaffoldPaddings ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(scaffoldPaddings),
        ) {
            Column {
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xFFA4FF94),
                    ),
                    shape = RectangleShape,
                ) {
                    Row(
                        modifier = Modifier
                            .padding(5.dp)
                            .fillMaxWidth()
                            .wrapContentWidth()
                            .height(22.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        if (pernasSummaryUiState.playerNames.isEmpty()) {
                            Text(text = "-")
                        } else {
                            when (pernasSummaryUiState.matchType) {
                                MatchType.Singles -> {
                                    Text(
                                        text = pernasSummaryUiState.playerNames[0],
                                        overflow = TextOverflow.Ellipsis,
                                        modifier = Modifier
                                            .weight(1f)
                                            .wrapContentWidth()
                                    )
                                    VersusText()
                                    Text(
                                        text = pernasSummaryUiState.playerNames[1],
                                        overflow = TextOverflow.Ellipsis,
                                        modifier = Modifier
                                            .weight(1f)
                                            .wrapContentWidth()
                                    )
                                    VersusText()
                                    Text(
                                        text = pernasSummaryUiState.playerNames[2],
                                        overflow = TextOverflow.Ellipsis,
                                        modifier = Modifier
                                            .weight(1f)
                                            .wrapContentWidth()
                                    )
                                }

                                MatchType.Doubles -> {

                                    Row(
                                        modifier = Modifier
                                            .weight(1f)
                                            .wrapContentWidth()
                                    ) {
                                        Text(text = pernasSummaryUiState.playerNames[0])
                                        Text(
                                            text = "/",
                                            modifier = Modifier
                                                .padding(start = 5.dp, end = 5.dp),
                                            color = Color(0xFF555555),
                                        )
                                        Text(text = pernasSummaryUiState.playerNames[1])
                                    }
                                    VersusText()
                                    Row(
                                        modifier = Modifier
                                            .weight(1f)
                                            .wrapContentWidth()
                                    ) {
                                        Text(text = pernasSummaryUiState.playerNames[2])
                                        Text(
                                            text = "/",
                                            modifier = Modifier
                                                .padding(start = 5.dp, end = 5.dp),
                                            color = Color(0xFF555555),
                                        )
                                        Text(text = pernasSummaryUiState.playerNames[3])
                                    }
                                }
                            }
                        }
                    }
                }
                Column(
                    modifier = Modifier
                        .padding(start = 10.dp, end = 10.dp, top = 20.dp),
                    verticalArrangement = Arrangement.spacedBy(5.dp)
                ) {
                    Row {
                        Text(
                            text = stringResource(R.string.perna),
                            modifier = Modifier
                                .weight(0.12f)
                                .wrapContentWidth(),
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = stringResource(R.string.status),
                            modifier = Modifier
                                .weight(0.18f)
                                .wrapContentWidth(),
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = pluralStringResource(
                                id = R.plurals.winner,
                                count = if (pernasSummaryUiState.matchType == MatchType.Singles) 1 else 2
                            ),
                            modifier = Modifier
                                .weight(0.5f)
                                .wrapContentWidth(),
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.width(48.dp))
                    }
                    Divider()
                    Column(
                        modifier = Modifier
                            .verticalScroll(rememberScrollState())
                    ) {
                        if (pernasSummaryUiState.pernaInfoList.isNotEmpty()) {
                            pernasSummaryUiState.pernaInfoList.forEachIndexed { index, pernaInfo ->
                                Row(
                                    modifier = Modifier
                                        .height(48.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = (index + 1).toString(),
                                        modifier = Modifier
                                            .weight(0.12f)
                                            .wrapContentWidth()
                                    )
                                    Icon(
                                        imageVector = if (pernaInfo.status == MatchStatus.Finished) Icons.Filled.Done else Icons.Filled.AccessTime,
                                        contentDescription = null,
                                        modifier = Modifier
                                            .weight(0.18f)
                                            .wrapContentWidth(),
                                        tint = if (pernaInfo.status == MatchStatus.Finished) Color(
                                            0xFF4CAF50
                                        ) else Color(0xFFFFC107),
                                    )
                                    Text(
                                        text = pernaInfo.winner ?: "-",
                                        modifier = Modifier
                                            .weight(0.5f)
                                            .wrapContentWidth(),
                                        textAlign = TextAlign.Center
                                    )
                                    IconButton(
                                        onClick = {
                                            onGoToPerna(
                                                pernasSummaryUiState.matchType,
                                                pernaInfo.id
                                            )
                                        }
                                    ) {
                                        Icon(
                                            imageVector = Icons.Filled.ArrowRight,
                                            contentDescription = null
                                        )
                                    }
                                }
                                Divider()
                            }
                        }
                    }
                }
            }
            if (isDialogShown) {
                NewPernaDialog(
                    playerNames = pernasSummaryUiState.playerNames,
                    onDismissRequest = { isDialogShown = false },
                    onConfirmClick = { firstPlayerToPlay ->
                        onCreatePerna(firstPlayerToPlay)
                        isDialogShown = false
                    }
                )
            }
        }
    }
}

@Composable
fun NewPernaDialog(
    playerNames: List<String>,
    onDismissRequest: () -> Unit,
    onConfirmClick: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    var selectedName: String? by rememberSaveable { mutableStateOf(null) }

    AlertDialog(
        onDismissRequest = onDismissRequest,
        title = { Text(text = stringResource(R.string.new_perna)) },
        text = {
            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
            ) {
                Text(
                    text = stringResource(R.string.which_player_starts),
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF021CC5),
                )
                playerNames.forEach { person ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .selectable(
                                selected = (selectedName == person),
                                onClick = {
                                    selectedName = person
                                }
                            )
                            .padding(horizontal = 16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = (selectedName == person),
                            onClick = {
                                selectedName = person
                            },
                            colors = RadioButtonDefaults.colors(
                                selectedColor = Color(0xFF021CC5),
                            )
                        )
                        Text(text = person)
                    }
                }
            }
        },
        dismissButton = {
            TextButton(
                onClick = onDismissRequest
            ) {
                Text(
                    text = stringResource(R.string.cancel),
                    color = Color.Red
                )
            }
        },
        confirmButton = {
            TextButton(
                onClick = { onConfirmClick(selectedName!!) },
                enabled = (selectedName != null)
            ) {
                Text(
                    text = stringResource(R.string.confirm),
                    color = Color(0xFF021CC5),
                )
            }
        },
        modifier = modifier,
    )
}

@Preview
@Composable
fun NewPernaDialogPreview() {
    PlacarBuracoTheme {
        NewPernaDialog(
            playerNames = listOf("Zinho", "Gian", "Mainha", "Painho"),
            onDismissRequest = { /*TODO*/ },
            onConfirmClick = {}
        )
    }
}

@Preview
@Composable
fun PernasSummaryScreenPreview() {
    PlacarBuracoTheme {
        PernasSummaryScreenSecundaria(
            pernasSummaryUiState = PernasSummaryUiState(
                matchType = MatchType.Doubles,
                playerNames = listOf("Zinho", "Gian", "Painho", "Mainha"),
                negaNum = 12,
                pernaInfoList = listOf(
                    PernaInfo(1, 1, MatchStatus.Finished, "Zinho / Gian"),
                    PernaInfo(2, 2, MatchStatus.Finished, "Painho / Mainha"),
                    PernaInfo(3, 3, MatchStatus.Underway, null),
                )
            ),
            onBackClick = {},
            onCreatePerna = {},
            onGoToPerna = {_, _ ->}
        )
    }
}