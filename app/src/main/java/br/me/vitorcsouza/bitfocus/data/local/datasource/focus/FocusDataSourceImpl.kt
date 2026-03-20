package br.me.vitorcsouza.bitfocus.data.local.datasource.focus


import br.me.vitorcsouza.bitfocus.data.local.dao.FocusDao
import br.me.vitorcsouza.bitfocus.data.local.entity.FocusEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FocusDataSourceImpl @Inject constructor(
    private val focusDao: FocusDao
) : FocusDataSource {
    override suspend fun saveSession(session: FocusEntity) {
        focusDao.insertSession(session)
    }

    override fun getAllSessions(): Flow<List<FocusEntity>> {
        return focusDao.getAllSessions()
    }
}