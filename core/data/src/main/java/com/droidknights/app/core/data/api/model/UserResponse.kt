package com.droidknights.app.core.data.api.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserResponse(
    @SerialName("id") val id: Long,
    @SerialName("name") val name: String,
    @SerialName("phoneNumber") val phoneNumber: String,
    @SerialName("birthday") val birthday: String,
    @SerialName("location")val location: String,
    @SerialName("job")val job: String,
    @SerialName("hobby") val hobby: String,
    @SerialName("email") val email: String,
    @SerialName("password")val password: String,
    @SerialName("tags") val tags: List<String>
)