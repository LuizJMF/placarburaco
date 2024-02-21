package com.gmail.luizjmfilho.buraco.ui

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowRight
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.gmail.luizjmfilho.buraco.R
import com.gmail.luizjmfilho.buraco.model.DoubleMatchPlayers
import com.gmail.luizjmfilho.buraco.model.SingleMatchPlayers
import com.gmail.luizjmfilho.buraco.ui.theme.PlacarBuracoTheme

@Composable
fun MatchesListScreenPrimaria(
    onCreateNewMatch: () -> Unit,
    modifier: Modifier = Modifier,
    matchesListViewModel: MatchesListViewModel = hiltViewModel(),
) {

    val matchesListUiState by matchesListViewModel.uiState.collectAsState()

    MatchesListScreenSecundaria(
        matchesListUiState = matchesListUiState,
        onCreateNewMatch = onCreateNewMatch,
        modifier = modifier,
    )
}

@Composable
fun MatchesListScreenSecundaria(
    matchesListUiState: MatchesListUiState,
    onCreateNewMatch: () -> Unit,
    modifier: Modifier = Modifier
) {
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
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {
                DoubleOrSingleTitleCard(
                    matchType = MatchType.Doubles
                )
                for (match in matchesListUiState.doubleMatchList) {
                    DoublesCard(
                        doubleMatchPlayers = match,
                        modifier = Modifier
                            .padding(start = 25.dp)
                    )
                    if (matchesListUiState.doubleMatchList.indexOf(match) < (matchesListUiState.doubleMatchList.size - 1)) Divider()
                }
                DoubleOrSingleTitleCard(
                    matchType = MatchType.Singles
                )
                for (match in matchesListUiState.singleMatchList) {
                    SinglesCard(
                        singleMatchPlayers = match,
                        modifier = Modifier
                            .padding(start = 25.dp)
                    )
                    Divider()
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
        color = Color.Red,
        modifier = modifier
            .padding(start = 10.dp, end = 10.dp),
        fontStyle = FontStyle.Italic
    )
}

@Composable
fun SinglesCard(
    singleMatchPlayers: SingleMatchPlayers,
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
                Text(text = singleMatchPlayers.player1)
                VersusText()
                Text(text = singleMatchPlayers.player2)
                VersusText()
                Text(text = singleMatchPlayers.player3)
            }
            IconButton(
                onClick = { /*TODO*/ },
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
    doubleMatchPlayers: DoubleMatchPlayers,
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
                Text(text = doubleMatchPlayers.player1)
                Text(
                    text = "/",
                    color = Color.Gray,
                    modifier = Modifier
                        .padding(start = 5.dp, end = 5.dp)
                )
                Text(text = doubleMatchPlayers.player2)
                VersusText()
                Text(text = doubleMatchPlayers.player3)
                Text(
                    text = "/",
                    color = Color.Gray,
                    modifier = Modifier
                        .padding(start = 5.dp, end = 5.dp)
                )
                Text(text = doubleMatchPlayers.player4, maxLines = 1, overflow = TextOverflow.Ellipsis)
            }
            IconButton(
                onClick = { /*TODO*/ },
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
                onClick = { /*TODO*/ }
            ) {
                Icon(
                    imageVector = Icons.Filled.ExpandMore,
                    contentDescription = null,
                    tint = textAndIconsColor,
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
    modifier: Modifier = Modifier
) {
    Column (
        modifier = modifier
    ){
        TopAppBar(
            title = {
                Text(
                    text = title,
                )
            },
            navigationIcon = {
                if (onBackClick != null) {
                    IconButton(
                        onClick = onBackClick
                    ) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = null
                        )
                    }
                }
            }
        )
        Divider(
            thickness = 1.dp,
            color = Color.Black
        )
    }
}

@Preview
@Composable
fun MatchesListScreenPreview() {
    PlacarBuracoTheme {
        MatchesListScreenSecundaria(
            matchesListUiState = MatchesListUiState(
                doubleMatchList = listOf(
                    DoubleMatchPlayers(
                        0,"Zinho","Gian","Painho","Mainha"
                    ),
                    DoubleMatchPlayers(
                        0,"Damião","Gian","Painho","Zinho"
                    ),
                    DoubleMatchPlayers(
                        0,"Zeguedega","Damião","Painho","Gian"
                    ),
                ),
                singleMatchList = listOf(
                    SingleMatchPlayers(
                        0,"Zinho","Gian","Painho"
                    ),
                    SingleMatchPlayers(
                        0,"Painho","Gian","Damião"
                    ),
                ),
            ),
            onCreateNewMatch = {}
        )
    }
}