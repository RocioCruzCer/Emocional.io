package mx.edu.dsi_code.notasmvvm.screen

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import mx.edu.dsi_code.notasmvvm.data.NotesDataSource
import mx.edu.dsi_code.notasmvvm.model.Note
import mx.edu.dsi_code.notasmvvm.repository.NoteRepository
import javax.inject.Inject


@HiltViewModel
class NoteViewModel @Inject constructor(private val repository: NoteRepository)  : ViewModel(){
    //private var noteList = mutableStateListOf<Note>()

    private val _nodeList = MutableStateFlow<List<Note>>(emptyList())
    val noteList = _nodeList.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO){
            repository.getAllNotes().distinctUntilChanged()
                .collect{ listOfNotes ->
                    if(listOfNotes.isNullOrEmpty()){
                        Log.d("Empty","La Lista esta Vacia")
                    }else{
                        _nodeList.value = listOfNotes
                    }
                }
        }
        //noteList.addAll(NotesDataSource().loadNotes())
    }

    suspend fun  addNote(note:Note) = viewModelScope.launch { repository.addNote(note) }
    suspend fun  removeNote(note: Note) = viewModelScope.launch { repository.deleteNote(note) }
    suspend fun  updateNote(note: Note) = viewModelScope.launch { repository.updateNote(note) }

    /*fun getAllNotes():List<Note>{
        return noteList
    }*/



}