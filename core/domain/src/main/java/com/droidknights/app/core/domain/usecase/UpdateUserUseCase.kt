package com.droidknights.app.core.domain.usecase

import com.droidknights.app.core.data.repository.api.UserRepository
import com.droidknights.app.core.model.User
import javax.inject.Inject

class UpdateUserUseCase @Inject constructor(
    private val userRepository: UserRepository,
) {

    suspend operator fun invoke(user: User): User =
        userRepository.updateUser(user)
}
