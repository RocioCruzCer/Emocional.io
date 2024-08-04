package mx.edu.dsi_code.notasmvvm.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.Instant
import java.time.LocalDateTime
import java.util.Date
import java.util.UUID

@Entity(tableName="notas_tbl")
data class Note(
    @PrimaryKey
    //3.-representa un identificador universal
    val id: UUID = UUID.randomUUID(),
    ///creamos un random universal
    @ColumnInfo(name="note_title")
    val title:String,
    @ColumnInfo(name = "note_description")
    val description: String,

    //@ColumnInfo(name = "note_entry_date")
    //val entryDate: Date= Date.from(Instant.now())
)
