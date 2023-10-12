package com.example.primerparcial.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.primerparcial.data.local.dao.DividirDao
import com.example.primerparcial.data.local.entities.Dividir

@Database(
    entities = [Dividir::class],
    version = 2
)
abstract class Database : RoomDatabase() {
    abstract fun DividirDao() : DividirDao
}