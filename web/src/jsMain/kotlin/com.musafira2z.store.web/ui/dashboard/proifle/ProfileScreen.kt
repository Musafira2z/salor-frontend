package com.musafira2z.store.web.ui.dashboard.proifle

import androidx.compose.runtime.*
import com.copperleaf.ballast.repository.cache.getCachedOrNull
import com.musafira2z.store.ui.profile.ProfileContract
import com.musafira2z.store.web.ui.checkout.AddressItem
import com.musafira2z.store.web.ui.di.ComposeWebInjector
import com.musafira2z.store.web.ui.utils.toClasses
import org.jetbrains.compose.web.attributes.InputType
import org.jetbrains.compose.web.attributes.onSubmit
import org.jetbrains.compose.web.dom.*

@Composable
fun ProfileScreen(
    webInjector: ComposeWebInjector
) {
    val viewModelScope = rememberCoroutineScope()
    val vm: ProfileViewModel =
        remember(viewModelScope) { webInjector.profileViewModel(viewModelScope) }
    LaunchedEffect(vm) {
        vm.trySend(ProfileContract.Inputs.Initialize)
    }

    val uiState by vm.observeStates().collectAsState()
    ProfileContent(uiState = uiState) {
        vm.trySend(it)
    }
}

@Composable
fun ProfileContent(
    uiState: ProfileContract.State,
    postInput: (ProfileContract.Inputs) -> Unit
) {
    Div(attrs = {
        classes("container", "mx-auto")
    }) {
        Div(attrs = {
            classes(
                "w-full",
                "xl:ml-64",
                "lg:ml-56",
                "md:ml-50",
                "sm:mx-5",
                "mx-5",
                "mt-5"
            )
        }) {
            Profile(uiState = uiState)
        }
    }
}

@Composable
fun DashboardSidebar() {
    Div(attrs = {
        classes(
            "shadow-md",
            "shadow-gray-300",
            "mt-5",
            "xl:w-80",
            "lg:w-72",
            "md:w-64",
            "w-60",
            "h-5/6",
            "fixed",
            "bg-slate-50",
            "p-1",
            "pt-5",
            "flex",
            "flex-col",
            "justify-between"
        )
    }) {

        Div {
            Ul {
                Li(attrs = {
                    classes("list-none")
                }) {
                    Text("Profile")
                }
                Li(attrs = {
                    classes("list-none")
                }) {
                    Text("My Orders")
                }
            }
        }

        Footer(attrs = {
            classes("py-10", "bg-white", "shadow-md", "shadow-slate-200")
        }) {
            H3(attrs = {
                classes("font-bold", "text-center", "pb-10")
            }) {
                Text(" Contact")

            }
            Ul(attrs = {
                classes("flex", "justify-around")
            }) {
                Li(attrs = {
                    classes("text-green-500")
                }) {
                    Button(attrs = {
                        classes("p-5", "rounded-full", "hover:bg-green-100")
                    }) {
//                        SiWhatsapp(attrs = {
//                            attr("size", "{30}")
//                        }) {}
                    }

                }
                Li(attrs = {
                    classes("text-green-500")
                }) {
                    Button(attrs = {
                        classes("p-5", "rounded-full", "hover:bg-green-100")
                    }) {
//                        CgMail(attrs = {
//                            attr("size", "{30}")
//                        }) {}

                    }

                }
                Li(attrs = {
                    classes("text-green-500")
                }) {
                    Button(attrs = {
                        classes("p-5", "rounded-full", "hover:bg-green-100")
                    }) {
//                        BsTelephoneForwardFill(attrs = {
//                            attr("size", "{30}")
//                        }) {}

                    }

                }
                Li(attrs = {
                    classes("text-green-500")
                }) {
                    Button(attrs = {
                        classes("p-5", "rounded-full", "hover:bg-green-100")
                    }) {
//                        FaFacebook(attrs = {
//                            attr("size", "{30}")
//                        }) {}

                    }

                }

            }

        }

    }
}

@Composable
fun Profile(
    uiState: ProfileContract.State
) {
    Div(attrs = {
        toClasses("bg-slate-100 w-full px-5 py-5 shadow-md shadow-gray-300")
    }) {
        Div(attrs = {
            classes("font-bold", "pb-10")
        }) {
            H1 {
                Text("Your Profile")
            }
        }

        val me = uiState.me.getCachedOrNull()

        Form(
            attrs = {
                onSubmit {
                    it.preventDefault()
                }
                classes("w-full")
            },
            action = ""
        ) {
            val firstName = remember(me?.firstName) { mutableStateOf(me?.firstName ?: "") }
            val lastName = remember(me?.lastName) { mutableStateOf(me?.lastName ?: "") }
            val email = remember(me?.email) { mutableStateOf(me?.email ?: "") }
            var isEdit by remember { mutableStateOf(false) }

            Div(attrs = {
                classes("grid", "grid-cols-12", "gap-2", "mt-2")
            }) {
                Div(attrs = {
                    classes(
                        "col-span-12",
                        "md:col-span-4",
                        "lg:col-span-4",
                        "w-full"
                    )
                }) {
                    com.musafira2z.store.web.ui.components.TextInput(
                        label = "First Name",
                        type = InputType.Text,
                        name = "firstName",
                        placeHolder = firstName.value,
                        disabled = isEdit,
                        defaultValue = firstName
                    ) {
                        firstName.value = it
                    }
                }

                Div(attrs = {
                    classes(
                        "col-span-12",
                        "md:col-span-4",
                        "lg:col-span-4",
                        "w-full"
                    )
                }) {
                    com.musafira2z.store.web.ui.components.TextInput(
                        label = "Last Name",
                        type = InputType.Text,
                        name = "lastName",
                        placeHolder = lastName.value,
                        disabled = isEdit,
                        defaultValue = lastName
                    ) {
                        lastName.value = it
                    }
                }

                Div(attrs = {
                    classes("col-span-4")
                }) {
                    if (isEdit) {
                        Button(attrs = {
                            toClasses("bg-green-500 h-10 w-full text-slate-50 font-bold rounded-full")
                            onClick {
                                isEdit = false
                            }
                        }) {
                            Text("Update")
                        }
                    } else {
                        Button(attrs = {
                            toClasses("bg-green-500 h-10 w-full text-slate-50 font-bold rounded-full")
                            onClick {
                                isEdit = true
                            }
                        }) {
                            Text("Edit")
                        }
                    }
                }

                Div(attrs = {
                    classes(
                        "col-span-12",
                        "w-full",
                        "mt-5",
                    )
                }) {
                    com.musafira2z.store.web.ui.components.TextInput(
                        label = "Email",
                        type = InputType.Text,
                        name = "email",
                        placeHolder = email.value,
                        disabled = false,
                        defaultValue = email
                    ) {

                    }
                }
            }

        }

        Div {
            H1(attrs = {
                classes("font-bold", "mt-10", "mb-5")
            }) {
                Text("Delivery Address")
            }

            Div(attrs = {
                classes("py-1")
            }) {
                // show all address
                uiState.address.getCachedOrNull()?.addresses?.let {
                    it.forEach { address ->
                        val name =
                            "${address.addressDetailsFragment.firstName} ${address.addressDetailsFragment.lastName}"
                        val adds =
                            "${address.addressDetailsFragment.streetAddress1}, ${address.addressDetailsFragment.city}, ${address.addressDetailsFragment.postalCode}"
                        val phone = address.addressDetailsFragment.phone ?: ""
                        AddressItem(name = name, address = adds, phone = phone, isChange = false) {

                        }
                    }
                }
            }
        }

    }
}


