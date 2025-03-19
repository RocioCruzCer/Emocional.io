package mx.edu.dsi_code.notasmvvm.screens


import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import mx.edu.dsi_code.notasmvvm.navigation.BottomBar
import mx.edu.dsi_code.notasmvvm.navigation.BottomNavGraph

@Composable
fun MainScreen() {

    val navController = rememberNavController()

    Scaffold(
        bottomBar = {
            BottomBar(navController = navController)
        }
    ) { innerPadding ->
        BottomNavGraph(navController = navController, modifier = Modifier.padding(innerPadding))
    }
}
