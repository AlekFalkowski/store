package dev.falkow.blanco.layout.storages

import androidx.room.Database
import androidx.room.RoomDatabase
import dev.falkow.blanco.shared.storages.IKeyValueLocalStorage
import dev.falkow.blanco.shared.storages.KeyValueTable

@Database(
    entities = [
        KeyValueTable::class,
        // Settings::class,
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun keyValueLocalStorage(): IKeyValueLocalStorage
    // abstract fun settingsStorage(): SettingStorage
}