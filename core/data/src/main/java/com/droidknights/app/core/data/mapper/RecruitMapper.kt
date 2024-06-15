package com.droidknights.app.core.data.mapper

import com.droidknights.app.core.data.api.model.CategoryResponse
import com.droidknights.app.core.data.api.model.RecruitResponse
import com.droidknights.app.core.data.api.model.CompanyResponse
import com.droidknights.app.core.model.Category
import com.droidknights.app.core.model.Recruit
import com.droidknights.app.core.model.Company
import com.droidknights.app.core.model.Tag

internal fun RecruitResponse.toData(): Recruit =
    Recruit(
        id = this.id,
        title = this.title,
        content = this.content,
        companies = this.companies.map { it.toData() },
        tags = this.tags.map { Tag(it) },
        category = this.category?.toData() ?: Category.ETC,
        startTime = this.startTime,
        endTime = this.endTime,
        isBookmarked = false,
    )

internal fun CategoryResponse.toData(): Category =
    when (this) {
        CategoryResponse.ETC -> Category.ETC
        CategoryResponse.JOB -> Category.JOB
        CategoryResponse.PART_TIME -> Category.PART_TIME
        CategoryResponse.SIDE_JOB -> Category.SIDE_JOB
    }

internal fun CompanyResponse.toData(): Company =
    Company(
        name = this.name,
        introduction = this.introduction,
        imageUrl = this.imageUrl,
    )
