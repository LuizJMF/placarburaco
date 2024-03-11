package com.gmail.luizjmfilho.buraco

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.gmail.luizjmfilho.buraco.ui.MatchesListScreenPrimaria
import com.gmail.luizjmfilho.buraco.ui.NewMatchScreenPrimaria
import com.gmail.luizjmfilho.buraco.ui.PlayersListScreenPrimaria
import com.gmail.luizjmfilho.buraco.ui.theme.PlacarBuracoTheme
import dagger.hilt.android.AndroidEntryPoint

enum class ScreenNames {
    MatchesList,
    NewMatch,
    PlayersList,
    MatchSummary,
    PernasSummary,
    Mao,
}

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PlacarBuracoTheme {
                PlacarBuracoNavHost()
            }
        }
    }
}