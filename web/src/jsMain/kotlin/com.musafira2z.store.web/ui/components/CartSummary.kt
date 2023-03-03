package com.musafira2z.store.web.ui.components

import androidx.compose.runtime.Composable
import org.jetbrains.compose.web.ExperimentalComposeWebSvgApi
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.P
import org.jetbrains.compose.web.dom.Span
import org.jetbrains.compose.web.dom.Text
import org.jetbrains.compose.web.svg.Circle
import org.jetbrains.compose.web.svg.Path
import org.jetbrains.compose.web.svg.Svg
import com.musafira2z.store.web.ui.utils.toClasses

@Composable
fun CartSummary(
    content: @Composable () -> Unit
) {
    Div(attrs = {
        classes("p-5")
    }) {
        Div(attrs = {
            classes("select-none", "cursor-pointer")
        }) {
            Div(attrs = {
                toClasses("grid grid-cols-6 text-green-500 hover:bg-green-100 p-1 rounded-md")
            }) {
                P(attrs = {
                    toClasses("col-span-4 text-left")
                }) {
                    Text("Sub total")
                }
                Span(attrs = {
                    classes("col-span-1", "text-left")
                }) {
                    Text(":")
                }
                Span(attrs = {
                    toClasses("col-span-1 flex  items-center  font-bold")
                }) {
                    TbCurrencyTaka()
                    Text("500")
                }
            }

            Div(attrs = {
                classes("grid", "grid-cols-6", "text-red-500", "hover:bg-green-200", "p-1", "rounded-md")
            }) {
                P(attrs = {
                    classes("col-span-4", "text-left")
                }) {
                    Text("Discount")
                }
                Span(attrs = {
                    classes("col-span-1", "text-left")
                }) {
                    Text(":")
                }
                Span(attrs = {
                    classes("flex", "items-center", "font-bold")
                }) {
                    Text(" -")
                    TbCurrencyTaka()
                    P {
                        Text("500")
                    }
                }
            }
            Div(attrs = {
                classes("grid", "grid-cols-6", "text-red-500", "hover:bg-green-200", "p-1", "rounded-md")
            }) {
                P(attrs = {
                    classes("col-span-4", "text-left")
                }) {
                    Text("Added from wallet")
                }
                Span(attrs = {
                    classes("col-span-1", "text-left")
                }) {
                    Text(":")
                }
                Span(attrs = {
                    classes("flex", "items-center", "font-bold")
                }) {
                    Text(" -")
                    TbCurrencyTaka()
                    P {
                        Text(" 500")
                    }
                }
            }
            Div(attrs = {
                classes("grid", "grid-cols-6", "text-green-500", "hover:bg-green-200", "p-1", "rounded-md")
            }) {
                P(attrs = {
                    classes("col-span-4", "text-left")
                }) {
                    Text("Total")
                }
                Span(attrs = {
                    classes("col-span-1", "text-left")
                }) {
                    Text(":")
                }
                Span(attrs = {
                    classes("flex", "items-center", "font-bold")
                }) {
                    TbCurrencyTaka()
                    P {
                        Text("500")
                    }
                }
            }
            content()
        }
    }
}

@OptIn(ExperimentalComposeWebSvgApi::class)
@Composable
fun TbCurrencyTaka() {
    Svg(attrs = {
        attr("xmlns", "http://www.w3.org/2000/svg")
        classes("icon", "icon-tabler", "icon-tabler-currency-taka")
        attr("width", "16")
        attr("height", "16")
        attr("viewBox", "0 0 24 24")
        attr("stroke-width", "1.5")
        attr("stroke", "#2c3e50")
        attr("fill", "none")
        attr("stroke-linecap", "round")
        attr("stroke-linejoin", "round")
    }) {
        Path(attrs = {
            attr("stroke", "none")
            attr("fill", "none")
        }, d = "M0 0h24v24H0z")
        Circle(attrs = {
            attr("cx", "16.5")
            attr("cy", "15.5")
            attr("r", "1")
        }, cx = 16.5, cy = 15.5, r = 1)
        Path(d = "M7 7a2 2 0 1 1 4 0v9a3 3 0 0 0 6 0v-.5")
        Path(d = "M8 11h6")
    }
}

