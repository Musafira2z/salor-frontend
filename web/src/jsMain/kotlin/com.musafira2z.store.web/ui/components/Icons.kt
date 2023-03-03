package com.musafira2z.store.web.ui.components

import androidx.compose.runtime.Composable
import org.jetbrains.compose.web.ExperimentalComposeWebSvgApi
import org.jetbrains.compose.web.svg.Path
import org.jetbrains.compose.web.svg.Svg
import com.musafira2z.store.web.ui.utils.toClasses

@OptIn(ExperimentalComposeWebSvgApi::class)
@Composable
fun RxCross2(
    clas: String? = null
) {
    Svg(attrs = {
        attr("width", "15")
        attr("height", "15")
        attr("viewBox", "0 0 15 15")
        attr("fill", "none")
        attr("xmlns", "http://www.w3.org/2000/svg")
        clas?.let {
            toClasses(it)
        }
    }) {
        Path(
            attrs = {
                attr("fill-rule", "evenodd")
                attr("clip-rule", "evenodd")
                attr("fill", "currentColor")
            },
            d = "M11.7816 4.03157C12.0062 3.80702 12.0062 3.44295 11.7816 3.2184C11.5571 2.99385 11.193 2.99385 10.9685 3.2184L7.50005 6.68682L4.03164 3.2184C3.80708 2.99385 3.44301 2.99385 3.21846 3.2184C2.99391 3.44295 2.99391 3.80702 3.21846 4.03157L6.68688 7.49999L3.21846 10.9684C2.99391 11.193 2.99391 11.557 3.21846 11.7816C3.44301 12.0061 3.80708 12.0061 4.03164 11.7816L7.50005 8.31316L10.9685 11.7816C11.193 12.0061 11.5571 12.0061 11.7816 11.7816C12.0062 11.557 12.0062 11.193 11.7816 10.9684L8.31322 7.49999L11.7816 4.03157Z"
        )
    }
}

@OptIn(ExperimentalComposeWebSvgApi::class)
@Composable
fun Plus(
    clas: String? = null
) {
    Svg(attrs = {
        attr("width", "15")
        attr("height", "15")
        attr("viewBox", "0 0 15 15")
        attr("fill", "none")
        attr("xmlns", "http://www.w3.org/2000/svg")
        clas?.let {
            toClasses(it)
        }
    }) {
        Path(
            attrs = {
                attr("fill", "currentColor")
                attr("fill-rule", "evenodd")
                attr("clip-rule", "evenodd")
            },
            d = "M8 2.75C8 2.47386 7.77614 2.25 7.5 2.25C7.22386 2.25 7 2.47386 7 2.75V7H2.75C2.47386 7 2.25 7.22386 2.25 7.5C2.25 7.77614 2.47386 8 2.75 8H7V12.25C7 12.5261 7.22386 12.75 7.5 12.75C7.77614 12.75 8 12.5261 8 12.25V8H12.25C12.5261 8 12.75 7.77614 12.75 7.5C12.75 7.22386 12.5261 7 12.25 7H8V2.75Z"
        )
    }
}

@OptIn(ExperimentalComposeWebSvgApi::class)
@Composable
fun Minus(
    clas: String? = null
) {
    Svg(attrs = {
        attr("width", "15")
        attr("height", "15")
        attr("viewBox", "0 0 15 15")
        attr("fill", "none")
        attr("xmlns", "http://www.w3.org/2000/svg")
        clas?.let {
            toClasses(it)
        }
    }) {
        Path(
            attrs = {
                attr("fill", "currentColor")
                attr("fill-rule", "evenodd")
                attr("clip-rule", "evenodd")
            },
            d = "M2.25 7.5C2.25 7.22386 2.47386 7 2.75 7H12.25C12.5261 7 12.75 7.22386 12.75 7.5C12.75 7.77614 12.5261 8 12.25 8H2.75C2.47386 8 2.25 7.77614 2.25 7.5Z"
        )
    }
}


