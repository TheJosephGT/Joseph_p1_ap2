package com.example.primerparcial.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.primerparcial.data.local.entities.Dividir
import kotlinx.coroutines.flow.Flow

@Dao
interface DividirDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(dividir: Dividir)

    @Query(
        """
            SELECT *
            FROM Dividir
            WHERE dividirId=:id
            LIMIT 1
        """
    )
    suspend fun find(id:Int) : Dividir?

    suspend fun delete(dividir: Dividir)

    fun getAll() : Flow<List<Dividir>>
}