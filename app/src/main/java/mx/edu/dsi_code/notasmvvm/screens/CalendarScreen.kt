package mx.edu.dsi_code.notasmvvm.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import kotlinx.datetime.*
import kotlinx.datetime.TimeZone
import java.time.format.TextStyle
import java.util.*
import kotlinx.datetime.Month
import java.util.Locale

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

                            Box(
                                modifier = Modifier
                                    .size(40.dp)
                                    .background(
                                        color = if (selectedDate == date) Color(0xFF0567B2) else Color.Transparent,
                                        shape = CircleShape
                                    )
                                    .clickable {
                                        selectedDate = date
                                    },
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = dayNumber.toString(),
                                    color = if (selectedDate == date) Color.White else Color.Black,
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

        // Selected date display (optional)
        Text(
            text = "Fecha seleccionada: ${selectedDate.dayOfMonth} de ${getMonthName(selectedDate.monthNumber)} de ${selectedDate.year}",
            fontSize = 16.sp,
            color = Color.Gray
        )
    }
}




