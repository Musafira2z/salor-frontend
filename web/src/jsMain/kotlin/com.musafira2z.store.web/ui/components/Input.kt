package com.musafira2z.store.web.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import com.musafira2z.store.web.ui.utils.toClasses
import org.jetbrains.compose.web.attributes.InputType
import org.jetbrains.compose.web.attributes.disabled
import org.jetbrains.compose.web.attributes.name
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.Input
import org.jetbrains.compose.web.dom.Label
import org.jetbrains.compose.web.dom.Span
import org.w3c.dom.Text

@Composable
fun TextInput(
    label: String,
    type: InputType.Text,
    name: String,
    placeHolder: String,
    disabled: Boolean = false,
    defaultValue: MutableState<String>,
    onChange: (String) -> Unit
) {
    Label(
        attrs = {
            classes("block")
        }
    ) {
        Div {
            Span {
                Text(label)
            }
            Div {
                if (!disabled) {
                    Input(type) {
                        id(name)
                        toClasses(
                            "mt-1 block w-full px-3 py-2 bg-white border border-green-500 rounded-md text-sm shadow-sm placeholder-slate-400 focus:outline-none focus:border-green-500 focus:ring-1 focus:ring-green-500 disabled:bg-slate-50 disabled:text-slate-500 disabled:border-slate-200 invalid:green-pink-500 invalid:text-pink-600 focus:invalid:border-green-500 focus:invalid:ring-green-500"
                        )
                        name(name)
                        attr("placeholder", placeHolder)
                        attr("label", label)
                        disabled()
                        onInput {
                            defaultValue.value = it.value
                        }
                    }
                } else {
                    Input(type) {
                        id(name)
                        toClasses(
                            "mt-1 block w-full px-3 py-2 bg-white border border-green-500 rounded-md text-sm shadow-sm placeholder-slate-400 focus:outline-none focus:border-green-500 focus:ring-1 focus:ring-green-500 disabled:bg-slate-50 disabled:text-slate-500 disabled:border-slate-200 invalid:green-pink-500 invalid:text-pink-600 focus:invalid:border-green-500 focus:invalid:ring-green-500"
                        )
                        name(name)
                        attr("placeholder", placeHolder)
                        attr("label", label)
                        onInput {
                            defaultValue.value = it.value
                        }
                    }
                }
            }
        }
    }
}