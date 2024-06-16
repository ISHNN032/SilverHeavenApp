package com.droidknights.app.core.model

import kotlinx.datetime.LocalDateTime

data class Recruit(
    val id: String,
    val title: String,
    val content: String,
    val imageUrl: String,
    val companies: List<Company>,
    val tags: List<Tag>,
    val category: Category,
    val startTime: LocalDateTime,
    val endTime: LocalDateTime,
    val isBookmarked: Boolean
)
