package com.droidknights.app.feature.home.component

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.droidknights.app.feature.home.BuildConfig
import com.droidknights.app.feature.home.utils.ChatGPTApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.Locale

@Composable
fun SpeechTestCard(
    onSpeechTextReceived: @Composable (String) -> Unit,
    onGeneratedTextReceived: @Composable (String) -> Unit
) {
    val startString = "지금부터 나한테 이런 순서로 질문을 해줘,\n" +
            "다음 질문을 하기 전에, 답변에 대한 추가 질문을 해줘. 질문당 2회 까지.\n" +
            "모든 질문에 대한 답변을 다 받으면 내 답변을 토대로 json 파일을 만들어줘.\n" +
            "\n" +
            "name: 이름\n" +
            "hobby: 취미\n" +
            "job: 전공\n" +
            "location: 사는 동네\n" +
            "tags:[]에 {\"name\":} 으로 추가 몸 불편한 곳은 없는지\n"+
            "tags:[]에 {\"name\":} 추가 가입목적\n" +
            "대답은 하지 말고, 바로 인사하고 질문부터 시작해줘. 질문에 번호는 붙이지 말고.\n" +
            "tags[]에 취미랑 전공에서 대답한 내용도 추가해줘"

    val chatGPTApi = Retrofit.Builder()
        .baseUrl("https://api.openai.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(ChatGPTApi::class.java)

    val viewModel: ChatGPTViewModel = remember {
        ChatGPTViewModel(chatGPTApi)
    }

    val uiState by viewModel.uiState.collectAsState()

    val requestCodeSpeechRecognition = 123

    val context = LocalContext.current

    // 음성 인식 시작
    val recognizerIntent = remember {
        Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
            putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
            putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
        }
    }

    val activityResultLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val recognizedText = result.data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)?.get(0)
            recognizedText?.let {
                viewModel.speechText(it)
                viewModel.generateText(it)
            }
        }
    }

    when (uiState) {
        is SpeechTestUiState.Loading -> {
            CircularProgressIndicator()
        }
        is SpeechTestUiState.Error -> {
            Text("Error: ${(uiState as SpeechTestUiState.Error).errorMessage}")
        }
        is SpeechTestUiState.Success -> {
            Text((uiState as SpeechTestUiState.Success).generatedText)
        }
        is SpeechTestUiState.Idle -> {
            Button(
                onClick = {
                    viewModel.generateText(startString)
                },
                Modifier.fillMaxWidth().padding(20.dp, 0.dp)
            ) {
                Text("대화 시작하기")
            }
        }
    }

    viewModel.speechText.collectAsState().value?.let { text ->
        onSpeechTextReceived(text)
    }

    viewModel.generatedText.collectAsState().value?.let { resource ->
        when (resource) {
            is ChatGPTViewModel.Resource.Loading -> {
                // 로딩 상태 UI 표시
            }
            is ChatGPTViewModel.Resource.Success -> {
                // 생성된 텍스트 처리
                onGeneratedTextReceived(resource.data)
                requestSpeechRecognitionPermission(context, requestCodeSpeechRecognition)
                activityResultLauncher.launch(recognizerIntent.putExtra(RecognizerIntent.EXTRA_PROMPT, resource.data.replace("\n","")))
            }
            is ChatGPTViewModel.Resource.Error -> {
                // 에러 상태 UI 표시
            }
        }
    }
}

private fun requestSpeechRecognitionPermission(
    context: Context,
    requestCode: Int
) {
    if (ContextCompat.checkSelfPermission(context, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
        ActivityCompat.requestPermissions(
            context as Activity,
            arrayOf(Manifest.permission.RECORD_AUDIO),
            requestCode
        )
    }
}

sealed class SpeechTestUiState {
    object Idle : SpeechTestUiState()
    object Loading : SpeechTestUiState()
    data class Success(val generatedText: String) : SpeechTestUiState()
    data class Error(val errorMessage: String) : SpeechTestUiState()
}

class ChatGPTViewModel(private val chatGPTApi: ChatGPTApi) : ViewModel() {
    val previousMessages = mutableListOf<ChatGPTApi.ChatGPTRequest.Message>()

    private val _speechText = MutableStateFlow("")
    private val _generatedText = MutableStateFlow<Resource<String>?>(null)
    val speechText: StateFlow<String?> = _speechText
    val generatedText: StateFlow<Resource<String>?> = _generatedText
    private val _uiState = MutableStateFlow<SpeechTestUiState>(SpeechTestUiState.Idle)
    val uiState: StateFlow<SpeechTestUiState> = _uiState.asStateFlow()

    fun speechText(prompt: String) {
        _speechText.value = prompt
    }

    fun generateText(prompt: String) {
        viewModelScope.launch {
            _generatedText.value = Resource.Loading()
            try {
                Log.d("SpeechTest", "send: $prompt");
                previousMessages.add(ChatGPTApi.ChatGPTRequest.Message("user", prompt))
                val request = ChatGPTApi.ChatGPTRequest(messages = previousMessages)
                val response = chatGPTApi.generateText(BuildConfig.GPT_API_KEY, request)

                val generatedText = response.choices.firstOrNull()?.message?.content.orEmpty()
                response.choices.first {
                    previousMessages.add(it.message)
                }

                Log.d("SpeechTest", "response: $generatedText");
                _generatedText.value = Resource.Success(generatedText)
            } catch (e: Exception) {
                _generatedText.value = Resource.Error(e.message ?: "Unknown error")
            }
        }
    }

    sealed class Resource<out T> {
        class Loading<out T> : Resource<T>()
        data class Success<out T>(val data: T) : Resource<T>()
        data class Error<T>(val message: String) : Resource<T>()
    }
}
