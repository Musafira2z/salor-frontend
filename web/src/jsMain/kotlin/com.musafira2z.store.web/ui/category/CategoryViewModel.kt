package com.musafira2z.store.web.ui.category

import com.copperleaf.ballast.BallastViewModelConfiguration
import com.copperleaf.ballast.build
import com.copperleaf.ballast.core.BasicViewModel
import com.musafira2z.store.ui.category.CategoryContract
import kotlinx.coroutines.CoroutineScope

class CategoryViewModel(
    coroutineScope: CoroutineScope,
    configBuilder: BallastViewModelConfiguration.Builder,
    categoryEventHandler: CategoryEventHandler
) : BasicViewModel<
        CategoryContract.Inputs,
        CategoryContract.Events,
        CategoryContract.State>(
    coroutineScope = coroutineScope,
    config = configBuilder
        .build(),
    eventHandler = categoryEventHandler,
)