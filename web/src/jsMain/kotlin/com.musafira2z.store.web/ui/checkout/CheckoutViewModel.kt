package com.musafira2z.store.web.ui.checkout

import com.copperleaf.ballast.BallastViewModelConfiguration
import com.copperleaf.ballast.build
import com.copperleaf.ballast.core.BasicViewModel
import com.musafira2z.store.ui.checkout.CheckoutContract
import kotlinx.coroutines.CoroutineScope

class CheckoutViewModel(
    coroutineScope: CoroutineScope,
    configBuilder: BallastViewModelConfiguration.Builder,
    eventHandler: CheckoutEventHandler
) : BasicViewModel<
        CheckoutContract.Inputs,
        CheckoutContract.Events,
        CheckoutContract.State>(
    coroutineScope = coroutineScope,
    config = configBuilder
        .build(),
    eventHandler = eventHandler,
)