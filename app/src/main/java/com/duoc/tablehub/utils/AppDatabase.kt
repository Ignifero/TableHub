package com.duoc.tablehub.utils

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.duoc.tablehub.models.Usuario
import com.duoc.tablehub.services.DaoUsuario

@Database(entities = [Usuario::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun usuarioDao(): DaoUsuario
    companion object {
        @Volatile
        private var Instance : AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, AppDatabase::class.java, "app_database")
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build()
                    .also { Instance = it }
            }
        }
    }
}
