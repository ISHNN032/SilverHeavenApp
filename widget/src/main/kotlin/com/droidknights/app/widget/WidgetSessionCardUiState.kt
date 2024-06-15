package com.droidknights.app.widget

import androidx.compose.runtime.Immutable
import com.droidknights.app.core.model.Recruit

@Immutable
data class WidgetSessionCardUiState(
    val recruit: Recruit,
) {
    val speakerLabel: String by lazy { recruit.companies.joinToString { it.name } }
}