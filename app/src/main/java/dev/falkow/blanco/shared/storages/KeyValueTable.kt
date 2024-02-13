package dev.falkow.blanco.shared.storages

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "setting")
data class KeyValueTable(
    @PrimaryKey()
    val key: String,
    val value: String,
)