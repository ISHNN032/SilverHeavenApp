package com.droidknights.app.feature.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.droidknights.app.core.data.repository.DefaultUserRepository
import com.droidknights.app.core.model.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val userRepository: DefaultUserRepository
) : ViewModel() {
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

        }
    }
}