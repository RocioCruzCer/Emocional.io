package mx.edu.dsi_code.notasmvvm.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.ui.text.input.ImeAction
import kotlinx.coroutines.launch
import mx.edu.dsi_code.notasmvvm.api.ApiService
import mx.edu.dsi_code.notasmvvm.model.Nota
import mx.edu.dsi_code.notasmvvm.R
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.clickable
import androidx.compose.material3.Button
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import mx.edu.dsi_code.notasmvvm.model.Emotion
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.graphicsLayer
import mx.edu.dsi_code.notasmvvm.model.Emocion
import kotlinx.datetime.*
import java.time.LocalDate
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import mx.edu.dsi_code.notasmvvm.data.UserPreferences

@Composable
fun AddScreen(navController: NavController) {
    val poppinsFont = FontFamily(Font(R.font.poppins_black, FontWeight.Black))
    val montserratFont = FontFamily(Font(R.font.montserrat_regular, FontWeight.Normal))

    var selectedEmotion by remember { mutableStateOf(1) }  // Default emotion (Feliz)
    var noteText by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }

    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    // Obtener el ID del usuario desde UserPreferences
    val context = LocalContext.current
    val userPreferences = UserPreferences(context)
    var userId by remember { mutableStateOf<String?>(null) }

    // Cargar el ID del usuario al inicio
    LaunchedEffect(true) {
        userPreferences.userIdFlow.collect { id ->
            userId = id
        }
    }

    // Si no hay usuario, mostramos un mensaje o redirigimos
    if (userId == null) {
        // Opcionalmente, podrías redirigir a una pantalla de login si el ID es null
        Text("Usuario no encontrado, redirigiendo al login...")
        return
    }

    // Emociones disponibles (sin imágenes, solo nombre, color y ID)
    val emotionsList = listOf(
        Emotion("Feliz", Color(0xFFFFEB3B), 1),
        Emotion("Ansia", Color(0xFF9C27B0), 2),
        Emotion("Enojo", Color(0xFFF44336), 3),
        Emotion("Triste", Color(0xFF2196F3), 4),
        Emotion("Calma", Color(0xFF4CAF50), 5)
    )

    // Función para agregar la nota
    fun addNote() {
        if (noteText.isNotBlank()) {
            isLoading = true
            coroutineScope.launch {
                try {
                    val currentDate = LocalDate.now().toString() // Obtener la fecha actual en formato ISO (YYYY-MM-DD)
                    // Convertimos el objeto Emotion a Emocion
                    val selectedEmotionObject = emotionsList.find { it.id == selectedEmotion }
                    val emocion = Emocion(id_emocion = selectedEmotionObject?.id ?: 0, emocionNombre = selectedEmotionObject?.name ?: "Desconocida")

                    val response = ApiService.RetrofitClient.apiService.addNote(
                        Nota(
                            id_nota = 0,
                            texto = noteText,
                            fecha = currentDate,
                            emocion = emocion,
                            id_usuario = userId?.toInt() ?: 0,  // Convertir el userId a Int
                            id_emocion = selectedEmotion
                        )
                    )

                    if (response.isSuccessful) {
                        snackbarHostState.showSnackbar("Nota guardada con éxito")
                        navController.popBackStack()
                    } else {
                        errorMessage = "Error al guardar la nota."
                        snackbarHostState.showSnackbar("Error: ${response.code()}")
                    }
                } catch (e: Exception) {
                    errorMessage = "Error de conexión: ${e.message}"
                    snackbarHostState.showSnackbar("Error: ${e.message}")
                }
                isLoading = false
            }
        } else {
            errorMessage = "La nota no puede estar vacía."
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        containerColor = Color(0xFFF5F5F5)
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(paddingValues)
                .padding(24.dp)
        ) {
            // Título de la pantalla
            Text(
                text = "Nueva Nota",
                fontSize = 28.sp,
                color = Color(0xFF0356A0),
                fontFamily = poppinsFont,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            // Selector de emoción (por ejemplo: Feliz, Triste, etc.)
            Text(
                text = "Selecciona una emoción",
                fontSize = 16.sp,
                color = Color(0xFF444444),
                fontFamily = montserratFont,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            EmocionesConIcono(
                emotions = emotionsList,
                selectedEmotion = emotionsList.find { it.id == selectedEmotion },
                onEmotionSelected = { selectedEmotion = it.id }
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Campo de texto para la nota

            Text(
                text = "Escribe tu nota",
                fontSize = 16.sp,
                color = Color(0xFF444444),
                fontFamily = montserratFont,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            BasicTextField(
                value = noteText,
                onValueChange = { noteText = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(350.dp)
                    .padding(16.dp)
                    .background(Color.White, shape = RoundedCornerShape(12.dp))
                    .border(1.dp, Color(0xFFE0E0E0), shape = RoundedCornerShape(12.dp)),
                textStyle = TextStyle(color = Color.Black, fontSize = 16.sp, fontFamily = montserratFont),
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(onDone = { /* Ocultar teclado si es necesario */ })
            )


            if (errorMessage.isNotEmpty()) {
                Text(
                    text = errorMessage,
                    color = Color.Red,
                    fontSize = 14.sp,
                    fontFamily = montserratFont,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Botón para guardar la nota
            Button(
                onClick = { addNote() },
                modifier = Modifier.fillMaxWidth().height(55.dp),
                enabled = !isLoading
            ) {
                if (isLoading) {
                    CircularProgressIndicator(color = Color.White, modifier = Modifier.size(24.dp))
                } else {
                    Text("Guardar Nota", fontSize = 18.sp, color = Color.White)
                }
            }
        }
    }
}



@Composable
fun EmocionesConIcono(
    emotions: List<Emotion>,
    selectedEmotion: Emotion?,
    onEmotionSelected: (Emotion) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        emotions.forEach { emotion ->

            val isSelected = selectedEmotion == emotion

            // Escala animada para efecto de rebote
            val scale by animateFloatAsState(
                targetValue = if (isSelected) 1.3f else 1f,
                animationSpec = tween(durationMillis = 300)
            )

            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .size(50.dp)
                        .graphicsLayer {
                            scaleX = scale
                            scaleY = scale
                        }
                        .clickable { onEmotionSelected(emotion) },
                    contentAlignment = Alignment.Center
                ) {
                    // Dibuja la estrella de color
                    StarShape(
                        color = emotion.color,
                        modifier = Modifier.fillMaxSize()
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = emotion.name,
                    fontSize = 12.sp,
                    color = Color(0xFF444444),
                    fontFamily = FontFamily(Font(R.font.montserrat_regular))
                )
            }
        }
    }
}

@Composable
fun StarShape(
    color: Color,
    modifier: Modifier = Modifier
) {
    Canvas(modifier = modifier.size(60.dp)) {
        val path = Path().apply {
            moveTo(size.width * 0.5f, 0f)
            lineTo(size.width * 0.61f, size.height * 0.35f)
            lineTo(size.width, size.height * 0.38f)
            lineTo(size.width * 0.68f, size.height * 0.62f)
            lineTo(size.width * 0.8f, size.height)
            lineTo(size.width * 0.5f, size.height * 0.75f)
            lineTo(size.width * 0.2f, size.height)
            lineTo(size.width * 0.32f, size.height * 0.62f)
            lineTo(0f, size.height * 0.38f)
            lineTo(size.width * 0.39f, size.height * 0.35f)
            close()
        }

        drawPath(
            path = path,
            color = color
        )
    }
}

