package com.musafira2z.store.web.ui.components

import androidx.compose.runtime.Composable
import org.jetbrains.compose.web.dom.*
import com.musafira2z.store.web.ui.utils.toClasses

@Composable
fun CartBody(
    onClose: () -> Unit, content: @Composable () -> Unit
) {
    Div(attrs = {
        toClasses("flex flex-col justify-end h-full overflow-hidden px-3")
    }) {
        Div {
            Div(attrs = {
                toClasses("bg-slate-100 p-2 flex justify-between")
            }) {
                Div(attrs = {
                    toClasses("flex items-center")
                }) {
                    Img(src = "icons/rx/value.svg", attrs = {
                        attr("width", "24")
                        attr("height", "24")
                    })
                    H4(attrs = { classes("ml-2", "font-bold") }) {
                        Text("3 Items")
                    }
                }
                Button(attrs = {
                    classes(*"bg-red-500 text-slate-50 rounded-lg text-6xl".split(" ").toTypedArray())
                    onClick {
                        onClose()
                    }
                }) {
                    Img(src = "icons/rx/cross-2.svg", attrs = {
                        attr("width", "40")
                        attr("height", "40")
                    })
                }
            }

            Div(attrs = { classes("h-140", "overflow-auto") }) {
                repeat(5) {
                    CartItem()
                }
            }
            Hr(attrs = { classes("h-4", "mt-5") })
        }
        Div(attrs = { classes("pb-6") }) {
            CartSummary {
                Div(attrs = {
                    toClasses("grid grid-cols-6 text-slate-50 font-bold mt-2 bg-green-500 hover:bg-green-600 p-1 rounded-md")
                }) {
                    content()
                }
            }
        }
    }
}

