package com.droidknights.app.core.data.di

import com.droidknights.app.core.data.api.AwsLambdaApi
import com.droidknights.app.core.data.api.GithubApi
import com.droidknights.app.core.data.api.GithubRawApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory

@Module
@InstallIn(SingletonComponent::class)
internal object ApiModule {

    @Provides
    @Singleton
    fun provideOkhttpClient(): OkHttpClient = OkHttpClient.Builder().build()

    @Provides
    @Singleton
    fun provideConverterFactory(
        json: Json,
    ): Converter.Factory {
        return json.asConverterFactory("application/json".toMediaType())
    }

    @Provides
    @Singleton
    fun provideAwsLambdaApi(
        okHttpClient: OkHttpClient,
        converterFactory: Converter.Factory,
    ): AwsLambdaApi {
        return Retrofit.Builder()
            .baseUrl("https://3496qxk81c.execute-api.us-east-2.amazonaws.com/Prod/")
            .addConverterFactory(converterFactory)
            .client(okHttpClient).build()
            .create(AwsLambdaApi::class.java)
    }

    @Provides
    @Singleton
    fun provideGithubApi(
        okHttpClient: OkHttpClient,
        converterFactory: Converter.Factory,
    ): GithubApi {
        return Retrofit.Builder()
            .baseUrl("https://api.github.com/")
            .addConverterFactory(converterFactory)
            .client(okHttpClient).build()
            .create(GithubApi::class.java)
    }

    @Provides
    @Singleton
    fun provideGitRawApi(
        okHttpClient: OkHttpClient,
        converterFactory: Converter.Factory,
    ): GithubRawApi = Retrofit.Builder()
        .baseUrl("https://raw.githubusercontent.com/")
        .addConverterFactory(converterFactory)
        .client(okHttpClient).build()
        .create(GithubRawApi::class.java)

    @Provides
    @Singleton
    fun provideJson(): Json = Json {
        ignoreUnknownKeys = true
        coerceInputValues = true
    }
}
