package com.musafira2z.store.web.ui.checkout

import androidx.compose.runtime.*
import com.musafira2z.store.ui.checkout.CheckoutContract
import com.musafira2z.store.web.ui.di.ComposeWebInjector
import com.musafira2z.store.web.ui.utils.toClasses
import org.jetbrains.compose.web.attributes.InputType
import org.jetbrains.compose.web.attributes.name
import org.jetbrains.compose.web.attributes.required
import org.jetbrains.compose.web.dom.*

@Composable
fun CheckoutPage(
    injector: ComposeWebInjector
) {
    val viewModelScope = rememberCoroutineScope()
    val vm = remember(viewModelScope) { injector.checkoutViewModel(viewModelScope) }
    LaunchedEffect(vm) {
        vm.trySend(CheckoutContract.Inputs.Initialize)
    }
    val uiState by vm.observeStates().collectAsState()
    CheckoutPageContent(uiState = uiState) {
        vm.trySend(it)
    }
}

@Composable
fun CheckoutPageContent(
    uiState: CheckoutContract.State,
    postInput: (CheckoutContract.Inputs) -> Unit
) {

    Div(attrs = {

    }) {
        Div(attrs = {
            classes("lg:flex", "lg:gap-x-5", "mb-28")
        }) {
            Div(attrs = {
                classes("my-10", "w-full", "lg:space-x-10", "lg:mr-150")
            }) {
                Div(attrs = {
                    classes(
                        "flex",
                        "justify-around",
                        "md:justify-start",
                        "lg:justify-start",
                        "lg:ml-9"
                    )
                }) {
                    //checked
//                    bg-green-500 duration-500 text-slate-50
                    Button(attrs = {
                        toClasses("font-bold  cursor-pointer py-5 px-12 md:px-28 lg:px-28   rounded-lg  border duration-500  select-none  ")
                    }) {
                        Text("Delivery")
                    }
                }
                Hr(attrs = {
                    classes("mt-7")
                })
                Div(attrs = {
                    classes("duration-500")
                }) {
                    Div {
                        Div(attrs = {
                            classes("bg-gray-50", "p-4", "rounded-lg")
                        }) {
                            Div(attrs = {
                                classes("flex", "justify-between", "items-center")
                            }) {
                                Div(attrs = {
                                    classes("flex", "items-center", "select-none")
                                }) {
                                    Div(attrs = {
                                        classes(
                                            "mr-2",
                                            "bg-green-500",
                                            "text-slate-50",
                                            "text-lg",
                                            "font-bold",
                                            "rounded-full",
                                            "h-10",
                                            "w-10",
                                            "flex",
                                            "justify-center",
                                            "items-center"
                                        )
                                    }) {
                                        P {
                                            Text("1")
                                        }

                                    }
                                    Div {
                                        H3(attrs = {
                                            classes("font-bold")
                                        }) {
                                            Text("Delivery Address")
                                        }
                                    }

                                }

                            }
                            Div(attrs = {
                                classes(
                                    "bg-green-500",
                                    "lg:w-96",
                                    "md:w-96",
                                    "sm:w-60",
                                    "py-3",
                                    "px-5",
                                    "mt-3",
                                    "rounded-xl",
                                    "group/item",
                                    "text-slate-50",
                                    "font-bold"
                                )
                            }) {
                                H3(attrs = {
                                    classes("text-xl", "font-bold")
                                }) {
                                    Text("Musafir")
                                }
                                P(attrs = {
                                    classes("text-slate-50", "font-bold")
                                }) {
                                    Text("1/3 Tarango, Mazumdari, Airport Road, Sylhet")
                                }

                            }

                            AddressForm()
                        }
                    }
                }
                Div(attrs = {
                    classes(
                        "w-128",
                        "fixed",
                        "lg:right-0",
                        "md:right-0",
                        "mt-24",
                        "lg:block",
                        "inset-y-0",
                        "hidden"
                    )
                }) {

                }
            }

        }

    }
}

@Composable
fun AddressForm() {
    Div {
        Form(attrs = {
            attr("onSubmit", "{handleSubmit}")
            classes( "px-8", "pt-6", "pb-8", "w-full")
        }, action = "") {
            Div(attrs = {
                classes("lg:flex", "md:flex", "sm:flex", "gap-2", "justify-between", )
            }) {
                Div(attrs = {
                    classes( "w-full")
                }) {
                    Input(InputType.Text) {
                        id("firstName")
                        name("firstName")
                        attr("type", "name")
                        attr("label", "Firs Name")
                        attr("placeholder", "Firs Name")
                        required()
                    }
                }
                Div(attrs = {
                    classes( "w-full")
                }) {
                    Input(InputType.Text) {
                        id("lastName")
                        name("lastName")
                        attr("type", "lastName")
                        attr("label", "Last Name")
                        attr("placeholder", "Last Name")
                        required()
                    }
                }

            }
            Input(InputType.Text) {
                id("Contact")
                name("Contact")
                attr("type", "tel")
                attr("label", "Contact number")
                attr("placeholder", "Contact number")
                required()
            }

            Div(attrs = {
                classes(
                    "flex",
                    "items-center",
                    "justify-end",
                    "p-6",
                    "border-t",
                    "border-solid",
                    "border-blueGray-200",
                    "rounded-b"
                )
            }) {
                Button(attrs = {
                    classes(
                        "text-white",
                        "bg-green-500",
                        "active:bg-opacity-95",
                        "font-bold",
                        "uppercase",
                        "text-sm",
                        "px-6",
                        "py-3",
                        "rounded",
                        "shadow",
                        "hover:shadow-lg",
                        "outline-none",
                        "focus:outline-none",
                        "mr-1",
                        "mb-1"
                    )
                    attr("type", "submit")
                }) {
                    Text(" Submit ")
                }
            }
        }

    }
}

