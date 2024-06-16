package com.droidknights.app.core.data.repository

import com.droidknights.app.core.data.api.AwsLambdaApi
import com.droidknights.app.core.data.mapper.toData
import com.droidknights.app.core.data.repository.api.UserRepository
import com.droidknights.app.core.datastore.datasource.UserPreferencesDataSource
import com.droidknights.app.core.model.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull
import javax.inject.Inject

class DefaultUserRepository @Inject constructor(
    private val awsLambdaApi: AwsLambdaApi,
    private val userDataSource: UserPreferencesDataSource
) : UserRepository {
    private var cachedUsers: List<User> = emptyList()
    private val tags: Flow<Set<String>> = userDataSource.tags

    override suspend fun getUsers(): List<User> {
        return awsLambdaApi.getUsers()
            .map { it.toData() }
            .also { cachedUsers = it }
    }

    override suspend fun getUser(userId: String): User {
        val cachedUser = cachedUsers.find { it.id == userId }
        if (cachedUser != null) {
            return cachedUser
        }

        return getUsers().find { it.id == userId }
            ?: error("User not found with id: $userId")
    }

    override suspend fun registerUser(user: User): User {
        return awsLambdaApi.createUser(user).toData()
    }

    override suspend fun updateUser(user: User): User {
        return awsLambdaApi.updateUser(user).toData()
    }

    override fun getTags(): Flow<Set<String>> {
        return tags.filterNotNull()
    }

    override suspend fun updateTags(_tags: Set<String>) {
        userDataSource.updateTags(_tags)
    }
}