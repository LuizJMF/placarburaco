package com.gmail.luizjmfilho.buraco

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.gmail.luizjmfilho.buraco.ui.MatchSummaryScreenPrimaria
import com.gmail.luizjmfilho.buraco.ui.MatchesListScreenPrimaria
import com.gmail.luizjmfilho.buraco.ui.NewMatchScreenPrimaria
import com.gmail.luizjmfilho.buraco.ui.PlayersListScreenPrimaria

@Composable
fun PlacarBuracoNavHost() {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = ScreenNames.MatchesList.name,
    ) {

        composable(
            route = ScreenNames.MatchesList.name
        ) {
            MatchesListScreenPrimaria(
                onCreateNewMatch = { navController.navigate(ScreenNames.NewMatch.name) },
                onSingleGroupClick = { matchType, singleGroupId ->
                    navController.navigate("${ScreenNames.MatchSummary.name}/${listOf(matchType.name, singleGroupId.toString()).joinToString(",")}")
                },
                onDoubleGroupClick = { matchType, doubleGroupId ->
                    navController.navigate("${ScreenNames.MatchSummary.name}/${listOf(matchType.name, doubleGroupId.toString()).joinToString(",")}")
                },
            )
        }

        composable(
            route = ScreenNames.NewMatch.name,
        ) {
            NewMatchScreenPrimaria(
                onChoosePlayer = { info ->
                    navController.navigate("${ScreenNames.PlayersList.name}/$info")
                },
                onGoToMatchesList = { navController.navigate(route = ScreenNames.MatchesList.name) }
            )
        }

        composable(
            route = "${ScreenNames.NewMatch.name}/{playerInfo}",
        ) {
            NewMatchScreenPrimaria(
                onChoosePlayer = { info ->
                    navController.navigate("${ScreenNames.PlayersList.name}/$info")
                },
                onGoToMatchesList = { navController.navigate(route = ScreenNames.MatchesList.name) }
            )
        }

        composable(
            route = "${ScreenNames.PlayersList.name}/{info}",
        ) {
            PlayersListScreenPrimaria(
                onSelectPlayer = { playerInfo ->
                    navController.navigate("${ScreenNames.NewMatch.name}/$playerInfo")
                }
            )
        }

        composable(
            route = "${ScreenNames.MatchSummary.name}/{groupInfo}"
        ) {
            MatchSummaryScreenPrimaria(
                onBackClick = { navController.navigateUp() }
            )
        }
    }
}