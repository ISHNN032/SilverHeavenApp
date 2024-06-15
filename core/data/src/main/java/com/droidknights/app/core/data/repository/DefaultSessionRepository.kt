package com.droidknights.app.core.data.repository

import com.droidknights.app.core.data.api.GithubRawApi
import com.droidknights.app.core.data.mapper.toData
import com.droidknights.app.core.data.repository.api.SessionRepository
import com.droidknights.app.core.datastore.datasource.SessionPreferencesDataSource
import com.droidknights.app.core.model.Recruit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import javax.inject.Inject

internal class DefaultSessionRepository @Inject constructor(
    private val githubRawApi: GithubRawApi,
    private val sessionDataSource: SessionPreferencesDataSource
) : SessionRepository {

    private var cachedRecruits: List<Recruit> = emptyList()

    private val bookmarkIds: Flow<Set<String>> = sessionDataSource.bookmarkedSession

    override suspend fun getRecruits(): List<Recruit> {
        return githubRawApi.getRecruits()
            .map { it.toData() }
            .also { cachedRecruits = it }
    }

    override suspend fun getRecruit(sessionId: String): Recruit {
        val cachedSession = cachedRecruits.find { it.id == sessionId }
        if (cachedSession != null) {
            return cachedSession
        }

        return getRecruits().find { it.id == sessionId }
            ?: error("Session not found with id: $sessionId")
    }

    override fun getBookmarkedRecruitIds(): Flow<Set<String>> {
        return bookmarkIds.filterNotNull()
    }

    override suspend fun bookmarkRecruit(recruitId: String, bookmark: Boolean) {
        val currentBookmarkedRecruitIds = bookmarkIds.first()
        sessionDataSource.updateBookmarkedSession(
            if (bookmark) {
                currentBookmarkedRecruitIds + recruitId
            } else {
                currentBookmarkedRecruitIds - recruitId
            }
        )
    }

    override suspend fun deleteBookmarkedSessions(sessionIds: Set<String>) {
        val currentBookmarkedSessionIds = bookmarkIds.first()
        sessionDataSource.updateBookmarkedSession(
            currentBookmarkedSessionIds - sessionIds
        )
    }
}
