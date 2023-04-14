package com.musafira2z.store.web.ui.dashboard.proifle

import androidx.compose.runtime.Composable
import com.musafira2z.store.web.ui.di.ComposeWebInjector
import org.jetbrains.compose.web.attributes.InputType
import org.jetbrains.compose.web.attributes.disabled
import org.jetbrains.compose.web.attributes.name
import org.jetbrains.compose.web.attributes.required
import org.jetbrains.compose.web.dom.*

@Composable
fun ProfileScreen(
    webInjector: ComposeWebInjector
) {
    ProfileContent()
}

@Composable
fun ProfileContent() {
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
            Profile()
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
fun Profile() {

    Div(attrs = {
        classes("bg-slate-100", "w-full", "px-5", "py-5", "shadow-md", "shadow-gray-300")
    }) {
        Div(attrs = {
            classes("font-bold", "pb-10")
        }) {
            H1 {
                Text("Your Profile")
            }

        }
        Form(attrs = {
            attr("onSubmit", "{handleProfileEdit}")
        }, action = "") {
            Div(attrs = {
                classes("grid", "grid-cols-12", "grid-rows-2", "gap-3")
            }) {
                Div(attrs = {
                    classes(
                        "xl:col-span-5",
                        "lg:col-span-5",
                        "md:col-span-12",
                        "col-span-12",
                        "w-full"
                    )
                }) {
                    Input(InputType.Text) {
                        id("fName")
                        name("firstName")
                        attr("type", "fName")
                        attr("label", "First Name")
                        attr("placeholder", "First Name")
                        attr("defaultValue", "Rukon")
                        disabled()
                        required()
                    }

                }
                Div(attrs = {
                    classes(
                        "xl:col-span-5",
                        "lg:col-span-5",
                        "md:col-span-12",
                        "col-span-12",
                        "w-full"
                    )
                }) {
                    Input(InputType.Text) {
                        id("lName")
                        name("lastName")
                        attr("type", "lName")
                        attr("label", "Last Name")
                        attr("placeholder", "Last Name")
                        attr("defaultValue", "Uddin")
                        disabled()
                        required()
                    }

                }
                Div(attrs = {
                    classes("xl:col-span-2", "lg:col-span-2", "md:col-span-12", "col-span-12")
                }) {
                    Button(attrs = {

                    }) {

                    }
                    Button(attrs = {

                    }) {

                    }
                }
                Div(attrs = {
                    classes(
                        "xl:col-span-5",
                        "lg:col-span-5",
                        "md:col-span-12",
                        "col-span-12",
                        "w-full",
                        "mt-5",
                    )
                }) {
                    Input(InputType.Email) {
                        id("email")
                        name("Email")
                        attr("type", "email")
                        attr("label", "Email")
                        disabled()
                        attr("defaultValue", "rukon.js@gmail.com")
                        attr("placeholder", "Email")
                        required()
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
                classes("py-10")
            }) {
//                Modal(attrs = {
//                    attr("showModal", "{showAddressModal}")
//                    attr("setShowModal", "{setShowAddressModal}")
//                    attr("modalOpenButton", "{")
//                }) {
//                    AddANewAddressModalOpenButton(attrs = {
//                        attr("setShowAddressModal", "{setShowAddressModal}")
//                    }) {}
//                    Text(" } title='Add New Address'> ")
//                    DeliveryAddressForm(attrs = {
//                        attr("setShowModal", "{setShowAddressModal}")
//                    }) {}
//
//                }

            }
        }

    }
}


