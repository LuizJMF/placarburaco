package com.gmail.luizjmfilho.buraco.ui

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowRight
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DismissDirection
import androidx.compose.material3.DismissState
import androidx.compose.material3.DismissValue
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SwipeToDismiss
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDismissState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.gmail.luizjmfilho.buraco.R
import com.gmail.luizjmfilho.buraco.model.DoubleMatchPlayers
import com.gmail.luizjmfilho.buraco.model.Player
import com.gmail.luizjmfilho.buraco.model.SingleMatchPlayers
import com.gmail.luizjmfilho.buraco.ui.theme.PlacarBuracoTheme
import kotlinx.coroutines.delay

@Composable
fun MatchesListScreenPrimaria(
    onCreateNewMatch: () -> Unit,
    modifier: Modifier = Modifier,
    onSingleGroupClick: (MatchType, Int) -> Unit,
    onDoubleGroupClick: (MatchType, Int) -> Unit,
    matchesListViewModel: MatchesListViewModel = hiltViewModel(),
) {

    val matchesListUiState by matchesListViewModel.uiState.collectAsState()

    MatchesListScreenSecundaria(
        matchesListUiState = matchesListUiState,
        onCreateNewMatch = onCreateNewMatch,
        onDeleteDoubleMatch = matchesListViewModel::onDeleteDoubleMatch,
        onDeleteSingleMatch = matchesListViewModel::onDeleteSingleMatch,
        onSingleGroupClick = onSingleGroupClick,
        onDoubleGroupClick = onDoubleGroupClick,
        onDeleteAllDoubleNegaPernaMaoDerivedFromGroupId = matchesListViewModel::onDeleteAllDoubleNegaPernaMaoDerivedFromGroupId,
        onDeleteAllSingleNegaPernaMaoDerivedFromGroupId = matchesListViewModel::onDeleteAllSingleNegaPernaMaoDerivedFromGroupId,
        modifier = modifier,
    )
}

