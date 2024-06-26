package com.droidknights.app.feature.session

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.droidknights.app.core.domain.usecase.GetBookmarkedSessionIdsUseCase
import com.droidknights.app.core.domain.usecase.GetSessionsUseCase
import com.droidknights.app.core.domain.usecase.GetUserUseCase
import com.droidknights.app.core.model.User
import com.droidknights.app.feature.session.model.SessionUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SessionViewModel @Inject constructor(
    getSessionsUseCase: GetSessionsUseCase,
    getBookmarkedSessionIdsUseCase: GetBookmarkedSessionIdsUseCase,
    private val getUserUseCase: GetUserUseCase
) : ViewModel() {
    private val _errorFlow = MutableSharedFlow<Throwable>()
    val errorFlow = _errorFlow.asSharedFlow()

    private val _uiState = MutableStateFlow<SessionUiState>(SessionUiState.Loading)
    val uiState = _uiState.asStateFlow()

    private lateinit var currentUser: User

    init {
        viewModelScope.launch {
            currentUser = getUserUseCase("1")

            combine(
                getSessionsUseCase(),
                getBookmarkedSessionIdsUseCase()
            ) { sessions, bookmarkedIds ->
                SessionUiState.Sessions(
                    recruits = sessions
                        .map { recruit ->
                            recruit.copy(isBookmarked = bookmarkedIds.contains(recruit.id))
                        }.sortedByDescending { rc ->
                            var matchTagCount = 0;
                            rc.tags.forEach { rt ->
                                currentUser.tags.forEach { ut ->
                                    Log.d("UserFilterDebug", "${ut.name} : ${rt.name}")
                                    if (ut.name.contains(rt.name) || rt.name.contains(ut.name)) {
                                        matchTagCount++
                                    }
                                }
                                Log.d("UserFilterDebug", "${currentUser.job} / ${currentUser.hobby} : ${rt.name}")
                                if (currentUser.job.contentEquals(rt.name) || currentUser.hobby.contentEquals(rt.name)) {
                                    matchTagCount++
                                }
                            }
                            Log.d("UserFilterDebug", "${rc.title} : $matchTagCount")
                            matchTagCount
                        }
                        .toPersistentList()
                )
            }
                .catch { throwable ->
                    _errorFlow.emit(throwable)
                }
                .onEach { combinedUiState ->
                    _uiState.value = combinedUiState
                }
                .launchIn(viewModelScope)
        }
    }
}
