package com.musafira2z.store.ui.checkout

object CheckoutContract {
    data class State(
        val loading: Boolean = false,
    )

    sealed class Inputs {
        object Initialize : Inputs()
        object GoBack : Inputs()
    }

    sealed class Events {
        object NavigateUp : Events()
    }
}
