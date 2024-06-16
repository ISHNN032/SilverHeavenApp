package com.droidknights.app.core.domain.usecase

import com.droidknights.app.core.data.repository.api.RecruitRepository
import javax.inject.Inject

class BookmarkSessionUseCase @Inject constructor(
    private val recruitRepository: RecruitRepository,
) {

    suspend operator fun invoke(sessionId: String, bookmark: Boolean) =
        recruitRepository.bookmarkRecruit(sessionId, bookmark)
}
