package br.me.vitorcsouza.bitfocus.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import br.me.vitorcsouza.bitfocus.data.local.entity.FocusEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FocusDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSession(session: FocusEntity)

    @Query("SELECT * FROM focus_sessions ORDER BY startTime DESC")
    fun getAllSessions(): Flow<List<FocusEntity>>

    @Query("SELECT SUM(duration) FROM focus_sessions")
    fun getTotalFocusTime(): Flow<Long?>
}


