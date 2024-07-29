package mx.edu.dsi_code.notasmvvm.data

import mx.edu.dsi_code.notasmvvm.model.Note

class  NotesDataSource{

    fun loadNotes(): List<Note>{

        return listOf(
            Note(title = "Un Buen Dia", description = "Cuando estas vacacionando sin pendientes"),
            Note(title="Codificando C#", description="Cuando estas desarrollandom en mvc netcore"),
            Note(title="Codificando Kotlin",
                description="Cuando estas codificacndo kotlin y diseñando apps"),
            Note(title = "Codificando Angular",
                description = "Cuando estas codificando en Angular y diseñando sitios web")
        )
    }
}