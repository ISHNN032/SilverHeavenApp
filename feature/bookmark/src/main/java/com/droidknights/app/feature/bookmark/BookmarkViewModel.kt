package com.droidknights.app.feature.bookmark

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.droidknights.app.core.domain.usecase.DeleteBookmarkedSessionUseCase
import com.droidknights.app.core.domain.usecase.GetBookmarkedSessionsUseCase
import com.droidknights.app.core.model.Recruit
import com.droidknights.app.feature.bookmark.model.BookmarkItemUiState
import com.droidknights.app.feature.bookmark.model.BookmarkUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.persistentSetOf
import kotlinx.collections.immutable.toPersistentList
import kotlinx.collections.immutable.toPersistentSet
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookmarkViewModel @Inject constructor(
    private val getBookmarkedSessionsUseCase: GetBookmarkedSessionsUseCase,
    private val deleteBookmarkedSessionUseCase: DeleteBookmarkedSessionUseCase,
) : ViewModel() {

    private val _errorFlow = MutableSharedFlow<Throwable>()
    val errorFlow = _errorFlow.asSharedFlow()

    private val _bookmarkUiState = MutableStateFlow<BookmarkUiState>(BookmarkUiState.Loading)
    val bookmarkUiState = _bookmarkUiState.asStateFlow()

    init {
        viewModelScope.launch {
            combine(
                bookmarkUiState,
                getBookmarkedSessionsUseCase(),
            ) { bookmarkUiState, bookmarkSessions ->
                when (bookmarkUiState) {
                    is BookmarkUiState.Loading -> {
                        BookmarkUiState.Success(
                            isEditMode = false,
                            bookmarks = bookmarkSessions
                                .mapIndexed { index, session ->
                                    BookmarkItemUiState(
                                        index = index,
                                        recruit = session,
                                    )
                                }
                                .toPersistentList()
                        )
                    }

                    is BookmarkUiState.Success -> {
                        bookmarkUiState.copy(
                            bookmarks = bookmarkSessions
                                .mapIndexed { index, session ->
                                    BookmarkItemUiState(
                                        index = index,
                                        recruit = session,
                                    )
                                }
                                .toPersistentList()
                        )
                    }
                }
            }.catch { throwable ->
                _errorFlow.emit(throwable)
            }.collect { _bookmarkUiState.value = it }
        }
    }

    fun toggleEditMode() {
        val state = _bookmarkUiState.value
        if (state !is BookmarkUiState.Success) {
            return
        }

        _bookmarkUiState.value = state.copy(
            isEditMode = state.isEditMode.not(),
            bookmarks = state.bookmarks,
            selectedSessionIds = persistentSetOf()
        )
    }

    fun selectSession(recruit: Recruit) {
        val state = _bookmarkUiState.value
        if (state !is BookmarkUiState.Success) {
            return
        }

        val isAlreadySelected = state.selectedSessionIds.contains(recruit.id)
        val newSelectedIds = if (isAlreadySelected) {
            state.selectedSessionIds - recruit.id
        } else {
            state.selectedSessionIds + recruit.id
        }

        _bookmarkUiState.value = state.copy(
            selectedSessionIds = newSelectedIds.toPersistentSet()
        )
    }

    fun deleteSessions() {
        val state = _bookmarkUiState.value
        if (state !is BookmarkUiState.Success) {
            return
        }

        flow {
            emit(deleteBookmarkedSessionUseCase(state.selectedSessionIds))
        }.onEach {
            _bookmarkUiState.update {
                state.copy(selectedSessionIds = persistentSetOf())
            }
        }.catch { throwable ->
            _errorFlow.emit(throwable)
        }.launchIn(viewModelScope)
    }
}
