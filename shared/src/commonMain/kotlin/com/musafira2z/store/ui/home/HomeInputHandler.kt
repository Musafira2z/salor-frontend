package com.musafira2z.store.ui.home

import com.copperleaf.ballast.InputHandler
import com.copperleaf.ballast.InputHandlerScope
import com.copperleaf.ballast.observeFlows
import com.copperleaf.ballast.postInput
import com.musafira2z.store.repository.menu.MenuRepository
import kotlinx.coroutines.flow.map

class HomeInputHandler(
    private val menuRepository: MenuRepository
) : InputHandler<
        HomeContract.Inputs,
        HomeContract.Events,
        HomeContract.State> {
    override suspend fun InputHandlerScope<
            HomeContract.Inputs,
            HomeContract.Events,
            HomeContract.State>.handleInput(
        input: HomeContract.Inputs
    ) = when (input) {
        is HomeContract.Inputs.Initialize -> {
            postInput(HomeContract.Inputs.FetchHomeBlocks(true))
        }
        is HomeContract.Inputs.GoBack -> {
            postEvent(HomeContract.Events.NavigateUp)
        }
        is HomeContract.Inputs.FetchHomeBlocks -> {
            observeFlows("FetchHomeBlocks") {
                listOf(
                    menuRepository.getHomeBlock(input.forceRefresh)
                        .map { HomeContract.Inputs.UpdateHomeBlocks(it) }
                )
            }
        }
        is HomeContract.Inputs.UpdateHomeBlocks -> {
            updateState { it.copy(blocks = input.blocks) }
        }
    }
}
