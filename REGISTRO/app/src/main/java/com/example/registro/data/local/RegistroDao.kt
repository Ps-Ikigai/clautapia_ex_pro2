package com.example.registro.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import com.example.registro.data.model.Registro

@Dao
interface RegistroDao {
    @Query("SELECT * FROM registros ORDER BY fecha DESC")
    fun getAllRegistros(): Flow<List<Registro>>

    @Insert
    suspend fun insertRegistro(registro: Registro)
}