package br.me.vitorcsouza.bitfocus.ui.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import br.me.vitorcsouza.bitfocus.ui.presentation.completion.CompletionScreenContent
import br.me.vitorcsouza.bitfocus.ui.presentation.completion.CompletionStates
import br.me.vitorcsouza.bitfocus.ui.presentation.home.HomeScreen
import br.me.vitorcsouza.bitfocus.ui.presentation.home.HomeScreenContent
import br.me.vitorcsouza.bitfocus.ui.presentation.home.HomeStates
import br.me.vitorcsouza.bitfocus.ui.presentation.lab.LabContent
import br.me.vitorcsouza.bitfocus.ui.presentation.lab.LabScreen
import br.me.vitorcsouza.bitfocus.ui.presentation.setup.SetupContent
import br.me.vitorcsouza.bitfocus.ui.presentation.setup.SetupScreen
import br.me.vitorcsouza.bitfocus.ui.presentation.setup.SetupStates
import br.me.vitorcsouza.bitfocus.ui.theme.BitFocusTheme

@Composable
fun BitFocusNavGraph(
    navController: NavHostController,
    isPreview: Boolean = false
) {
    NavHost(
        navController = navController,
        startDestination = "setup",
        enterTransition = { fadeIn(animationSpec = tween(500)) },
        exitTransition = { fadeOut(animationSpec = tween(500)) },
    ) {
        composable("setup") {
            if (isPreview) {
                SetupContent(
                    state = SetupStates(),
                    onEnergyLevelSelected = {},
                    onDurationChanged = {},
                    onGoalChanged = {},
                    onStartSession = { duration, goal ->
                        navController.navigate("home/$duration/$goal")
                    }
                )
            } else {
                SetupScreen(onStartSession = { duration, goal ->
                    navController.navigate("home/$duration/$goal")
                })
            }
        }
        
        composable(
            route = "home/{duration}/{goal}",
            arguments = listOf(
                navArgument("duration") { type = NavType.IntType },
                navArgument("goal") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val duration = backStackEntry.arguments?.getInt("duration") ?: 0
            val goal = backStackEntry.arguments?.getString("goal") ?: "Focus"
            
            if (isPreview) {
                HomeScreenContent(
                    state = HomeStates(currentGoal = goal),
                    onToggleTimer = {},
                    onOpenAnalytics = { navController.navigate("lab") }
                )
            } else {
                HomeScreen(
                    onFinished = { navController.navigate("completion/$duration/$goal") },
                    onOpenAnalytics = { navController.navigate("lab") }
                )
            }
        }

        composable("lab") { 
            if (isPreview) {
                LabContent(
                    totalBits = 142,
                    totalHours = 68.5,
                    streakDays = 12,
                    weeklyTrend = listOf(4, 6, 5, 8, 7, 9, 6),
                    isLoading = false
                )
            } else {
                LabScreen()
            }
        }

        composable(
            route = "completion/{duration}/{goal}",
            arguments = listOf(
                navArgument("duration") { type = NavType.IntType },
                navArgument("goal") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val duration = backStackEntry.arguments?.getInt("duration") ?: 25
            val goal = backStackEntry.arguments?.getString("goal") ?: "Focus"

            CompletionScreenContent(
                state = CompletionStates(
                    durationDisplay = "${duration}:00",
                    goalName = goal
                ),
                onClose = {
                    navController.navigate("setup") {
                        popUpTo("setup") { inclusive = true }
                    }
                }
            )
        }
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun BitFocusNavGraphPreview() {
    val navController = rememberNavController()
    BitFocusTheme {
        BitFocusNavGraph(navController = navController, isPreview = true)
    }
}
