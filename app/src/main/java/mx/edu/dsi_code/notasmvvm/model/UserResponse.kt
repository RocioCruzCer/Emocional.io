package mx.edu.dsi_code.notasmvvm.model

data class UserResponse(
    val id_usuario: Int,
    val correo: String,
    val password: String,
    val apodo: String
)
