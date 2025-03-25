package mx.edu.dsi_code.notasmvvm.api

import android.content.Context
import android.content.SharedPreferences

fun getUserId(context: Context): Int {
    val sharedPreferences: SharedPreferences = context.getSharedPreferences("UserPreferences", Context.MODE_PRIVATE)
    return sharedPreferences.getInt("userId", -1)  // Retorna el userId guardado o -1 si no existe
}
