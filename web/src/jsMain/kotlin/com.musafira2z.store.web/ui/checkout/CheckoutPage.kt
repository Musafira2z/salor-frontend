package com.musafira2z.store.web.ui.checkout

import androidx.compose.runtime.*
import com.apollographql.apollo3.api.Optional
import com.copperleaf.ballast.repository.cache.getCachedOrNull
import com.musafira2z.store.type.AddressInput
import com.musafira2z.store.ui.checkout.CheckoutContract
import com.musafira2z.store.web.ui.components.CartBody
import com.musafira2z.store.web.ui.di.ComposeWebInjector
import com.musafira2z.store.web.ui.utils.toClasses
import org.jetbrains.compose.web.attributes.InputType
import org.jetbrains.compose.web.attributes.name
import org.jetbrains.compose.web.attributes.onSubmit
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
                                    Div {
                                        H3(attrs = {
                                            classes("font-bold")
                                        }) {
                                            Text("Address")
                                        }
                                    }
                                }
                            }

                            uiState.carts.getCachedOrNull()?.let {
                                val shippingMethod = it.availableShippingMethods.firstOrNull()
                                LaunchedEffect(shippingMethod) {
                                    if (it.shippingMethod == null && shippingMethod != null) {
                                        postInput(
                                            CheckoutContract.Inputs.SetShippingMethod(
                                                shippingMethod.deliveryMethodFragment.id
                                            )
                                        )
                                    }
                                }

                                if (it.billingAddress == null) {
                                    uiState.address.getCachedOrNull()?.let {
                                        if (it.addresses?.isEmpty() == true) {
                                            AddressForm(postInput = postInput)
                                        } else {
                                            Div(
                                                attrs = {
                                                    classes("grid", "md:grid-cols-3", "md:gap-6")
                                                }
                                            ) {
                                                it.addresses?.forEach { address ->
                                                    val name =
                                                        "${address.addressDetailsFragment.firstName} ${address.addressDetailsFragment.lastName}"
                                                    val adds =
                                                        "${address.addressDetailsFragment.streetAddress1}, ${address.addressDetailsFragment.city}, ${address.addressDetailsFragment.postalCode}"
                                                    val phone =
                                                        address.addressDetailsFragment.phone ?: ""

                                                    LaunchedEffect(address.addressDetailsFragment.isDefaultBillingAddress) {
                                                        if (address.addressDetailsFragment.isDefaultBillingAddress == true) {
                                                            val billingAddress =
                                                                address.addressDetailsFragment
                                                            postInput(
                                                                CheckoutContract.Inputs.SetBillingAddress(
                                                                    address = AddressInput(
                                                                        firstName = Optional.presentIfNotNull(
                                                                            billingAddress.firstName
                                                                        ),
                                                                        lastName = Optional.presentIfNotNull(
                                                                            billingAddress.lastName
                                                                        ),
                                                                        city = Optional.presentIfNotNull(
                                                                            billingAddress.city
                                                                        ),
                                                                        phone = Optional.presentIfNotNull(
                                                                            billingAddress.phone
                                                                        ),
                                                                        postalCode = Optional.presentIfNotNull(
                                                                            billingAddress.postalCode
                                                                        ),
                                                                        streetAddress1 = Optional.presentIfNotNull(
                                                                            billingAddress.streetAddress1
                                                                        )
                                                                    )
                                                                )
                                                            )
                                                        }
                                                    }

                                                    AddressItem(
                                                        name = name,
                                                        address = adds,
                                                        phone = phone
                                                    ) {
                                                        val billingAddress =
                                                            address.addressDetailsFragment
                                                        postInput(
                                                            CheckoutContract.Inputs.SetBillingAddress(
                                                                address = AddressInput(
                                                                    firstName = Optional.presentIfNotNull(
                                                                        billingAddress.firstName
                                                                    ),
                                                                    lastName = Optional.presentIfNotNull(
                                                                        billingAddress.lastName
                                                                    ),
                                                                    city = Optional.presentIfNotNull(
                                                                        billingAddress.city
                                                                    ),
                                                                    phone = Optional.presentIfNotNull(
                                                                        billingAddress.phone
                                                                    ),
                                                                    postalCode = Optional.presentIfNotNull(
                                                                        billingAddress.postalCode
                                                                    ),
                                                                    streetAddress1 = Optional.presentIfNotNull(
                                                                        billingAddress.streetAddress1
                                                                    )
                                                                )
                                                            )
                                                        )
                                                    }
                                                }
                                            }
                                        }
                                    }
                                } else {
                                    val address = it.billingAddress!!.addressDetailsFragment
                                    val name = "${address.firstName} ${address.lastName}"
                                    val adds =
                                        "${address.streetAddress1}, ${address.city}, ${address.postalCode}"
                                    val phone = address.phone ?: ""
                                    AddressItem(
                                        name = name,
                                        address = adds,
                                        phone = phone,
                                        isChange = true
                                    ) {

                                    }
                                }
                            }
                        }
                    }
                }


                Div(attrs = {
                    toClasses("bg-gray-50 rounded-lg md:hidden lg:hidden")
                }) {
                    uiState.carts.getCachedOrNull()?.let { _cart ->
                        CartBody(
                            cart = _cart,
                            onClose = {

                            },
                            onDecrement = { variantId, qty ->
//                                    postInput(CheckoutContract.Inputs.Decrement(variantId, qty))
                            },
                            onIncrement = {
                                postInput(CheckoutContract.Inputs.Increment(lineId = it))
                            },
                            removeLine = {
                                postInput(CheckoutContract.Inputs.RemoveLine(lineId = it))
                            }
                        ) {
                            Div(attrs = {
                                toClasses("col-span-6 text-center text-slate-50 hover:text-slate-50 active:text-slate-50 focus:text-slate-50")
                                onClick {
                                    it.preventDefault()
                                    _cart.availablePaymentGateways.firstOrNull()?.id?.let {
                                        postInput(CheckoutContract.Inputs.PlaceOrder(it))
                                    }
                                }
                            }) {
                                Button(attrs = {

                                }) {
                                    Text("Place Order")
                                }
                            }
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
                    Div(
                        attrs = {
                            toClasses("mt-4 bg-slate-50")
                        }
                    ) {
                        uiState.carts.getCachedOrNull()?.let { _cart ->
                            CartBody(
                                cart = _cart,
                                onClose = {

                                },
                                onDecrement = { variantId, qty ->
//                                    postInput(CheckoutContract.Inputs.Decrement(variantId, qty))
                                },
                                onIncrement = {
                                    postInput(CheckoutContract.Inputs.Increment(lineId = it))
                                },
                                removeLine = {
                                    postInput(CheckoutContract.Inputs.RemoveLine(lineId = it))
                                }
                            ) {
                                Div {
                                    Div(attrs = {
                                        toClasses("w-full col-span-6 text-center text-slate-50 hover:text-slate-50 active:text-slate-50 focus:text-slate-50")
                                        onClick {
                                            it.preventDefault()
                                            _cart.availablePaymentGateways.firstOrNull()?.id?.let {
                                                postInput(CheckoutContract.Inputs.PlaceOrder(it))
                                            }
                                        }
                                    }) {
                                        Button(attrs = {

                                        }) {
                                            Text("Place Order")
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

        }
    }
}

@Composable
fun AddressItem(
    name: String,
    address: String,
    phone: String,
    isChange: Boolean = false,
    onSet: () -> Unit
) {
    Div(
        attrs = {
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
            onClick {
                onSet()
            }
        }
    ) {
        H3(attrs = {
            classes("text-xl", "font-bold")
        }) {
            Text(name)
        }

        H3(attrs = {
            classes("text-xl", "font-bold")
        }) {
            Text(phone)
        }

        P(
            attrs = {
                classes("text-slate-50", "font-bold")
            }
        ) {
            Text(address)
        }
    }
}

@Composable
fun AddressForm(
    postInput: (CheckoutContract.Inputs) -> Unit
) {
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var street by remember { mutableStateOf("") }
    var city by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var postcode by remember { mutableStateOf("") }

    Form(
        attrs = {
            onSubmit {
                it.preventDefault()
                //post all address and response
                postInput(
                    CheckoutContract.Inputs.SetBillingAddress(
                        address = AddressInput(
                            firstName = Optional.presentIfNotNull(
                                firstName
                            ),
                            lastName = Optional.presentIfNotNull(
                                lastName
                            ),
                            city = Optional.presentIfNotNull(
                                city
                            ),
                            phone = Optional.presentIfNotNull(
                                phone
                            ),
                            postalCode = Optional.presentIfNotNull(postcode),
                            streetAddress1 = Optional.presentIfNotNull(street)
                        )
                    )
                )
            }
        }
    ) {
        Div(attrs = {
            classes("grid", "md:grid-cols-2", "md:gap-6")
        }) {
            Div(attrs = {
                classes("relative", "z-0", "w-full", "mb-6", "group")
            }) {
                Input(type = InputType.Text) {
                    name("floating_first_name")
                    id("floating_first_name")
                    classes(
                        "block",
                        "py-2.5",
                        "px-0",
                        "w-full",
                        "text-sm",
                        "text-gray-900",
                        "bg-transparent",
                        "border-0",
                        "border-b-2",
                        "border-gray-300",
                        "appearance-none",
                        "dark:text-white",
                        "dark:border-gray-600",
                        "dark:focus:border-blue-500",
                        "focus:outline-none",
                        "focus:ring-0",
                        "focus:border-blue-600",
                        "peer"
                    )
                    attr("placeholder", " ")
                    required()
                    onInput {
                        firstName = it.value
                    }
                }
                Label(attrs = {
                    classes(
                        "peer-focus:font-medium",
                        "absolute",
                        "text-sm",
                        "text-gray-500",
                        "dark:text-gray-400",
                        "duration-300",
                        "transform",
                        "-translate-y-6",
                        "scale-75",
                        "top-3",
                        "-z-10",
                        "origin-[0]",
                        "peer-focus:left-0",
                        "peer-focus:text-blue-600",
                        "peer-focus:dark:text-blue-500",
                        "peer-placeholder-shown:scale-100",
                        "peer-placeholder-shown:translate-y-0",
                        "peer-focus:scale-75",
                        "peer-focus:-translate-y-6"
                    )
                }, forId = "floating_first_name") {
                    Text("First name")
                }
            }

            Div(attrs = {
                classes("relative", "z-0", "w-full", "mb-6", "group")
            }) {
                Input(type = InputType.Text) {
                    name("floating_last_name")
                    id("floating_last_name")
                    classes(
                        "block",
                        "py-2.5",
                        "px-0",
                        "w-full",
                        "text-sm",
                        "text-gray-900",
                        "bg-transparent",
                        "border-0",
                        "border-b-2",
                        "border-gray-300",
                        "appearance-none",
                        "dark:text-white",
                        "dark:border-gray-600",
                        "dark:focus:border-blue-500",
                        "focus:outline-none",
                        "focus:ring-0",
                        "focus:border-blue-600",
                        "peer"
                    )
                    attr("placeholder", " ")
                    required()
                    onInput {
                        lastName = it.value
                    }
                }
                Label(attrs = {
                    classes(
                        "peer-focus:font-medium",
                        "absolute",
                        "text-sm",
                        "text-gray-500",
                        "dark:text-gray-400",
                        "duration-300",
                        "transform",
                        "-translate-y-6",
                        "scale-75",
                        "top-3",
                        "-z-10",
                        "origin-[0]",
                        "peer-focus:left-0",
                        "peer-focus:text-blue-600",
                        "peer-focus:dark:text-blue-500",
                        "peer-placeholder-shown:scale-100",
                        "peer-placeholder-shown:translate-y-0",
                        "peer-focus:scale-75",
                        "peer-focus:-translate-y-6"
                    )
                }, forId = "floating_last_name") {
                    Text("Last name")
                }
            }
        }

        Div(attrs = {
            classes("grid", "md:grid-cols-2", "md:gap-6")
        }) {
            Div(attrs = {
                classes("relative", "z-0", "w-full", "mb-6", "group")
            }) {
                Input(type = InputType.Text) {
                    name("floating_street")
                    id("floating_street")
                    classes(
                        "block",
                        "py-2.5",
                        "px-0",
                        "w-full",
                        "text-sm",
                        "text-gray-900",
                        "bg-transparent",
                        "border-0",
                        "border-b-2",
                        "border-gray-300",
                        "appearance-none",
                        "dark:text-white",
                        "dark:border-gray-600",
                        "dark:focus:border-blue-500",
                        "focus:outline-none",
                        "focus:ring-0",
                        "focus:border-blue-600",
                        "peer"
                    )
                    attr("placeholder", " ")
                    required()
                    onInput {
                        street = it.value
                    }
                }
                Label(attrs = {
                    classes(
                        "peer-focus:font-medium",
                        "absolute",
                        "text-sm",
                        "text-gray-500",
                        "dark:text-gray-400",
                        "duration-300",
                        "transform",
                        "-translate-y-6",
                        "scale-75",
                        "top-3",
                        "-z-10",
                        "origin-[0]",
                        "peer-focus:left-0",
                        "peer-focus:text-blue-600",
                        "peer-focus:dark:text-blue-500",
                        "peer-placeholder-shown:scale-100",
                        "peer-placeholder-shown:translate-y-0",
                        "peer-focus:scale-75",
                        "peer-focus:-translate-y-6"
                    )
                }, forId = "floating_street") {
                    Text("Street")
                }
            }

            Div(attrs = {
                classes("relative", "z-0", "w-full", "mb-6", "group")
            }) {
                Input(type = InputType.Text) {
                    name("floating_city")
                    id("floating_city")
                    classes(
                        "block",
                        "py-2.5",
                        "px-0",
                        "w-full",
                        "text-sm",
                        "text-gray-900",
                        "bg-transparent",
                        "border-0",
                        "border-b-2",
                        "border-gray-300",
                        "appearance-none",
                        "dark:text-white",
                        "dark:border-gray-600",
                        "dark:focus:border-blue-500",
                        "focus:outline-none",
                        "focus:ring-0",
                        "focus:border-blue-600",
                        "peer"
                    )
                    attr("placeholder", " ")
                    required()
                    onInput {
                        city = it.value
                    }
                }
                Label(attrs = {
                    classes(
                        "peer-focus:font-medium",
                        "absolute",
                        "text-sm",
                        "text-gray-500",
                        "dark:text-gray-400",
                        "duration-300",
                        "transform",
                        "-translate-y-6",
                        "scale-75",
                        "top-3",
                        "-z-10",
                        "origin-[0]",
                        "peer-focus:left-0",
                        "peer-focus:text-blue-600",
                        "peer-focus:dark:text-blue-500",
                        "peer-placeholder-shown:scale-100",
                        "peer-placeholder-shown:translate-y-0",
                        "peer-focus:scale-75",
                        "peer-focus:-translate-y-6"
                    )
                }, forId = "floating_city") {
                    Text("City")
                }
            }
        }

        Div(attrs = {
            classes("grid", "md:grid-cols-2", "md:gap-6")
        }) {
            Div(attrs = {
                classes("relative", "z-0", "w-full", "mb-6", "group")
            }) {
                Input(type = InputType.Tel) {
//                    attr("pattern", "[0-9]{3}-[0-9]{3}-[0-9]{4}")
                    name("floating_phone")
                    id("floating_phone")
                    classes(
                        "block",
                        "py-2.5",
                        "px-0",
                        "w-full",
                        "text-sm",
                        "text-gray-900",
                        "bg-transparent",
                        "border-0",
                        "border-b-2",
                        "border-gray-300",
                        "appearance-none",
                        "dark:text-white",
                        "dark:border-gray-600",
                        "dark:focus:border-blue-500",
                        "focus:outline-none",
                        "focus:ring-0",
                        "focus:border-blue-600",
                        "peer"
                    )
                    attr("placeholder", " ")
                    required()
                    onInput {
                        phone = it.value
                    }
                }
                Label(attrs = {
                    classes(
                        "peer-focus:font-medium",
                        "absolute",
                        "text-sm",
                        "text-gray-500",
                        "dark:text-gray-400",
                        "duration-300",
                        "transform",
                        "-translate-y-6",
                        "scale-75",
                        "top-3",
                        "-z-10",
                        "origin-[0]",
                        "peer-focus:left-0",
                        "peer-focus:text-blue-600",
                        "peer-focus:dark:text-blue-500",
                        "peer-placeholder-shown:scale-100",
                        "peer-placeholder-shown:translate-y-0",
                        "peer-focus:scale-75",
                        "peer-focus:-translate-y-6"
                    )
                }, forId = "floating_phone") {
                    Text("Phone number (123-456-7890)")
                }

            }
            Div(attrs = {
                classes("relative", "z-0", "w-full", "mb-6", "group")
            }) {
                Input(type = InputType.Text) {
                    name("floating_postcode")
                    id("floating_postcode")
                    classes(
                        "block",
                        "py-2.5",
                        "px-0",
                        "w-full",
                        "text-sm",
                        "text-gray-900",
                        "bg-transparent",
                        "border-0",
                        "border-b-2",
                        "border-gray-300",
                        "appearance-none",
                        "dark:text-white",
                        "dark:border-gray-600",
                        "dark:focus:border-blue-500",
                        "focus:outline-none",
                        "focus:ring-0",
                        "focus:border-blue-600",
                        "peer"
                    )
                    attr("placeholder", " ")
                    required()
                    onInput {
                        postcode = it.value
                    }
                }
                Label(attrs = {
                    classes(
                        "peer-focus:font-medium",
                        "absolute",
                        "text-sm",
                        "text-gray-500",
                        "dark:text-gray-400",
                        "duration-300",
                        "transform",
                        "-translate-y-6",
                        "scale-75",
                        "top-3",
                        "-z-10",
                        "origin-[0]",
                        "peer-focus:left-0",
                        "peer-focus:text-blue-600",
                        "peer-focus:dark:text-blue-500",
                        "peer-placeholder-shown:scale-100",
                        "peer-placeholder-shown:translate-y-0",
                        "peer-focus:scale-75",
                        "peer-focus:-translate-y-6"
                    )
                }, forId = "floating_postcode") {
                    Text("Postcode")
                }
            }
        }
        Button(attrs = {
            attr("type", "submit")
            classes(
                "text-white",
                "bg-blue-700",
                "hover:bg-blue-800",
                "focus:ring-4",
                "focus:outline-none",
                "focus:ring-blue-300",
                "font-medium",
                "rounded-lg",
                "text-sm",
                "w-full",
                "sm:w-auto",
                "px-5",
                "py-2.5",
                "text-center",
                "dark:bg-blue-600",
                "dark:hover:bg-blue-700",
                "dark:focus:ring-blue-800"
            )
        }) {
            Text("Save")
        }
    }
}



