package com.musafira2z.store.web.ui.components.shared

import androidx.compose.runtime.Composable
import org.jetbrains.compose.web.dom.Aside
import org.jetbrains.compose.web.dom.Div


@Composable
fun SideBar(
    content: @Composable () -> Unit
) {
    Aside(attrs = {
        id("logo-sidebar")
        classes(
            "fixed",
            "top-0",
            "left-0",
            "z-40",
            "w-64",
            "h-screen",
            "pt-28",
            "transition-transform",
            "-translate-x-full",
            "bg-white",
            "border-r",
            "border-gray-200",
            "sm:translate-x-0",
            "dark:bg-gray-800",
            "dark:border-gray-700"
        )
        attr("aria-label", "Sidebar")
    }) {
        Div(attrs = {
            classes("h-full", "px-3", "pb-4", "overflow-y-auto", "bg-white", "dark:bg-gray-800")
        }) {
            content()
        }
    }
}