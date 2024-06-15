package com.droidknights.app.feature.bookmark.model

import com.droidknights.app.core.model.Recruit
import java.time.LocalTime
import javax.annotation.concurrent.Immutable
import kotlinx.datetime.toJavaLocalDateTime

@Immutable
data class BookmarkItemUiState(
    val index: Int,
    val recruit: Recruit,
) {

    val sequence: Int
        get() = index + 1

    val tagLabel: String
        get() = recruit.tags.joinToString { it.name }

    val speakerLabel: String
        get() = recruit.companies.joinToString { it.name }

    val time: LocalTime
        get() = recruit.startTime.toJavaLocalDateTime().toLocalTime()
}
