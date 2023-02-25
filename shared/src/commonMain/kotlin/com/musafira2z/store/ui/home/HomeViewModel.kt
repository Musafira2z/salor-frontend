package com.musafira2z.store.ui.home

import com.copperleaf.ballast.core.BasicViewModel
import com.copperleaf.ballast.BallastViewModelConfiguration
import com.copperleaf.ballast.build
import com.copperleaf.ballast.withViewModel
import kotlinx.coroutines.CoroutineScope

class HomeViewModel(
    coroutineScope: CoroutineScope,
    configBuilder: BallastViewModelConfiguration.Builder,
    inputHandler: HomeInputHandler
) : BasicViewModel<
        HomeContract.Inputs,
        HomeContract.Events,
        HomeContract.State>(
    coroutineScope = coroutineScope,
    config = configBuilder
        .withViewModel(
            inputHandler = inputHandler,
            initialState = HomeContract.State(),
            name = "Home",
        )
        .build(),
    eventHandler = HomeEventHandler(),
)
