package com.droidknights.app.feature.session.model

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import com.droidknights.app.core.model.Category
import com.droidknights.app.core.model.Recruit
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.mapNotNull

@Immutable
data class SessionGroup(
    val category: Category,
    val recruits: PersistentList<Recruit>,
)

@Stable
class SessionState(
    private val recruits: ImmutableList<Recruit>,
    val listState: LazyListState,
    selectedCategory: Category? = recruits.map { it.category }.firstOrNull(),
) {

    val groups: List<SessionGroup> = recruits
        .groupBy { it.category }
        .map { (room, sessions) -> SessionGroup(room, sessions.toPersistentList()) }

    val categories: List<Category> = recruits.map { it.category }.distinct()

    private val categoryPositions: Map<Category, Int> = buildMap {
        var position = 0
        groups.forEach { group ->
            put(group.category, position)
            position += group.recruits.size
        }
    }

    var selectedCategory: Category? by mutableStateOf(selectedCategory)
        private set

    val isAtTop by derivedStateOf {
        listState.firstVisibleItemIndex == 0 && listState.firstVisibleItemScrollOffset == 0
    }

    fun groupIndex(index: Int): Category? {
        for ((room, position) in categoryPositions) {
            if (position == index) {
                return room
            }
        }
        return null
    }

    fun select(category: Category) {
        selectedCategory = category
    }

    suspend fun scrollTo(category: Category) {
        val index = categoryPositions[category] ?: return
        listState.animateScrollToItem(index)
    }

    companion object {

        fun Saver(
            recruits: ImmutableList<Recruit>,
            listState: LazyListState,
        ): Saver<SessionState, *> = Saver(
            save = { it.selectedCategory },
            restore = { selectedRoom ->
                SessionState(
                    recruits = recruits,
                    listState = listState,
                    selectedCategory = selectedRoom,
                )
            }
        )
    }
}

@Composable
internal fun rememberSessionState(
    recruits: ImmutableList<Recruit>,
    listState: LazyListState = rememberLazyListState(),
): SessionState {
    val state = rememberSaveable(
        recruits,
        listState,
        saver = SessionState.Saver(recruits, listState),
    ) {
        SessionState(recruits, listState)
    }
    LaunchedEffect(recruits, listState) {
        snapshotFlow { listState.firstVisibleItemIndex }
            .mapNotNull { index -> state.groupIndex(index) }
            .distinctUntilChanged()
            .collect { room -> state.select(room) }
    }
    return state
}
