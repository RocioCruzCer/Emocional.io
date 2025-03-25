package mx.edu.dsi_code.notasmvvm.screens

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import mx.edu.dsi_code.notasmvvm.model.User
import mx.edu.dsi_code.notasmvvm.model.UserResponse
import retrofit2.Response
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.*
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.*
import mx.edu.dsi_code.notasmvvm.R
import retrofit2.Call
import retrofit2.Callback
import mx.edu.dsi_code.notasmvvm.api.ApiService

@Composable
fun RegisterScreen(navController: NavController) {

    val poppinsFont = FontFamily(Font(R.font.poppins_black, FontWeight.Black))
    val montserratFont = FontFamily(Font(R.font.montserrat_regular, FontWeight.Normal))

    var name by remember { mutableStateOf("") } // Nombre o apodo
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var confirmPasswordVisible by remember { mutableStateOf(false) }

    var emailError by remember { mutableStateOf("") }
    var passwordError by remember { mutableStateOf("") }
    var confirmPasswordError by remember { mutableStateOf("") }
    var nameError by remember { mutableStateOf("") }

    val scope = rememberCoroutineScope() // Definir el CoroutineScope

    // Función para validar el correo
    fun isValidEmail(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    // Función para validar la contraseña (al menos 8 caracteres)
    fun isValidPassword(password: String): Boolean {
        return password.length >= 8
    }

    // Función para validar que las contraseñas coincidan
    fun doPasswordsMatch(password: String, confirmPassword: String): Boolean {
        return password == confirmPassword
    }

    // Función para validar el nombre (no vacío)
    fun isValidName(name: String): Boolean {
        return name.isNotEmpty()
    }

    // Función para registrar al usuario
    fun registerUser() {
        // Verificar que todos los campos sean válidos
        if (!isValidEmail(email)) {
            emailError = "Correo inválido"
        }
        if (!isValidPassword(password)) {
            passwordError = "La contraseña debe tener al menos 8 caracteres"
        }
        if (!doPasswordsMatch(password, confirmPassword)) {
            confirmPasswordError = "Las contraseñas no coinciden"
        }
        if (!isValidName(name)) {
            nameError = "El nombre no puede estar vacío"
        }

        // Si todo es válido, hacer la solicitud a la API para registrar al usuario
        if (isValidEmail(email) && isValidPassword(password) && doPasswordsMatch(password, confirmPassword) && isValidName(name)) {
            val user = User(email, password, name) // Crea el objeto con los datos

            // Llamar a Retrofit para registrar el usuario dentro de una corrutina
            scope.launch {
                try {
                    val response = ApiService.RetrofitClient.apiService.registerUser(user)

                    if (response.isSuccessful) {
                        // Redirigir a la siguiente pantalla si el registro es exitoso
                        navController.navigate("start_screen")
                    } else {
                        // Manejar el error si la respuesta no es exitosa
                        Log.d("RegisterUserError", "Error: ${response.errorBody()?.string()}")
                    }
                } catch (e: Exception) {
                    // Manejar error de conexión
                    Log.d("RegisterUserError", "Excepción: ${e.message}")
                }
            }
        }
    }

    // UI Composables
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

        // Campo de Nombre/Apodo
        OutlinedTextField(
            value = name,
            onValueChange = {
                name = it
                nameError = "" // Limpiar el error al cambiar el valor
            },
            placeholder = {
                Text(
                    text = "Tu nombre o apodo",
                    fontFamily = montserratFont
                )
            },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "Nombre",
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
            isError = nameError.isNotEmpty()
        )
        // Mostrar error si el nombre está vacío
        if (nameError.isNotEmpty()) {
            Text(
                text = nameError,
                color = Color.Red,
                fontFamily = montserratFont,
                fontSize = 12.sp,
                modifier = Modifier.padding(start = 16.dp)
            )
        }

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
                passwordError = ""
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

        // Campo de Confirmar Contraseña
        OutlinedTextField(
            value = confirmPassword,
            onValueChange = {
                confirmPassword = it
                confirmPasswordError = "" // Limpiar el error al cambiar el valor
            },
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
            ),
            isError = confirmPasswordError.isNotEmpty()
        )
        // Mostrar error si las contraseñas no coinciden
        if (confirmPasswordError.isNotEmpty()) {
            Text(
                text = confirmPasswordError,
                color = Color.Red,
                fontFamily = montserratFont,
                fontSize = 12.sp,
                modifier = Modifier.padding(start = 16.dp)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Botón Crear Cuenta
        Button(
            onClick = { registerUser() }, // Llamar a la función de registro
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

        // Texto final de registro
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
                    navController.navigate("login_screen")
                }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))
    }
}




