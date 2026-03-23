package br.me.vitorcsouza.bitfocus.domain.model

enum class SessionCategory {
    WORK, STUDY, LEISURE, OTHER;

    companion object {
        fun fromGoal(goal: String): SessionCategory {
            val lowerGoal = goal.lowercase()
            return when {
                lowerGoal.contains("work") || lowerGoal.contains("job") || lowerGoal.contains("coding") || lowerGoal.contains("trabalho") -> WORK
                lowerGoal.contains("study") || lowerGoal.contains("learn") || lowerGoal.contains("read") || lowerGoal.contains("estudo") -> STUDY
                lowerGoal.contains("game") || lowerGoal.contains("play") || lowerGoal.contains("relax") || lowerGoal.contains("lazer") -> LEISURE
                else -> OTHER
            }
        }
    }
}

data class FocusSession(
    val id: Long = 0,
    val startTime: Long,
    val endTime: Long,
    val duration: Long,
    val goal: String,
    val category: SessionCategory = SessionCategory.fromGoal(goal)
)
