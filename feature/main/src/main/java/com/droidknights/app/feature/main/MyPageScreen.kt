package com.droidknights.app.feature.main

import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.droidknights.app.core.model.User
import com.droidknights.app.feature.home.HomeViewModel
import com.droidknights.app.feature.home.component.SponsorCard

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
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 60.dp, top = 16.dp)
            )
            Image(
                painter = painterResource(id = R.drawable.ic_smile_face),
                contentDescription = null,
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .width(80.dp)
                    .height(80.dp)
                    .padding(start = 15.dp)
                    .alpha(0.8f)
            )
        }

        UserInfoLabel(stringResource(id = R.string.name))
        UserInfo(user?.name)

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 30.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "생일",
                color = colorResource(id = R.color.black),
                fontSize = 18.sp,
                modifier = Modifier.weight(1f)
            )
            Text(
                text = stringResource(id = R.string.gender),
                color = colorResource(id = R.color.black),
                fontSize = 18.sp,
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 160.dp)
            )
        }

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
            .padding(top = 30.dp)
    )
}

@Composable
fun UserInfo(info: String?) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
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