package com.droidknights.app.core.data.api.model

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RecruitResponse(
    @SerialName("id") val id: Long,
    @SerialName("title") val title: String,
    @SerialName("content") val content: String,
    @SerialName("imageUrl") val imageUrl: String,
    @SerialName("companies") val companies: List<CompanyResponse>,
    @SerialName("tags") val tags: List<TagResponse>,
    @SerialName("category") val category: CategoryResponse?,
    @SerialName("startTime") val startTime: LocalDateTime,
    @SerialName("endTime") val endTime: LocalDateTime,
)
