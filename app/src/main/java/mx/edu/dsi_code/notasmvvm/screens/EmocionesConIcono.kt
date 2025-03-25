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
