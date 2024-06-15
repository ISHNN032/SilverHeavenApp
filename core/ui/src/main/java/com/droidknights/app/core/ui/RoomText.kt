package com.droidknights.app.core.ui

import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import com.droidknights.app.core.model.Category

val Category.textRes: Int
    get() = when (this) {
        Category.ETC -> R.string.session_room_keynote
        Category.JOB -> R.string.session_room_track_01
        Category.PART_TIME -> R.string.session_room_track_02
        Category.SIDE_JOB -> R.string.session_room_track_03
    }

@Composable
fun RoomText(
    category: Category,
    style: TextStyle,
    color: Color = LocalContentColor.current,
    onTextLayout: (TextLayoutResult) -> Unit = {},
) {
    Text(
        text = stringResource(id = category.textRes),
        style = style,
        color = color,
        onTextLayout = onTextLayout,
    )
}
