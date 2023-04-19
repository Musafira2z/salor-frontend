package com.musafira2z.store.web.ui.components.shared

import androidx.compose.runtime.Composable
import org.jetbrains.compose.web.ExperimentalComposeWebSvgApi
import org.jetbrains.compose.web.dom.*
import org.jetbrains.compose.web.svg.Path
import org.jetbrains.compose.web.svg.Svg

@OptIn(ExperimentalComposeWebSvgApi::class)
@Composable
fun MobileMenuButton() {
    Button(
        attrs = {
            attr("data-drawer-target", "logo-sidebar")
            attr("data-drawer-toggle", "logo-sidebar")
            attr("aria-controls", "logo-sidebar")
            attr("type", "button")
            classes(
                "inline-flex",
                "items-center",
                "p-2",
                "mt-2",
                "ml-1",
                "text-sm",
                "text-gray-500",
                "rounded-lg",
                "md:hidden",
                "lg:hidden",
                "hover:bg-gray-100",
                "focus:outline-none",
                "focus:ring-2",
                "focus:ring-gray-200",
                "dark:text-gray-400",
                "dark:hover:bg-gray-700",
                "dark:focus:ring-gray-600"
            )
        }
    ) {
        Span(attrs = {
            classes("sr-only")
        }) {
            Text("Open sidebar")
        }
        Svg(attrs = {
            classes("w-6", "h-6")
            attr("aria-hidden", "true")
            attr("fill", "currentColor")
            attr("viewBox", "0 0 20 20")
            attr("xmlns", "http://www.w3.org/2000/svg")
        }) {
            Path(
                attrs = {
                    attr("clip-rule", "evenodd")
                    attr("fill-rule", "evenodd")
                },
                d = "M2 4.75A.75.75 0 012.75 4h14.5a.75.75 0 010 1.5H2.75A.75.75 0 012 4.75zm0 10.5a.75.75 0 01.75-.75h7.5a.75.75 0 010 1.5h-7.5a.75.75 0 01-.75-.75zM2 10a.75.75 0 01.75-.75h14.5a.75.75 0 010 1.5H2.75A.75.75 0 012 10z"
            )
        }

    }
}


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