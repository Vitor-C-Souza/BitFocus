package br.me.vitorcsouza.bitfocus.data.local.datasource.focus

import br.me.vitorcsouza.bitfocus.data.local.entity.FocusEntity
import kotlinx.coroutines.flow.Flow

interface FocusDataSource {
    suspend fun saveSession(session: FocusEntity)

    fun getAllSessions(): Flow<List<FocusEntity>>
}