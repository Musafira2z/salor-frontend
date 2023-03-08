package com.musafira2z.store.web.ui.components

import androidx.compose.runtime.Composable
import com.musafira2z.store.fragment.CheckoutDetailsFragment
import com.musafira2z.store.web.ui.utils.toFormatPrice
import com.musafira2z.store.web.ui.utils.toUnDiscountFormatPrice
import org.jetbrains.compose.web.attributes.InputType
import org.jetbrains.compose.web.dom.*

@Composable
fun CartItem(
    line: CheckoutDetailsFragment.Line,
    onIncrement: () -> Unit,
    onDecrement: () -> Unit,
    onAdjust: (Int) -> Unit
) {
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
                    onClick {
                        onIncrement()
                    }
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
                    Text("${line.checkoutLineDetailsFragment.quantity}")
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
                    onClick {
                        onDecrement()
                    }
                }) {
                    Minus()
                }

            }
            Div(attrs = {
                classes("col-span-4")
            }) {

                val image =
                    line.checkoutLineDetailsFragment.variant.product.thumbnail?.imageFragment?.url?.replace(
                        "http://localhost:8000",
                        "https://musafirtd.sgp1.cdn.digitaloceanspaces.com"
                    )

                Img(
                    attrs = {
                        classes("w-auto")
                    },
                    src = image ?: ""
                )
            }
            Div(attrs = {
                classes("col-span-4")
            }) {
                P(attrs = {
                    classes("text-sm", "font-bold")
                }) {
                    Text(line.checkoutLineDetailsFragment.variant.product.name)
                }
                line.checkoutLineDetailsFragment.variant.pricing?.price?.gross?.priceFragment.toUnDiscountFormatPrice(
                    line.checkoutLineDetailsFragment.variant.pricing?.price?.gross?.priceFragment?.amount
                )?.let {
                    P(attrs = {
                        classes("text-red-500", "font-bold")
                    }) {
                        Text("৳${it}")
                    }
                }

//                P {
//                    Text(line.checkoutLineDetailsFragment.variant.name)
//                }
            }
            Div(attrs = {
                classes("col-span-1")
            }) {
                P(attrs = {
                    classes("text-green-500")
                }) {
                    Text("৳${line.checkoutLineDetailsFragment.variant.pricing?.price?.gross?.priceFragment?.toFormatPrice()}")
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


