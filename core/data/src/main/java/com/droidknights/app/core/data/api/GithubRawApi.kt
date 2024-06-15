package com.droidknights.app.core.data.api

import com.droidknights.app.core.data.api.model.ContributionYearResponse
import com.droidknights.app.core.data.api.model.RecruitResponse
import com.droidknights.app.core.data.api.model.SponsorResponse
import retrofit2.http.GET

internal interface GithubRawApi {

    @GET("/ISHNN032/SilverHeavenApp/main/core/data/src/main/assets/sponsors.json")
    suspend fun getSponsors(): List<SponsorResponse>

    @GET("/ISHNN032/SilverHeavenApp/main/core/data/src/main/assets/recruits.json")
    suspend fun getRecruits(): List<RecruitResponse>

    @GET("/ISHNN032/SilverHeavenApp/main/core/data/src/main/assets/contributors.json")
    suspend fun getContributorWithYears(): List<ContributionYearResponse>
}
