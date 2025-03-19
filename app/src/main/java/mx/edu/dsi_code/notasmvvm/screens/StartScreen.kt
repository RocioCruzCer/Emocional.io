package mx.edu.dsi_code.notasmvvm.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import mx.edu.dsi_code.notasmvvm.R

@Composable
fun StartScreen(navController: NavController) {

    // Tipografías personalizadas
    val poppinsFont = FontFamily(Font(R.font.poppins_black, FontWeight.Black))
    val montserratFont = FontFamily(Font(R.font.montserrat_regular, FontWeight.Normal))

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {

        // Imagen de fondo
        Image(
            painter = painterResource(id = R.drawable.welcome_background),
            contentDescription = "Imagen de fondo",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        // Capa de oscurecimiento para contraste
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0x66000000)) // Negro semitransparente
        )

        // Contenido principal
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // Título
            Text(
                text = "“Un espacio seguro para tus emociones”",
                color = Color.White,
                fontSize = 26.sp,
                fontWeight = FontWeight.Black,
                fontFamily = poppinsFont,
                textAlign = TextAlign.Center,
                lineHeight = 32.sp,
                modifier = Modifier.padding(horizontal = 16.dp)
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Botón Empezar
            Button(
                onClick = {
                    navController.navigate("intro_screen")
                },
                shape = RoundedCornerShape(50),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF0356A0),
                    contentColor = Color.White
                )
            ) {
                Text(
                    text = "Empezar",
                    fontSize = 16.sp,
                    fontFamily = montserratFont,
                    fontWeight = FontWeight.SemiBold
                )
            }

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

