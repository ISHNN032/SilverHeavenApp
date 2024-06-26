package com.droidknights.app.feature.session.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import com.droidknights.app.core.designsystem.component.TextChip
import com.droidknights.app.core.designsystem.theme.DarkGray
import com.droidknights.app.core.designsystem.theme.LightGray
import com.droidknights.app.core.model.Category
import com.droidknights.app.core.model.Recruit
import com.droidknights.app.core.model.Tag
import com.droidknights.app.core.ui.textRes
import com.droidknights.app.feature.session.R
import kotlinx.collections.immutable.toPersistentList
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.toJavaLocalDateTime
import java.time.format.DateTimeFormatter

@Composable
internal fun SessionChips(recruit: Recruit) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        val sessionTags = recruit.tags.toPersistentList()

        TrackChip(category = recruit.category)
        TimeChip(dateTime = recruit.startTime)
        sessionTags.forEach { tag ->
            TagChip(tag = tag)
        }
    }
}

@Composable
internal fun TagChip(tag: Tag) {
    TextChip(
        text = tag.name,
        containerColor = DarkGray,
        labelColor = LightGray,
    )
}

@Composable
internal fun TrackChip(category: Category) {
    TextChip(
        text = stringResource(id = category.textRes),
        containerColor = MaterialTheme.colorScheme.secondaryContainer,
        labelColor = MaterialTheme.colorScheme.onSecondaryContainer,
    )
}

@Composable
internal fun TimeChip(dateTime: LocalDateTime) {
    val pattern = stringResource(id = R.string.session_time_fmt)
    val formatter = remember { DateTimeFormatter.ofPattern(pattern) }
    val time = remember { dateTime.toJavaLocalDateTime().toLocalTime() }

    TextChip(
        text = formatter.format(time),
        containerColor = MaterialTheme.colorScheme.tertiaryContainer,
        labelColor = MaterialTheme.colorScheme.onTertiaryContainer,
    )
}

internal class RoomPreviewParameterProvider : PreviewParameterProvider<Category> {
    override val values = sequenceOf(
        Category.JOB,
        Category.PART_TIME,
        Category.SIDE_JOB,
        Category.ETC
    )
}

@Preview
@Composable
private fun TagChipPreview() {
    TagChip(Tag("Android"))
}

@Preview
@Composable
fun TrackChipPreview(
    @PreviewParameter(RoomPreviewParameterProvider::class) category: Category,
) {
    TrackChip(category)
}

@Preview
@Composable
fun TimeChipPreview() {
    TimeChip(LocalDateTime(2022, 1, 1, 10, 22))
}
