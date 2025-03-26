package mx.edu.dsi_code.notasmvvm.model

data class UserResponse(
    val Data: Int,  // El ID del usuario
    val Message: String,
    val HttpStatusCode: Int
)
