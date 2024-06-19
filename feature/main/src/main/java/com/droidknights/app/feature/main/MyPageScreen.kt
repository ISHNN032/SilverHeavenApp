package com.droidknights.app.feature.main

import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.BoxScopeInstance.align
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.droidknights.app.core.model.User
import com.droidknights.app.feature.home.HomeViewModel
import com.droidknights.app.feature.home.component.SponsorCard

import androidx.compose.foundation.border
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.draw.clip


@Composable
fun MyPageScreen(
    userViewModel: UserViewModel = hiltViewModel(),
) {
    val context = LocalContext.current
    val padding = 16.dp
    var user by remember { mutableStateOf<User?>(null) }

    LaunchedEffect(Unit) {
        user = userViewModel.getCurrentUser()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.backgroundColor))
            .padding(padding)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 30.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(id = R.string.my_page),
                color = colorResource(id = R.color.black),
                fontSize = 30.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 50.dp, top = 40.dp)
            )
            Image(
                painter = painterResource(id = R.drawable.ic_smile_face),
                contentDescription = null,
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .width(70.dp)
                    .height(70.dp)
                    .alpha(0.8f)
                    .padding(end = 10.dp)
            )
        }

        UserInfoLabel(stringResource(id = R.string.name))
        UserInfo(user?.name)

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(id = R.string.birthday),
                color = colorResource(id = R.color.black),
                fontSize = 18.sp,
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 8.dp),  // 좌측 패딩 추가
                textAlign = TextAlign.Start  // 왼쪽 정렬

            )
            Text(
                text = stringResource(id = R.string.gender),
                color = colorResource(id = R.color.black),
                fontSize = 18.sp,
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 8.dp),  // 좌측 패딩 추가
                textAlign = TextAlign.Start  // 왼쪽 정렬
            )
        }

        BirthdayAndIdRow(birthday = user?.birthday, id = user?.id) // birthday와 id를 일렬로 배치

        UserInfoLabel(stringResource(id = R.string.phone_number))
        UserInfo(user?.phoneNumber)

        UserInfoLabel(stringResource(id = R.string.address))
        UserInfo(user?.location)

        UserInfoLabel(stringResource(id = R.string.hobby))
        UserInfo(user?.hobby)

        UserInfoLabel(stringResource(id = R.string.major))
        UserInfo(user?.job)

        UserInfoLabel(stringResource(id = R.string.myEmail))
        UserInfo(user?.email)

        UserInfoLabel(stringResource(id = R.string.myPass))
        UserInfo(user?.password)

        Text(
            text = stringResource(id = R.string.ai_info_update),
            color = colorResource(id = R.color.black),
            fontSize = 16.sp,
            modifier = Modifier
                .padding(top = 30.dp)
                .align(Alignment.Start)
        )

        val sponsorsUiState by hiltViewModel<HomeViewModel>().sponsorsUiState.collectAsStateWithLifecycle()
        SponsorCard(uiState = sponsorsUiState, onClick = {
            context.startActivity(Intent(context, AiRegisterActivity::class.java))
        })
    }
}

@Composable
fun UserInfoLabel(label: String) {
    Text(
        text = label,
        color = colorResource(id = R.color.black),
        fontSize = 18.sp,
        modifier = Modifier
            .padding(top = 10.dp)
            .fillMaxWidth()
            .padding(top = 20.dp, start = 8.dp)
    )
}

@Composable
fun UserInfo(info: String?) {
    val shape = RoundedCornerShape(10.dp)

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .clip(shape)
            .background(color = colorResource(id = R.color.white))  // 배경 색 설정
            .border(
                width = 2.dp,
                color = colorResource(id = R.color.backgroundColor),  // 테두리 색상 참조
                shape = shape  // 둥근 모서리 모양 설정

            )
    ) {
        if (info != null) {
            Text(
                text = info,
                color = colorResource(id = R.color.black),
                fontSize = 18.sp,
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}

@Composable
fun BirthdayAndIdRow(birthday: String?, id: String?) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(modifier = Modifier.weight(1f)) {
            UserInfoBox(value = birthday)
        }
        Box(modifier = Modifier.weight(1f)) {
            UserInfoBox(value = id)
        }
    }
}

@Composable
fun UserInfoBox(value: String?) {
    val shape = RoundedCornerShape(16.dp)  // 둥근 모서리의 반경 설정

    Box(
        modifier = Modifier
            .fillMaxWidth()  // 부모의 전체 너비를 채움
            .padding(8.dp)
            .clip(shape)  // 모서리 둥글게 자르기
            .background(color = colorResource(id = R.color.white))  // 배경 색 설정
            .border(
                width = 2.dp,
                color = colorResource(id = R.color.backgroundColor),  // 테두리 색상 참조
                shape = shape  // 둥근 모서리 모양 설정
            )
            .padding(8.dp)  // 내부 여백
    ) {
        Text(
            text = value ?: "",
            color = colorResource(id = R.color.black),
            fontSize = 18.sp
        )
    }
}



