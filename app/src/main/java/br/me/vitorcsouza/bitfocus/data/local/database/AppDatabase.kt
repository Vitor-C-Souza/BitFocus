package br.me.vitorcsouza.bitfocus.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import br.me.vitorcsouza.bitfocus.data.local.dao.FocusDao
import br.me.vitorcsouza.bitfocus.data.local.entity.FocusEntity

@Database(entities = [FocusEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase: RoomDatabase() {
    abstract fun focusDao(): FocusDao
}