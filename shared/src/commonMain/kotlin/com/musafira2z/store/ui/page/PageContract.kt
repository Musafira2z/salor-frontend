package com.musafira2z.store.ui.page

import com.musafira2z.store.PageQuery
import com.musafira2z.store.utils.ResponseResource

object PageContract {
    data class State(
        val loading: Boolean = false,
        val slug: String? = null,
        val page: ResponseResource<PageQuery.Data> = ResponseResource.Idle
    )

    sealed class Inputs {
        data class Initialize(val slug: String?) : Inputs()
        data class GetPage(val slug: String?) : Inputs()
        data class UpdatePage(val page: ResponseResource<PageQuery.Data>) : Inputs()
        object GoBack : Inputs()
    }

    sealed class Events {
        object NavigateUp : Events()
    }
}
