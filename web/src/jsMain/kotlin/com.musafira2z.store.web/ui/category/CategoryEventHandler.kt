package com.musafira2z.store.web.ui.category

import com.copperleaf.ballast.EventHandler
import com.copperleaf.ballast.EventHandlerScope
import com.copperleaf.ballast.navigation.routing.RouterContract
import com.copperleaf.ballast.navigation.routing.build
import com.copperleaf.ballast.navigation.routing.directions
import com.copperleaf.ballast.navigation.routing.path
import com.copperleaf.ballast.navigation.vm.Router
import com.musafira2z.store.ui.category.CategoryContract
import com.musafira2z.store.web.ui.router.WebPage

class CategoryEventHandler(
    private val router: Router<WebPage>
) : EventHandler<
        CategoryContract.Inputs,
        CategoryContract.Events,
        CategoryContract.State> {
    override suspend fun EventHandlerScope<
            CategoryContract.Inputs,
            CategoryContract.Events,
            CategoryContract.State>.handleEvent(
        event: CategoryContract.Events
    ) = when (event) {
        is CategoryContract.Events.NavigateUp -> {

        }
        is CategoryContract.Events.GoCategoryPage -> {
            router.trySend(
                RouterContract.Inputs.GoToDestination(
                    WebPage.Category.directions().path(event.slug).build()
                )
            )
            Unit
        }
    }
}