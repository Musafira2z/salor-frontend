package com.musafira2z.store.web.ui.components

import androidx.compose.runtime.Composable
import org.jetbrains.compose.web.dom.Div

@Composable
fun Drawer(
    isOpen: Boolean = false, content: @Composable () -> Unit
) {
    val openClasses =
        if (isOpen) "transition-opacity opacity-100 duration-500 translate-x-0" else "transition-all delay-500 opacity-0 translate-x-full"
    Div(attrs = {
        classes(
            "z-11",
            "fixed",
            "overflow-auto",
            "z-50",
            "bg-gray-900",
            "bg-opacity-25",
            "inset-0",
            "transform",
            "ease-in-out",
            *openClasses.split(" ").toTypedArray()
        )
    }) {
        Div(attrs = {
            classes(
                "w-screen",
                "max-w-lg",
                "right-0",
                "absolute",
                "bg-slate-50",
                "h-full",
                "shadow-xl",
                "delay-400",
                "duration-500",
                "ease-in-out",
                "transition-all",
                "transform",
                if (isOpen) "translate-x-0" else "translate-x-full"
            )
        }) {
            Div {
                content()
            }
        }
    }
}
