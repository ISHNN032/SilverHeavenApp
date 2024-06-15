package com.droidknights.app.core.data.api.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal enum class CategoryResponse {
    ETC,

    @SerialName("Job")
    JOB,

    @SerialName("PartTime")
    PART_TIME,

    @SerialName("SideJob")
    SIDE_JOB
}
