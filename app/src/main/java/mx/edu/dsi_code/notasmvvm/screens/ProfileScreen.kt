package mx.edu.dsi_code.notasmvvm.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import mx.edu.dsi_code.notasmvvm.R


@Composable
fun ProfileScreen(navController: NavController) {

    val poppinsFont = FontFamily(Font(R.font.poppins_black, FontWeight.Black))
    val montserratFont = FontFamily(Font(R.font.montserrat_regular, FontWeight.Normal))

    // Simulación de notas
    val notas = listOf(
        "Hoy me sentí feliz porque salí a caminar.",
        "Día complicado en el trabajo, pero lo superé."
    )

    // Datos de ejemplo para la gráfica semanal (valores ficticios)
    val datosSemana = listOf(3, 5, 2, 4, 6, 1, 4)

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp)
    ) {

        // Título de perfil
        item {
            Text(
                text = "Tu Perfil",
                fontFamily = poppinsFont,
                fontSize = 28.sp,
                color = Color(0xFF0356A0),
                modifier = Modifier.padding(bottom = 16.dp)
            )
        }

        // Sección de Notas
        item {
            Text(
                text = "Tus Notas",
                fontFamily = montserratFont,
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color(0xFF0567B2),
                modifier = Modifier.padding(bottom = 8.dp)
            )

            notas.forEach { nota ->
                Card(
                    shape = RoundedCornerShape(12.dp),
                    elevation = CardDefaults.cardElevation(4.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFF5F5F5)),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                ) {
                    Text(
                        text = nota,
                        fontFamily = montserratFont,
                        fontSize = 16.sp,
                        color = Color.DarkGray,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
        }

        // Sección de Estadísticas
        item {
            Text(
                text = "Estadísticas Semanales",
                fontFamily = montserratFont,
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color(0xFF0567B2),
                modifier = Modifier.padding(bottom = 16.dp)
            )

            // Gráfica sencilla (simulación de barras)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .background(Color(0xFFF5F5F5), shape = RoundedCornerShape(12.dp))
                    .padding(16.dp)
            ) {
                Canvas(modifier = Modifier.fillMaxSize()) {
                    val barWidth = size.width / (datosSemana.size * 2)
                    val maxValor = datosSemana.maxOrNull() ?: 1

                    datosSemana.forEachIndexed { index, valor ->
                        val barHeight = (valor / maxValor.toFloat()) * size.height

                        drawRect(
                            color = Color(0xFF0567B2),
                            topLeft = androidx.compose.ui.geometry.Offset(
                                x = (index * 2 + 1) * barWidth,
                                y = size.height - barHeight
                            ),
                            size = androidx.compose.ui.geometry.Size(
                                width = barWidth,
                                height = barHeight
                            )
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

