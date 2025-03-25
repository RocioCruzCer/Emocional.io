package mx.edu.dsi_code.notasmvvm.viewmodel

import androidx.lifecycle.ViewModel
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.State

class LoginViewModel : ViewModel() {

    private val _userId = mutableStateOf<Int?>(null)
    val userId: State<Int?> get() = _userId

    fun setUserId(id: Int) {
        _userId.value = id
    }
}

