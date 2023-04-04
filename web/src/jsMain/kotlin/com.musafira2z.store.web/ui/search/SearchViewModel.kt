package com.musafira2z.store.web.ui.search

import com.copperleaf.ballast.core.BasicViewModel
import com.copperleaf.ballast.BallastViewModelConfiguration
import com.copperleaf.ballast.build
import com.musafira2z.store.ui.search.SearchContract
import kotlinx.coroutines.CoroutineScope

class SearchViewModel(
    coroutineScope: CoroutineScope,
    configBuilder: BallastViewModelConfiguration.Builder,
    eventHandler: SearchEventHandler
) : BasicViewModel<
        SearchContract.Inputs,
        SearchContract.Events,
        SearchContract.State>(
    coroutineScope = coroutineScope,
    config = configBuilder
        .build(),
    eventHandler = eventHandler,
)
