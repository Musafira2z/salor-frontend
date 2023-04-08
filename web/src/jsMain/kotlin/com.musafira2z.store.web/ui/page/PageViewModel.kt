package com.musafira2z.store.web.ui.page

import com.copperleaf.ballast.BallastViewModelConfiguration
import com.copperleaf.ballast.build
import com.copperleaf.ballast.core.BasicViewModel
import com.musafira2z.store.ui.page.PageContract
import kotlinx.coroutines.CoroutineScope

class PageViewModel(
    coroutineScope: CoroutineScope,
    configBuilder: BallastViewModelConfiguration.Builder,
    eventHandler: PageEventHandler
) : BasicViewModel<
        PageContract.Inputs,
        PageContract.Events,
        PageContract.State>(
    coroutineScope = coroutineScope,
    config = configBuilder
        .build(),
    eventHandler = eventHandler,
)
