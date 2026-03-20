package br.me.vitorcsouza.bitfocus.domain.usecase.home

import br.me.vitorcsouza.bitfocus.domain.model.FocusSession

interface HomeUseCase {
    suspend fun saveCompletedSession(session: FocusSession)
}