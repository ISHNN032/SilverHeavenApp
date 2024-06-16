package com.droidknights.app.feature.main

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.droidknights.app.core.designsystem.theme.KnightsTheme
import com.droidknights.app.core.model.Category
import com.droidknights.app.core.model.Recruit
import com.droidknights.app.core.ui.RoomText
import com.droidknights.app.feature.home.component.SpeechTestCard
import com.droidknights.app.feature.session.R
import com.droidknights.app.feature.session.SessionViewModel
import com.droidknights.app.feature.session.model.SessionState
import kotlinx.coroutines.flow.collectLatest


@Composable
internal fun AiRegisterScreen(
    onBackClick: () -> Unit,
    onResultClick: (Recruit) -> Unit,
    onShowErrorSnackBar: (throwable: Throwable?) -> Unit,
    sessionViewModel: SessionViewModel = hiltViewModel(),
) {
    val scrollState = rememberScrollState()
    LaunchedEffect(Unit) {
        sessionViewModel.errorFlow.collectLatest { throwable -> onShowErrorSnackBar(throwable) }
    }
    Column(
        Modifier
            .padding(10.dp)
            .padding(horizontal = 8.dp)
            .verticalScroll(scrollState)
            .padding(bottom = 4.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        var speechText by remember { mutableStateOf("") }
        var generatedText by remember { mutableStateOf("") }

        SpeechTestCard (
            onSpeechTextReceived = { text ->
                speechText = text
            },
            onGeneratedTextReceived = { text ->
                generatedText = text
            }
        )

        Text(
            text = ">:\n $speechText",
            modifier = Modifier.padding(16.dp)
        )

        Text(
            text = "<:\n $generatedText",
            modifier = Modifier.padding(16.dp)
        )
    }
}

@Composable
private fun SessionList(
    sessionState: SessionState,
    onSessionClick: (Recruit) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = modifier,
        state = sessionState.listState,
        contentPadding = PaddingValues(horizontal = 8.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        sessionState.groups.forEachIndexed { groupIndex, group ->
            itemsIndexed(group.recruits) { sessionIndex, session ->
                val isFirstSession = sessionIndex == 0
                val isLastGroup = groupIndex == sessionState.groups.size - 1
                val isLastSession = sessionIndex == group.recruits.size - 1

                Column {
                    if (isFirstSession) {
                        RoomTitle(
                            category = group.category,
                            topPadding = if (groupIndex == 0) SessionTopSpace else SessionGroupSpace,
                        )
                    }
                    //SessionCard(recruit = session, onSessionClick = onSessionClick)
                }

                if (isLastGroup && isLastSession) {
                    DroidKnightsFooter()
                }
            }
        }
    }
}

@Composable
private fun RoomTitle(
    category: Category,
    topPadding: Dp,
) {
    Column(modifier = Modifier.padding(start = 20.dp, top = topPadding, end = 20.dp)) {
        RoomText(
            category = category,
            style = KnightsTheme.typography.titleLargeB,
            color = MaterialTheme.colorScheme.onPrimaryContainer,
        )

        Spacer(modifier = Modifier.height(8.dp))

        HorizontalDivider(thickness = 2.dp, color = MaterialTheme.colorScheme.onPrimaryContainer)

        Spacer(modifier = Modifier.height(32.dp))
    }
}

private val SessionTopSpace = 16.dp
private val SessionGroupSpace = 100.dp

@Composable
private fun DroidKnightsFooter() {
    Text(
        modifier = Modifier.fillMaxWidth().wrapContentHeight().padding(top = 56.dp, bottom = 80.dp),
        text = stringResource(id = R.string.footer_text),
        style = KnightsTheme.typography.labelMediumR,
        color = Color.LightGray,
        textAlign = TextAlign.Center
    )
}