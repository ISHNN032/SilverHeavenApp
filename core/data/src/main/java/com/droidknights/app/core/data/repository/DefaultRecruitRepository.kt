package com.droidknights.app.core.data.repository

import com.droidknights.app.core.data.api.AwsLambdaApi
import com.droidknights.app.core.data.mapper.toData
import com.droidknights.app.core.data.repository.api.RecruitRepository
import com.droidknights.app.core.datastore.datasource.RecruitPreferencesDataSource
import com.droidknights.app.core.model.Recruit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import javax.inject.Inject

internal class DefaultRecruitRepository @Inject constructor(
    private val awsLambdaApi: AwsLambdaApi,
    private val sessionDataSource: RecruitPreferencesDataSource
) : RecruitRepository {

    private var cachedRecruits: List<Recruit> = emptyList()

    private val bookmarkIds: Flow<Set<String>> = sessionDataSource.bookmarkedSession

    override suspend fun getRecruits(): List<Recruit> {
        return awsLambdaApi.getRecruits()
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

    override suspend fun bookmarkRecruit(sessionId: String, bookmark: Boolean) {
        val currentBookmarkedRecruitIds = bookmarkIds.first()
        sessionDataSource.updateBookmarkedSession(
            if (bookmark) {
                currentBookmarkedRecruitIds + sessionId
            } else {
                currentBookmarkedRecruitIds - sessionId
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
