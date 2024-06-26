package com.droidknights.app.feature.bookmark.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import com.droidknights.app.core.designsystem.theme.KnightsTheme
import com.droidknights.app.core.designsystem.theme.Purple01
import com.droidknights.app.core.model.Category
import com.droidknights.app.core.ui.RoomText

@Composable
internal fun BookmarkCard(
    modifier: Modifier = Modifier,
    tagLabel: String,
    category: Category,
    title: String,
    speaker: String,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = MaterialTheme.colorScheme.primaryContainer,
                shape = RoundedCornerShape(8.dp)
            )
            .padding(start = 16.dp, end = 18.dp, top = 16.dp, bottom = 16.dp),
        verticalArrangement = Arrangement.spacedBy(space = 8.dp)
    ) {
        Row(
            modifier = Modifier,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .background(color = Purple01, shape = CircleShape)
                    .size(12.dp)
            )
            Text(
                modifier = Modifier.weight(1F),
                text = tagLabel,
                style = KnightsTheme.typography.labelSmallM,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            RoomText(
                category,
                style = KnightsTheme.typography.labelSmallM,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        Text(
            text = title,
            style = KnightsTheme.typography.titleSmallB,
            color = MaterialTheme.colorScheme.onSecondaryContainer
        )

        Text(
            text = speaker,
            style = KnightsTheme.typography.labelSmallM,
            color = MaterialTheme.colorScheme.onSecondaryContainer
        )
    }
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
private fun BookmarkCardPreview(
    @PreviewParameter(RoomPreviewParameterProvider::class) category: Category,
) {
    KnightsTheme {
        Box {
            BookmarkCard(
                tagLabel = "효율적인 코드 베이스",
                category = category,
                title = "Jetpack Compose에 있는 것, 없는것",
                speaker = "홍길동"
            )
        }
    }
}
