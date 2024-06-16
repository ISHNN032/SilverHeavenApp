package com.droidknights.app.core.datastore.datasource

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringSetPreferencesKey
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Named

class DefaultUserPreferencesDataSource @Inject constructor(
    @Named("user") private val dataStore: DataStore<Preferences>,
) : UserPreferencesDataSource {
    object PreferencesKey {
        val USER_TAGS = stringSetPreferencesKey("USER_TAGS")
    }

    override val tags = dataStore.data.map { preferences ->
        preferences[PreferencesKey.USER_TAGS] ?: emptySet()
    }

    override suspend fun updateTags(tags: Set<String>) {
        dataStore.edit { preferences ->
            preferences[PreferencesKey.USER_TAGS] = tags
        }
    }
}
