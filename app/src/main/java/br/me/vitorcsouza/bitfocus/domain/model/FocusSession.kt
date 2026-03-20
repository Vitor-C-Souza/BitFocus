package br.me.vitorcsouza.bitfocus.domain.model

data class FocusSession(
    val id: Long = 0,
    val startTime: Long,
    val endTime: Long,
    val duration: Long,
    val goal: String
)