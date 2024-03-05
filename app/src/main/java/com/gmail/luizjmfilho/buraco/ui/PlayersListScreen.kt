package com.gmail.luizjmfilho.buraco.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PersonAddAlt1
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.gmail.luizjmfilho.buraco.R
import com.gmail.luizjmfilho.buraco.ui.theme.PlacarBuracoTheme

@Composable
fun PlayersListScreenPrimaria(
    onSelectPlayer: (String) -> Unit,
    modifier: Modifier = Modifier,
    playersListViewModel: PlayersListViewModel = hiltViewModel()
) {
    val playersListUiState by playersListViewModel.uiState.collectAsState()

    PlayersListScreenSecundaria(
        playersListUiState = playersListUiState,
        onAddPlayer = playersListViewModel::onAddPlayer,
        onTypeNewPlayer = playersListViewModel::onTypeNewPlayer,
        onSelectPlayer = { playerName ->
            val index = playersListViewModel.getInfo().take(1)
            val matchType = playersListViewModel.getInfo().substring(1,8)
            val playersNames = playersListViewModel.getInfo().substring(8).split(",").toMutableList()
            playersNames[index.toInt()] = playerName
            onSelectPlayer(index + matchType + playersNames.joinToString(","))
        },
        onFloatingButtonClick = playersListViewModel::onFloatingButtonClick,
        onDismissDialog = playersListViewModel::onDismissDialog,
        modifier = modifier,
    )
}

@Composable
fun PlayersListScreenSecundaria(
    playersListUiState: PlayersListUiState,
    onAddPlayer: () -> Unit,
    onTypeNewPlayer: (String) -> Unit,
    onSelectPlayer: (String) -> Unit,
    onFloatingButtonClick: () -> Unit,
    onDismissDialog: () -> Unit,
    modifier: Modifier = Modifier,
) {

    Scaffold(
        modifier = modifier,
        floatingActionButton = {
            FloatingActionButton(
                onClick = onFloatingButtonClick,
                containerColor = Color(0xFF021CC5),
                contentColor = Color.White
            ) {
                Icon(
                    imageVector = Icons.Filled.PersonAddAlt1,
                    contentDescription = null
                )
            }
        },
    ) { scaffoldPaddings ->
        Box(
            modifier = Modifier
                .padding(scaffoldPaddings)
                .fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .padding(10.dp)
            ) {
                for (player in playersListUiState.playersList) {
                    Text(
                        text = player,
                        modifier = Modifier
                            .height(48.dp)
                            .fillMaxHeight()
                            .fillMaxWidth()
                            .clickable {
                                onSelectPlayer(player)
                            }
                            .wrapContentHeight()
                    )
                    Divider()
                }
            }
            if (playersListUiState.isDialogShown) {
                AddPlayerDialog(
                    onDismissRequest = onDismissDialog,
                    value = playersListUiState.playerBeingAdded,
                    onValueChange = {
                        onTypeNewPlayer(it)
                    },
                    onAddPlayer = {
                        onAddPlayer()
                    },
                    isError = (playersListUiState.nameError != null),
                    supportingText = {
                        when (playersListUiState.nameError) {
                            NameError.Empty -> Text(text = "Nome vazio")
                            NameError.Exists -> Text(text = "Esse nome já existe")
                            else -> {}
                        }
                    }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddPlayerDialog(
    value: String,
    onValueChange: (String) -> Unit,
    onDismissRequest: () -> Unit,
    onAddPlayer: () -> Unit,
    isError: Boolean,
    supportingText: @Composable () -> Unit,
    modifier: Modifier = Modifier,
) {
    val labelAndIconSchoolTextFieldColor = Color(0xFF021CC5)
    val containerTextFieldColor = Color(0xFF7DDAFF)

    AlertDialog(
        onDismissRequest = onDismissRequest,
        title = { Text(text = stringResource(R.string.add_a_player))},
        text = {
            Column(
                modifier = Modifier
                    .width(IntrinsicSize.Max)
            ) {
                TextField(
                    value = value,
                    onValueChange = onValueChange,
                    trailingIcon = {
                        Icon(
                            imageVector = Icons.Filled.Person,
                            contentDescription = null,
                            tint = Color(0xFF021CC5)
                        )
                    },
                    label = { Text(text = stringResource(R.string.new_player))},
                    colors = TextFieldDefaults.textFieldColors(
                        unfocusedLabelColor = labelAndIconSchoolTextFieldColor,
                        focusedLabelColor = labelAndIconSchoolTextFieldColor,
                        containerColor = containerTextFieldColor,
                        unfocusedTrailingIconColor = labelAndIconSchoolTextFieldColor,
                        focusedTrailingIconColor = labelAndIconSchoolTextFieldColor,
                        unfocusedIndicatorColor = labelAndIconSchoolTextFieldColor,
                        focusedIndicatorColor = labelAndIconSchoolTextFieldColor,
                        focusedTextColor = labelAndIconSchoolTextFieldColor,
                        unfocusedTextColor = labelAndIconSchoolTextFieldColor,
                    ),
                    isError = isError,
                    supportingText = supportingText
                )
                Text(
                    text = "${value.length}/15",
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentWidth(Alignment.End)
                )

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
                onClick = onAddPlayer
            ) {
                Text(
                    text = stringResource(R.string.add),
                    color = Color(0xFF021CC5)
                )
            }
        },
        modifier = modifier,
    )
}

@Preview
@Composable
fun PlayersListScreenPreview() {
    PlacarBuracoTheme {
        PlayersListScreenSecundaria(
            playersListUiState = PlayersListUiState(
                playersList = listOf("Zinho", "Gian", "Luiz (pai)", "Minesa"),
                playerBeingAdded = "Bonitão"
            ),
            onAddPlayer = {},
            onTypeNewPlayer = {},
            onSelectPlayer = {},
            onDismissDialog = {},
            onFloatingButtonClick = {}
        )
    }
}