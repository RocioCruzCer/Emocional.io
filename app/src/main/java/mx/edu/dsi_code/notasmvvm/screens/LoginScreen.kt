package mx.edu.dsi_code.notasmvvm.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
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
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.*
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.*
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import mx.edu.dsi_code.notasmvvm.R
import mx.edu.dsi_code.notasmvvm.api.ApiService
import mx.edu.dsi_code.notasmvvm.model.Login
import mx.edu.dsi_code.notasmvvm.viewmodel.LoginViewModel


@Composable
fun LoginScreen(navController: NavController, loginViewModel: LoginViewModel = viewModel()) {
    val poppinsFont = FontFamily(Font(R.font.poppins_black, FontWeight.Black))
    val montserratFont = FontFamily(Font(R.font.montserrat_regular, FontWeight.Normal))

    val keyboardController = LocalSoftwareKeyboardController.current

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var emailError by remember { mutableStateOf("") }
    var passwordError by remember { mutableStateOf("") }

    fun isValidEmail(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    fun isValidPassword(password: String): Boolean {
        return password.length >= 8
    }

    fun loginUser() {
        if (isValidEmail(email) && isValidPassword(password)) {
            val login = Login(email, password)

            CoroutineScope(Dispatchers.IO).launch {
                try {
                    val response = ApiService.RetrofitClient.apiService.loginUser(login)
                    withContext(Dispatchers.Main) {
                        if (response.isSuccessful) {
                            val userResponse = response.body()
                            val userId = userResponse?.id_usuario ?: 0

                            // Guardamos el userId en el ViewModel
                            loginViewModel.setUserId(userId)

                            // Si la respuesta es exitosa, navegar a la siguiente pantalla
                            navController.navigate("start_screen")
                        } else {
                            passwordError = "Credenciales inválidas"
                        }
                    }
                } catch (e: Exception) {
                    withContext(Dispatchers.Main) {
                        passwordError = "Error al conectar con el servidor"
                    }
                }
            }
        } else {
            if (!isValidEmail(email)) emailError = "Correo inválido"
            if (!isValidPassword(password)) passwordError = "La contraseña debe tener al menos 8 caracteres"
        }
    }

    // Layout del LoginScreen con los campos de email y password
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 32.dp, vertical = 16.dp)
            .imePadding(), // Para subir el contenido cuando se muestra el teclado
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
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
            onValueChange = {
                email = it
                emailError = "" // Limpiar el error al cambiar el valor
            },
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
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
            isError = emailError.isNotEmpty()
        )
        // Mostrar error si el correo es inválido
        if (emailError.isNotEmpty()) {
            Text(
                text = emailError,
                color = Color.Red,
                fontFamily = montserratFont,
                fontSize = 12.sp,
                modifier = Modifier.padding(start = 16.dp)
            )
        }

        // Campo de Contraseña
        OutlinedTextField(
            value = password,
            onValueChange = {
                password = it
                passwordError = "" // Limpiar el error al cambiar el valor
            },
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
            ),
            isError = passwordError.isNotEmpty()
        )
        // Mostrar error si la contraseña es inválida
        if (passwordError.isNotEmpty()) {
            Text(
                text = passwordError,
                color = Color.Red,
                fontFamily = montserratFont,
                fontSize = 12.sp,
                modifier = Modifier.padding(start = 16.dp)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Botón de Iniciar Sesión
        Button(
            onClick = { loginUser() },
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





