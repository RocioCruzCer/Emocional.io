package mx.edu.dsi_code.notasmvvm.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import mx.edu.dsi_code.notasmvvm.R


@Composable
fun RegisterScreen(navController: NavController) {

    val poppinsFont = FontFamily(Font(R.font.poppins_black, FontWeight.Black))
    val montserratFont = FontFamily(Font(R.font.montserrat_regular, FontWeight.Normal))

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var confirmPasswordVisible by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 32.dp, vertical = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {

        // Título
        Text(
            text = "Crear Cuenta",
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
            )
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
                    Icon(imageVector = icon, contentDescription = "Mostrar/Ocultar contraseña")
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
            )
        )

        // Campo de Confirmar Contraseña
        OutlinedTextField(
            value = confirmPassword,
            onValueChange = { confirmPassword = it },
            placeholder = {
                Text(
                    text = "Confirmar Contraseña",
                    fontFamily = montserratFont
                )
            },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Lock,
                    contentDescription = "Confirmar Contraseña",
                    tint = Color(0xFF00468D)
                )
            },
            trailingIcon = {
                val icon = if (confirmPasswordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff
                IconButton(onClick = { confirmPasswordVisible = !confirmPasswordVisible }) {
                    Icon(imageVector = icon, contentDescription = "Mostrar/Ocultar contraseña")
                }
            },
            singleLine = true,
            shape = RoundedCornerShape(50),
            visualTransformation = if (confirmPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedContainerColor = Color(0xFFF2F2F2),
                focusedContainerColor = Color(0xFFF2F2F2),
                focusedBorderColor = Color.Transparent,
                unfocusedBorderColor = Color.Transparent
            )
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Botón Crear Cuenta
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
                text = "Crear Cuenta",
                fontSize = 16.sp,
                fontFamily = montserratFont
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Texto separador
        Text(
            text = "• O Continúa Con •",
            color = Color.Gray,
            textAlign = TextAlign.Center,
            fontFamily = montserratFont,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Iconos de redes sociales
        Row(
            horizontalArrangement = Arrangement.spacedBy(24.dp)
        ) {
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

        Spacer(modifier = Modifier.height(16.dp))

        // Texto de "¿Ya tienes cuenta?"
        Row {
            Text(
                text = "¿Ya tienes cuenta? ",
                color = Color.Gray,
                fontFamily = montserratFont
            )
            Text(
                text = "Inicia Sesión",
                color = Color(0xFF0567B2),
                fontWeight = FontWeight.Bold,
                fontFamily = montserratFont,
                textDecoration = TextDecoration.Underline,
                modifier = Modifier.clickable {
                    navController.navigate("login_screen") {
                        // Evita volver atrás a esta pantalla si no quieres que regresen
                        popUpTo("register_screen") { inclusive = true }
                    }
                }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))
    }
}


