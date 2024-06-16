package com.droidknights.app.core.domain.usecase

import com.droidknights.app.core.data.repository.api.RecruitRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetBookmarkedSessionIdsUseCase @Inject constructor(
    private val recruitRepository: RecruitRepository,
) {

    operator fun invoke(): Flow<Set<String>> =
        recruitRepository.getBookmarkedRecruitIds()
}
