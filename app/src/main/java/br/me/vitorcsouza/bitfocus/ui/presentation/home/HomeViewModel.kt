package br.me.vitorcsouza.bitfocus.ui.presentation.home

import androidx.lifecycle.ViewModel
import br.me.vitorcsouza.bitfocus.domain.usecase.home.HomeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val homeUseCase: HomeUseCase
) : ViewModel() {
    // ViewModel logic for the home screen
}