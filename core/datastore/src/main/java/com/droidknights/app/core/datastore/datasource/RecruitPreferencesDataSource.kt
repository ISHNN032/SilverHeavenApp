package com.droidknights.app.core.datastore.datasource

import kotlinx.coroutines.flow.Flow

interface RecruitPreferencesDataSource {
    val bookmarkedSession: Flow<Set<String>>
    suspend fun updateBookmarkedSession(bookmarkedSession: Set<String>)
}
