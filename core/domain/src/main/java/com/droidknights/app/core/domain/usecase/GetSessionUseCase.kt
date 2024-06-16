package com.droidknights.app.core.domain.usecase

import com.droidknights.app.core.data.repository.api.RecruitRepository
import com.droidknights.app.core.model.Recruit
import javax.inject.Inject

class GetSessionUseCase @Inject constructor(
    private val recruitRepository: RecruitRepository,
) {

    suspend operator fun invoke(sessionId: String): Recruit =
        recruitRepository.getRecruit(sessionId)
}
