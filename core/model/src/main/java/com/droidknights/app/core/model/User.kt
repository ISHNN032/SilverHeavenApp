package com.droidknights.app.core.model

data class User(
    val id: String,
    val name: String,
    val phoneNumber: String,
    val birthday: String,
    val location: String,
    val job: String,
    val hobby: String,
    val email: String,
    val password: String,
    val tags: List<Tag>
)