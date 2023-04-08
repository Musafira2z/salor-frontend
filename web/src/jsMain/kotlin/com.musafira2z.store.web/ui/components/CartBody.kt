package com.musafira2z.store.web.ui.components

import androidx.compose.runtime.Composable
import com.musafira2z.store.fragment.CheckoutDetailsFragment
import com.musafira2z.store.web.ui.utils.toClasses
import com.musafira2z.store.web.ui.utils.toFormatPrice
import org.jetbrains.compose.web.dom.*

@Composable
fun CartBody(
    cart: CheckoutDetailsFragment,
    onClose: () -> Unit,
    onIncrement: (String) -> Unit,
    onDecrement: (String, Int) -> Unit,
    removeLine: (String) -> Unit,
    content: @Composable () -> Unit
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
                    IcShoppingCart()
                    H4(attrs = { classes("ml-2", "font-bold") }) {
                        Text("${cart.lines.size} Items")
                    }
                }
                Button(attrs = {
                    toClasses("bg-red-500 text-slate-50 rounded-lg text-6xl")
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
                cart.lines.forEach { line ->
                    CartItem(
                        line = line,
                        onIncrement = {
                            onIncrement(line.checkoutLineDetailsFragment.variant.id)
                        },
                        onDecrement = {
                            val qty = line.checkoutLineDetailsFragment.quantity
                            if (qty > 1) {
                                onDecrement(
                                    line.checkoutLineDetailsFragment.variant.id,
                                    qty - 1
                                )
                            } else {
                                removeLine(line.checkoutLineDetailsFragment.id)
                            }
                        },
                        removeLine = removeLine
                    )
                }
            }
            Hr(attrs = { classes("h-4", "mt-5") })
        }
        Div(attrs = { classes("pb-6") }) {
            CartSummary(
                subTotal = cart.subtotalPrice.net.priceFragment.toFormatPrice(),
                discount = cart.discount?.priceFragment?.let {
                    if (it.amount > 0) {
                        it.toFormatPrice()
                    } else null
                },
                total = cart.totalPrice.gross.priceFragment.toFormatPrice()
            ) {
                Div(attrs = {
                    toClasses("grid grid-cols-6 text-slate-50 font-bold mt-2 bg-green-500 hover:bg-green-600 p-1 rounded-md")
                }) {
                    content()
                }
            }
        }
    }
}

