package com.musafira2z.store.web.ui.components

import androidx.compose.runtime.*
import org.jetbrains.compose.web.dom.Button
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.H3
import org.jetbrains.compose.web.dom.Text

@Composable
fun Modal(
    showModal: Boolean = false,
    modalOpenButton: @Composable () -> Unit,
    title: String,
    onHide: (Boolean) -> Unit,
    modalBody: @Composable () -> Unit
) {
    Div {
        Div {
            modalOpenButton()
        }
        if (showModal) {
            Div {
                Div(attrs = {
                    classes(
                        "flex",
                        "justify-center",
                        "items-center",
                        "overflow-x-hidden",
                        "overflow-y-auto",
                        "fixed",
                        "inset-0",
                        "z-50",
                        "outline-none",
                        "focus:outline-none",
                        "bg-slate-700",
                        "bg-opacity-60"
                    )
                }) {
                    Div(attrs = {
                        classes("relative", "my-6", "mx-auto", "w-11/12", "max-w-3xl")
                    }) {
                        Div(attrs = {
                            classes(
                                "border-0",
                                "rounded-lg",
                                "shadow-lg",
                                "relative",
                                "flex",
                                "flex-col",
                                "w-full",
                                "bg-white",
                                "outline-none",
                                "focus:outline-none"
                            )
                        }) {
                            Div(attrs = {
                                classes(
                                    "flex",
                                    "items-start",
                                    "justify-between",
                                    "p-5",
                                    "border-b",
                                    "border-solid",
                                    "border-gray-300",
                                    "rounded-t"
                                )
                            }) {
                                H3(attrs = {
                                    classes("text-lg", "font=semibold")
                                }) {
                                    Text(title)
                                }

                                Button(attrs = {
                                    classes(
                                        "bg-transparent",
                                        "border-0",
                                        "text-black",
                                        "float-right"
                                    )
                                    onClick {
                                        onHide(false)
                                    }
                                }) {
                                    Button(attrs = {
                                        classes("py-2", "rounded-full", "text-red-500", "font-bold")
                                    }) {
                                        RxCross2()
                                    }
                                }
                            }
                            Div(attrs = {
                                classes("relative", "p-6", "flex-auto")
                            }) {
                                modalBody()
                            }
                        }
                    }
                }
            }
        }
    }
}
