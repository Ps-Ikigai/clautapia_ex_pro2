package com.example.registro.data.repository

import com.example.registro.data.local.RegistroDao
import com.example.registro.data.model.Registro
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class RegistroRepository(private val registroDao: RegistroDao) {

    val allRegistros: Flow<List<Registro>> = registroDao.getAllRegistros()

    suspend fun insertRegistro(registro: Registro) {
        withContext(Dispatchers.IO) {
            registroDao.insertRegistro(registro)
        }
    }
}