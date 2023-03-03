package com.musafira2z.store.web.ui.components

import androidx.compose.runtime.Composable
import org.jetbrains.compose.web.dom.*

@Composable
fun CartItem() {
    Div(attrs = {
        classes("py-2", "bg-white")
    }) {
        Div(attrs = {
            classes(
                "grid",
                "grid-cols-12",
                "gap-4",
                "border",
                "p-5",
                "h-auto",
                "w-auto",
                "content-center",
                "items-center",
                "rounded-lg",
                "shadow-md"
            )
        }) {
            Div(attrs = {
                classes("col-span-2", "inline-flex", "flex-col", "rounded-md", "shadow-sm")
                attr("role", "group")
            }) {
                Button(attrs = {
                    attr("type", "button")
                    classes(
                        "px-4",
                        "py-2",
                        "text-sm",
                        "font-medium",
                        "text-green-500",
                        "bg-transparent",
                        "border",
                        "border-gray-900",
                        "rounded-t-lg",
                        "hover:bg-green-500",
                        "hover:text-white"
                    )
                }) {
                    Plus()
                }
                Button(attrs = {
                    attr("type", "button")
                    classes(
                        "px-4",
                        "py-2",
                        "text-sm",
                        "text-gray-900",
                        "bg-transparent",
                        "border",
                        "border-t-0",
                        "border-b-0",
                        "font-bold",
                        "border-gray-900"
                    )
                }) {
                    Text(" 5 ")
                }
                Button(attrs = {
                    attr("type", "button")
                    classes(
                        "px-4",
                        "py-2",
                        "text-sm",
                        "font-medium",
                        "text-red-500",
                        "bg-transparent",
                        "border",
                        "border-gray-900",
                        "rounded-b-lg",
                        "hover:bg-red-500",
                        "hover:text-white"
                    )
                }) {
                    Minus()
                }

            }
            Div(attrs = {
                classes("col-span-4")
            }) {
                Img(
                    attrs = {
                        classes("w-auto")
                    },
                    src = "https://shatkora.sgp1.cdn.digitaloceanspaces.com/media/thumbnails/products/8941100513194.webp"
                )
            }
            Div(attrs = {
                classes("col-span-4")
            }) {
                P(attrs = {
                    classes("text-sm", "font-bold")
                }) {
                    Text("Ruchi BBQ Chanachur 150gm Ruchi BBQ Chanachur 150gm ")
                }
                P(attrs = {
                    classes("text-red-500", "font-bold")
                }) {
                    Text("৳100")
                }
                P {
                    Text("150gm")
                }
            }
            Div(attrs = {
                classes("col-span-1")
            }) {
                P(attrs = {
                    classes("text-green-500")
                }) {
                    Text("৳43")
                }
            }
            Div(attrs = {
                classes("col-span-1", "items-center")
            }) {
                Button(attrs = {
                    classes("bg-red-500", "p-2", "rounded-md")
                }) {
                    RxCross2(clas = "text-slate-50")
                }
            }
        }
    }
}


