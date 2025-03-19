package mx.edu.dsi_code.notasmvvm.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.*
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.*
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.*
import androidx.navigation.NavController
import mx.edu.dsi_code.notasmvvm.R


@Composable
fun LoginScreen(navController: NavController) {

    val poppinsFont = FontFamily(Font(R.font.poppins_black, FontWeight.Black))
    val montserratFont = FontFamily(Font(R.font.montserrat_regular, FontWeight.Normal))

    val keyboardController = LocalSoftwareKeyboardController.current

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 32.dp, vertical = 16.dp)
            .imePadding(), // Para subir el contenido cuando se muestra el teclado
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {

        // Título
        Text(
            text = "Iniciar Sesión",
            fontSize = 32.sp,
            fontWeight = FontWeight.Black,
            fontFamily = poppinsFont,
            color = Color(0xFF0356A0),
            modifier = Modifier.padding(top = 32.dp, bottom = 24.dp)
        )

        // Campo de Correo
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            placeholder = {
                Text(
                    text = "Correo Electrónico",
                    fontFamily = montserratFont
                )
            },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Email,
                    contentDescription = "Correo",
                    tint = Color(0xFF00468D)
                )
            },
            singleLine = true,
            shape = RoundedCornerShape(50),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedContainerColor = Color(0xFFF2F2F2),
                focusedContainerColor = Color(0xFFF2F2F2),
                focusedBorderColor = Color.Transparent,
                unfocusedBorderColor = Color.Transparent
            ),
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next)
        )

        // Campo de Contraseña
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            placeholder = {
                Text(
                    text = "Contraseña",
                    fontFamily = montserratFont
                )
            },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Lock,
                    contentDescription = "Contraseña",
                    tint = Color(0xFF00468D)
                )
            },
            trailingIcon = {
                val icon = if (passwordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff
                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(imageVector = icon, contentDescription = "Mostrar/ocultar contraseña")
                }
            },
            singleLine = true,
            shape = RoundedCornerShape(50),
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedContainerColor = Color(0xFFF2F2F2),
                focusedContainerColor = Color(0xFFF2F2F2),
                focusedBorderColor = Color.Transparent,
                unfocusedBorderColor = Color.Transparent
            ),
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(
                onDone = { keyboardController?.hide() }
            )
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Botón de Iniciar Sesión
        Button(
            onClick = {
                navController.navigate("start_screen")
            },
            shape = RoundedCornerShape(50),
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF0356A0),
                contentColor = Color.White
            )
        ) {
            Text(
                text = "Iniciar Sesión",
                fontSize = 16.sp,
                fontFamily = montserratFont
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Sección "O continúa con" y redes sociales
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "• O Continúa Con •",
                color = Color.Gray,
                textAlign = TextAlign.Center,
                fontFamily = montserratFont,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            // Iconos de redes sociales
            Row(horizontalArrangement = Arrangement.spacedBy(24.dp)) {

                // Google
                IconButton(
                    onClick = { /* Acción Google */ },
                    modifier = Modifier
                        .size(48.dp)
                        .background(Color.White, CircleShape)
                        .border(width = 1.dp, color = Color(0xFF0567B2), shape = CircleShape)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_google),
                        contentDescription = "Google",
                        modifier = Modifier.size(24.dp),
                        contentScale = ContentScale.Fit
                    )
                }

                // Apple
                IconButton(
                    onClick = { /* Acción Apple */ },
                    modifier = Modifier
                        .size(48.dp)
                        .background(Color.White, CircleShape)
                        .border(width = 1.dp, color = Color(0xFF0567B2), shape = CircleShape)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_apple),
                        contentDescription = "Apple",
                        modifier = Modifier.size(24.dp),
                        contentScale = ContentScale.Fit
                    )
                }

                // Facebook
                IconButton(
                    onClick = { /* Acción Facebook */ },
                    modifier = Modifier
                        .size(48.dp)
                        .background(Color.White, CircleShape)
                        .border(width = 1.dp, color = Color(0xFF0567B2), shape = CircleShape)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_facebook),
                        contentDescription = "Facebook",
                        modifier = Modifier.size(24.dp),
                        contentScale = ContentScale.Fit
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Texto final de registro
            Row {
                Text(
                    text = "¿No tienes cuenta? ",
                    color = Color.Gray,
                    fontFamily = montserratFont
                )
                Text(
                    text = "Regístrate",
                    color = Color(0xFF0567B2),
                    fontWeight = FontWeight.Bold,
                    fontFamily = montserratFont,
                    textDecoration = TextDecoration.Underline,
                    modifier = Modifier.clickable {
                        navController.navigate("register_screen")
                    }
                )
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}



