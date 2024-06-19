package com.droidknights.app.core.domain.usecase

import com.droidknights.app.core.data.repository.api.UserRepository
import com.droidknights.app.core.model.User
import javax.inject.Inject

class GetUserUseCase @Inject constructor(
    private val userRepository: UserRepository,
) {

    suspend operator fun invoke(userId: String): User =
        userRepository.getUser(userId)
}
