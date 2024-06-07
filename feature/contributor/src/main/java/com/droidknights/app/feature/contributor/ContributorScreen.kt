package com.droidknights.app.feature.contributor

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.droidknights.app.core.designsystem.component.BottomLogo
import com.droidknights.app.core.model.Contributor
import com.droidknights.app.feature.contributor.component.ContributorCard
import com.droidknights.app.feature.contributor.component.ContributorTopAppBar
import com.droidknights.app.feature.contributor.component.ContributorTopBanner
import com.droidknights.app.feature.contributor.model.ContributorsUiState
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.flow.collectLatest

@Composable
fun ContributorRoute(
    onBackClick: () -> Unit,
    onShowErrorSnackBar: (throwable: Throwable?) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: ContributorViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.errorFlow.collectLatest { throwable -> onShowErrorSnackBar(throwable) }
    }

    ContributorScreen(
        uiState = uiState,
        onBackClick = onBackClick,
        modifier = modifier,
    )
}

@Composable
internal fun ContributorScreen(
    uiState: ContributorsUiState,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val lazyListState = rememberLazyListState()
    val isAppBarAtTop by remember {
        derivedStateOf { lazyListState.firstVisibleItemIndex == 0 }
    }

    Box(
        modifier = modifier.navigationBarsPadding(),
    ) {
        ContributorList(
            uiState = uiState,
            lazyListState = lazyListState
        )
    }
    ContributorTopAppBar(
        isAtTop = isAppBarAtTop,
        onBackClick = onBackClick,
    )
}

@Composable
private fun ContributorList(
    uiState: ContributorsUiState,
    lazyListState: LazyListState,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = modifier,
        state = lazyListState,
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        item {
            ContributorTopBanner()
        }

        when (uiState) {
            ContributorsUiState.Loading -> {
                items(SHIMMERING_ITEM_COUNT) {
                    ContributorCard(
                        contributor = null,
                        modifier = Modifier.padding(horizontal = 8.dp)
                    )
                }
            }

            is ContributorsUiState.Contributors -> {
                items(uiState.contributors) { contributor ->
                    ContributorCard(
                        contributor = contributor,
                        modifier = Modifier.padding(horizontal = 8.dp),
                    )
                }
            }
        }

        item {
            Footer(modifier = Modifier.padding(bottom = 16.dp))
        }
    }
}

@Composable
private fun Footer(modifier: Modifier = Modifier) {
    Box(modifier = modifier) {
        BottomLogo()
    }
}

private const val SHIMMERING_ITEM_COUNT = 4

internal class ContributorPreviewParameterProvider : PreviewParameterProvider<ContributorsUiState> {
    override val values = sequenceOf(
        ContributorsUiState.Loading,
        ContributorsUiState.Contributors(
            persistentListOf(
                Contributor(
                    0L,
                    "Contributor1",
                    "https://avatars.githubusercontent.com/u/25101514",
                    "https://github.com/droidknights",
                ),
                Contributor(
                    1L,
                    "Contributor2",
                    "https://avatars.githubusercontent.com/u/25101514",

                    "https://github.com/droidknights",
                ),
            )
        )
    )
}

@Preview
@Composable
private fun ContributorScreenPreview(
    @PreviewParameter(ContributorPreviewParameterProvider::class) uiState: ContributorsUiState,
) {
    ContributorScreen(
        uiState = uiState,
        onBackClick = {},
    )
}
