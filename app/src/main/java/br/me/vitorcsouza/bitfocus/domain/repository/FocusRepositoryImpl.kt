package br.me.vitorcsouza.bitfocus.domain.repository

import br.me.vitorcsouza.bitfocus.data.repository.FocusRepository
import javax.inject.Inject

class FocusRepositoryImpl @Inject constructor(
    private val focusRepository: FocusRepository
) {
    // Implementation of domain-specific logic using the data repository
}