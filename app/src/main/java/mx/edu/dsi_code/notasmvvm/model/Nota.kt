package mx.edu.dsi_code.notasmvvm.model

data class Nota(
    val id_nota: Int,
    val texto: String,
    val fecha: String,
    val emocion: Emocion,
    val id_usuario: Int,
    val id_emocion: Int
)

