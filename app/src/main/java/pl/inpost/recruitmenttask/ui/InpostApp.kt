package pl.inpost.recruitmenttask.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import pl.inpost.recruitmenttask.navigation.ScreenSpec
import pl.inpost.recruitmenttask.navigation.ShipmentsScreenSpec


@Composable
fun InpostApp(navController: NavHostController = rememberNavController()) {
    AppNavHost(navController)
}

@Composable
fun AppNavHost(navController: NavHostController) {
    NavHost(navController = navController, startDestination = ShipmentsScreenSpec.route) {
        ScreenSpec.allScreens.forEach { screen ->
            composable(
                screen.route,
                screen.arguments,
                screen.deepLinks
            ) {
                screen.Content(navController = navController, navBackStackEntry = it)
            }
        }
    }
}