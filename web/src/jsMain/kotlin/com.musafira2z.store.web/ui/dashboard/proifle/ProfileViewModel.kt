package com.musafira2z.store.web.ui.dashboard.proifle

import com.copperleaf.ballast.BallastViewModelConfiguration
import com.copperleaf.ballast.build
import com.copperleaf.ballast.core.BasicViewModel
import com.musafira2z.store.ui.profile.ProfileContract
import kotlinx.coroutines.CoroutineScope

class ProfileViewModel(
    coroutineScope: CoroutineScope,
    configBuilder: BallastViewModelConfiguration.Builder,
    eventHandler: ProfileEventHandler
) : BasicViewModel<
        ProfileContract.Inputs,
        ProfileContract.Events,
        ProfileContract.State>(
    coroutineScope = coroutineScope,
    config = configBuilder
        .build(),
    eventHandler = eventHandler,
)
