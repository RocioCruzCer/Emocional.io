package mx.edu.dsi_code.notasmvvm.screen

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import mx.edu.dsi_code.notasmvvm.data.NotesDataSource
import mx.edu.dsi_code.notasmvvm.model.Note

class NoteViewModel : ViewModel(){
    private var noteList = mutableStateListOf<Note>()

    init {
        noteList.addAll(NotesDataSource().loadNotes())
    }
    fun addNote(note: Note){
        noteList.add(note)
    }

    fun removeNote(note:Note){
        noteList.remove(note)
    }

    fun getAllNotes():List<Note>{
        return noteList
    }



}