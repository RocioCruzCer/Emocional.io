package mx.edu.dsi_code.notasmvvm.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
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
fun IntroScreen(navController: NavController) {

    // Tipografías personalizadas
    val poppinsFont = FontFamily(Font(R.font.poppins_black, FontWeight.Black))
    val montserratFont = FontFamily(Font(R.font.montserrat_regular, FontWeight.Normal))

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(24.dp)
    ) {

        // Flecha atrás
        IconButton(
            onClick = { /* Acción de volver */ },
            modifier = Modifier.align(Alignment.TopStart)
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Volver",
                tint = Color(0xFF0356A0)
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Spacer(modifier = Modifier.height(48.dp))

            // Título
            Text(
                text = "Bienvenido a \n \n Emocional.io",
                fontSize = 35.sp,
                fontWeight = FontWeight.Black,
                fontFamily = poppinsFont,
                color = Color(0xFF0356A0),
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            // Cuerpo del texto
            Text(
                text = "“¡Bienvenido a emocional.io! Este es tu espacio seguro para explorar y expresar tus emociones.”\n\n" +
                        "Aquí podrás escribir libremente y reflexionar sobre tus sentimientos. " +
                        "Cada emoción cuenta tu historia, y estamos aquí para acompañarte en este camino.",
                fontSize = 16.sp,
                color = Color(0xFF444444),
                textAlign = TextAlign.Center,
                lineHeight = 22.sp,
                fontFamily = montserratFont
            )
        }

        // Indicadores + Botón "Siguiente"
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(bottom = 24.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            // Indicadores de progreso
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(start = 16.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(8.dp)
                        .background(Color.LightGray, shape = CircleShape)
                )
                Box(
                    modifier = Modifier
                        .width(24.dp)
                        .height(8.dp)
                        .background(Color(0xFF0356A0), shape = RoundedCornerShape(50))
                )
                Box(
                    modifier = Modifier
                        .size(8.dp)
                        .background(Color.LightGray, shape = CircleShape)
                )

            }

            // Botón Siguiente (Botón real mejor que un Text clickable)
            Button(
                onClick = {
                    navController.navigate("main_screen") {
                        popUpTo("login_screen") { inclusive = true }
                    }
                },
                shape = RoundedCornerShape(50),
                modifier = Modifier
                    .padding(end = 16.dp)
                    .height(45.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF0567B2),
                    contentColor = Color.White
                )
            ) {
                Text(
                    text = "Continuar",
                    fontSize = 16.sp,
                    fontFamily = montserratFont,
                    fontWeight = FontWeight.SemiBold
                )
            }

        }
    }
}
