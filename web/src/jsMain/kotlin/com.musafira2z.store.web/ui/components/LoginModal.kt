package com.musafira2z.store.web.ui.components

import androidx.compose.runtime.*
import com.musafira2z.store.LoginCustomerMutation
import com.musafira2z.store.RegisterCustomerMutation
import com.musafira2z.store.ui.app.AppContract
import com.musafira2z.store.utils.ResponseResource
import com.musafira2z.store.web.ui.utils.toClasses
import org.jetbrains.compose.web.ExperimentalComposeWebSvgApi
import org.jetbrains.compose.web.attributes.InputType
import org.jetbrains.compose.web.dom.*
import org.jetbrains.compose.web.svg.Path
import org.jetbrains.compose.web.svg.Svg

@Composable
fun LoginModal(
    postInput: (AppContract.Inputs) -> Unit,
    loginResponse: ResponseResource<LoginCustomerMutation.TokenCreate?>,
    signUpResponse: ResponseResource<RegisterCustomerMutation.AccountRegister?>,
    isLoggedIn: Boolean
) {
    var modalState by remember { mutableStateOf(false) }
    var isSignUp by remember { mutableStateOf(false) }


    LaunchedEffect(isLoggedIn) {
        if (isLoggedIn) {
            modalState = false
        }
    }
    Modal(
        showModal = modalState,
        modalOpenButton = {
            Button(attrs = {
                toClasses("active:translate-x-1 font-bold flex items-center gap-1 bg-green-500 text-slate-50 rounded-full px-2 py-1")
                onClick {
                    modalState = true
                }
            }) {
                OutlineLogin()
                Text("Login")
            }
        },
        title = "Welcome to Musafir!",
        onHide = {
            modalState = it
        }
    ) {
        Div(attrs = {
            toClasses("flex justify-center")
        }) {
            LoginSvg()
        }
        Div(attrs = {
            classes("pb-5")
        }) {
            if (isSignUp) {
                SignUpForm(
                    signUpResponse = signUpResponse,
                    onLogin = {
                        isSignUp = false
                    }
                ) { fullName, username, password ->
                    postInput(AppContract.Inputs.SignUpUser(fullName, username, password))
                }
            } else {
                LoginForm(loginResponse = loginResponse, onSignup = {
                    isSignUp = true
                }, onForget = {
                    postInput(AppContract.Inputs.ForgetPassword(username = it))
                }) { username, password ->
                    postInput(AppContract.Inputs.LoginUser(username, password))
                }
            }
        }
    }
}

