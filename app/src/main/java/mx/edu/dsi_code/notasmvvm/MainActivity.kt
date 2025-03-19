package mx.edu.dsi_code.notasmvvm


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import mx.edu.dsi_code.notasmvvm.navigation.AppNavigation
import mx.edu.dsi_code.notasmvvm.ui.theme.NotasMVVMTheme



class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NotasMVVMTheme {
                val navController = rememberNavController()
                AppNavigation(navController = navController)
            }
        }
    }
}
























