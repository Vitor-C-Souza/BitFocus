package br.me.vitorcsouza.bitfocus.domain.usecase.home

import br.me.vitorcsouza.bitfocus.domain.model.FocusSession
import br.me.vitorcsouza.bitfocus.domain.repository.FocusRepository
import javax.inject.Inject

class HomeUseCaseImpl @Inject constructor(
    private val repository: FocusRepository
) : HomeUseCase {
    override suspend fun saveCompletedSession(session: FocusSession) {
        repository.insertSession(session)
    }
}