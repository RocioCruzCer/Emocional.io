package mx.edu.dsi_code.notasmvvm.screens


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import mx.edu.dsi_code.notasmvvm.R


@Composable
fun WelcomeScreen(navController: NavController) {

    val poppinsFont = FontFamily(
        Font(R.font.poppins_black, FontWeight.Black)
    )

    val montserratFont = FontFamily(
        Font(R.font.montserrat_regular, FontWeight.Normal)
    )

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.welcome_screen),
            contentDescription = "Fondo Bienvenida",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0x80000000)) // Oscurecimiento
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Bottom
        ) {
            // Título
            Text(
                text = "Bienvenido a\n \nEmocional.io",
                fontSize = 32.sp,
                fontWeight = FontWeight.Black,
                fontFamily = poppinsFont,
                color = Color.White,
                textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )

            // Subtítulo
            Text(
                text = "\"Un espacio seguro para conectar con tus emociones.\"",
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal,
                fontFamily = montserratFont,
                color = Color.White,
                textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                modifier = Modifier.padding(bottom = 32.dp)
            )

            // Botón "Crea una cuenta" -> Navega a register_screen
            Button(
                onClick = {
                    navController.navigate("register_screen")
                },
                shape = RoundedCornerShape(50),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF0356A0),
                    contentColor = Color.White
                )
            ) {
                Text(
                    "Crea una cuenta",
                    fontFamily = montserratFont,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 16.sp
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Botón "Iniciar Sesión" -> Navega a login_screen
            Button(
                onClick = {
                    navController.navigate("login_screen")
                },
                shape = RoundedCornerShape(50),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF00468D),
                    contentColor = Color.White
                )
            ) {
                Text(
                    "Iniciar Sesión",
                    fontFamily = montserratFont,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 16.sp
                )
            }
            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}
