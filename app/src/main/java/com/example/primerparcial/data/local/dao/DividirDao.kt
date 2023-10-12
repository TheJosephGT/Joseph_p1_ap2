package com.example.primerparcial.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Upsert
import com.example.primerparcial.data.local.entities.Dividir
import kotlinx.coroutines.flow.Flow

@Dao
interface DividirDao {
    @Upsert
    suspend fun save(dividir: Dividir)

    @Query(
        """
            SELECT *
            FROM Divisores
            WHERE dividirId=:id
            LIMIT 1
        """
    )
    suspend fun find(id:Int) : Dividir?

    @Delete
    suspend fun delete(dividir: Dividir)

    @Query("SELECT * FROM Divisores")
    fun getAll() : Flow<List<Dividir>>
}