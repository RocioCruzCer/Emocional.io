package mx.edu.dsi_code.notasmvvm.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import mx.edu.dsi_code.notasmvvm.R

@Composable
fun NameInputScreen(navController: NavController) {

    val poppinsFont = FontFamily(Font(R.font.poppins_black, FontWeight.Black))
    val montserratFont = FontFamily(Font(R.font.montserrat_regular, FontWeight.Normal))


    var name by remember { mutableStateOf("") }
    var nameError by remember { mutableStateOf("") }

    // Función para validar el nombre
    fun isValidName(name: String): Boolean {
        return name.isNotEmpty()
    }

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
            text = "¿Cómo puedo \n \n llamarte?",
            fontSize = 32.sp,
            fontWeight = FontWeight.Black,
            fontFamily = poppinsFont,
            color = Color(0xFF0356A0),
            modifier = Modifier
                .padding(top = 48.dp, bottom = 24.dp)
                .fillMaxWidth(),
            textAlign = TextAlign.Start
        )


        // Campo de Nombre
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
        // Mostrar error si el nombre es inválido
        if (nameError.isNotEmpty()) {
            Text(
                text = nameError,
                color = Color.Red,
                fontFamily = montserratFont,
                fontSize = 12.sp,
                modifier = Modifier.padding(start = 16.dp)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Botón de Enviar
        Button(
            onClick = {
                // Validar el nombre antes de navegar
                if (isValidName(name)) {
                    // Si el nombre es válido, navegar a la pantalla de inicio
                    navController.navigate("start_screen")
                } else {
                    // Si el nombre no es válido, mostrar error
                    nameError = "El nombre no puede estar vacío"
                }
            },
            shape = RoundedCornerShape(50),
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF0356A0),
                contentColor = Color.White
            )
        )
        {
            Text(
                text = "Enviar",
                fontSize = 16.sp,
                fontFamily = montserratFont
            )
        }
    }
}


