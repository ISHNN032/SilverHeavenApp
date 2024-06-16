package com.droidknights.app.core.data.api

import com.droidknights.app.core.data.api.model.RecruitResponse
import com.droidknights.app.core.data.api.model.UserResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface AwsLambdaApi {
    @GET("users/all")
    suspend fun getUsers(): List<UserResponse>

    @POST("users/register")
    suspend fun createUser(@Body user: UserResponse): UserResponse

    @POST("users/update")
    suspend fun updateUser(@Body user: UserResponse): UserResponse

    @GET("recruits/all")
    suspend fun getRecruits(): List<RecruitResponse>
}