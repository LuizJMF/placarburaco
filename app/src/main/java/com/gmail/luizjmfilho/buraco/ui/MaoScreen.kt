package com.gmail.luizjmfilho.buraco.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Circle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.DeleteForever
import androidx.compose.material.icons.filled.Handshake
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.gmail.luizjmfilho.buraco.R
import com.gmail.luizjmfilho.buraco.ui.theme.PlacarBuracoTheme

@Composable
fun MaoScreenPrimaria(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit,
    maoViewModel: MaoViewModel = hiltViewModel(),
) {

    val maoUiState by maoViewModel.uiState.collectAsState()

    MaoScreenSecundaria(
        maoUiState = maoUiState,
        onBackClick = onBackClick,
        onCreateMao = maoViewModel::onCreateMao,
        onTypeScore = maoViewModel::onTypeScore,
        onDismissTypeScoreDialog = maoViewModel::onDismissTypeScoreDialog,
        onConfirmTypeScoreDialog = maoViewModel::onConfirmTypeScoreDialog,
        onCreateWoMao = maoViewModel::onCreateWoMao,
        onDeleteLastMao = maoViewModel::onDeleteLastMao,
        modifier = modifier,
    )
}

@Composable
fun MaoScreenSecundaria(
    maoUiState: MaoUiState,
    onBackClick: () -> Unit,
    onCreateMao: () -> Unit,
    onTypeScore: (String) -> Unit,
    onConfirmTypeScoreDialog: (Int, Int) -> Unit,
    onDismissTypeScoreDialog: () -> Unit,
    onCreateWoMao: (String) -> Unit,
    onDeleteLastMao: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val darkMainColor = Color(0xFF3A3A3A)
    val lightMainColor = Color(0xFFA2A2A2)
    val colorStartingPlayerIcon = Color(0xFF29FF00)

    var isDropMenuExpanded by rememberSaveable { mutableStateOf(false) }
    var isWinnerByWoDialogShown by rememberSaveable { mutableStateOf(false) }
    var isTypeScoreDialogShown by rememberSaveable { mutableStateOf(false) }
    var playerInTypeScoreDialog by rememberSaveable { mutableStateOf("") }
    var playerOrNumberTeam by rememberSaveable { mutableIntStateOf(0) }
    var maoNumberBeingTyped by rememberSaveable { mutableIntStateOf(0) }

    Scaffold(
        modifier = modifier,
        topBar = {
            DefaultTopBar(
                onBackClick = onBackClick,
                title = "Perna ${maoUiState.pernaNum}",
                icon = when(maoUiState.matchType) {
                    MatchType.Singles -> Icons.Filled.Person
                    MatchType.Doubles -> Icons.Filled.People
                },
                containerColor = darkMainColor,
                textColor = Color.White
            )
        },
        floatingActionButtonPosition = FabPosition.Center,
        bottomBar = {
            BottomAppBar(
                containerColor = lightMainColor,
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
                    IconButton(
                        onClick = { /*TODO*/ }
                    ) {
                        // esse Button existe só pra ajustar as coisas na tela na posição que eu quero kkkk
                        Icon(
                            imageVector = Icons.Filled.MoreVert,
                            contentDescription = null,
                            tint = lightMainColor
                        )
                    }
                    Spacer(modifier = Modifier.weight(1f))
                    FloatingActionButton(
                        onClick = onCreateMao,
                        containerColor = darkMainColor,
                        contentColor = Color.White,
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Add,
                            contentDescription = null
                        )
                    }
                    Spacer(modifier = Modifier.weight(1f))
                    IconButton(
                        onClick = {
                            isDropMenuExpanded = true
                        },
                    ) {
                        Icon(
                            imageVector = Icons.Filled.MoreHoriz,
                            contentDescription = null
                        )
                        DropDownMaoMenu(
                            isDropMenuExpanded = isDropMenuExpanded,
                            onDismissRequest = { isDropMenuExpanded = false },
                            onDeleteLastMao = onDeleteLastMao,
                            onWinnerByWo = {
                                isDropMenuExpanded = false
                                isWinnerByWoDialogShown = true
                            },
                            isDeleteLastMaoEnabled = (maoUiState.maoInfoList.size > 1)
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
                        containerColor = lightMainColor,
                    ),
                    shape = RectangleShape,
                ) {
                    Row(
                        modifier = Modifier
                            .padding(5.dp)
                            .fillMaxWidth()
                            .wrapContentWidth()
                    ) {
                        if (maoUiState.playerNames.isEmpty()) {
                            Text(text = "-")
                        } else {
                            when (maoUiState.matchType) {
                                MatchType.Singles -> {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.spacedBy(3.dp),
                                        modifier = Modifier
                                            .weight(1f)
                                            .wrapContentWidth()
                                    ) {
                                        Text(
                                            text = maoUiState.playerNames[0],
                                            overflow = TextOverflow.Ellipsis,
                                            fontWeight = FontWeight.Bold,
                                        )
                                        if(maoUiState.startingPlayer == maoUiState.playerNames[0]) {
                                            Icon(
                                                imageVector = Icons.Filled.Circle,
                                                contentDescription = null,
                                                tint = colorStartingPlayerIcon,
                                                modifier = Modifier
                                                    .size(5.dp)
                                            )
                                        }
                                    }
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.spacedBy(3.dp),
                                        modifier = Modifier
                                            .weight(1f)
                                            .wrapContentWidth()
                                    ) {
                                        Text(
                                            text = maoUiState.playerNames[1],
                                            overflow = TextOverflow.Ellipsis,
                                            fontWeight = FontWeight.Bold,
                                        )
                                        if(maoUiState.startingPlayer == maoUiState.playerNames[1]) {
                                            Icon(
                                                imageVector = Icons.Filled.Circle,
                                                contentDescription = null,
                                                tint = colorStartingPlayerIcon,
                                                modifier = Modifier
                                                    .size(5.dp)
                                            )
                                        }
                                    }
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.spacedBy(3.dp),
                                        modifier = Modifier
                                            .weight(1f)
                                            .wrapContentWidth()
                                    ) {
                                        Text(
                                            text = maoUiState.playerNames[2],
                                            overflow = TextOverflow.Ellipsis,
                                            fontWeight = FontWeight.Bold,
                                        )
                                        if(maoUiState.startingPlayer == maoUiState.playerNames[2]) {
                                            Icon(
                                                imageVector = Icons.Filled.Circle,
                                                contentDescription = null,
                                                tint = colorStartingPlayerIcon,
                                                modifier = Modifier
                                                    .size(5.dp)
                                            )
                                        }
                                    }
                                }

                                MatchType.Doubles -> {

                                    Row(
                                        modifier = Modifier
                                            .weight(1f)
                                            .wrapContentWidth()
                                    ) {
                                        Row(
                                            verticalAlignment = Alignment.CenterVertically,
                                            horizontalArrangement = Arrangement.spacedBy(3.dp)
                                        ) {
                                            Text(
                                                text = maoUiState.playerNames[0],
                                                fontWeight = FontWeight.Bold
                                            )
                                            if(maoUiState.startingPlayer == maoUiState.playerNames[0]) {
                                                Icon(
                                                    imageVector = Icons.Filled.Circle,
                                                    contentDescription = null,
                                                    tint = colorStartingPlayerIcon,
                                                    modifier = Modifier
                                                        .size(5.dp)
                                                )
                                            }
                                        }
                                        Text(
                                            text = "/",
                                            modifier = Modifier
                                                .padding(start = 5.dp, end = 5.dp),
                                            color = Color(0xFF555555),
                                        )
                                        Row(
                                            verticalAlignment = Alignment.CenterVertically,
                                            horizontalArrangement = Arrangement.spacedBy(3.dp)
                                        ) {
                                            Text(
                                                text = maoUiState.playerNames[1],
                                                fontWeight = FontWeight.Bold
                                            )
                                            if(maoUiState.startingPlayer == maoUiState.playerNames[1]) {
                                                Icon(
                                                    imageVector = Icons.Filled.Circle,
                                                    contentDescription = null,
                                                    tint = colorStartingPlayerIcon,
                                                    modifier = Modifier
                                                        .size(5.dp)
                                                )
                                            }
                                        }
                                    }
                                    Row(
                                        modifier = Modifier
                                            .weight(1f)
                                            .wrapContentWidth()
                                    ) {
                                        Row(
                                            verticalAlignment = Alignment.CenterVertically,
                                            horizontalArrangement = Arrangement.spacedBy(3.dp)
                                        ) {
                                            Text(
                                                text = maoUiState.playerNames[2],
                                                fontWeight = FontWeight.Bold
                                            )
                                            if(maoUiState.startingPlayer == maoUiState.playerNames[2]) {
                                                Icon(
                                                    imageVector = Icons.Filled.Circle,
                                                    contentDescription = null,
                                                    tint = colorStartingPlayerIcon,
                                                    modifier = Modifier
                                                        .size(5.dp)
                                                )
                                            }
                                        }
                                        Text(
                                            text = "/",
                                            modifier = Modifier
                                                .padding(start = 5.dp, end = 5.dp),
                                            color = Color(0xFF555555),
                                        )
                                        Row(
                                            verticalAlignment = Alignment.CenterVertically,
                                            horizontalArrangement = Arrangement.spacedBy(3.dp)
                                        ) {
                                            Text(
                                                text = maoUiState.playerNames[3],
                                                fontWeight = FontWeight.Bold
                                            )
                                            if(maoUiState.startingPlayer == maoUiState.playerNames[3]) {
                                                Icon(
                                                    imageVector = Icons.Filled.Circle,
                                                    contentDescription = null,
                                                    tint = colorStartingPlayerIcon,
                                                    modifier = Modifier
                                                        .size(5.dp)
                                                )
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                when (maoUiState.matchType) {
                    MatchType.Singles -> {
                        Column(
                            modifier = Modifier
                                .padding(top = 10.dp, bottom = 10.dp, start = 5.dp, end = 5.dp)
                                .fillMaxWidth()
                                .verticalScroll(rememberScrollState()),
                            verticalArrangement = Arrangement.spacedBy(5.dp)
                        ) {
                            Row(
                                modifier = Modifier
                                    .height(IntrinsicSize.Min)
                            ) {
                                PointColumn(
                                    scoreList = maoUiState.maoInfoList.map { it.pts1 },
                                    modifier = Modifier
                                        .weight(1f),
                                    totalPoints = maoUiState.totalPoints.pts1,
                                    color = darkMainColor,
                                    onClickScore = { maoNumber ->
                                        playerInTypeScoreDialog = maoUiState.playerNames[0]
                                        playerOrNumberTeam = 0
                                        maoNumberBeingTyped = maoNumber
                                        isTypeScoreDialogShown = true
                                    }
                                )
                                Divider(
                                    modifier = Modifier
                                        .fillMaxHeight()
                                        .width(1.dp)
                                )
                                PointColumn(
                                    scoreList = maoUiState.maoInfoList.map { it.pts2 },
                                    modifier = Modifier
                                        .weight(1f),
                                    totalPoints = maoUiState.totalPoints.pts2,
                                    color = darkMainColor,
                                    onClickScore = { maoNumber ->
                                        playerInTypeScoreDialog = maoUiState.playerNames[1]
                                        playerOrNumberTeam = 1
                                        maoNumberBeingTyped = maoNumber
                                        isTypeScoreDialogShown = true
                                    }
                                )
                                Divider(
                                    modifier = Modifier
                                        .fillMaxHeight()
                                        .width(1.dp)
                                )
                                PointColumn(
                                    scoreList = maoUiState.maoInfoList.map { it.pts3 },
                                    modifier = Modifier
                                        .weight(1f),
                                    totalPoints = maoUiState.totalPoints.pts3,
                                    color = darkMainColor,
                                    onClickScore = { maoNumber ->
                                        playerInTypeScoreDialog = maoUiState.playerNames[2]
                                        playerOrNumberTeam = 2
                                        maoNumberBeingTyped = maoNumber
                                        isTypeScoreDialogShown = true
                                    }
                                )
                            }
                        }
                    }
                    MatchType.Doubles -> {
                        Column(
                            modifier = Modifier
                                .padding(top = 10.dp, bottom = 10.dp, start = 5.dp, end = 5.dp)
                                .fillMaxWidth()
                                .verticalScroll(rememberScrollState()),
                            verticalArrangement = Arrangement.spacedBy(5.dp)
                        ) {
                            Row(
                                modifier = Modifier
                                    .height(IntrinsicSize.Min)
                            ) {
                                PointColumn(
                                    scoreList = maoUiState.maoInfoList.map { it.pts1 },
                                    modifier = Modifier
                                        .weight(1f),
                                    totalPoints = maoUiState.totalPoints.pts1,
                                    color = darkMainColor,
                                    onClickScore = { maoNumber ->
                                        playerInTypeScoreDialog = "${maoUiState.playerNames[0]} / ${maoUiState.playerNames[1]}"
                                        playerOrNumberTeam = 0
                                        maoNumberBeingTyped = maoNumber
                                        isTypeScoreDialogShown = true
                                    }
                                )
                                Divider(
                                    modifier = Modifier
                                        .fillMaxHeight()
                                        .width(1.dp)
                                )
                                PointColumn(
                                    scoreList = maoUiState.maoInfoList.map { it.pts2 },
                                    modifier = Modifier
                                        .weight(1f),
                                    totalPoints = maoUiState.totalPoints.pts2,
                                    color = darkMainColor,
                                    onClickScore = { maoNumber ->
                                        playerInTypeScoreDialog = "${maoUiState.playerNames[1]} / ${maoUiState.playerNames[2]}"
                                        playerOrNumberTeam = 1
                                        maoNumberBeingTyped = maoNumber
                                        isTypeScoreDialogShown = true
                                    }
                                )
                            }
                        }
                    }
                }
            }
            if (isTypeScoreDialogShown) {
                TypeScoreDialog(
                    playerName = playerInTypeScoreDialog,
                    value = maoUiState.scoreTyped,
                    onValueChange = onTypeScore ,
                    onDismissRequest = {
                        onDismissTypeScoreDialog()
                        isTypeScoreDialogShown = false
                    },
                    onConfirmClick = {
                        onConfirmTypeScoreDialog(playerOrNumberTeam, maoNumberBeingTyped)
                        isTypeScoreDialogShown = false
                    },
                )
            }
            if (isWinnerByWoDialogShown) {
                WinnerByWoDialog(
                    playerNames = maoUiState.playerNames,
                    onDismissRequest = { isWinnerByWoDialogShown = false },
                    onConfirmClick = { winnerPlayerByWo ->
                        onCreateWoMao(winnerPlayerByWo)
                        isWinnerByWoDialogShown = false
                    },
                    matchType = maoUiState.matchType,
                )
            }
        }
    }
}

@Composable
fun DropDownMaoMenu(
    isDropMenuExpanded: Boolean,
    onDismissRequest: () -> Unit,
    onWinnerByWo: () -> Unit,
    isDeleteLastMaoEnabled: Boolean,
    onDeleteLastMao: () -> Unit,
) {
    DropdownMenu(
        expanded = isDropMenuExpanded,
        onDismissRequest = onDismissRequest,
    ) {
        DropdownMenuItem(
            text = {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(5.dp)
                ) {
                    Icon(
                        imageVector = Icons.Filled.DeleteForever,
                        contentDescription = null
                    )
                    Text(text = stringResource(R.string.delete_last_mao))
                }
            },
            onClick = onDeleteLastMao,
            enabled = isDeleteLastMaoEnabled,
        )
        DropdownMenuItem(
            text = {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(5.dp)
                ) {
                    Icon(
                        imageVector = Icons.Filled.Handshake,
                        contentDescription = null
                    )
                    Text(text = stringResource(R.string.winner_by_wo))
                }
            },
            onClick = onWinnerByWo
        )
    }
}

@Composable
fun PointColumn(
    scoreList: List<Int>,
    totalPoints: Int,
    color: Color,
    onClickScore: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val woPoints = 20000
    Column(
        modifier = modifier
            .padding(5.dp),
        verticalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            scoreList.forEachIndexed { index, score ->
                if (score == woPoints || score == -woPoints) {
                    Text(
                        text = stringResource(R.string.wo),
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentWidth(),
                        fontStyle = FontStyle.Italic,
                        color = if(score == woPoints) Color(0xFF0D34F8) else Color(0xFFA30000)
                    )
                } else {
                    Text(
                        text = score.toString(),
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentWidth()
                            .clickable { onClickScore(index + 1) },
                        fontStyle = FontStyle.Italic,
                        color = when {
                            score < 0 -> Color(0xFFFF4444)
                            score > 0 -> Color(0xFF0F8307)
                            else -> color
                        },
                    )
                }
            }
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            Divider()
            Text(
                text = totalPoints.toString(),
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentWidth(),
                fontStyle = FontStyle.Italic,
                fontWeight = FontWeight.Bold,
                color = color,
                fontSize = 24.sp
            )
        }
    }
}

@Composable
fun WinnerByWoDialog(
    playerNames: List<String>,
    onDismissRequest: () -> Unit,
    onConfirmClick: (String) -> Unit,
    matchType: MatchType,
    modifier: Modifier = Modifier,
) {
    var selectedName: String? by rememberSaveable { mutableStateOf(null) }

    AlertDialog(
        onDismissRequest = onDismissRequest,
        title = { Text(text = stringResource(id = R.string.wo)) },
        text = {
            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
            ) {
                Text(
                    text = when(matchType) {
                        MatchType.Singles -> "Qual jogador venceu por W0?"
                        MatchType.Doubles -> "Qual dupla venceu por WO?"
                    },
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF021CC5),
                )
                when(matchType) {
                    MatchType.Singles -> {
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

                    MatchType.Doubles -> {
                        playerNames.forEachIndexed { index, person ->
                            if (index == 0 || index == 2) {
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
                                    Text(text = "$person / ${playerNames[index + 1]}")
                                }
                            }
                        }
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TypeScoreDialog(
    playerName: String,
    value: String,
    onValueChange: (String) -> Unit,
    onDismissRequest: () -> Unit,
    onConfirmClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    var selectedName: String? by rememberSaveable { mutableStateOf(null) }

    AlertDialog(
        onDismissRequest = onDismissRequest,
        title = {},
        text = {
            TextField(
                value = value,
                onValueChange = onValueChange,
                label = { Text(
                    text = "Pontuação de $playerName",
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1
                )
                },
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.Transparent,
                    focusedLabelColor = Color.Black,
                    cursorColor = Color.Black,
                    focusedIndicatorColor = Color.Black
                ),
                textStyle = TextStyle(
                    fontSize = 40.sp,
                    textAlign = TextAlign.Center
                ),
                modifier = Modifier
                    .height(IntrinsicSize.Min),
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
            )
        },
        dismissButton = {
            IconButton(
                onClick = onDismissRequest,
            ) {
                Icon(
                    imageVector = Icons.Filled.Close,
                    contentDescription = null,
                    tint = Color(0xFFFF0000),
                )
            }
        },
        confirmButton = {
            IconButton(
                onClick = onConfirmClick,
            ) {
                Icon(
                    imageVector = Icons.Filled.Check,
                    contentDescription = null,
                    tint = Color(0xFF4CAF50),
                )
            }
        },
        modifier = modifier
    )
}

@Preview
@Composable
fun TypeScoreDialogPreview() {
    PlacarBuracoTheme {
        TypeScoreDialog(
            playerName = "Zinho",
            onDismissRequest = { /*TODO*/ },
            onConfirmClick = {},
            value = "1280",
            onValueChange = {},
        )
    }
}

@Preview
@Composable
fun MaoScreenPreview() {
    PlacarBuracoTheme {
        MaoScreenSecundaria(
            maoUiState = MaoUiState(
                matchType = MatchType.Singles,
                pernaNum = 2,
                playerNames = listOf("Painho", "Mainha", "Zinho", "Gian"),
                maoInfoList = listOf(
                    MaoInfo(2, 10, 430, 225),
                    MaoInfo(3, 180, 155, 800),
                    MaoInfo(25,-70, 350, -125),
                    MaoInfo(82,1025, 750, 250),
                    MaoInfo(14,-20000, 20000, -20000),
                ),
                totalPoints = MaoInfo(null, 1140, 1670, 1240),
                startingPlayer = "Zinho"
            ),
            onBackClick = { /*TODO*/ },
            onCreateMao = {},
            onTypeScore = {},
            onDismissTypeScoreDialog = {},
            onConfirmTypeScoreDialog = {_, _ ->},
            onCreateWoMao = {},
            onDeleteLastMao = {}
        )
    }
}