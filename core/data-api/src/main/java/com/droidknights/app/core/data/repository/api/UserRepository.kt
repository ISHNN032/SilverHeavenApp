package com.droidknights.app.core.data.repository.api

import com.droidknights.app.core.model.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun getUsers(): List<User>

    suspend fun getUser(userId: String): User

    suspend fun registerUser(user: User): User

    suspend fun updateUser(user: User): User

    fun getTags(): Flow<Set<String>>

    suspend fun updateTags(tags: Set<String>)
}
