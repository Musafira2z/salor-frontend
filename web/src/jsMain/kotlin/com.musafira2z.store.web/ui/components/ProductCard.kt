package com.musafira2z.store.web.ui.components

import androidx.compose.runtime.Composable
import org.jetbrains.compose.web.dom.*


@Composable
fun Product() {
    Div(attrs = {
        classes(
            "col-span-12",
            "sm:col-span-6",
            "md:col-span-6",
            "lg:col-span-3",
            "border",
            "p-5",
            "rounded-lg",
            "hover:shadow-green-200",
            "relative",
            "hover:no-underline",
            "hover:text-slate-700",
            "shadow-lg",
            "hover:shadow-xl",
            "hover:transform",
            "hover:scale-105",
            "duration-300",
            "h-fit"
        )
    }) {
        Div(attrs = {
            classes("absolute", "right-0", "pr-1")
        }) {
            Button(attrs = {
                classes(
                    "bg-gradient-to-r",
                    "from-yellow-300",
                    "via-red-400",
                    "to-red-500",
                    "rounded-md",
                    "py-1",
                    "px-2",
                    "text-slate-50",
                    "font-bold"
                )
            }) {
                Text("5% Offer")
            }
        }
        Div {
            Div(attrs = {
                classes("flex", "justify-center")
            }) {
                Img(
                    src = "https://shatkora.sgp1.cdn.digitaloceanspaces.com/media/thumbnails/products/8941100513194.webp",
                    alt = ""
                )
            }
            H1(attrs = {
                classes("text-lg", "py-7", "truncate", "hover:text-clip")
            }) {
                Text(" Ruchi BBQ Chanachur 150gm Ruchi BBQ Chanachur 150gm")
            }
        }
        Div {
            Div(attrs = {
                classes("flex", "justify-end")
            }) {
                P(attrs = {
                    classes("text-red-500", "font-bold", "line-through")
                }) {
                    Text("৳100")
                }
            }
            Div(attrs = {
                classes("flex", "justify-between", "font-bold", "pb-4")
            }) {
                P {
                    Text("150gm")
                }
                P(attrs = {
                    classes("text-green-500")
                }) {
                    Text("৳43")
                }
            }
            Div {
                AddToCartButton()
            }
        }
    }
}

@Composable
fun AddToCartButton() {
    Button(attrs = {
        classes(
            "border-2",
            "border-green-500",
            "rounded-lg",
            "text-green-500",
            "hover:bg-green-500",
            "hover:text-slate-50",
            "font-bold",
            "hover:duration-500",
            "duration-500",
            "w-full",
            "py-3",
            "px-6"
        )
    }) {
        Text("Add to Cart")
    }
}