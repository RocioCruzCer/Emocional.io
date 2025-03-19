package mx.edu.dsi_code.notasmvvm.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import mx.edu.dsi_code.notasmvvm.screens.BottomBarScreen
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import mx.edu.dsi_code.notasmvvm.screens.*

@Composable
fun BottomNavGraph(navController: NavHostController, modifier: Modifier = Modifier) {

    NavHost(
        navController = navController,
        startDestination = BottomBarScreen.Home.route,
        modifier = modifier
    ) {
        composable(route = BottomBarScreen.Home.route) {
            HomeScreen(navController)
        }
        composable(route = BottomBarScreen.Calendar.route) {
            CalendarScreen(navController)
        }
        composable(route = BottomBarScreen.Add.route) {
            AddScreen(navController)
        }
        composable(route = BottomBarScreen.Profile.route) {
            ProfileScreen(navController)
        }
    }
}
