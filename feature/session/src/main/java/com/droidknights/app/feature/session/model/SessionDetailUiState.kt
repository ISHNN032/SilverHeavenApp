package com.droidknights.app.feature.session.model

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import com.droidknights.app.core.model.Recruit

@Stable
sealed interface SessionDetailUiState {

    @Immutable
    data object Loading : SessionDetailUiState

    @Immutable
    data class Success(val recruit: Recruit, val bookmarked: Boolean = false) : SessionDetailUiState
}
