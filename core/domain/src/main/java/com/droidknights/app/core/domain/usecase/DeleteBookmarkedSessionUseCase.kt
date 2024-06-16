package com.droidknights.app.core.domain.usecase

import com.droidknights.app.core.data.repository.api.RecruitRepository
import javax.inject.Inject

class DeleteBookmarkedSessionUseCase @Inject constructor(
    private val recruitRepository: RecruitRepository,
) {
    suspend operator fun invoke(sessionIds: Set<String>) =
        recruitRepository.deleteBookmarkedSessions(sessionIds)
}
