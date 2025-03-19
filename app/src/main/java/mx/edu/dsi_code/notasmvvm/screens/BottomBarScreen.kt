package mx.edu.dsi_code.notasmvvm.screens

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomBarScreen(
    val route: String,
    val title: String,
    val icon: ImageVector
) {
    object Home : BottomBarScreen(
        route = "home_screen",
        title = "Inicio",
        icon = Icons.Default.Home
    )
    object Calendar : BottomBarScreen(
        route = "calendar_screen",
        title = "Calendario",
        icon = Icons.Default.DateRange
    )
    object Add : BottomBarScreen(
        route = "add_screen",
        title = "Agregar",
        icon = Icons.Default.Add
    )
    object Profile : BottomBarScreen(
        route = "profile_screen",
        title = "Perfil",
        icon = Icons.Default.Person
    )
}