@OptIn(ExperimentalComposeWebSvgApi::class)
@Composable
fun LoginForm(
    loginResponse: ResponseResource<LoginCustomerMutation.TokenCreate?>,
    onSignup: () -> Unit,
    onForget: (username: String) -> Unit,
    onLogin: (username: String, password: String) -> Unit
) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isForget by remember { mutableStateOf(false) }

    Label(
        attrs = {
            classes("block", "mb-2", "text-sm", "font-medium", "text-gray-900", "dark:text-white")
        }, forId = "input-group-1"
    ) {
        Text("Email")
    }
    Div(attrs = {
        classes("relative", "mb-6")
    }) {
        Div(attrs = {
            classes(
                "absolute",
                "inset-y-0",
                "left-0",
                "flex",
                "items-center",
                "pl-3",
                "pointer-events-none"
            )
        }) {
            Svg(attrs = {
                attr("aria-hidden", "true")
                classes("w-5", "h-5", "text-gray-500", "dark:text-gray-400")
                attr("fill", "currentColor")
                attr("viewBox", "0 0 20 20")
                attr("xmlns", "http://www.w3.org/2000/svg")
            }) {
                Path(
                    d =
                    "M2.003 5.884L10 9.882l7.997-3.998A2 2 0 0016 4H4a2 2 0 00-1.997 1.884z"
                )
                Path(
                    d =
                    "M18 8.118l-8 4-8-4V14a2 2 0 002 2h12a2 2 0 002-2V8.118z"
                )
            }

        }

        Input(InputType.Email) {
            id("input-group-1")
            classes(
                "bg-gray-50",
                "border",
                "border-gray-300",
                "text-gray-900",
                "text-sm",
                "rounded-lg",
                "focus:ring-blue-500",
                "focus:border-blue-500",
                "block",
                "w-full",
                "pl-10",
                "p-2.5",
                "dark:bg-gray-700",
                "dark:border-gray-600",
                "dark:placeholder-gray-400",
                "dark:text-white",
                "dark:focus:ring-blue-500",
                "dark:focus:border-blue-500"
            )
            attr("placeholder", "name@gmail.com")
            onInput {
                username = it.value
            }
        }
    }

    if (isForget) {
        Div(attrs = {
        }) {
            Button(
                attrs = {
                    onClick {
                        onForget(username)
                        isForget = false
                    }
                    toClasses("mt-4 text-white bg-blue-700 hover:bg-blue-800 focus:outline-none focus:ring-4 focus:ring-blue-300 font-medium rounded-full text-sm px-5 py-2.5 text-center mr-2 mb-2 dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800")
                }
            ) {
                Text("Submit")
            }

            Button(
                attrs = {
                    onClick {
                        isForget = false
                    }
                    toClasses("mt-4 text-white bg-blue-700 hover:bg-blue-800 focus:outline-none focus:ring-4 focus:ring-blue-300 font-medium rounded-full text-sm px-5 py-2.5 text-center mr-2 mb-2 dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800")
                }
            ) {
                Text("Cancel")
            }
        }
    } else {
        Label(attrs = {
            classes("block", "mb-2", "text-sm", "font-medium", "text-gray-900", "dark:text-white")
        }, forId = "website-admin") {
            Text("Password")
        }
        Div(attrs = {
            classes("flex")
        }) {
            Span(attrs = {
                classes(
                    "inline-flex",
                    "items-center",
                    "px-3",
                    "text-sm",
                    "text-gray-900",
                    "bg-gray-200",
                    "border",
                    "border-r-0",
                    "border-gray-300",
                    "rounded-l-md",
                    "dark:bg-gray-600",
                    "dark:text-gray-400",
                    "dark:border-gray-600"
                )
            }) {
                Text(" @ ")
            }
            Input(type = InputType.Password) {
                id("website-admin")
                classes(
                    "rounded-none",
                    "rounded-r-lg",
                    "bg-gray-50",
                    "border",
                    "text-gray-900",
                    "focus:ring-blue-500",
                    "focus:border-blue-500",
                    "block",
                    "flex-1",
                    "min-w-0",
                    "w-full",
                    "text-sm",
                    "border-gray-300",
                    "p-2.5",
                    "dark:bg-gray-700",
                    "dark:border-gray-600",
                    "dark:placeholder-gray-400",
                    "dark:text-white",
                    "dark:focus:ring-blue-500",
                    "dark:focus:border-blue-500"
                )
                attr("placeholder", "password")
                onInput {
                    password = it.value
                }
            }
        }

        Div {
            Button(
                attrs = {
                    classes("mt-2")
                    onClick {
                        isForget = true
                    }
                }
            ) {
                P(attrs = {
                    classes("text-blue-500")
                }) {
                    Text("Forget password ?")
                }
            }
        }


        Div(attrs = {
        }) {
            Button(
                attrs = {
                    onClick {
                        onLogin(username, password)
                    }
                    toClasses("mt-4 text-white bg-blue-700 hover:bg-blue-800 focus:outline-none focus:ring-4 focus:ring-blue-300 font-medium rounded-full text-sm px-5 py-2.5 text-center mr-2 mb-2 dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800")
                }
            ) {
                Text("Login")
            }

            Button(attrs = {
                onClick {
                    onSignup()
                }
                classes(
                    "relative",
                    "inline-flex",
                    "items-center",
                    "justify-center",
                    "p-0.5",
                    "mb-2",
                    "mr-2",
                    "overflow-hidden",
                    "text-sm",
                    "font-medium",
                    "text-gray-900",
                    "rounded-lg",
                    "group",
                    "bg-gradient-to-br",
                    "from-green-400",
                    "to-blue-600",
                    "group-hover:from-green-400",
                    "group-hover:to-blue-600",
                    "hover:text-white",
                    "dark:text-white",
                    "focus:ring-4",
                    "focus:outline-none",
                    "focus:ring-green-200",
                    "dark:focus:ring-green-800"
                )
            }) {
                Span(attrs = {
                    classes(
                        "relative",
                        "px-5",
                        "py-2.5",
                        "transition-all",
                        "ease-in",
                        "duration-75",
                        "bg-white",
                        "dark:bg-gray-900",
                        "rounded-md",
                        "group-hover:bg-opacity-0"
                    )
                }) {
                    Text(" Sign up  ")
                }
            }
        }
    }

    when (loginResponse) {
        is ResponseResource.Error -> {

        }
        ResponseResource.Idle -> {

        }
        ResponseResource.Loading -> {
            Div(attrs = {
                attr("role", "status")
            }) {
                Svg(attrs = {
                    attr("aria-hidden", "true")
                    classes(
                        "w-8",
                        "h-8",
                        "mr-2",
                        "text-gray-200",
                        "animate-spin",
                        "dark:text-gray-600",
                        "fill-blue-600"
                    )
                    attr("viewBox", "0 0 100 101")
                    attr("fill", "none")
                    attr("xmlns", "http://www.w3.org/2000/svg")
                }) {
                    Path(
                        attrs = {
                            attr("fill", "currentColor")
                        },
                        d = "M100 50.5908C100 78.2051 77.6142 100.591 50 100.591C22.3858 100.591 0 78.2051 0 50.5908C0 22.9766 22.3858 0.59082 50 0.59082C77.6142 0.59082 100 22.9766 100 50.5908ZM9.08144 50.5908C9.08144 73.1895 27.4013 91.5094 50 91.5094C72.5987 91.5094 90.9186 73.1895 90.9186 50.5908C90.9186 27.9921 72.5987 9.67226 50 9.67226C27.4013 9.67226 9.08144 27.9921 9.08144 50.5908Z"
                    )
                    Path(
                        attrs = {
                            attr("fill", "currentFill")
                        },
                        d = "M93.9676 39.0409C96.393 38.4038 97.8624 35.9116 97.0079 33.5539C95.2932 28.8227 92.871 24.3692 89.8167 20.348C85.8452 15.1192 80.8826 10.7238 75.2124 7.41289C69.5422 4.10194 63.2754 1.94025 56.7698 1.05124C51.7666 0.367541 46.6976 0.446843 41.7345 1.27873C39.2613 1.69328 37.813 4.19778 38.4501 6.62326C39.0873 9.04874 41.5694 10.4717 44.0505 10.1071C47.8511 9.54855 51.7191 9.52689 55.5402 10.0491C60.8642 10.7766 65.9928 12.5457 70.6331 15.2552C75.2735 17.9648 79.3347 21.5619 82.5849 25.841C84.9175 28.9121 86.7997 32.2913 88.1811 35.8758C89.083 38.2158 91.5421 39.6781 93.9676 39.0409Z"
                    )
                }
                Span(attrs = {
                    classes("sr-only")
                }) {
                    Text("Loading...")
                }
            }
        }
        is ResponseResource.Success -> {

        }
    }

}

