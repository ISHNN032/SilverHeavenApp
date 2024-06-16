package com.droidknights.app.core.domain.usecase

import com.droidknights.app.core.data.repository.api.RecruitRepository
import com.droidknights.app.core.model.Recruit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetSessionsUseCase @Inject constructor(
    private val recruitRepository: RecruitRepository,
) {
    operator fun invoke(): Flow<List<Recruit>> =
        flow {
            emit(recruitRepository.getRecruits())
        }
}
