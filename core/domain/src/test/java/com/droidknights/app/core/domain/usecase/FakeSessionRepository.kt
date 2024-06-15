package com.droidknights.app.core.domain.usecase

import com.droidknights.app.core.data.repository.api.SessionRepository
import com.droidknights.app.core.model.Recruit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

internal class FakeSessionRepository(
    private val bookmarkedSessionIds: Set<String>,
    private val recruits: List<Recruit>
) : SessionRepository {

    override suspend fun getRecruits(): List<Recruit> {
        return recruits
    }

    override suspend fun getRecruit(sessionId: String): Recruit {
        return recruits.first { it.id == sessionId }
    }

    override fun getBookmarkedRecruitIds(): Flow<Set<String>> {
        return flow { emit(bookmarkedSessionIds) }
    }

    override suspend fun bookmarkRecruit(sessionId: String, bookmark: Boolean) {
        return
    }

    override suspend fun deleteBookmarkedSessions(sessionIds: Set<String>) {
        TODO("Not yet implemented")
    }
}
