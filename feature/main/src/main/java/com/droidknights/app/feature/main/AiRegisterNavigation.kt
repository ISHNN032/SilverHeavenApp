package com.droidknights.app.feature.main

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.droidknights.app.core.model.Recruit
import com.droidknights.app.core.navigation.Route

fun NavController.navigateAiRegister() {
    navigate(Route.AiRegister)
}

fun NavGraphBuilder.aiRegisterNavGraph(
    onBackClick: () -> Unit,
    onResultClick: (Recruit) -> Unit,
    onShowErrorSnackBar: (throwable: Throwable?) -> Unit,
) {
    composable<Route.AiRegister> {
        AiRegisterScreen()
    }

    composable<Route.SessionDetail> { navBackStackEntry ->
        val sessionId = navBackStackEntry.toRoute<Route.SessionDetail>().sessionId

    }
}