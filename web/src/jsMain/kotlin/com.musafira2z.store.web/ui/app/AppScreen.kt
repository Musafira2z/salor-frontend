package com.musafira2z.store.web.ui.app

import androidx.compose.runtime.*
import com.copperleaf.ballast.navigation.routing.currentDestinationOrNull
import com.copperleaf.ballast.navigation.routing.currentRouteOrNull
import com.copperleaf.ballast.navigation.routing.renderCurrentDestination
import com.copperleaf.ballast.repository.cache.getCachedOrNull
import com.musafira2z.store.ui.app.AppContract
import com.musafira2z.store.ui.app.AppViewModel
import com.musafira2z.store.web.ui.components.*
import com.musafira2z.store.web.ui.di.ComposeWebInjector
import com.musafira2z.store.web.ui.home.HomePage
import com.musafira2z.store.web.ui.router.WebPage
import com.musafira2z.store.web.ui.utils.toClasses
import org.jetbrains.compose.web.ExperimentalComposeWebSvgApi
import org.jetbrains.compose.web.dom.*
import org.jetbrains.compose.web.svg.Path
import org.jetbrains.compose.web.svg.Svg

@Composable
fun AppScreen() {
    val applicationScope = rememberCoroutineScope()
    val injector: ComposeWebInjector = remember(applicationScope) {
        ComposeWebInjector(
            applicationScope,
            true,
            WebPage.HomePage
        )
    }

    val router = remember(injector) { injector.router() }
    val routerState by router.observeStates().collectAsState()
    val currentDestination = routerState.currentDestinationOrNull
    val currentRoute = routerState.currentRouteOrNull

    val vm: AppViewModel = remember(applicationScope) { injector.appViewModel(applicationScope) }

    LaunchedEffect(vm) {
        vm.trySend(AppContract.Inputs.Initialize)
    }

    val uiState by vm.observeStates().collectAsState()

    //TopAppBar
    TopAppBar()
    //sidebar
    SideBar()
    //content
    Div(attrs = {
        toClasses("md:ml-60  lg:ml-60 p-5")
    }) {
        routerState.renderCurrentDestination(
            route = {
                when (it) {
                    WebPage.HomePage -> {
                        HomePage(webInjector = injector)
                    }
                }
            },
            notFound = {
                Text("Not found")
            }
        )
    }

    //carts
    uiState.carts.getCachedOrNull()?.let {
        if (it.lines.isNotEmpty()) {
            CartBar()
        }
    }
}

