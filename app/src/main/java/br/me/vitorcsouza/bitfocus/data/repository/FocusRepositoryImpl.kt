package br.me.vitorcsouza.bitfocus.data.repository

import br.me.vitorcsouza.bitfocus.data.local.datasource.focus.FocusDataSource
import br.me.vitorcsouza.bitfocus.data.local.entity.FocusEntity
import br.me.vitorcsouza.bitfocus.domain.model.FocusSession
import br.me.vitorcsouza.bitfocus.domain.repository.FocusRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class FocusRepositoryImpl @Inject constructor(
    private val dataSource: FocusDataSource
) : FocusRepository {
    override suspend fun insertSession(session: FocusSession) {
        dataSource.saveSession(
            FocusEntity(
                startTime = session.startTime,
                endTime = session.endTime,
                duration = session.duration,
                goal = session.goal
            )
        )
    }

    override fun getAllSessions(): Flow<List<FocusSession>> {
        return dataSource.getAllSessions().map { entities ->
            entities.map {
                FocusSession(
                    id = it.id,
                    startTime = it.startTime,
                    endTime = it.endTime,
                    duration = it.duration,
                    goal = it.goal
                )
            }
        }
    }
}