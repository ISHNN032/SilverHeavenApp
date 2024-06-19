package com.droidknights.app.core.data.mapper

import com.droidknights.app.core.data.api.model.TagResponse
import com.droidknights.app.core.data.api.model.UserResponse
import com.droidknights.app.core.model.Tag
import com.droidknights.app.core.model.User

internal fun UserResponse.toData(): User =
    User(
        id = this.id.toString(),
        name = this.name,
        phoneNumber = this.phoneNumber,
        birthday = this.birthday,
        location = this.location,
        job = this.job,
        hobby = this.hobby,
        email = this.email,
        password = this.password,
        tags = this.tags.map { Tag(it.name) },
    )

internal fun User.toResponse(): UserResponse =
    UserResponse(
        id = if (id.isBlank()) 0L else id.toLong(),
        name = this.name,
        phoneNumber = this.phoneNumber,
        birthday = this.birthday,
        location = this.location,
        job = this.job,
        hobby = this.hobby,
        email = this.email,
        password = this.password,
        tags = this.tags.map { TagResponse(null, it.name) },
    )