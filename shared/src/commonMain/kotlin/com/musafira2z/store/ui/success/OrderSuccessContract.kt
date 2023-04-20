package com.musafira2z.store.ui.success

object OrderSuccessContract {
    data class State(
        val loading: Boolean = false,
        val slug: String = ""
    )

    sealed class Inputs {
        data class Initialize(val slug: String) : Inputs()
        object GoBack : Inputs()
    }

    sealed class Events {
        object NavigateUp : Events()
    }
}
