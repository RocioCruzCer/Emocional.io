package mx.edu.dsi_code.notasmvvm.screens


import android.util.Log
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import mx.edu.dsi_code.notasmvvm.R
import mx.edu.dsi_code.notasmvvm.model.Emotion
import androidx.compose.foundation.border
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import mx.edu.dsi_code.notasmvvm.api.ApiService
import mx.edu.dsi_code.notasmvvm.model.Note
import kotlinx.datetime.LocalDateTime
import mx.edu.dsi_code.notasmvvm.model.Emocion
import mx.edu.dsi_code.notasmvvm.model.Nota
import kotlinx.datetime.*
import mx.edu.dsi_code.notasmvvm.viewmodel.LoginViewModel
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.material3.SnackbarHost
import kotlinx.coroutines.launch
import retrofit2.Response
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

@Composable
fun AddScreen(navController: NavController, loginViewModel: LoginViewModel = viewModel()) {
    val userId = loginViewModel.userId.value
    var selectedEmotion by remember { mutableStateOf<Emotion?>(null) }
    var noteText by remember { mutableStateOf("") }

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val haptic = LocalHapticFeedback.current

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
            // Botón de regreso usando NavController
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Volver",
                    tint = Color(0xFF0356A0)
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Nueva Nota",
                fontSize = 28.sp,
                color = Color(0xFF0356A0),
                modifier = Modifier.padding(bottom = 16.dp)
            )

            Text(
                text = "Selecciona una emoción",
                fontSize = 16.sp,
                color = Color(0xFF444444),
                modifier = Modifier.padding(bottom = 16.dp)
            )

            EmocionesConIcono(
                emotions = listOf(
                    Emotion("Feliz", 0, Color(0xFFFFEB3B), 1),
                    Emotion("Ansia", 0, Color(0xFF9C27B0), 2),
                    Emotion("Enojo", 0, Color(0xFFF44336), 3),
                    Emotion("Triste", 0, Color(0xFF2196F3), 4),
                    Emotion("Calma", 0, Color(0xFF4CAF50), 5)
                ),
                selectedEmotion = selectedEmotion,
                onEmotionSelected = { selectedEmotion = it }
            )

            Spacer(modifier = Modifier.height(32.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White, RoundedCornerShape(12.dp))
                    .border(1.dp, Color(0xFFE0E0E0), RoundedCornerShape(12.dp))
                    .padding(horizontal = 16.dp, vertical = 12.dp)
            ) {
                Text(
                    text = "Escribe una nota",
                    fontSize = 14.sp,
                    color = Color(0xFF444444),
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                BasicTextField(
                    value = noteText,
                    onValueChange = { noteText = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(160.dp),
                    textStyle = TextStyle(
                        color = Color.Black,
                        fontSize = 16.sp
                    ),
                    cursorBrush = SolidColor(Color(0xFF0356A0)),
                    decorationBox = { innerTextField ->
                        Box(Modifier.fillMaxSize()) {
                            if (noteText.isEmpty()) {
                                Text(
                                    text = "Escribe aquí tu nota...",
                                    color = Color.Gray
                                )
                            }
                            innerTextField()
                        }
                    }
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Botón guardar
        /*    Button(
                onClick = {
                    if (selectedEmotion != null && noteText.isNotBlank()) {
                        // Feedback háptico
                        haptic.performHapticFeedback(HapticFeedbackType.LongPress)

                        // Llamada a la API para agregar la nota
                        scope.launch {
                            try {
                                val response = ApiService.RetrofitClient.apiService.addNote(
                                    Nota(
                                        id_nota = 0,  // Si la API maneja la generación del id, dejamos en 0
                                        id_usuario = userId ?: 0, // Usamos el userId del login
                                        id_emocion = selectedEmotion?.id ?: 0, // Asignamos el ID de la emoción seleccionada
                                        texto = noteText
                                    )
                                )

                                // Verificar la respuesta
                                if (response.isSuccessful) {
                                    snackbarHostState.showSnackbar("¡Nota guardada con éxito!")
                                } else {
                                    // Log para obtener el cuerpo del error
                                    Log.d("AddNoteError", "Error: ${response.errorBody()?.string()}")
                                    snackbarHostState.showSnackbar("Error al guardar la nota. Código: ${response.code()}")
                                }
                            } catch (e: Exception) {
                                snackbarHostState.showSnackbar("Error al guardar la nota. Excepción: ${e.message}")
                            }
                        }
                    } else {
                        // Mostrar mensaje si no se seleccionó emoción o no se escribió texto
                        scope.launch {
                            snackbarHostState.showSnackbar("Selecciona una emoción y escribe tu nota")
                        }
                    }

                    // Limpiar los campos fuera de la corutina
                    selectedEmotion = null
                    noteText = ""
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp)
                    .shadow(4.dp, RoundedCornerShape(50.dp)),
                shape = RoundedCornerShape(50.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF0356A0)
                )
            ) {
                Text(
                    text = "Guardar Nota",
                    fontSize = 18.sp,
                    color = Color.White
                )
            }*/

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}


