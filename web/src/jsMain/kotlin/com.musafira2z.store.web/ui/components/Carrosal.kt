package com.musafira2z.store.web.ui.components

import androidx.compose.runtime.Composable
import com.musafira2z.store.HomeBannerMenuQuery
import org.jetbrains.compose.web.ExperimentalComposeWebSvgApi
import org.jetbrains.compose.web.dom.*

@OptIn(ExperimentalComposeWebSvgApi::class)
@Composable
fun Carousal(banners: HomeBannerMenuQuery.Data) {
    Div(attrs = {
        id("default-carousel")
        classes("relative")
    }) {
        // Carousel wrapper
        Div(attrs = {
            classes("relative", "h-56", "overflow-hidden", "rounded-lg", "md:h-96")
        }) {
            banners.menu?.items?.firstOrNull()?.let {
                // Item 1
                Div(attrs = {
                    classes("duration-700", "ease-in-out")
                    attr("data-carousel-item", "")
                }) {
                    Span(attrs = {
                        classes(
                            "absolute",
                            "text-2xl",
                            "font-semibold",
                            "text-white",
                            "-translate-x-1/2",
                            "-translate-y-1/2",
                            "top-1/2",
                            "left-1/2",
                            "sm:text-3xl",
                            "dark:text-gray-800"
                        )
                    }) {
                        Text(it.menuItemWithChildrenFragment.name)
                    }
                    Img(
                        attrs = {
                            classes(
                                "absolute",
                                "block",
                                "w-full",
                                "-translate-x-1/2",
                                "-translate-y-1/2",
                                "top-1/2",
                                "left-1/2"
                            )
                        },
                        src = it.menuItemWithChildrenFragment.collection?.backgroundImage?.url
                            ?.replace(
                                "http://localhost:8000",
                                "https://musafirtd.sgp1.cdn.digitaloceanspaces.com"
                            ) ?: "",
                        alt = it.menuItemWithChildrenFragment.collection?.backgroundImage?.alt
                            ?: ""
                    )
                }
            }
        }
    }
}
