package mx.edu.dsi_code.notasmvvm.screens

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import mx.edu.dsi_code.notasmvvm.R
import kotlinx.coroutines.delay
import androidx.compose.ui.graphics.Brush
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.border
import androidx.compose.runtime.remember
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset// Compose básico
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.*
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material3.Surface
import androidx.compose.animation.core.*
import androidx.compose.ui.draw.clip


@Composable
fun SplashScreen(navController: NavController) {

    val scale = remember { Animatable(0f) }
    val alpha = remember { Animatable(0f) }

    val montserratFont = FontFamily(
        Font(R.font.montserrat_regular, FontWeight.Normal)
    )


    // Animación de entrada
    LaunchedEffect(true) {
        scale.animateTo(
            targetValue = 1f,
            animationSpec = tween(
                durationMillis = 1000,
                easing = FastOutSlowInEasing
            )
        )
        alpha.animateTo(
            targetValue = 1f,
            animationSpec = tween(
                durationMillis = 1000
            )
        )

        delay(3000)

        navController.navigate("welcome_screen") {
            popUpTo("splash_screen") { inclusive = true }
        }
    }

    // Fondo gradiente
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.linearGradient(
                    colors = listOf(
                        Color(0xFF0356A0),
                        Color(0xFF00468D)
                    ),
                    start = Offset(0f, 0f),
                    end = Offset.Infinite
                )
            )
    ) {

        // Círculos animados en el fondo
        AnimatedCircles()

        // Logo central con animación de escala y alpha
        Box(
            modifier = Modifier
                .align(Alignment.Center)
                .scale(scale.value)
                .alpha(alpha.value)
                .size(200.dp)
                .background(Color.White.copy(alpha = 0.1f), shape = CircleShape)
                .border(2.dp, Color.White.copy(alpha = 0.3f), CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo_splash), // Tu imagen
                contentDescription = "Logo Circular",
                modifier = Modifier
                    .size(150.dp)  // Tamaño de la imagen
                    .clip(CircleShape)  // Corta la imagen en círculo
            )

        }

        // Nombre de la app abajo
        Text(
            text = "Emocional.io",
            color = Color.White,
            fontSize = 32.sp,
            fontWeight = FontWeight.Normal,
            fontFamily = montserratFont,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 80.dp)
                .alpha(alpha.value)
        )
    }
}

@Composable
fun AnimatedCircles() {
    val infiniteTransition = rememberInfiniteTransition()

    val circle1Offset by infiniteTransition.animateFloat(
        initialValue = -100f,
        targetValue = 100f,
        animationSpec = infiniteRepeatable(
            animation = tween(3000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    val circle2Scale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.5f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    Canvas(modifier = Modifier.fillMaxSize()) {
        drawCircle(
            color = Color(0xFF0567B2).copy(alpha = 0.2f),
            radius = size.minDimension / 3 * circle2Scale,
            center = Offset(size.width / 4 + circle1Offset, size.height / 3)
        )

        drawCircle(
            color = Color(0xFF0356A0).copy(alpha = 0.15f),
            radius = size.minDimension / 4,
            center = Offset(size.width * 0.8f - circle1Offset, size.height / 1.5f)
        )
    }
}





