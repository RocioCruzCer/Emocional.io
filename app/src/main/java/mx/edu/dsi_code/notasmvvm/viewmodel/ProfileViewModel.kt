package mx.edu.dsi_code.notasmvvm.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import mx.edu.dsi_code.notasmvvm.api.RetrofitClient
import mx.edu.dsi_code.notasmvvm.model.NotasResponse
import retrofit2.Response

class ProfileViewModel : ViewModel() {

    // Función para eliminar una nota
    suspend fun eliminarNotaPorId(notaId: Int): Response<NotasResponse> {
        return withContext(Dispatchers.IO) {
            RetrofitClient.apiService.eliminarNotaPorId(notaId)
        }
    }
}
