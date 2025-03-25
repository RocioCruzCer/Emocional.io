package mx.edu.dsi_code.notasmvvm.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import mx.edu.dsi_code.notasmvvm.api.ApiService
import mx.edu.dsi_code.notasmvvm.model.Emocion
import kotlinx.coroutines.Dispatchers
import retrofit2.Response

class EmocionViewModel : ViewModel() {

    // Función suspendida para obtener emociones
  /*  private suspend fun getEmocionesFromApi(): List<Emocion> {
        val apiService = RetrofitClient.retrofitInstance.create(ApiService::class.java)
        try {
            // Llamada a la API
            val response: Response<List<Emocion>> = apiService.getEmociones() // La respuesta es un Response<List<Emocion>>
            return if (response.isSuccessful) {
                response.body() ?: emptyList() // Si la respuesta es exitosa, obtenemos el cuerpo
            } else {
                emptyList() // Si la respuesta no es exitosa, retornamos una lista vacía
            }
        } catch (e: Exception) {
            return emptyList() // En caso de error, retornamos una lista vacía
        }
    }*/

    // Función para obtener las emociones y exponerlas como LiveData
    fun getEmociones() = liveData(Dispatchers.IO) {
        emit(emptyList<Emocion>()) // Emitimos una lista vacía inicialmente
        try {
            // Llamada a la función suspendida dentro de una corrutina
        //    val emociones = getEmocionesFromApi()
          //  emit(emociones) // Emitimos las emociones obtenidas de la API
        } catch (e: Exception) {
            emit(emptyList<Emocion>()) // En caso de error, emitimos una lista vacía
        }
    }
}


