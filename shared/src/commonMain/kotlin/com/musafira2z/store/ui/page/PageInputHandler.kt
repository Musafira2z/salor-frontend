package com.musafira2z.store.ui.page

import com.apollographql.apollo3.ApolloClient
import com.copperleaf.ballast.InputHandler
import com.copperleaf.ballast.InputHandlerScope
import com.copperleaf.ballast.postInput
import com.musafira2z.store.PageQuery
import com.musafira2z.store.type.LanguageCodeEnum
import com.musafira2z.store.utils.ResponseResource

class PageInputHandler(
    private val apolloClient: ApolloClient
) : InputHandler<
        PageContract.Inputs,
        PageContract.Events,
        PageContract.State> {
    override suspend fun InputHandlerScope<
            PageContract.Inputs,
            PageContract.Events,
            PageContract.State>.handleInput(
        input: PageContract.Inputs
    ) = when (input) {
        is PageContract.Inputs.Initialize -> {
            updateState { it.copy(slug = input.slug) }
            postInput(PageContract.Inputs.GetPage(input.slug))
        }
        is PageContract.Inputs.GoBack -> {
            postEvent(PageContract.Events.NavigateUp)
        }
        is PageContract.Inputs.GetPage -> {
            sideJob("FetchPage") {
                try {
                    val data = apolloClient.query(
                        PageQuery(slug = input.slug ?: "", locale = LanguageCodeEnum.EN)
                    ).execute().data!!
                    postInput(PageContract.Inputs.UpdatePage(ResponseResource.Success(data)))
                } catch (e: Exception) {
                    e.printStackTrace()
                    postInput(PageContract.Inputs.UpdatePage(ResponseResource.Error(e)))
                }
            }
        }
        is PageContract.Inputs.UpdatePage -> {
            updateState { it.copy(page = input.page) }
        }
    }
}
