package com.droidknights.app.core.data.datastore.fake

import com.droidknights.app.core.datastore.datasource.RecruitPreferencesDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.filterNotNull

class FakeRecruitPreferencesDataSource : RecruitPreferencesDataSource {

    private val _bookmarkedSession = MutableStateFlow(emptySet<String>())

    override val bookmarkedSession: Flow<Set<String>> = _bookmarkedSession.filterNotNull()

    override suspend fun updateBookmarkedSession(bookmarkedSession: Set<String>) {
        _bookmarkedSession.value = bookmarkedSession.toSet()
    }
}
