package mx.edu.dsi_code.notasmvvm.data

import androidx.room.Database
import androidx.room.RoomDatabase
import mx.edu.dsi_code.notasmvvm.model.Note

@Database(entities =[Note::class], version=1,exportSchema= false)
abstract class NoteDatabase: RoomDatabase() {
    abstract fun noteDao(): NoteDatabaseDao
}