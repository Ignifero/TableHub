package com.duoc.tablehub.services

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.duoc.tablehub.models.Usuario

@Dao
interface DaoUsuario {
    @Insert
    suspend fun insertUsuario(usuario: Usuario)

    @Update
    suspend fun updateUsuario(usuario: Usuario)

    @Delete
    suspend fun deleteUsuario(usuario: Usuario)

    @Query("SELECT * FROM usuario WHERE mail = :mail")
    suspend fun getUsuario(mail: String): Usuario
}