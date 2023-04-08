package com.musafira2z.store.ui.home

import com.copperleaf.ballast.BallastViewModelConfiguration
import com.copperleaf.ballast.build
import com.copperleaf.ballast.core.AndroidViewModel

class HomeViewModel(
    configBuilder: BallastViewModelConfiguration.Builder,
) : AndroidViewModel<
        HomeContract.Inputs,
        HomeContract.Events,
        HomeContract.State>(
    config = configBuilder
        .build(),
)
