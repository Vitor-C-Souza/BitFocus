package br.me.vitorcsouza.bitfocus.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import br.me.vitorcsouza.bitfocus.domain.model.SessionCategory

@Entity(tableName = "focus_sessions")
data class FocusEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val startTime: Long,
    val endTime: Long,
    val duration: Long,
    val goal: String,
    val category: String = SessionCategory.fromGoal(goal).name
)
