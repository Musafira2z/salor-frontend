package com.musafira2z.store.web.ui.dashboard.orders

import com.copperleaf.ballast.BallastViewModelConfiguration
import com.copperleaf.ballast.build
import com.copperleaf.ballast.core.BasicViewModel
import com.musafira2z.store.ui.orders.OrdersContract
import kotlinx.coroutines.CoroutineScope

class OrdersViewModel(
    coroutineScope: CoroutineScope,
    configBuilder: BallastViewModelConfiguration.Builder,
    eventHandler: OrdersEventHandler
) : BasicViewModel<
        OrdersContract.Inputs,
        OrdersContract.Events,
        OrdersContract.State>(
    coroutineScope = coroutineScope,
    config = configBuilder
        .build(),
    eventHandler = eventHandler,
)
