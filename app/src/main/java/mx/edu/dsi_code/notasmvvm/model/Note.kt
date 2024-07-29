package mx.edu.dsi_code.notasmvvm.model

import java.time.LocalDateTime
import java.util.UUID

data class Note(
    //3.-representa un identificador universal
    val id: UUID = UUID.randomUUID(),
    ///creamos un random universal
    val title:String,
    val description: String,
    val entryDate: LocalDateTime = LocalDateTime.now()
)
