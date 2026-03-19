package br.me.vitorcsouza.bitfocus.domain.entity

data class FocusSessionEntity(
    val id: Long,
    val startTime: Long,
    val endTime: Long,
    val durationInMinutes: Int
)