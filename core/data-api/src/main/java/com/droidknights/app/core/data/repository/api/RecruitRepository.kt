package com.droidknights.app.core.data.repository.api

import com.droidknights.app.core.model.Recruit
import kotlinx.coroutines.flow.Flow

interface RecruitRepository {

    suspend fun getRecruits(): List<Recruit>

    suspend fun getRecruit(sessionId: String): Recruit

    fun getBookmarkedRecruitIds(): Flow<Set<String>>

    suspend fun bookmarkRecruit(sessionId: String, bookmark: Boolean)

    suspend fun deleteBookmarkedSessions(sessionIds: Set<String>)
}
