package br.me.vitorcsouza.bitfocus.di

import android.content.Context
import androidx.room.Room
import br.me.vitorcsouza.bitfocus.data.local.dao.FocusDao
import br.me.vitorcsouza.bitfocus.data.local.database.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "bitfocus_db"
        )
            .fallbackToDestructiveMigration(dropAllTables = true)
            .build()
    }

    @Provides
    fun provideFocusDao(database: AppDatabase): FocusDao = database.focusDao()
}
