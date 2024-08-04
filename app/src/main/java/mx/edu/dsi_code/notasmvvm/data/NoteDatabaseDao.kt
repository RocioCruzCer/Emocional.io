package mx.edu.dsi_code.notasmvvm.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import mx.edu.dsi_code.notasmvvm.model.Note

@Dao
interface NoteDatabaseDao {

    @Query("SELECT * from notas_tbl")
    fun getNotes(): Flow<List<Note>>

    @Query("SELECT * from notas_tbl where id =:id")
    suspend fun getNoteById(id: String): Note

     @Insert(onConflict =  OnConflictStrategy.REPLACE)
     suspend fun insert(note:Note)

     @Update(onConflict =  OnConflictStrategy.REPLACE)
     suspend fun update(note: Note)

     @Query("DELETE from notas_tbl")
     suspend fun deleteAll()

     @Delete
     suspend fun deleteNote(note:Note)

}
