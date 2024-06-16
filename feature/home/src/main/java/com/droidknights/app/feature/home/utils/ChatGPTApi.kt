package com.droidknights.app.feature.home.utils;

import com.google.gson.annotations.SerializedName;

import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

interface ChatGPTApi {
    @POST("v1/chat/completions")
    suspend fun generateText(
            @Header("Authorization")apiKey: String,
            @Body request: ChatGPTRequest
    ): ChatGPTResponse

    data class ChatGPTRequest(
        @SerializedName("model") val model: String = "gpt-4o",
        @SerializedName("messages") val messages: List<Message>,
        @SerializedName("max_tokens") val maxTokens: Int = 2048,
        @SerializedName("temperature") val temperature: Double = 0.7,
        @SerializedName("top_p") val topP: Double = 1.0,
        @SerializedName("n") val n: Int = 1,
        @SerializedName("stream") val stream: Boolean = false
    ) {
        data class Message(
            @SerializedName("role") val role: String,
            @SerializedName("content") val content: String
        )
    }

    data class ChatGPTResponse(
        @SerializedName("choices") val choices: List<Choice>,
        @SerializedName("usage") val usage: Usage
    ) {
        data class Choice(
            @SerializedName("message") val message: ChatGPTRequest.Message,
            @SerializedName("index") val index: Int,
            @SerializedName("logprobs") val logprobs: Any?,
            @SerializedName("finish_reason") val finishReason: String
        )

        data class Usage(
                @SerializedName("prompt_tokens") val promptTokens: Int,
                @SerializedName("completion_tokens") val completionTokens: Int,
                @SerializedName("total_tokens") val totalTokens: Int
        )
    }
}
