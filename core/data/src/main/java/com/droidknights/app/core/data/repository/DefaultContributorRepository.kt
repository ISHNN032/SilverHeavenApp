package com.droidknights.app.core.data.repository

import com.droidknights.app.core.data.api.GithubApi
import com.droidknights.app.core.data.api.GithubRawApi
import com.droidknights.app.core.data.mapper.toData
import com.droidknights.app.core.data.repository.api.ContributorRepository
import com.droidknights.app.core.model.Contributor
import javax.inject.Inject

internal class DefaultContributorRepository @Inject constructor(
    private val githubApi: GithubApi,
    private val githubRawApi: GithubRawApi
) : ContributorRepository {

    override suspend fun getContributors(
        owner: String,
        name: String,
    ): List<Contributor> {
        val contributorResponse = githubApi.getContributors(owner, name)
        val contributorWithYearResponse = githubRawApi.getContributorWithYears()

        return contributorResponse.map { contributor ->
            val contributionYears =
                contributorWithYearResponse.firstOrNull { it.id == contributor.id }?.years
                    ?: emptyList()

            contributor.toData(contributionYears)
        }
    }
}
