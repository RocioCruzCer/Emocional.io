package mx.edu.dsi_code.notasmvvm.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.compose.ui.res.painterResource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import mx.edu.dsi_code.notasmvvm.api.RetrofitClient
import mx.edu.dsi_code.notasmvvm.model.Nota
import mx.edu.dsi_code.notasmvvm.R
import mx.edu.dsi_code.notasmvvm.data.UserPreferences

@Composable
fun ProfileScreen(navController: NavController) {
    val poppinsFont = FontFamily(Font(R.font.poppins_black, FontWeight.Black))
    val montserratFont = FontFamily(Font(R.font.montserrat_regular, FontWeight.Normal))

    // Estado para manejar las notas, error y estado de carga
    var notas by remember { mutableStateOf<List<Nota>>(emptyList()) }
    var error by remember { mutableStateOf<String?>(null) }
    var loading by remember { mutableStateOf(false) }
    var showDialog by remember { mutableStateOf(false) }
    var notaToDelete by remember { mutableStateOf<Nota?>(null) }

    // Obtener el ID del usuario desde UserPreferences
    val context = LocalContext.current
    val userPreferences = UserPreferences(context)
    var userId by remember { mutableStateOf<String?>(null) }

    // Cargar el ID del usuario al inicio
    LaunchedEffect(true) {
        userPreferences.userIdFlow.collect { id ->
            userId = id
        }
    }

    // Si no hay usuario, mostramos un mensaje o redirigimos
    if (userId == null) {
        // Opcionalmente, podrías redirigir a una pantalla de login si el ID es null
        Text("Usuario no encontrado, redirigiendo al login...")
        return
    }

    // Lanzamos la llamada a la API cuando el Composable se monta
    LaunchedEffect(userId) {
        loading = true
        try {
            val response = RetrofitClient.apiService.getNotasPorUsuario(userId?.toInt() ?: 0) // Convertimos el userId a Int
            if (response.isSuccessful) {
                val data = response.body()?.Data
                notas = data ?: emptyList()
                error = if (notas.isEmpty()) "No se han registrado notas" else null
            } else {
                error = "Error al obtener las notas."
            }
        } catch (e: Exception) {
            error = "Error al conectar con la API: ${e.message}"
            Log.e("ProfileScreen", "Error al obtener notas: ${e.message}")
        }
        loading = false
    }

    // Función para eliminar una nota
    fun eliminarNota(notaId: Int) {
        loading = true
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = RetrofitClient.apiService.eliminarNotaPorId(notaId)
                withContext(Dispatchers.Main) {
                    if (response.isSuccessful) {
                        // Si se eliminó, actualizamos las notas
                        notas = notas.filter { it.id_nota != notaId }
                        Log.d("ProfileScreen", "Nota eliminada correctamente.")
                    } else {
                        error = "Error al eliminar la nota."
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    error = "Error al conectar con la API: ${e.message}"
                    Log.e("ProfileScreen", "Error al eliminar nota: ${e.message}")
                }
            }
            loading = false
        }
    }

    // Mostrar un cuadro de confirmación antes de eliminar la nota
    if (showDialog && notaToDelete != null) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text(text = "Confirmar Eliminación") },
            text = { Text(text = "¿Estás seguro de que deseas eliminar esta nota?") },
            confirmButton = {
                TextButton(
                    onClick = {
                        // Eliminar la nota
                        eliminarNota(notaToDelete!!.id_nota)
                        showDialog = false
                    }
                ) {
                    Text("Sí")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDialog = false }) {
                    Text("Cancelar")
                }
            }
        )
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp)
    ) {
        // Título de perfil
        item {
            Text(
                text = "Tu Perfil",
                fontFamily = poppinsFont,
                fontSize = 28.sp,
                color = Color(0xFF0356A0),
                modifier = Modifier.padding(bottom = 16.dp)
            )
        }

        // Mostrar error si hay
        if (!error.isNullOrEmpty()) {
            item {
                Text(
                    text = error ?: "",
                    color = Color.Red,
                    fontFamily = montserratFont,
                    fontSize = 16.sp,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
            }
        }

        if (notas.isNotEmpty()) {
            item {
                Text(
                    text = "Tus Notas",
                    fontFamily = montserratFont,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFF0567B2),
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                notas.forEach { nota ->
                    Card(
                        shape = RoundedCornerShape(12.dp),
                        elevation = CardDefaults.cardElevation(4.dp),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFF5F5F5)),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = "Emoción: ${nota.emocion.emocionNombre}\n${nota.texto}",
                                        fontFamily = montserratFont,
                                        fontSize = 16.sp,
                                        color = Color.DarkGray
                                    )
                                }

                                // Ícono de la papelera para eliminar la nota
                                IconButton(
                                    onClick = {
                                        notaToDelete = nota
                                        showDialog = true
                                    },
                                    modifier = Modifier.padding(8.dp)
                                ) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.ic_baseline_delete_24),
                                        contentDescription = "Eliminar Nota",
                                        tint = Color(0xFF00468D)
                                    )
                                }
                            }
                        }
                    }
                }
            }
        } else {
            item {
                Text(
                    text = "No se han registrado notas",
                    color = Color.Gray,
                    fontFamily = montserratFont,
                    fontSize = 16.sp
                )
            }
        }

        // Mostrar estado de carga
        if (loading) {
            item {
                Text(
                    text = "Cargando notas...",
                    fontFamily = montserratFont,
                    fontSize = 16.sp,
                    color = Color.Gray,
                    modifier = Modifier.padding(top = 16.dp)
                )
            }
        }
    }
}
