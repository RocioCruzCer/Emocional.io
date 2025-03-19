package mx.edu.dsi_code.notasmvvm.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.vector.VectorProperty
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import mx.edu.dsi_code.notasmvvm.R
import java.time.LocalDate
import java.time.format.DateTimeFormatter


@Composable
fun HomeScreen(navController: NavController) {

    val poppinsFont = FontFamily(Font(R.font.poppins_black, FontWeight.Black))
    val montserratFont = FontFamily(Font(R.font.montserrat_regular, FontWeight.Normal))

    // Fecha actual
    val currentDate = LocalDate.now()
    val formatter = DateTimeFormatter.ofPattern("dd 'de' MMMM yyyy")
    val formattedDate = currentDate.format(formatter)

    // Progreso (simulado)
    val progress = 0.65f // 65%

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            // Título
            Text(
                text = "Tus Emociones",
                fontFamily = montserratFont,
                fontWeight = FontWeight.SemiBold,
                fontSize = 24.sp,
                color = Color(0xFF0356A0)
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Círculo de progreso
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.size(300.dp)
            ) {
                // Círculo de fondo
                Canvas(modifier = Modifier.size(300.dp)) {
                    drawCircle(
                        color = Color(0xFFE0E0E0),
                        style = Stroke(width = 20f)
                    )
                }

                // Progreso
                Canvas(modifier = Modifier.size(300.dp)) {
                    drawArc(
                        color = Color(0xFF0567B2),
                        startAngle = -90f,
                        sweepAngle = 360 * progress,
                        useCenter = false,
                        style = Stroke(width = 20f, cap = StrokeCap.Round)
                    )
                }

                // Contenido del círculo
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = " $formattedDate",
                        fontFamily = montserratFont,
                        fontSize = 14.sp,
                        color = Color.Gray
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = "Hoy es un buen día para\nser tu mejor versión.",
                        fontFamily = montserratFont,
                        fontSize = 18.sp,
                        color = Color(0xFF0356A0),
                        textAlign = TextAlign.Center
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Botón inferior
            Button(
                onClick = { navController.navigate("add_screen") },
                shape = RoundedCornerShape(50),
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF0567B2),
                    contentColor = Color.White
                )
            ) {
                Text(
                    "¿Cómo te sientes hoy? ☺",
                    fontFamily = montserratFont,
                    fontSize = 16.sp
                )
            }
        }
    }
}