@Composable
fun MatchesListScreenSecundaria(
    matchesListUiState: MatchesListUiState,
    onCreateNewMatch: () -> Unit,
    onDeleteDoubleMatch: (DoubleMatchPlayers) -> Unit,
    onDeleteSingleMatch: (SingleMatchPlayers) -> Unit,
    onDeleteAllSingleNegaPernaMaoDerivedFromGroupId: (Int) -> Unit,
    onDeleteAllDoubleNegaPernaMaoDerivedFromGroupId: (Int) -> Unit,
    onSingleGroupClick: (MatchType, Int) -> Unit,
    onDoubleGroupClick: (MatchType, Int) -> Unit,
    modifier: Modifier = Modifier
) {
    var isDoubleMatchesShown by rememberSaveable { mutableStateOf(true) }
    var isSingleMatchesShown by rememberSaveable { mutableStateOf(true) }

    Scaffold(
        modifier = modifier,
        floatingActionButton = {
            FloatingActionButton(
                onClick = onCreateNewMatch,
                containerColor = Color(0xFF021CC5),
                contentColor = Color.White
            ) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = null
                )
            }
        },
    ) { scaffoldPaddings ->
        BackHandler {}
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(scaffoldPaddings),
        ) {
            LazyColumn {
                item {
                    DoubleOrSingleTitleCard(
                        matchType = MatchType.Doubles,
                        expanded = isDoubleMatchesShown,
                        onShowMatchesClick = {
                            isDoubleMatchesShown = !isDoubleMatchesShown
                        }
                    )
                }
                items(
                    items = matchesListUiState.doubleMatchList,
                    key = { it.doubleGroupId }
                ) { match ->
                    AnimatedVisibility(
                        visible = isDoubleMatchesShown
                    ) {
                        Column {
                            SwipeToDeleteContainer(
                                item = match,
                                onDelete = {
                                    onDeleteDoubleMatch(it)
                                    onDeleteAllDoubleNegaPernaMaoDerivedFromGroupId(it.doubleGroupId)
                                }
                            ) {
                                DoublesCard(
                                    p1Name = matchesListUiState.playersList[matchesListUiState.playersList.indexOfFirst { it.playerId ==  match.player1id }].playerName,
                                    p2Name = matchesListUiState.playersList[matchesListUiState.playersList.indexOfFirst { it.playerId ==  match.player2id }].playerName,
                                    p3Name = matchesListUiState.playersList[matchesListUiState.playersList.indexOfFirst { it.playerId ==  match.player3id }].playerName,
                                    p4Name = matchesListUiState.playersList[matchesListUiState.playersList.indexOfFirst { it.playerId ==  match.player4id }].playerName,
                                    modifier = Modifier
                                        .padding(start = 25.dp),
                                    onDoubleGroupClick = {
                                        onDoubleGroupClick(MatchType.Doubles, match.doubleGroupId)
                                    }
                                )
                            }
                            if (matchesListUiState.doubleMatchList.indexOf(match) < (matchesListUiState.doubleMatchList.size - 1)) Divider()
                        }
                    }
                }
                item {
                    DoubleOrSingleTitleCard(
                        matchType = MatchType.Singles,
                        expanded = isSingleMatchesShown,
                        onShowMatchesClick = {
                            isSingleMatchesShown = !isSingleMatchesShown
                        }
                    )
                }
                items(
                    items = matchesListUiState.singleMatchList,
                    key = { it.singleGroupId + 5000} // esse 5000 é só pra que os items individuais gerem keys diferentes dos items de dupla
                ) { match ->
                    AnimatedVisibility(
                        visible = isSingleMatchesShown
                    ) {
                        Column {
                            SwipeToDeleteContainer(
                                item = match,
                                onDelete = {
                                    onDeleteSingleMatch(it)
                                    onDeleteAllSingleNegaPernaMaoDerivedFromGroupId(it.singleGroupId)
                                }
                            ) {
                                SinglesCard(
                                    p1Name = matchesListUiState.playersList[matchesListUiState.playersList.indexOfFirst { it.playerId ==  match.player1id }].playerName,
                                    p2Name = matchesListUiState.playersList[matchesListUiState.playersList.indexOfFirst { it.playerId ==  match.player2id }].playerName,
                                    p3Name = matchesListUiState.playersList[matchesListUiState.playersList.indexOfFirst { it.playerId ==  match.player3id }].playerName,
                                    modifier = Modifier
                                        .padding(start = 25.dp),
                                    onSingleGroupClick = {
                                        onSingleGroupClick(MatchType.Singles, match.singleGroupId)
                                    }
                                )
                            }
                            Divider()
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun VersusText(
    modifier: Modifier = Modifier
) {
    Text(
        text = stringResource(R.string.versus_acronym),
        color = Color(0xFFFF0000),
        modifier = modifier
            .padding(start = 10.dp, end = 10.dp),
        fontStyle = FontStyle.Italic
    )
}

@Composable
fun SinglesCard(
    p1Name: String,
    p2Name: String,
    p3Name: String,
    onSingleGroupClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(48.dp)
            .wrapContentHeight(),
        shape = RectangleShape,
        colors = CardDefaults.cardColors(containerColor = Color.White),
    ) {
        Row {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(0.85f)
            ) {
                Text(text = p1Name)
                VersusText()
                Text(text = p2Name)
                VersusText()
                Text(text = p3Name)
            }
            IconButton(
                onClick = onSingleGroupClick,
                modifier = Modifier
                    .weight(0.15f)
                    .wrapContentWidth(Alignment.End)
                    .widthIn(min = 48.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.ArrowRight,
                    contentDescription = null
                )
            }
        }
    }
}

@Composable
fun DoublesCard(
    p1Name: String,
    p2Name: String,
    p3Name: String,
    p4Name: String,
    onDoubleGroupClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(48.dp)
            .wrapContentHeight(),
        shape = RectangleShape,
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(0.85f)
            ) {
                Text(text = p1Name)
                Text(
                    text = "/",
                    color = Color.Gray,
                    modifier = Modifier
                        .padding(start = 5.dp, end = 5.dp)
                )
                Text(text = p2Name)
                VersusText()
                Text(text = p3Name)
                Text(
                    text = "/",
                    color = Color.Gray,
                    modifier = Modifier
                        .padding(start = 5.dp, end = 5.dp)
                )
                Text(text = p4Name, maxLines = 1, overflow = TextOverflow.Ellipsis)
            }
            IconButton(
                onClick = onDoubleGroupClick,
                modifier = Modifier
                    .weight(0.15f)
                    .wrapContentWidth(Alignment.End)
                    .widthIn(min = 48.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.ArrowRight,
                    contentDescription = null
                )
            }
        }
    }
}

