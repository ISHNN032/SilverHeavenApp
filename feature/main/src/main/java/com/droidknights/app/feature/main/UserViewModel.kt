package com.droidknights.app.feature.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.droidknights.app.core.data.repository.DefaultUserRepository
import com.droidknights.app.core.model.User
import com.droidknights.app.feature.session.model.SessionDetailUiState
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

class UserViewModel @Inject constructor(
    private val userRepository: DefaultUserRepository
) : ViewModel() {
    val user: User? = null

    fun registerUser(user: User) {
        viewModelScope.launch {
            userRepository.registerUser(user)
        }
    }

    fun updateUser(user: User) {
        viewModelScope.launch {
            userRepository.updateUser(user)
            userRepository.updateTags(user.tags.map { it.name }.toSet())
        }
    }

    fun updateUser(jsonString: String) {
        viewModelScope.launch {

//            userRepository.updateUser(user)
//            userRepository.updateTags(user.tags.map { it.name }.toSet())
        }
    }
}