package mx.edu.dsi_code.notasmvvm.screens

import android.content.Context
import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.ui.platform.LocalContext
import mx.edu.dsi_code.notasmvvm.api.RetrofitClient
import mx.edu.dsi_code.notasmvvm.data.UserPreferences
import mx.edu.dsi_code.notasmvvm.model.Emotion
import mx.edu.dsi_code.notasmvvm.model.Nota


fun getMonthName(monthNumber: Int): String {
    return Month(monthNumber)
        .getDisplayName(TextStyle.FULL, Locale("es", "ES"))
}

fun getDaysInMonth(year: Int, month: Int): Int {
    val date = java.time.YearMonth.of(year, month)
    return date.lengthOfMonth()
}

// Función para obtener el ID del usuario desde SharedPreferences
fun getUserId(context: Context): Int {
    val sharedPreferences = context.getSharedPreferences("UserPreferences", Context.MODE_PRIVATE)
    val userId = sharedPreferences.getInt("USER_ID", -1)
    Log.d("UserPreferences", "ID Usuario recuperado: $userId")
    return userId
}

@Composable
fun CalendarScreen(navController: NavController) {
    val context = LocalContext.current  // Obtén el contexto de la actividad
    val userPreferences = UserPreferences(context)  // Instancia de UserPreferences
    var userId by remember { mutableStateOf<String?>(null) }

    // Cargar el ID del usuario al inicio
    LaunchedEffect(true) {
        userPreferences.userIdFlow.collect { id ->
            userId = id
        }
    }

    // Si no hay usuario, mostramos un mensaje o redirigimos
    if (userId == null) {
        Text("Usuario no encontrado, redirigiendo al login...")
        return
    }

    val today = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date
    var currentMonth by remember { mutableStateOf(today.monthNumber) }
    var currentYear by remember { mutableStateOf(today.year) }
    var selectedDate by remember { mutableStateOf(today) }

    // Lista para almacenar las notas de la fecha seleccionada
    var notas by remember { mutableStateOf<List<Nota>>(emptyList()) }
    var loading by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    // Función para obtener las notas de la API para el usuario con ID dinámico
    suspend fun obtenerNotas(fecha: String) {
        val userIdInt = userId?.toInt() ?: 0  // Asegúrate de que el userId sea un número entero
        Log.d("APIRequest", "ID Usuario: $userIdInt, Fecha: $fecha")  // Log para verificar los parámetros

        loading = true
        try {
            // Enviar la fecha en formato "YYYY-MM-DD"
            val response = RetrofitClient.apiService.obtenerNotasPorFecha(userIdInt, fecha)
            Log.d("APIResponse", "Respuesta: ${response.body()}")  // Log para ver la respuesta completa

            if (response.isSuccessful) {
                val data = response.body()?.Data
                if (data.isNullOrEmpty()) {
                    errorMessage = "No hay notas registradas este día"
                } else {
                    notas = data
                    errorMessage = null
                }
            } else {
                errorMessage = "Error al obtener las notas, código: ${response.code()}"
            }
        } catch (e: Exception) {
            Log.e("APIError", "Error al conectar con la API: ${e.message}")  // Log para el error de la conexión
            errorMessage = "Error al conectar con la API"
        }
        loading = false
    }

    // Filtra las fechas con notas para el usuario con ID dinámico
    val datesWithNotes = remember { mutableStateListOf<LocalDate>() }
    LaunchedEffect(Unit) {
        loading = true
        try {
            val currentMonthString = "${currentYear}-${currentMonth.toString().padStart(2, '0')}"
            val response = RetrofitClient.apiService.obtenerNotasPorFecha(userId?.toInt() ?: 0, currentMonthString)  // Cambiar este endpoint si es necesario para obtener todas las fechas del mes
            if (response.isSuccessful) {
                val data = response.body()?.Data
                if (!data.isNullOrEmpty()) {
                    // Solo agregar las fechas donde haya notas
                    datesWithNotes.clear()
                    data.forEach { nota ->
                        val noteDate = LocalDate.parse(nota.fecha)  // Asumiendo que la fecha es un string en formato YYYY-MM-DD
                        datesWithNotes.add(noteDate)
                    }
                } else {
                    errorMessage = "No se encontraron notas para este mes"
                }
            } else {
                errorMessage = "Error al obtener las fechas de notas"
            }
        } catch (e: Exception) {
            Log.e("APIError", "Error al conectar con la API para obtener fechas: ${e.message}")
            errorMessage = "Error al conectar con la API"
        }
        loading = false
    }

    // Modificar el comportamiento del calendario al seleccionar una fecha
    val selectedDateString = "${selectedDate.year}-${selectedDate.monthNumber.toString().padStart(2, '0')}-${selectedDate.dayOfMonth.toString().padStart(2, '0')}"
    LaunchedEffect(selectedDate) {
        obtenerNotas(selectedDateString)  // Llamamos a la función con el contexto para obtener el idUsuario
    }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
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
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround) {
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
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround) {
                    repeat(7) { columnIndex ->
                        val dayNumber = rowIndex * 7 + columnIndex - firstDayOfMonth + 1
                        if (dayNumber in 1..daysInMonth) {
                            val date = LocalDate(currentYear, currentMonth, dayNumber)
                            Box(
                                modifier = Modifier
                                    .size(40.dp)
                                    .background(
                                        color = if (selectedDate == date) Color(0xFF0567B2) else
                                            if (datesWithNotes.contains(date)) Color(0xFF9C27B0) else Color.Transparent,
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

        // Mostrar las notas
        if (loading) {
            Text("Cargando notas...")
        } else if (errorMessage != null) {
            Text(errorMessage ?: "No se encontraron notas")
        } else {
            notas.forEach { nota ->
                Card(
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.padding(vertical = 8.dp),
                  // elevation = 4.dp,  // Mantener la elevación en dp
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFF2F2F2)) // Usando colors en lugar de backgroundColor
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "Emoción: ${nota.texto}",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF0356A0)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Fecha: ${nota.fecha}",
                            fontSize = 14.sp,
                            color = Color.Gray
                        )
                    }
                }

            }
        }
    }
}
