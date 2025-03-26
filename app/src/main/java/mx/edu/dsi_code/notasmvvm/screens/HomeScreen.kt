package mx.edu.dsi_code.notasmvvm.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
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
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.getValue
import android.util.Log
import androidx.compose.runtime.*
import androidx.navigation.NavController
import mx.edu.dsi_code.notasmvvm.api.RetrofitClient
import mx.edu.dsi_code.notasmvvm.model.EmotionFrequencyResponse
import mx.edu.dsi_code.notasmvvm.R
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import mx.edu.dsi_code.notasmvvm.model.Frecuencia

@Composable
fun HomeScreen(navController: NavController) {
    val poppinsFont = FontFamily(Font(R.font.poppins_black, FontWeight.Black))
    val montserratFont = FontFamily(Font(R.font.montserrat_regular, FontWeight.Normal))

    // Estado para manejar las frecuencias, error y estado de carga
    var frequencies by remember { mutableStateOf<List<Frecuencia>>(emptyList()) }
    var error by remember { mutableStateOf<String?>(null) }
    var loading by remember { mutableStateOf(false) }

    val userId = 43 // Cambia esto por el ID del usuario actual

    // Llamamos a la API para obtener las frecuencias de las emociones
    LaunchedEffect(userId) {
        loading = true
        try {
            val response = RetrofitClient.apiService.getFrecuenciasEmocionales(userId)
            if (response.isSuccessful) {
                val data = response.body()?.Data?.ListaFrecuencias
                frequencies = data ?: emptyList()
                error = if (frequencies.isEmpty()) "No se han registrado emociones" else null
            } else {
                error = "Error al obtener las frecuencias."
            }
        } catch (e: Exception) {
            error = "Error al conectar con la API: ${e.message}"
            Log.e("HomeScreen", "Error al obtener frecuencias: ${e.message}")
        }
        loading = false
    }

    // Función para obtener el color basado en el id_emocion
    fun getEmotionColor(idEmocion: Int): Color {
        return when (idEmocion) {
            1 -> Color(0xFFFFEB3B)
            2 -> Color(0xFF9C27B0)
            3 -> Color(0xFFF44336)
            4 -> Color(0xFF2196F3)
            5 -> Color(0xFF4CAF50)
            else -> Color.Gray
        }
    }

    // Fecha actual
    val currentDate = LocalDate.now()
    val formatter = DateTimeFormatter.ofPattern("dd 'de' MMMM yyyy")
    val formattedDate = currentDate.format(formatter)

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
                fontFamily = poppinsFont,
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

                // Progreso con el color según las emociones
                frequencies.forEachIndexed { index, frequency ->
                    val emotionColor = getEmotionColor(frequency.id_emocion)  // Usamos id_emocion para asignar el color
                    val sweepAngle = 360 * (frequency.frecuencia / frequencies.sumOf { it.frecuencia }.toFloat())

                    // Dibuja el progreso en el círculo
                    Canvas(modifier = Modifier.size(300.dp)) {
                        drawArc(
                            color = emotionColor,
                            startAngle = -90f + (index * sweepAngle),
                            sweepAngle = sweepAngle,
                            useCenter = false,
                            style = Stroke(width = 20f, cap = StrokeCap.Round)
                        )
                    }
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
