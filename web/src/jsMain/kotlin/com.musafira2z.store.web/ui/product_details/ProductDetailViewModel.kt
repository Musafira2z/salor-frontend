package com.musafira2z.store.web.ui.product_details

import com.copperleaf.ballast.BallastViewModelConfiguration
import com.copperleaf.ballast.build
import com.copperleaf.ballast.core.BasicViewModel
import com.musafira2z.store.ui.product_details.ProductDetailContract
import kotlinx.coroutines.CoroutineScope

class ProductDetailViewModel(
    coroutineScope: CoroutineScope,
    configBuilder: BallastViewModelConfiguration.Builder,
    eventHandler: ProductDetailEventHandler
) : BasicViewModel<
        ProductDetailContract.Inputs,
        ProductDetailContract.Events,
        ProductDetailContract.State>(
    coroutineScope = coroutineScope,
    config = configBuilder
        .build(),
    eventHandler = eventHandler
)
