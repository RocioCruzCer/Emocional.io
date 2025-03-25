package mx.edu.dsi_code.notasmvvm.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import kotlinx.datetime.*
import kotlinx.datetime.TimeZone
import java.time.format.TextStyle
import kotlinx.datetime.Month
import java.util.Locale
import android.util.Log
import androidx.compose.ui.res.painterResource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import mx.edu.dsi_code.notasmvvm.R
import androidx.compose.foundation.shape.RoundedCornerShape
import mx.edu.dsi_code.notasmvvm.model.Nota
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import kotlinx.datetime.*
import java.util.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import kotlinx.datetime.*
import java.util.*

// Clase para representar una emoción
data class Emotion(
    val name: String,
    val color: Color,
    val description: String  // Agregamos campo para la descripción
)

// Devuelve el nombre del mes en español
fun getMonthName(monthNumber: Int): String {
    return Month(monthNumber)
        .getDisplayName(TextStyle.FULL, Locale("es", "ES"))
}

// Devuelve el número de días que tiene el mes correspondiente
fun getDaysInMonth(year: Int, month: Int): Int {
    val date = java.time.YearMonth.of(year, month)
    return date.lengthOfMonth()
}

@Composable
fun CalendarScreen(navController: NavController) {
    val today = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date
    var currentMonth by remember { mutableStateOf(today.monthNumber) }
    var currentYear by remember { mutableStateOf(today.year) }
    var selectedDate by remember { mutableStateOf(today) }

    // Estado para controlar si se muestra el diálogo de emociones
    var showEmotionDialog by remember { mutableStateOf(false) }

    // Mapa para almacenar las emociones por fecha
    val emotions = remember { mutableStateMapOf<LocalDate, Emotion>() }

    // Lista de emociones disponibles con descripciones
    val availableEmotions = listOf(
        Emotion(
            "Feliz",
            Color(0xFF4CAF50),  // Verde
            "Te sentiste optimista y con buen humor. Experimentaste alegría y satisfacción durante el día."
        ),
        Emotion(
            "Triste",
            Color(0xFF2196F3),  // Azul
            "Te sentiste decaído o melancólico. Quizás experimentaste alguna pérdida o decepción."
        ),
        Emotion(
            "Enojado",
            Color(0xFFF44336),  // Rojo
            "Sentiste frustración o irritación. Algo te molestó o te hizo sentir indignado."
        ),
        Emotion(
            "Ansioso",
            Color(0xFFFFEB3B),  // Amarillo
            "Experimentaste preocupación o nerviosismo. Sentiste inquietud ante situaciones inciertas."
        ),
        Emotion(
            "Tranquilo",
            Color(0xFF9C27B0),  // Morado
            "Te sentiste en paz y relajado. Experimentaste calma y serenidad a lo largo del día."
        ),
        Emotion(
            "Neutro",
            Color(0xFF9E9E9E),   // Gris
            "No experimentaste emociones intensas. El día transcurrió sin altibajos emocionales significativos."
        )
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Top bar for month/year navigation
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            IconButton(onClick = {
                if (currentMonth == 1) {
                    currentMonth = 12
                    currentYear--
                } else {
                    currentMonth--
                }
            }) {
                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Mes anterior")
            }

            Text(
                text = "${getMonthName(currentMonth)} $currentYear",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )

            IconButton(onClick = {
                if (currentMonth == 12) {
                    currentMonth = 1
                    currentYear++
                } else {
                    currentMonth++
                }
            }) {
                Icon(imageVector = Icons.Default.ArrowForward, contentDescription = "Mes siguiente")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Days of the week header
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            listOf("D", "L", "M", "X", "J", "V", "S").forEach { day ->
                Text(
                    text = day,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.weight(1f)
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Dates grid
        val daysInMonth = getDaysInMonth(currentYear, currentMonth)
        val firstDayOfMonth = LocalDate(currentYear, currentMonth, 1).dayOfWeek.isoDayNumber % 7

        val totalGridSlots = daysInMonth + firstDayOfMonth
        val rows = (totalGridSlots / 7) + if (totalGridSlots % 7 > 0) 1 else 0

        Column {
            repeat(rows) { rowIndex ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    repeat(7) { columnIndex ->
                        val dayNumber = rowIndex * 7 + columnIndex - firstDayOfMonth + 1
                        if (dayNumber in 1..daysInMonth) {
                            val date = LocalDate(currentYear, currentMonth, dayNumber)
                            val emotion = emotions[date]

                            // Determina el color de fondo basado en la emoción o en la selección
                            val backgroundColor = when {
                                emotion != null -> emotion.color
                                selectedDate == date -> Color(0xFF0567B2)
                                else -> Color.Transparent
                            }

                            // Determina el color del texto basado en el fondo
                            val textColor = if (backgroundColor != Color.Transparent) Color.White else Color.Black

                            Box(
                                modifier = Modifier
                                    .size(40.dp)
                                    .background(
                                        color = backgroundColor,
                                        shape = CircleShape
                                    )
                                    .clickable {
                                        selectedDate = date
                                        showEmotionDialog = true
                                    },
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = dayNumber.toString(),
                                    color = textColor,
                                    textAlign = TextAlign.Center
                                )
                            }
                        } else {
                            Spacer(modifier = Modifier.size(40.dp))
                        }
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Selected date display
        Text(
            text = "Fecha seleccionada: ${selectedDate.dayOfMonth} de ${getMonthName(selectedDate.monthNumber)} de ${selectedDate.year}",
            fontSize = 16.sp,
            color = Color.Gray
        )

        // Mostrar la emoción seleccionada si existe
        emotions[selectedDate]?.let { emotion ->
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Emoción: ${emotion.name}",
                fontSize = 16.sp,
                color = emotion.color,
                fontWeight = FontWeight.Bold
            )

            // Mostrar la descripción de la emoción
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = emotion.description,
                fontSize = 14.sp,
                color = Color.DarkGray
            )
        }
    }

    // Diálogo de selección de emociones
    if (showEmotionDialog) {
        Dialog(onDismissRequest = { showEmotionDialog = false }) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = "¿Cómo te sentiste el ${selectedDate.dayOfMonth} de ${getMonthName(selectedDate.monthNumber)}?",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )

                    availableEmotions.forEach { emotion ->
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    emotions[selectedDate] = emotion
                                    showEmotionDialog = false
                                }
                                .padding(vertical = 8.dp),
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(24.dp)
                                        .background(emotion.color, CircleShape)
                                )
                                Spacer(modifier = Modifier.width(16.dp))
                                Text(
                                    text = emotion.name,
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Medium
                                )
                            }

                            // Descripción de la emoción
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = emotion.description,
                                fontSize = 14.sp,
                                color = Color.DarkGray,
                                modifier = Modifier.padding(start = 40.dp)
                            )

                            Divider(
                                modifier = Modifier.padding(top = 8.dp),
                                color = Color.LightGray
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        TextButton(onClick = { showEmotionDialog = false }) {
                            Text("Cancelar")
                        }
                    }
                }
            }
        }
    }
}