@OptIn(ExperimentalComposeWebSvgApi::class)
@Composable
fun SignUpForm(
    signUpResponse: ResponseResource<RegisterCustomerMutation.AccountRegister?>,
    onLogin: () -> Unit,
    onSignUp: (fullName: String, username: String, password: String) -> Unit
) {
    var fullname by remember { mutableStateOf("") }
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Label(
        attrs = {
            classes("block", "mb-2", "text-sm", "font-medium", "text-gray-900", "dark:text-white")
        }, forId = "input-group-3"
    ) {
        Text("Full name")
    }
    Div(attrs = {
        classes("relative", "mb-6")
    }) {
        Div(attrs = {
            classes(
                "absolute",
                "inset-y-0",
                "left-0",
                "flex",
                "items-center",
                "pl-3",
                "pointer-events-none"
            )
        }) {
            Svg(attrs = {
                attr("aria-hidden", "true")
                classes("w-5", "h-5", "text-gray-500", "dark:text-gray-400")
                attr("fill", "currentColor")
                attr("viewBox", "0 0 20 20")
                attr("xmlns", "http://www.w3.org/2000/svg")
            }) {
                Path(
                    d =
                    "M2.003 5.884L10 9.882l7.997-3.998A2 2 0 0016 4H4a2 2 0 00-1.997 1.884z"
                )
                Path(
                    d =
                    "M18 8.118l-8 4-8-4V14a2 2 0 002 2h12a2 2 0 002-2V8.118z"
                )
            }
        }

        Input(InputType.Text) {
            id("input-group-3")
            classes(
                "bg-gray-50",
                "border",
                "border-gray-300",
                "text-gray-900",
                "text-sm",
                "rounded-lg",
                "focus:ring-blue-500",
                "focus:border-blue-500",
                "block",
                "w-full",
                "pl-10",
                "p-2.5",
                "dark:bg-gray-700",
                "dark:border-gray-600",
                "dark:placeholder-gray-400",
                "dark:text-white",
                "dark:focus:ring-blue-500",
                "dark:focus:border-blue-500"
            )
            attr("placeholder", "Jhon Doe")
            onInput {
                fullname = it.value
            }
        }
    }

    Label(
        attrs = {
            classes("block", "mb-2", "text-sm", "font-medium", "text-gray-900", "dark:text-white")
        }, forId = "input-group-1"
    ) {
        Text("Email")
    }
    Div(attrs = {
        classes("relative", "mb-6")
    }) {
        Div(attrs = {
            classes(
                "absolute",
                "inset-y-0",
                "left-0",
                "flex",
                "items-center",
                "pl-3",
                "pointer-events-none"
            )
        }) {
            Svg(attrs = {
                attr("aria-hidden", "true")
                classes("w-5", "h-5", "text-gray-500", "dark:text-gray-400")
                attr("fill", "currentColor")
                attr("viewBox", "0 0 20 20")
                attr("xmlns", "http://www.w3.org/2000/svg")
            }) {
                Path(
                    d =
                    "M2.003 5.884L10 9.882l7.997-3.998A2 2 0 0016 4H4a2 2 0 00-1.997 1.884z"
                )
                Path(
                    d =
                    "M18 8.118l-8 4-8-4V14a2 2 0 002 2h12a2 2 0 002-2V8.118z"
                )
            }

        }

        Input(InputType.Email) {
            id("input-group-1")
            classes(
                "bg-gray-50",
                "border",
                "border-gray-300",
                "text-gray-900",
                "text-sm",
                "rounded-lg",
                "focus:ring-blue-500",
                "focus:border-blue-500",
                "block",
                "w-full",
                "pl-10",
                "p-2.5",
                "dark:bg-gray-700",
                "dark:border-gray-600",
                "dark:placeholder-gray-400",
                "dark:text-white",
                "dark:focus:ring-blue-500",
                "dark:focus:border-blue-500"
            )
            attr("placeholder", "name@gmail.com")
            onInput {
                username = it.value
            }
        }
    }
    Label(attrs = {
        classes("block", "mb-2", "text-sm", "font-medium", "text-gray-900", "dark:text-white")
    }, forId = "website-admin") {
        Text("Password")
    }
    Div(attrs = {
        classes("flex")
    }) {
        Span(attrs = {
            classes(
                "inline-flex",
                "items-center",
                "px-3",
                "text-sm",
                "text-gray-900",
                "bg-gray-200",
                "border",
                "border-r-0",
                "border-gray-300",
                "rounded-l-md",
                "dark:bg-gray-600",
                "dark:text-gray-400",
                "dark:border-gray-600"
            )
        }) {
            Text(" @ ")
        }
        Input(type = InputType.Password) {
            id("website-admin")
            classes(
                "rounded-none",
                "rounded-r-lg",
                "bg-gray-50",
                "border",
                "text-gray-900",
                "focus:ring-blue-500",
                "focus:border-blue-500",
                "block",
                "flex-1",
                "min-w-0",
                "w-full",
                "text-sm",
                "border-gray-300",
                "p-2.5",
                "dark:bg-gray-700",
                "dark:border-gray-600",
                "dark:placeholder-gray-400",
                "dark:text-white",
                "dark:focus:ring-blue-500",
                "dark:focus:border-blue-500"
            )
            attr("placeholder", "password")
            onInput {
                password = it.value
            }
        }
    }

    when (signUpResponse) {
        is ResponseResource.Error -> {

        }
        ResponseResource.Idle -> {

        }
        ResponseResource.Loading -> {
            Div(attrs = {
                attr("role", "status")
            }) {
                Svg(attrs = {
                    attr("aria-hidden", "true")
                    classes(
                        "w-8",
                        "h-8",
                        "mr-2",
                        "text-gray-200",
                        "animate-spin",
                        "dark:text-gray-600",
                        "fill-blue-600"
                    )
                    attr("viewBox", "0 0 100 101")
                    attr("fill", "none")
                    attr("xmlns", "http://www.w3.org/2000/svg")
                }) {
                    Path(
                        attrs = {
                            attr("fill", "currentColor")
                        },
                        d = "M100 50.5908C100 78.2051 77.6142 100.591 50 100.591C22.3858 100.591 0 78.2051 0 50.5908C0 22.9766 22.3858 0.59082 50 0.59082C77.6142 0.59082 100 22.9766 100 50.5908ZM9.08144 50.5908C9.08144 73.1895 27.4013 91.5094 50 91.5094C72.5987 91.5094 90.9186 73.1895 90.9186 50.5908C90.9186 27.9921 72.5987 9.67226 50 9.67226C27.4013 9.67226 9.08144 27.9921 9.08144 50.5908Z"
                    )
                    Path(
                        attrs = {
                            attr("fill", "currentFill")
                        },
                        d = "M93.9676 39.0409C96.393 38.4038 97.8624 35.9116 97.0079 33.5539C95.2932 28.8227 92.871 24.3692 89.8167 20.348C85.8452 15.1192 80.8826 10.7238 75.2124 7.41289C69.5422 4.10194 63.2754 1.94025 56.7698 1.05124C51.7666 0.367541 46.6976 0.446843 41.7345 1.27873C39.2613 1.69328 37.813 4.19778 38.4501 6.62326C39.0873 9.04874 41.5694 10.4717 44.0505 10.1071C47.8511 9.54855 51.7191 9.52689 55.5402 10.0491C60.8642 10.7766 65.9928 12.5457 70.6331 15.2552C75.2735 17.9648 79.3347 21.5619 82.5849 25.841C84.9175 28.9121 86.7997 32.2913 88.1811 35.8758C89.083 38.2158 91.5421 39.6781 93.9676 39.0409Z"
                    )
                }
                Span(attrs = {
                    classes("sr-only")
                }) {
                    Text("Loading...")
                }
            }
        }
        is ResponseResource.Success -> {
            LaunchedEffect(Unit) {
                onLogin()
            }
        }
    }

    Div(attrs = {
    }) {
        Button(
            attrs = {
                onClick {
                    onSignUp(fullname, username, password)
                }
                toClasses("mt-4 text-white bg-blue-700 hover:bg-blue-800 focus:outline-none focus:ring-4 focus:ring-blue-300 font-medium rounded-full text-sm px-5 py-2.5 text-center mr-2 mb-2 dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800")
            }
        ) {
            Text("Signup")
        }

        Button(attrs = {
            onClick {
                onLogin()
            }
            classes(
                "relative",
                "inline-flex",
                "items-center",
                "justify-center",
                "p-0.5",
                "mb-2",
                "mr-2",
                "overflow-hidden",
                "text-sm",
                "font-medium",
                "text-gray-900",
                "rounded-lg",
                "group",
                "bg-gradient-to-br",
                "from-green-400",
                "to-blue-600",
                "group-hover:from-green-400",
                "group-hover:to-blue-600",
                "hover:text-white",
                "dark:text-white",
                "focus:ring-4",
                "focus:outline-none",
                "focus:ring-green-200",
                "dark:focus:ring-green-800"
            )
        }) {
            Span(attrs = {
                classes(
                    "relative",
                    "px-5",
                    "py-2.5",
                    "transition-all",
                    "ease-in",
                    "duration-75",
                    "bg-white",
                    "dark:bg-gray-900",
                    "rounded-md",
                    "group-hover:bg-opacity-0"
                )
            }) {
                Text(" Login  ")
            }
        }
    }
}


