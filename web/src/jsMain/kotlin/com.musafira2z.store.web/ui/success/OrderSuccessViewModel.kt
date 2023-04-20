package com.musafira2z.store.web.ui.success

import com.copperleaf.ballast.core.BasicViewModel
import com.copperleaf.ballast.BallastViewModelConfiguration
import com.copperleaf.ballast.build
import com.copperleaf.ballast.withViewModel
import com.musafira2z.store.ui.success.OrderSuccessContract
import kotlinx.coroutines.CoroutineScope

class OrderSuccessViewModel(
    coroutineScope: CoroutineScope,
    configBuilder: BallastViewModelConfiguration.Builder,
    eventHandler: OrderSuccessEventHandler
) : BasicViewModel<
        OrderSuccessContract.Inputs,
        OrderSuccessContract.Events,
        OrderSuccessContract.State>(
    coroutineScope = coroutineScope,
    config = configBuilder
        .build(),
    eventHandler = eventHandler,
)
