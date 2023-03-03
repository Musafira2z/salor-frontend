package com.musafira2z.store.web.ui.components

import androidx.compose.runtime.Composable
import org.jetbrains.compose.web.dom.Div
import com.musafira2z.store.web.ui.utils.toClasses

@Composable
fun Products() {
    Div(attrs = {
        classes("py-10")
    }) {
        Div(attrs = {
            toClasses("grid grid-cols-12 gap-5")
        }) {
            repeat(20) {
                Product()
            }
        }
    }
}

