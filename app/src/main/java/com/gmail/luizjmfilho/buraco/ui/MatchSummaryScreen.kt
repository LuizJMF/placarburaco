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
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.gmail.luizjmfilho.buraco.R
import com.gmail.luizjmfilho.buraco.ui.theme.PlacarBuracoTheme

@Composable
fun MatchSummaryScreenPrimaria(
    modifier: Modifier = Modifier,
    onGoToNega: (MatchType, Int) -> Unit,
    onBackClick: () -> Unit,
    matchSummaryViewModel: MatchSummaryViewModel = hiltViewModel(),
) {

    val lifecycleOwner = LocalLifecycleOwner.current
    DisposableEffect(key1 = Unit) {
        lifecycleOwner.lifecycle.addObserver(matchSummaryViewModel)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(matchSummaryViewModel)
        }
    }
    val matchSummaryUiState by matchSummaryViewModel.uiState.collectAsState()

    MatchSummaryScreenSecundaria(
        matchSummaryUiState = matchSummaryUiState,
        onBackClick = onBackClick,
        onCreateNega = matchSummaryViewModel::onCreateNega,
        onGoToNega = onGoToNega,
        modifier = modifier,
    )
}

@Composable
fun MatchSummaryScreenSecundaria(
    matchSummaryUiState: MatchSummaryUiState,
    onBackClick: () -> Unit,
    onGoToNega: (MatchType, Int) -> Unit,
    onCreateNega: (String, Boolean) -> Unit,
    modifier: Modifier = Modifier,
) {

    var isDialogShown by rememberSaveable { mutableStateOf(false) }

    Scaffold(
        modifier = modifier,
        topBar = {
            DefaultTopBar(
                onBackClick = onBackClick,
                title = stringResource(R.string.match_summary_title),
                icon = when(matchSummaryUiState.matchType) {
                    MatchType.Singles -> Icons.Filled.Person
                    MatchType.Doubles -> Icons.Filled.People
                },
                containerColor = Color(0xFF021CC5),
                textColor = Color.White
            )
        },
        floatingActionButtonPosition = FabPosition.Center,
        bottomBar = {
            BottomAppBar(
                containerColor = Color(0xFF69B1FF),
                modifier = Modifier
                    .height(70.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxHeight()
                        .padding(bottom = 5.dp)
                ) {
//                    Spacer(modifier = Modifier.weight(1f))
//                    IconButton(
//                        onClick = { /*TODO*/ }
//                    ) {
//                        Icon(
//                            imageVector = Icons.Filled.FormatListNumbered,
//                            contentDescription = null,
//                        )
//                    }
                    Spacer(modifier = Modifier.weight(1f))
                    FloatingActionButton(
                        onClick = { isDialogShown = true },
                        containerColor = Color(0xFF021CC5),
                        contentColor = Color.White,

                        ) {
                        Icon(
                            imageVector = Icons.Filled.Add,
                            contentDescription = null
                        )
                    }
                    Spacer(modifier = Modifier.weight(1f))
//                    IconButton(
//                        onClick = { /*TODO*/ }
//                    ) {
//                        Icon(
//                            imageVector = Icons.Filled.Leaderboard,
//                            contentDescription = null,
//                        )
//                    }
//                    Spacer(modifier = Modifier.weight(1f))
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
                        containerColor = Color(0xFF69B1FF),
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
                        if (matchSummaryUiState.playerNames.isEmpty()) {
                            Text(text = "-")
                        } else {
                            when (matchSummaryUiState.matchType) {
                                MatchType.Singles -> {
                                    Text(
                                        text = matchSummaryUiState.playerNames[0],
                                        overflow = TextOverflow.Ellipsis,
                                        modifier = Modifier
                                            .weight(1f)
                                            .wrapContentWidth()
                                    )
                                    VersusText()
                                    Text(
                                        text = matchSummaryUiState.playerNames[1],
                                        overflow = TextOverflow.Ellipsis,
                                        modifier = Modifier
                                            .weight(1f)
                                            .wrapContentWidth()
                                    )
                                    VersusText()
                                    Text(
                                        text = matchSummaryUiState.playerNames[2],
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
                                        Text(text = matchSummaryUiState.playerNames[0])
                                        Text(
                                            text = "/",
                                            modifier = Modifier
                                                .padding(start = 5.dp, end = 5.dp),
                                            color = Color(0xFF555555),
                                        )
                                        Text(text = matchSummaryUiState.playerNames[1], overflow = TextOverflow.Ellipsis)
                                    }
                                    VersusText()
                                    Row(
                                        modifier = Modifier
                                            .weight(1f)
                                            .wrapContentWidth()
                                    ) {
                                        Text(text = matchSummaryUiState.playerNames[2])
                                        Text(
                                            text = "/",
                                            modifier = Modifier
                                                .padding(start = 5.dp, end = 5.dp),
                                            color = Color(0xFF555555),
                                        )
                                        Text(text = matchSummaryUiState.playerNames[3], overflow = TextOverflow.Ellipsis)
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
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "#",
                            modifier = Modifier
                                .weight(0.12f)
                                .wrapContentWidth(),
                            fontWeight = FontWeight.Bold,
                            overflow = TextOverflow.Ellipsis,
                            maxLines = 1
                        )
                        Text(
                            text = stringResource(R.string.status),
                            modifier = Modifier
                                .weight(0.18f)
                                .wrapContentWidth(),
                            fontWeight = FontWeight.Bold,
                            overflow = TextOverflow.Ellipsis,
                            maxLines = 1
                        )
                        Text(
                            text = pluralStringResource(
                                id = R.plurals.winner,
                                count = if (matchSummaryUiState.matchType == MatchType.Singles) 1 else 2
                            ),
                            modifier = Modifier
                                .weight(0.5f)
                                .wrapContentWidth(),
                            fontWeight = FontWeight.Bold,
                            overflow = TextOverflow.Ellipsis,
                            maxLines = 1
                        )
                        Spacer(modifier = Modifier.width(48.dp))
                    }
                    Divider()
                    Column(
                        modifier = Modifier
                            .verticalScroll(rememberScrollState())
                    ) {
                        if (matchSummaryUiState.negaInfoList.isNotEmpty()) {
                            matchSummaryUiState.negaInfoList.forEachIndexed { index, negaInfo ->
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
                                        imageVector = if (negaInfo.status == MatchStatus.Finished) Icons.Filled.Done else Icons.Filled.AccessTime,
                                        contentDescription = null,
                                        modifier = Modifier
                                            .weight(0.18f)
                                            .wrapContentWidth(),
                                        tint = if (negaInfo.status == MatchStatus.Finished) Color(
                                            0xFF4CAF50
                                        ) else Color(0xFFFFC107),
                                    )
                                    Text(
                                        text = negaInfo.winner ?: "-",
                                        modifier = Modifier
                                            .weight(0.5f)
                                            .wrapContentWidth()
                                    )
                                    IconButton(
                                        onClick = {
                                            onGoToNega(
                                                matchSummaryUiState.matchType,
                                                negaInfo.id
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
                NewNegaDialog(
                    playerNames = matchSummaryUiState.playerNames,
                    matchType = matchSummaryUiState.matchType,
                    onDismissRequest = { isDialogShown = false },
                    onConfirmClick = { firstPlayerToPlay, isNormalOrder ->
                        onCreateNega(firstPlayerToPlay, isNormalOrder)
                        isDialogShown = false
                    }
                )
            }
        }
    }
}

@Composable
fun NewNegaDialog(
    playerNames: List<String>,
    matchType: MatchType,
    onDismissRequest: () -> Unit,
    onConfirmClick: (String, Boolean) -> Unit,
    modifier: Modifier = Modifier,
) {
    var selectedName: String? by rememberSaveable { mutableStateOf(null) }
    var selectedOrder: Int? by rememberSaveable { mutableStateOf(null) }
    val isNormalOrder = if (selectedOrder == null) null else (selectedOrder == 1)

    AlertDialog(
        onDismissRequest = onDismissRequest,
        title = { Text(text = stringResource(R.string.new_match)) },
        text = {
            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
            ) {
                Text(
                    text = stringResource(R.string.to_select_players_sequence),
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF021CC5),
                )
                for (i in 1..2) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .selectable(
                                selected = (selectedOrder == i),
                                onClick = {
                                    selectedOrder = i
                                }
                            )
                            .padding(horizontal = 16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = (selectedOrder == i),
                            onClick = {
                                selectedOrder = i
                            },
                            colors = RadioButtonDefaults.colors(
                                selectedColor = Color(0xFF021CC5),
                            )
                        )
                        Text(
                            text = when (matchType) {
                                MatchType.Singles -> stringResource(R.string.player_comes_before_player, playerNames[0], playerNames[i])
                                MatchType.Doubles -> stringResource(R.string.player_comes_before_player, playerNames[0], playerNames[i + 1])
                            }
                        )
                    }
                }
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
                onClick = { onConfirmClick(selectedName!!, isNormalOrder!!) },
                enabled = ((selectedName != null) && (selectedOrder != null))
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
fun NewNegaDialogPreview() {
    PlacarBuracoTheme {
        NewNegaDialog(
            playerNames = listOf("Zinho", "Gian", "Mainha", "Painho"),
            matchType = MatchType.Doubles,
            onDismissRequest = { /*TODO*/ },
            onConfirmClick = {_, _ ->}
        )
    }
}

@Preview
@Composable
fun MatchSummaryScreenPreview() {
    PlacarBuracoTheme {
        MatchSummaryScreenSecundaria(
            matchSummaryUiState = MatchSummaryUiState(
                matchType = MatchType.Singles,
                playerNames = listOf("Zinho", "Gian", "Painho"),
                negaInfoList = listOf(
                    NegaInfo(1, 1, MatchStatus.Finished, "Zinho"),
                    NegaInfo(1, 1, MatchStatus.Underway, null),
                )
            ),
            onBackClick = {},
            onCreateNega = {_, _ ->},
            onGoToNega = {_, _ ->}
        )
    }
}