package br.me.vitorcsouza.bitfocus.domain.repository

import br.me.vitorcsouza.bitfocus.domain.model.FocusSession
import kotlinx.coroutines.flow.Flow

interface FocusRepository {
    suspend fun insertSession(session: FocusSession)
    fun getAllSessions(): Flow<List<FocusSession>>
}