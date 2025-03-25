package mx.edu.dsi_code.notasmvvm.navigation


import android.content.Context
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import mx.edu.dsi_code.notasmvvm.screens.*


@Composable
fun AppNavigation(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = "splash_screen"
    ) {

        composable("splash_screen") {
            SplashScreen(navController)
        }

        composable("welcome_screen") {
            WelcomeScreen(navController)
        }

        composable("login_screen") {
            LoginScreen(navController)
        }

        composable("register_screen") {
            RegisterScreen(navController)
        }
        composable("start_screen") {
            StartScreen(navController)
        }
        composable("intro_screen") {
            IntroScreen(navController)
        }
        composable("home_screen") {
            HomeScreen(navController)
        }
        composable("add_screen") {
            AddScreen(navController)
        }
        composable("calendar_screen") {
            CalendarScreen(navController)
        }
        composable("profile_screen") {
            ProfileScreen(navController)
        }
        composable("name_input_screen") {
            NameInputScreen(navController)
        }
        composable("main_screen") {
            MainScreen() // Llama el menú inferior con las 4 pantallas
        }

    }
}