@Composable
fun DoubleOrSingleTitleCard(
    matchType: MatchType,
    onShowMatchesClick: () -> Unit,
    expanded: Boolean,
    modifier: Modifier = Modifier,
) {
    val textAndIconsColor = Color.White
    Card(
        modifier = modifier
            .fillMaxWidth(),
        shape = RectangleShape,
        colors = CardDefaults.cardColors(containerColor = Color(0xFF4689FF))
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(start = 10.dp)
        ) {
            Icon(
                imageVector = when (matchType) {
                    MatchType.Doubles -> Icons.Filled.People
                    MatchType.Singles -> Icons.Filled.Person
                },
                contentDescription = null,
                tint = textAndIconsColor
            )
            Text(
                text = when (matchType) {
                    MatchType.Doubles -> stringResource(R.string.doubles).uppercase()
                    MatchType.Singles -> stringResource(R.string.singles).uppercase()
                },
                modifier = Modifier
                    .padding(start = 10.dp),
                color = textAndIconsColor
            )
            Spacer(modifier = Modifier.weight(1f))
            IconButton(
                onClick = onShowMatchesClick
            ) {
                val degree by animateFloatAsState(
                    targetValue = if (expanded) 0f else 180f,
                )
                Icon(
                    imageVector = Icons.Filled.ExpandLess,
                    contentDescription = null,
                    tint = textAndIconsColor,
                    modifier = Modifier
                        .rotate(degree)
                )
            }
        }
        Divider(color = Color.Black)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DefaultTopBar(
    onBackClick: (() -> Unit)?,
    title: String,
    icon: ImageVector,
    containerColor: Color,
    textColor: Color,
    modifier: Modifier = Modifier
) {
    Column (
        modifier = modifier
    ){
        TopAppBar(
            title = {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = title,
                        color = textColor,
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        tint = textColor,
                        modifier = Modifier
                            .padding(end = 15.dp)
                    )
                }

            },
            navigationIcon = {
                if (onBackClick != null) {
                    IconButton(
                        onClick = onBackClick
                    ) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = null,
                            tint = textColor,
                        )
                    }
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = containerColor
            )
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T> SwipeToDeleteContainer(
    item: T,
    onDelete: (T) -> Unit,
    animationDuration: Int = 500,
    content: @Composable (T) -> Unit,
) {
    var isRemoved by rememberSaveable { mutableStateOf(false) }
    val state = rememberDismissState(
        confirmValueChange = { value ->
            if(value == DismissValue.DismissedToStart) {
                isRemoved = true
                true
            } else {
                false
            }
        }
    )
    
    LaunchedEffect(key1 = isRemoved) {
        if(isRemoved) {
            delay(animationDuration.toLong())
            onDelete(item)
        }
    }

    AnimatedVisibility(
        visible = !isRemoved,
        exit = shrinkVertically(
            animationSpec = tween(durationMillis = animationDuration),
            shrinkTowards = Alignment.Top
        ) + fadeOut()
    ) {
        SwipeToDismiss(
            state = state,
            background = {
                DeleteBackground(swipeDismissState = state)
            },
            dismissContent = { content(item) },
            directions = setOf(DismissDirection.EndToStart)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeleteBackground(
    swipeDismissState: DismissState,
) {
    val color = if(swipeDismissState.dismissDirection == DismissDirection.EndToStart) {
        Color.Red
    } else Color.Transparent

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color)
            .padding(16.dp),
        contentAlignment = Alignment.CenterEnd
    ) {
        Icon(imageVector = Icons.Default.Delete, contentDescription = null, tint = Color.White)
    }
}

@Composable
fun DeleteMatchAlertDialog(
    onDismissRequest: () -> Unit,
    onConfirmButton: () -> Unit,
    modifier: Modifier = Modifier,
) {
    AlertDialog(
        onDismissRequest = onDismissRequest,
        confirmButton = {
            TextButton(
                onClick = onConfirmButton,
            ) {
                Text(text = stringResource(R.string.confirm))
            }
        },
        text = { Text(text = stringResource(R.string.delete_match_warning)) },
        dismissButton = {
            TextButton(
                onClick = onDismissRequest
            ) {
                Text(text = stringResource(id = R.string.cancel))
            }
        },
        modifier = modifier,
    )
}

@Preview
@Composable
fun MatchesListScreenPreview() {
    PlacarBuracoTheme {
        MatchesListScreenSecundaria(
            matchesListUiState = MatchesListUiState(
                doubleMatchList = listOf(
                    DoubleMatchPlayers(
                        0,1,2,3,4
                    ),
                    DoubleMatchPlayers(
                        1,2,4,5,6
                    ),
                ),
                singleMatchList = listOf(
                    SingleMatchPlayers(
                        4,1,2,3
                    ),
                    SingleMatchPlayers(
                        5,4,5,6
                    ),
                ),
                playersList = listOf(
                    Player(1, "Zinho"),
                    Player(2, "Gian"),
                    Player(3, "Painho"),
                    Player(4, "Mainha"),
                    Player(5, "Damião"),
                    Player(6, "Deivinho"),
                )
            ),
            onCreateNewMatch = {},
            onDeleteDoubleMatch = {},
            onDeleteSingleMatch = {},
            onSingleGroupClick = {_, _ ->},
            onDoubleGroupClick = {_, _ ->},
            onDeleteAllDoubleNegaPernaMaoDerivedFromGroupId = {},
            onDeleteAllSingleNegaPernaMaoDerivedFromGroupId = {}
        )
    }
}

@Preview
@Composable
fun TopBarPreview() {
    PlacarBuracoTheme {
        DefaultTopBar(
            onBackClick = { /*TODO*/ },
            title = "Resumo",
            icon = Icons.Filled.Person,
            containerColor = Color.White,
            textColor = Color.Black
        )
    }
}