package dev.falkow.blanco.shared.storages

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface IKeyValueLocalStorage {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun setValueByKey(keyValueTable: KeyValueTable)

    @Query("SELECT value from setting WHERE `key` = :name")
    suspend fun getValueByKey(name: String): String?

    @Query("SELECT value from setting WHERE `key` = :name")
    fun observeValueByKey(name: String): Flow<String?>

    @Query("DELETE from setting WHERE `key` = :name")
    suspend fun deleteValueByKey(name: String)
}