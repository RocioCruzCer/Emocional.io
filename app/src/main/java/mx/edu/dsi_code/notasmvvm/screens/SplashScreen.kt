package mx.edu.dsi_code.notasmvvm.screens
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import mx.edu.dsi_code.notasmvvm.R
import kotlinx.coroutines.delay


@Composable
fun SplashScreen(navController: NavController) {


    LaunchedEffect (key1 = true) {
        delay(200)
        navController.navigate("welcome_screen") {
            popUpTo("splash_screen") { inclusive = true }
        }
    }


    val poppinsFont = FontFamily(
        Font(R.font.poppins_black, FontWeight.Black)
    )

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.White
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 120.dp), // Distancia desde abajo
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Bottom // Coloca el contenido abajo
            ) {
                Text(
                    text = "Emocional.io",
                    fontSize = 50.sp,
                    fontWeight = FontWeight.Black,
                    fontFamily = poppinsFont, // ← Aquí aplicamos la tipografía Poppins
                    color = Color(0xFF0356A0) // Color personalizado que pediste
                )
            }
        }
    }
}

/*
@Preview(showBackground = true)
@Composable
fun PreviewSplashScreen() {
    SplashScreen()
}*/
