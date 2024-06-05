package com.duoc.tablehub.models

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(indices = [Index(value = ["nickname"], unique = true)])
data class Usuario(
    @PrimaryKey var mail: String,
    var nickname: String,
    var nombre: String,
    var apellido: String,
    var plan : String?
)
