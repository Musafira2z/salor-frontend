package com.musafira2z.store.ui.home

import com.copperleaf.ballast.core.AndroidViewModel
import com.copperleaf.ballast.BallastViewModelConfiguration
import com.copperleaf.ballast.build
import com.copperleaf.ballast.withViewModel

class HomeViewModel(
    configBuilder: BallastViewModelConfiguration.Builder,
) : AndroidViewModel<
        HomeContract.Inputs,
        HomeContract.Events,
        HomeContract.State>(
    config = configBuilder
        .withViewModel(
            inputHandler = HomeInputHandler(),
            initialState = HomeContract.State(),
            name = "Home",
        )
        .build(),
)
