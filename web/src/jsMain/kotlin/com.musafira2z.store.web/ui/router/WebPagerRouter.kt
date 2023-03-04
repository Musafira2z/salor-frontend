package com.musafira2z.store.web.ui.router

import com.copperleaf.ballast.BallastViewModelConfiguration
import com.copperleaf.ballast.core.BasicViewModel
import com.copperleaf.ballast.eventHandler
import com.copperleaf.ballast.navigation.routing.RouterContract
import com.copperleaf.ballast.navigation.vm.Router
import kotlinx.coroutines.CoroutineScope


class WebPagerRouter(
    viewModelCoroutineScope: CoroutineScope,
    config: BallastViewModelConfiguration<
            RouterContract.Inputs<WebPage>,
            RouterContract.Events<WebPage>,
            RouterContract.State<WebPage>>,
) : BasicViewModel<
        RouterContract.Inputs<WebPage>,
        RouterContract.Events<WebPage>,
        RouterContract.State<WebPage>>(
    config = config,
    eventHandler = eventHandler { },
    coroutineScope = viewModelCoroutineScope,
), Router<WebPage>