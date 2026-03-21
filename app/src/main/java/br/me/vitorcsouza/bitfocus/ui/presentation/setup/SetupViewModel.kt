package br.me.vitorcsouza.bitfocus.ui.presentation.setup

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class SetupViewModel @Inject constructor(

) : ViewModel() {
    private val _state = MutableStateFlow(SetupStates())
    val state = _state.asStateFlow()


    fun onEnergyLevelSelected(level: EnergyLevel) {
        _state.update { it.copy(energyLevel = level) }
    }

    fun onDurationChanged(duration: Int) {
        _state.update { it.copy(durationMinutes = duration) }
    }

    fun onGoalChanged(goal: String) {
        _state.update { it.copy(sessionGoal = goal) }
    }

}
