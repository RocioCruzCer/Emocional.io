package mx.edu.dsi_code.notasmvvm.navigation

import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import mx.edu.dsi_code.notasmvvm.screens.BottomBarScreen

@Composable
fun BottomBar(navController: NavHostController) {

    val screens = listOf(
        BottomBarScreen.Home,
        BottomBarScreen.Calendar,
        BottomBarScreen.Add,
        BottomBarScreen.Profile
    )

    val navBackStackEntry = navController.currentBackStackEntryAsState().value
    val currentDestination = navBackStackEntry?.destination?.route

    NavigationBar (
        containerColor = MaterialTheme.colorScheme.background
    ) {
        screens.forEach { screen ->
            NavigationBarItem(
                selected = currentDestination == screen.route,
                onClick = {
                    navController.navigate(screen.route) {
                        popUpTo(navController.graph.findStartDestination().id)
                        launchSingleTop = true
                    }
                },
                icon = {
                    Icon(imageVector = screen.icon, contentDescription = screen.title)
                },
                label = {
                    Text(screen.title)
                }
            )
        }
    }
}