@OptIn(ExperimentalComposeWebSvgApi::class)
@Composable
fun SideBar() {
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
            Div(attrs = {
                toClasses("flex justify-center mb-5")
            }) {
                Button(attrs = {
                    toClasses("w-full h-10 text-slate-50  text-lg font-extrabold bg-gradient-to-r from-yellow-300 rounded-lg to-pink-700")
                }) {
                    Text("Offer")
                }
            }
            Hr()
            Ul(attrs = {
                classes("space-y-2")
            }) {
                Li {
                    A(attrs = {
                        classes(
                            "flex",
                            "items-center",
                            "p-2",
                            "text-base",
                            "font-normal",
                            "text-gray-900",
                            "rounded-lg",
                            "dark:text-white",
                            "hover:bg-gray-100",
                            "dark:hover:bg-gray-700"
                        )
                    }, href = "#") {
                        Svg(attrs = {
                            attr("aria-hidden", "true")
                            classes(
                                "w-6",
                                "h-6",
                                "text-gray-500",
                                "transition",
                                "duration-75",
                                "dark:text-gray-400",
                                "group-hover:text-gray-900",
                                "dark:group-hover:text-white"
                            )
                            attr("fill", "currentColor")
                            attr("viewBox", "0 0 20 20")
                            attr("xmlns", "http://www.w3.org/2000/svg")
                        }) {
                            Path(
                                d = "M2 10a8 8 0 018-8v8h8a8 8 0 11-16 0z"
                            )
                            Path(
                                d = "M12 2.252A8.014 8.014 0 0117.748 8H12V2.252z"
                            )
                        }
                        Span(attrs = {
                            classes("ml-3")
                        }) {
                            Text("Dashboard")

                        }

                    }

                }
                Li {
                    A(attrs = {
                        classes(
                            "flex",
                            "items-center",
                            "p-2",
                            "text-base",
                            "font-normal",
                            "text-gray-900",
                            "rounded-lg",
                            "dark:text-white",
                            "hover:bg-gray-100",
                            "dark:hover:bg-gray-700"
                        )
                    }, href = "#") {
                        Svg(attrs = {
                            attr("aria-hidden", "true")
                            classes(
                                "flex-shrink-0",
                                "w-6",
                                "h-6",
                                "text-gray-500",
                                "transition",
                                "duration-75",
                                "dark:text-gray-400",
                                "group-hover:text-gray-900",
                                "dark:group-hover:text-white"
                            )
                            attr("fill", "currentColor")
                            attr("viewBox", "0 0 20 20")
                            attr("xmlns", "http://www.w3.org/2000/svg")
                        }) {
                            Path(
                                d = "M5 3a2 2 0 00-2 2v2a2 2 0 002 2h2a2 2 0 002-2V5a2 2 0 00-2-2H5zM5 11a2 2 0 00-2 2v2a2 2 0 002 2h2a2 2 0 002-2v-2a2 2 0 00-2-2H5zM11 5a2 2 0 012-2h2a2 2 0 012 2v2a2 2 0 01-2 2h-2a2 2 0 01-2-2V5zM11 13a2 2 0 012-2h2a2 2 0 012 2v2a2 2 0 01-2 2h-2a2 2 0 01-2-2v-2z"
                            )
                        }
                        Span(attrs = {
                            classes("flex-1", "ml-3", "whitespace-nowrap")
                        }) {
                            Text("Kanban")

                        }
                        Span(attrs = {
                            classes(
                                "inline-flex",
                                "items-center",
                                "justify-center",
                                "px-2",
                                "ml-3",
                                "text-sm",
                                "font-medium",
                                "text-gray-800",
                                "bg-gray-200",
                                "rounded-full",
                                "dark:bg-gray-700",
                                "dark:text-gray-300"
                            )
                        }) {
                            Text("Pro")

                        }

                    }

                }
                Li {
                    A(attrs = {
                        classes(
                            "flex",
                            "items-center",
                            "p-2",
                            "text-base",
                            "font-normal",
                            "text-gray-900",
                            "rounded-lg",
                            "dark:text-white",
                            "hover:bg-gray-100",
                            "dark:hover:bg-gray-700"
                        )
                    }, href = "#") {
                        Svg(attrs = {
                            attr("aria-hidden", "true")
                            classes(
                                "flex-shrink-0",
                                "w-6",
                                "h-6",
                                "text-gray-500",
                                "transition",
                                "duration-75",
                                "dark:text-gray-400",
                                "group-hover:text-gray-900",
                                "dark:group-hover:text-white"
                            )
                            attr("fill", "currentColor")
                            attr("viewBox", "0 0 20 20")
                            attr("xmlns", "http://www.w3.org/2000/svg")
                        }) {
                            Path(
                                d = "M8.707 7.293a1 1 0 00-1.414 1.414l2 2a1 1 0 001.414 0l2-2a1 1 0 00-1.414-1.414L11 7.586V3a1 1 0 10-2 0v4.586l-.293-.293z"
                            )
                            Path(
                                d = "M3 5a2 2 0 012-2h1a1 1 0 010 2H5v7h2l1 2h4l1-2h2V5h-1a1 1 0 110-2h1a2 2 0 012 2v10a2 2 0 01-2 2H5a2 2 0 01-2-2V5z"
                            )
                        }
                        Span(attrs = {
                            classes("flex-1", "ml-3", "whitespace-nowrap")
                        }) {
                            Text("Inbox")

                        }
                        Span(attrs = {
                            classes(
                                "inline-flex",
                                "items-center",
                                "justify-center",
                                "w-3",
                                "h-3",
                                "p-3",
                                "ml-3",
                                "text-sm",
                                "font-medium",
                                "text-blue-800",
                                "bg-blue-100",
                                "rounded-full",
                                "dark:bg-blue-900",
                                "dark:text-blue-300"
                            )
                        }) {
                            Text("3")

                        }

                    }

                }
                Li {
                    A(attrs = {
                        classes(
                            "flex",
                            "items-center",
                            "p-2",
                            "text-base",
                            "font-normal",
                            "text-gray-900",
                            "rounded-lg",
                            "dark:text-white",
                            "hover:bg-gray-100",
                            "dark:hover:bg-gray-700"
                        )
                    }, href = "#") {
                        Svg(attrs = {
                            attr("aria-hidden", "true")
                            classes(
                                "flex-shrink-0",
                                "w-6",
                                "h-6",
                                "text-gray-500",
                                "transition",
                                "duration-75",
                                "dark:text-gray-400",
                                "group-hover:text-gray-900",
                                "dark:group-hover:text-white"
                            )
                            attr("fill", "currentColor")
                            attr("viewBox", "0 0 20 20")
                            attr("xmlns", "http://www.w3.org/2000/svg")
                        }) {
                            Path(
                                attrs = {
                                    attr("fill-rule", "evenodd")
                                    attr("clip-rule", "evenodd")
                                }, d = "M10 9a3 3 0 100-6 3 3 0 000 6zm-7 9a7 7 0 1114 0H3z"
                            )
                        }
                        Span(attrs = {
                            classes("flex-1", "ml-3", "whitespace-nowrap")
                        }) {
                            Text("Users")

                        }

                    }

                }
                Li {
                    A(attrs = {
                        classes(
                            "flex",
                            "items-center",
                            "p-2",
                            "text-base",
                            "font-normal",
                            "text-gray-900",
                            "rounded-lg",
                            "dark:text-white",
                            "hover:bg-gray-100",
                            "dark:hover:bg-gray-700"
                        )
                    }, href = "#") {
                        Svg(attrs = {
                            attr("aria-hidden", "true")
                            classes(
                                "flex-shrink-0",
                                "w-6",
                                "h-6",
                                "text-gray-500",
                                "transition",
                                "duration-75",
                                "dark:text-gray-400",
                                "group-hover:text-gray-900",
                                "dark:group-hover:text-white"
                            )
                            attr("fill", "currentColor")
                            attr("viewBox", "0 0 20 20")
                            attr("xmlns", "http://www.w3.org/2000/svg")
                        }) {
                            Path(
                                attrs = {
                                    attr("fill-rule", "evenodd")
                                    attr("clip-rule", "evenodd")
                                },
                                d = "M10 2a4 4 0 00-4 4v1H5a1 1 0 00-.994.89l-1 9A1 1 0 004 18h12a1 1 0 00.994-1.11l-1-9A1 1 0 0015 7h-1V6a4 4 0 00-4-4zm2 5V6a2 2 0 10-4 0v1h4zm-6 3a1 1 0 112 0 1 1 0 01-2 0zm7-1a1 1 0 100 2 1 1 0 000-2z"
                            )
                        }
                        Span(attrs = {
                            classes("flex-1", "ml-3", "whitespace-nowrap")
                        }) {
                            Text("Products")

                        }

                    }

                }
                Li {
                    A(attrs = {
                        classes(
                            "flex",
                            "items-center",
                            "p-2",
                            "text-base",
                            "font-normal",
                            "text-gray-900",
                            "rounded-lg",
                            "dark:text-white",
                            "hover:bg-gray-100",
                            "dark:hover:bg-gray-700"
                        )
                    }, href = "#") {
                        Svg(attrs = {
                            attr("aria-hidden", "true")
                            classes(
                                "flex-shrink-0",
                                "w-6",
                                "h-6",
                                "text-gray-500",
                                "transition",
                                "duration-75",
                                "dark:text-gray-400",
                                "group-hover:text-gray-900",
                                "dark:group-hover:text-white"
                            )
                            attr("fill", "currentColor")
                            attr("viewBox", "0 0 20 20")
                            attr("xmlns", "http://www.w3.org/2000/svg")
                        }) {
                            Path(
                                attrs = {
                                    attr("fill-rule", "evenodd")
                                    attr("clip-rule", "evenodd")
                                },
                                d = "M3 3a1 1 0 00-1 1v12a1 1 0 102 0V4a1 1 0 00-1-1zm10.293 9.293a1 1 0 001.414 1.414l3-3a1 1 0 000-1.414l-3-3a1 1 0 10-1.414 1.414L14.586 9H7a1 1 0 100 2h7.586l-1.293 1.293z"
                            )
                        }
                        Span(attrs = {
                            classes("flex-1", "ml-3", "whitespace-nowrap")
                        }) {
                            Text("Sign In")

                        }

                    }

                }
                Li {
                    A(attrs = {
                        classes(
                            "flex",
                            "items-center",
                            "p-2",
                            "text-base",
                            "font-normal",
                            "text-gray-900",
                            "rounded-lg",
                            "dark:text-white",
                            "hover:bg-gray-100",
                            "dark:hover:bg-gray-700"
                        )
                    }, href = "#") {
                        Svg(attrs = {
                            attr("aria-hidden", "true")
                            classes(
                                "flex-shrink-0",
                                "w-6",
                                "h-6",
                                "text-gray-500",
                                "transition",
                                "duration-75",
                                "dark:text-gray-400",
                                "group-hover:text-gray-900",
                                "dark:group-hover:text-white"
                            )
                            attr("fill", "currentColor")
                            attr("viewBox", "0 0 20 20")
                            attr("xmlns", "http://www.w3.org/2000/svg")
                        }) {
                            Path(
                                attrs = {
                                    attr("fill-rule", "evenodd")
                                    attr("clip-rule", "evenodd")
                                },
                                d = "M5 4a3 3 0 00-3 3v6a3 3 0 003 3h10a3 3 0 003-3V7a3 3 0 00-3-3H5zm-1 9v-1h5v2H5a1 1 0 01-1-1zm7 1h4a1 1 0 001-1v-1h-5v2zm0-4h5V8h-5v2zM9 8H4v2h5V8z"
                            )
                        }
                        Span(attrs = {
                            classes("flex-1", "ml-3", "whitespace-nowrap")
                        }) {
                            Text("Sign Up")
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CartBar() {
    var isOpen by remember { mutableStateOf(false) }
    Div {
        if (!isOpen) {
            Div(attrs = {
                classes(
                    "bg-green-500",
                    "h-[7rem]",
                    "w-[7rem]",
                    "rounded-lg",
                    "rounded-r-none",
                    "z-30",
                    "fixed",
                    "top-1/2",
                    "right-0",
                    "flex",
                    "flex-col",
                    "justify-between",
                    "p-2",
                    "cursor-pointer",
                    "select-none"
                )
                onClick {
                    isOpen = true
                }
            }) {
                P(attrs = {
                    classes("text-slate-50", "text-sm", "md:text-xl", "text-center", "font-bold")
                }) {
                    Text("6 Items")
                }
                Button(attrs = {
                    classes(
                        *"bg-slate-50 text-slate-800 text-sm md:text-base py-2 px-6 rounded-lg font-bold".split(
                            " "
                        )
                            .toTypedArray()
                    )
                }) {
                    Text("৳50420")
                }
            }
        }

        Drawer(isOpen = isOpen) {
            CartBody(onClose = {
                isOpen = false
            }) {
                NavLink(to = "/checkout") {
                    Button {
                        Text("Checkout")
                    }
                }
            }
        }
    }
}


@OptIn(ExperimentalComposeWebSvgApi::class)
@Composable
fun RightDrawer(
    Content: @Composable () -> Unit
) {
    Div(attrs = {
        id("drawer-right-example")
        classes(
            "fixed",
            "top-0",
            "right-0",
            "z-40",
            "h-screen",
            "p-4",
            "overflow-y-auto",
            "transition-transform",
            "translate-x-full",
            "bg-white",
            "w-80",
            "dark:bg-gray-800"
        )
        attr("tabindex", "-1")
        attr("aria-labelledby", "drawer-right-label")
    }) {
        H5(attrs = {
            id("drawer-right-label")
            classes(
                "inline-flex",
                "items-center",
                "mb-4",
                "text-base",
                "font-semibold",
                "text-gray-500",
                "dark:text-gray-400"
            )
        }) {
            Svg(attrs = {
                classes("w-5", "h-5", "mr-2")
                attr("aria-hidden", "true")
                attr("fill", "currentColor")
                attr("viewBox", "0 0 20 20")
                attr("xmlns", "http://www.w3.org/2000/svg")
            }) {
                Path(
                    attrs = {
                        attr("fill-rule", "evenodd")
                        attr("clip-rule", "evenodd")
                    },
                    d = "M18 10a8 8 0 11-16 0 8 8 0 0116 0zm-7-4a1 1 0 11-2 0 1 1 0 012 0zM9 9a1 1 0 000 2v3a1 1 0 001 1h1a1 1 0 100-2v-3a1 1 0 00-1-1H9z"
                )
            }
            Text("Right drawer")

        }
        Button(attrs = {
            attr("type", "button")
            attr("data-drawer-hide", "drawer-right-example")
            attr("aria-controls", "drawer-right-example")
            classes(
                "text-gray-400",
                "bg-transparent",
                "hover:bg-gray-200",
                "hover:text-gray-900",
                "rounded-lg",
                "text-sm",
                "p-1.5",
                "absolute",
                "top-2.5",
                "right-2.5",
                "inline-flex",
                "items-center",
                "dark:hover:bg-gray-600",
                "dark:hover:text-white"
            )
        }) {
            Svg(attrs = {
                attr("aria-hidden", "true")
                classes("w-5", "h-5")
                attr("fill", "currentColor")
                attr("viewBox", "0 0 20 20")
                attr("xmlns", "http://www.w3.org/2000/svg")
            }) {
                Path(
                    attrs = {
                        attr("fill-rule", "evenodd")
                        attr("clip-rule", "evenodd")
                    },
                    d = "M4.293 4.293a1 1 0 011.414 0L10 8.586l4.293-4.293a1 1 0 111.414 1.414L11.414 10l4.293 4.293a1 1 0 01-1.414 1.414L10 11.414l-4.293 4.293a1 1 0 01-1.414-1.414L8.586 10 4.293 5.707a1 1 0 010-1.414z"
                )
            }
            Span(attrs = {
                classes("sr-only")
            }) {
                Text("Close menu")
            }
        }

        P(attrs = {
            classes("mb-6", "text-sm", "text-gray-500", "dark:text-gray-400")
        }) {
            Text("Supercharge your hiring by taking advantage of our ")
            A(attrs = {
                classes("text-blue-600", "underline", "dark:text-blue-500", "hover:no-underline")
            }, href = "#") {
                Text("limited-time sale")

            }
            Text(" for Flowbite Docs + Job Board. Unlimited access to over 190K top-ranked candidates and the #1 design job board.")

        }

        Div(attrs = {
            classes("grid", "grid-cols-2", "gap-4")
        }) {
            A(attrs = {
                classes(
                    "px-4",
                    "py-2",
                    "text-sm",
                    "font-medium",
                    "text-center",
                    "text-gray-900",
                    "bg-white",
                    "border",
                    "border-gray-200",
                    "rounded-lg",
                    "focus:outline-none",
                    "hover:bg-gray-100",
                    "hover:text-blue-700",
                    "focus:z-10",
                    "focus:ring-4",
                    "focus:ring-gray-200",
                    "dark:focus:ring-gray-700",
                    "dark:bg-gray-800",
                    "dark:text-gray-400",
                    "dark:border-gray-600",
                    "dark:hover:text-white",
                    "dark:hover:bg-gray-700"
                )
            }, href = "#") {
                Text("Learn more")

            }
            A(attrs = {
                classes(
                    "inline-flex",
                    "items-center",
                    "px-4",
                    "py-2",
                    "text-sm",
                    "font-medium",
                    "text-center",
                    "text-white",
                    "bg-blue-700",
                    "rounded-lg",
                    "hover:bg-blue-800",
                    "focus:ring-4",
                    "focus:ring-blue-300",
                    "dark:bg-blue-600",
                    "dark:hover:bg-blue-700",
                    "focus:outline-none",
                    "dark:focus:ring-blue-800"
                )
            }, href = "#") {
                Text("Get access ")
                Svg(attrs = {
                    classes("w-4", "h-4", "ml-2")
                    attr("aria-hidden", "true")
                    attr("fill", "currentColor")
                    attr("viewBox", "0 0 20 20")
                    attr("xmlns", "http://www.w3.org/2000/svg")
                }) {
                    Path(
                        attrs = {
                            attr("fill-rule", "evenodd")
                            attr("clip-rule", "evenodd")
                        },
                        d = "M12.293 5.293a1 1 0 011.414 0l4 4a1 1 0 010 1.414l-4 4a1 1 0 01-1.414-1.414L14.586 11H3a1 1 0 110-2h11.586l-2.293-2.293a1 1 0 010-1.414z"
                    )
                }
            }
        }

    }
}

@Composable
fun NavLink(
    to: String, content: @Composable () -> Unit
) {
    A(attrs = {
        toClasses("col-span-6 w-full text-center text-slate-50 hover:text-slate-50 active:text-slate-50 focus:text-slate-50")
    }, href = to) {
        content()
    }
}