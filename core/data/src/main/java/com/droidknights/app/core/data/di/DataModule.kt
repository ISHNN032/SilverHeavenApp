package com.droidknights.app.core.data.di

import android.content.Context
import com.droidknights.app.core.data.api.AwsLambdaApi
import com.droidknights.app.core.data.api.GithubApi
import com.droidknights.app.core.data.api.GithubRawApi
import com.droidknights.app.core.data.api.fake.AssetsGithubRawApi
import com.droidknights.app.core.data.repository.DefaultContributorRepository
import com.droidknights.app.core.data.repository.DefaultRecruitRepository
import com.droidknights.app.core.data.repository.DefaultSettingsRepository
import com.droidknights.app.core.data.repository.DefaultSponsorRepository
import com.droidknights.app.core.data.repository.DefaultUserRepository
import com.droidknights.app.core.data.repository.api.ContributorRepository
import com.droidknights.app.core.data.repository.api.RecruitRepository
import com.droidknights.app.core.data.repository.api.SettingsRepository
import com.droidknights.app.core.data.repository.api.SponsorRepository
import com.droidknights.app.core.data.repository.api.UserRepository
import com.droidknights.app.core.datastore.datasource.DefaultSessionPreferencesDataSource
import com.droidknights.app.core.datastore.datasource.DefaultUserPreferencesDataSource
import com.droidknights.app.core.datastore.datasource.SessionPreferencesDataSource
import com.droidknights.app.core.datastore.datasource.UserPreferencesDataSource
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
internal abstract class DataModule {

    @Binds
    abstract fun bindsSettingsRepository(
        repository: DefaultSettingsRepository,
    ): SettingsRepository

    @Binds
    abstract fun bindSessionLocalDataSource(
        dataSource: DefaultSessionPreferencesDataSource,
    ): SessionPreferencesDataSource

    @Binds
    abstract fun bindUserLocalDataSource(
        dataSource: DefaultUserPreferencesDataSource,
    ): UserPreferencesDataSource

    @InstallIn(SingletonComponent::class)
    @Module
    internal object FakeModule {

        @Provides
        @Singleton
        fun provideSponsorRepository(
            githubRawApi: GithubRawApi,
        ): SponsorRepository =
            DefaultSponsorRepository(githubRawApi)

        @Provides
        @Singleton
        fun provideSessionRepository(
            awsLambdaApi: AwsLambdaApi,
            sessionDataSource: SessionPreferencesDataSource,
        ): RecruitRepository =
            DefaultRecruitRepository(awsLambdaApi, sessionDataSource)

        @Provides
        @Singleton
        fun provideContributorRepository(
            githubApi: GithubApi,
            githubRawApi: AssetsGithubRawApi,
        ): ContributorRepository =
            DefaultContributorRepository(githubApi, githubRawApi)

        @Provides
        @Singleton
        fun provideUserRepository(
            awsLambdaApi: AwsLambdaApi,
            userDataSource: UserPreferencesDataSource
        ): UserRepository =
            DefaultUserRepository(awsLambdaApi, userDataSource)

        @Provides
        @Singleton
        fun provideGithubRawApi(
            @ApplicationContext context: Context,
            json: Json,
        ): AssetsGithubRawApi =
            AssetsGithubRawApi(
                json = json,
                sponsors = context.assets.open("sponsors.json"),
                recruits = context.assets.open("recruits.json"),
                contributors = context.assets.open("contributors.json"),
            )
    }
}
