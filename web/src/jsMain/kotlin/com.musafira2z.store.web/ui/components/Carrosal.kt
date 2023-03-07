package com.musafira2z.store.web.ui.components

import androidx.compose.runtime.Composable
import com.musafira2z.store.HomeBannerMenuQuery
import org.jetbrains.compose.web.ExperimentalComposeWebSvgApi
import org.jetbrains.compose.web.dom.*
import org.jetbrains.compose.web.svg.Path
import org.jetbrains.compose.web.svg.Svg

@OptIn(ExperimentalComposeWebSvgApi::class)
@Composable
fun Carousal(banners: HomeBannerMenuQuery.Data) {
    Div(attrs = {
        id("default-carousel")
        classes("relative")
        attr("data-carousel", "static")
    }) {
        // Carousel wrapper
        Div(attrs = {
            classes("relative", "h-56", "overflow-hidden", "rounded-lg", "md:h-96")
        }) {
            banners.menu?.items?.forEachIndexed { index, item ->
                // Item 1
                Div(attrs = {
                    classes("hidden", "duration-700", "ease-in-out")
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
                        Text(item.menuItemWithChildrenFragment.name)
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
                        src = item.menuItemWithChildrenFragment.collection?.backgroundImage?.url
                            ?.replace(
                                "http://localhost:8000",
                                "https://musafirtd.sgp1.cdn.digitaloceanspaces.com"
                            ) ?: "",
                        alt = item.menuItemWithChildrenFragment.collection?.backgroundImage?.url
                            ?: ""
                    )
                }
            }

            // Item 2
            /* Div(attrs = {
                 classes("hidden", "duration-700", "ease-in-out")
                 attr("data-carousel-item", "")
             }) {
                 Img(attrs = {
                     classes(
                         "absolute",
                         "block",
                         "w-full",
                         "-translate-x-1/2",
                         "-translate-y-1/2",
                         "top-1/2",
                         "left-1/2"
                     )
                 }, src = "https://flowbite.com/docs/images/carousel/carousel-2.svg", alt = "...")
             }*/

            // Item 3
            /*Div(attrs = {
                classes("hidden", "duration-700", "ease-in-out")
                attr("data-carousel-item", "")
            }) {
                Img(attrs = {
                    classes(
                        "absolute",
                        "block",
                        "w-full",
                        "-translate-x-1/2",
                        "-translate-y-1/2",
                        "top-1/2",
                        "left-1/2"
                    )
                }, src = "https://flowbite.com/docs/images/carousel/carousel-3.svg", alt = "...")

            }*/

        }
        // Slider indicators
        Div(attrs = {
            classes(
                "absolute",
                "z-30",
                "flex",
                "space-x-3",
                "-translate-x-1/2",
                "bottom-5",
                "left-1/2"
            )
        }) {
            Button(attrs = {
                attr("type", "button")
                classes("w-3", "h-3", "rounded-full")
                attr("aria-current", "false")
                attr("aria-label", "Slide 1")
                attr("data-carousel-slide-to", "0")
            }) {}
            Button(attrs = {
                attr("type", "button")
                classes("w-3", "h-3", "rounded-full")
                attr("aria-current", "false")
                attr("aria-label", "Slide 2")
                attr("data-carousel-slide-to", "1")
            }) {}
            Button(attrs = {
                attr("type", "button")
                classes("w-3", "h-3", "rounded-full")
                attr("aria-current", "false")
                attr("aria-label", "Slide 3")
                attr("data-carousel-slide-to", "2")
            }) {}

        }
        // Slider controls
        Button(attrs = {
            attr("type", "button")
            classes(
                "absolute",
                "top-0",
                "left-0",
                "z-30",
                "flex",
                "items-center",
                "justify-center",
                "h-full",
                "px-4",
                "cursor-pointer",
                "group",
                "focus:outline-none"
            )
            attr("data-carousel-prev", "")
        }) {
            Span(attrs = {
                classes(
                    "inline-flex",
                    "items-center",
                    "justify-center",
                    "w-8",
                    "h-8",
                    "rounded-full",
                    "sm:w-10",
                    "sm:h-10",
                    "bg-white/30",
                    "dark:bg-gray-800/30",
                    "group-hover:bg-white/50",
                    "dark:group-hover:bg-gray-800/60",
                    "group-focus:ring-4",
                    "group-focus:ring-white",
                    "dark:group-focus:ring-gray-800/70",
                    "group-focus:outline-none"
                )
            }) {
                Svg(attrs = {
                    attr("aria-hidden", "true")
                    classes("w-5", "h-5", "text-white", "sm:w-6", "sm:h-6", "dark:text-gray-800")
                    attr("fill", "none")
                    attr("stroke", "currentColor")
                    attr("viewBox", "0 0 24 24")
                    attr("xmlns", "http://www.w3.org/2000/svg")
                }) {
                    Path(attrs = {
                        attr("stroke-linecap", "round")
                        attr("stroke-linejoin", "round")
                        attr("stroke-width", "2")
                    }, d = "M15 19l-7-7 7-7")
                }
                Span(attrs = {
                    classes("sr-only")
                }) {
                    Text("Previous")
                }
            }
        }
        Button(attrs = {
            attr("type", "button")
            classes(
                "absolute",
                "top-0",
                "right-0",
                "z-30",
                "flex",
                "items-center",
                "justify-center",
                "h-full",
                "px-4",
                "cursor-pointer",
                "group",
                "focus:outline-none"
            )
            attr("data-carousel-next", "")
        }) {
            Span(attrs = {
                classes(
                    "inline-flex",
                    "items-center",
                    "justify-center",
                    "w-8",
                    "h-8",
                    "rounded-full",
                    "sm:w-10",
                    "sm:h-10",
                    "bg-white/30",
                    "dark:bg-gray-800/30",
                    "group-hover:bg-white/50",
                    "dark:group-hover:bg-gray-800/60",
                    "group-focus:ring-4",
                    "group-focus:ring-white",
                    "dark:group-focus:ring-gray-800/70",
                    "group-focus:outline-none"
                )
            }) {
                Svg(attrs = {
                    attr("aria-hidden", "true")
                    classes("w-5", "h-5", "text-white", "sm:w-6", "sm:h-6", "dark:text-gray-800")
                    attr("fill", "none")
                    attr("stroke", "currentColor")
                    attr("viewBox", "0 0 24 24")
                    attr("xmlns", "http://www.w3.org/2000/svg")
                }) {
                    Path(attrs = {
                        attr("stroke-linecap", "round")
                        attr("stroke-linejoin", "round")
                        attr("stroke-width", "2")
                    }, d = "M9 5l7 7-7 7")
                }
                Span(attrs = {
                    classes("sr-only")
                }) {
                    Text("Next")
                }
            }
        }
    }
}
