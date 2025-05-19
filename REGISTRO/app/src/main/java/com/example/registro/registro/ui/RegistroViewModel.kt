package com.example.registro.registro.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.registro.data.model.Registro
import com.example.registro.data.repository.RegistroRepository
import kotlinx.coroutines.launch
import java.util.Date

class RegistroViewModel(private val repository: RegistroRepository) : ViewModel() {

    val allRegistros: LiveData<List<Registro>> = repository.allRegistros.asLiveData()

    fun insertarRegistro(tipoGasto: Registro.TipoGasto, valorMedidor: Double, fecha: Date) {
        viewModelScope.launch {
            val nuevoRegistro = Registro(tipoGasto = tipoGasto, valorMedidor = valorMedidor, fecha = fecha)
            repository.insertRegistro(nuevoRegistro)
        }
    }

    class RegistroViewModelFactory(private val repository: RegistroRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(RegistroViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return RegistroViewModel(repository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}