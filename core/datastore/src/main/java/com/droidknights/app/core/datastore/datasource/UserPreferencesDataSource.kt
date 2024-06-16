package com.droidknights.app.core.datastore.datasource

import kotlinx.coroutines.flow.Flow

interface UserPreferencesDataSource {
    val tags: Flow<Set<String>>
    suspend fun updateTags(tags: Set<String>)
}
