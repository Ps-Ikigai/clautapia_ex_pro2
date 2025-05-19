package com.example.registro.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "registros")
data class Registro(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val tipoGasto: TipoGasto,
    val valorMedidor: Double,
    val fecha: Date
)

enum class TipoGasto {
    AGUA, LUZ, GAS
}