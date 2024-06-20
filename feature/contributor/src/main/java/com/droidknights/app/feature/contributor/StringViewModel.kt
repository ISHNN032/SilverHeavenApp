package com.droidknights.app.feature.contributor

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class StringViewModel @Inject constructor() : ViewModel() {
    private val strings = listOf(
        "한사랑 산악회", "롤 솔랭 같이 하실 분", "독서 카페", "나는 취미가 개발", "스터디 그룹",
        "영등포 광야 풋살 모임", "영어 회화 스터디", "돌잡이 클라이밍", "춤신춤왕", "가산독산 2030",
        "황야의 골프 모임", "검도회", "터틀 러닝 크루"
    )
    private var currentIndex = 0

    private val _currentString = MutableStateFlow(strings[currentIndex])
    val currentString: StateFlow<String> get() = _currentString

    // 순차적으로 문자열을 선택하는 메서드
    fun getNextString() {
        currentIndex = (currentIndex + 1) % strings.size
        _currentString.value = strings[currentIndex]
    }

    // 랜덤하게 문자열을 선택하는 메서드
    fun getRandomString(): String {
        return strings.random()
    }
}