package mx.edu.dsi_code.notasmvvm.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import mx.edu.dsi_code.notasmvvm.R
import mx.edu.dsi_code.notasmvvm.model.Emotion
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.draw.clip
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

@Composable
fun AddScreen(navController: NavController) {

    val montserratFont = FontFamily(Font(R.font.montserrat_regular, FontWeight.Normal))
    val poppinsFont = FontFamily(Font(R.font.poppins_black, FontWeight.Black))

    val emotions = listOf(
        Emotion("Feliz", R.drawable.ic_happy, Color(0xFFFFEB3B)),
        Emotion("Miedo/Ansiedad", R.drawable.ic_scared, Color(0xFF9C27B0)),
        Emotion("Enojado", R.drawable.ic_angry, Color(0xFFF44336)),
        Emotion("Triste", R.drawable.ic_sad, Color(0xFF2196F3)),
        Emotion("Calma", R.drawable.ic_calm, Color(0xFF4CAF50))
    )

    var selectedEmotion by remember { mutableStateOf<Emotion?>(null) }
    var noteText by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        // Flecha de regreso
        Row(modifier = Modifier.fillMaxWidth()) {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Volver",
                    tint = Color(0xFF0356A0)
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Título
        Text(
            text = "Escribe una Nota",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF0356A0),
            fontFamily = poppinsFont
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Subtítulo
        Text(
            text = "¿Cómo te sientes?",
            fontSize = 16.sp,
            color = Color.Black,
            fontFamily = montserratFont
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Lista de emociones
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            emotions.forEach { emotion ->
                Box(
                    modifier = Modifier
                        .size(64.dp)
                        .clip(CircleShape)
                        .background(
                            if (selectedEmotion == emotion) emotion.color.copy(alpha = 0.8f)
                            else emotion.color
                        )
                        .border(
                            width = if (selectedEmotion == emotion) 3.dp else 0.dp,
                            color = Color(0xFF0356A0),
                            shape = CircleShape
                        )
                        .clickable { selectedEmotion = emotion },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(id = emotion.iconRes),
                        contentDescription = emotion.name,
                        tint = Color.Black,
                        modifier = Modifier.size(32.dp)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Caja de nota con mejor diseño
        BasicTextField(
            value = noteText,
            onValueChange = { noteText = it },
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp)
                .clip(RoundedCornerShape(20.dp))
                .background(Color(0xFFF2F2F2))
                .border(1.dp, Color(0xFFE0E0E0), RoundedCornerShape(20.dp))
                .padding(16.dp),
            textStyle = TextStyle(
                color = Color.Black,
                fontSize = 16.sp,
                fontFamily = montserratFont
            ),
            cursorBrush = SolidColor(Color(0xFF0356A0)),
            decorationBox = { innerTextField ->
                if (noteText.isEmpty()) {
                    Text(
                        text = "Escribe aquí tu nota...",
                        color = Color.Gray,
                        fontFamily = montserratFont
                    )
                }
                innerTextField()
            }
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Botón Guardar Nota
        Button(
            onClick = {
                // Aquí guarda la nota con la emoción seleccionada
                if (selectedEmotion != null && noteText.isNotBlank()) {
                    // Acción guardar
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(55.dp),
            shape = RoundedCornerShape(50.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF0356A0)
            )
        ) {
            Text(
                text = "Guardar Nota",
                fontSize = 18.sp,
                fontFamily = montserratFont
            )
        }
    }
}


