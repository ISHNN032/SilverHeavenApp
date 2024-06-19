package com.droidknights.app.feature.main

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.droidknights.app.core.domain.usecase.GetUserUseCase
import com.droidknights.app.core.domain.usecase.RegisterUserUseCase
import com.droidknights.app.core.domain.usecase.UpdateUserUseCase
import com.droidknights.app.core.model.User
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val getUserUseCase: GetUserUseCase,
    private val registerUserUseCase: RegisterUserUseCase,
    private val updateUserUseCase: UpdateUserUseCase
) : ViewModel() {
    suspend fun getCurrentUser() : User {
        return getUserUseCase("1")
    }

    suspend fun registerUser(user: User) {
        registerUserUseCase.invoke(user)
    }

    fun updateUser(jsonString: String) {
        viewModelScope.launch {
            Log.d("AIRegisterDebug", jsonString)
            val userFromGson: User = Gson().fromJson(jsonString, User::class.java)
            val user = getCurrentUser()
            updateUserUseCase(User(
                id = user.id,
                email = user.email,
                password = user.password,
                phoneNumber = user.phoneNumber,
                birthday = user.birthday,

                name = userFromGson.name,
                hobby = userFromGson.hobby,
                job = userFromGson.job,
                location = userFromGson.location,
                tags = userFromGson.tags
            ))
        }
    }
}