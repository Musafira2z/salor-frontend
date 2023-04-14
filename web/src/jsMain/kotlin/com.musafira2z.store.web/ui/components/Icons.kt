package com.musafira2z.store.web.ui.components

import androidx.compose.runtime.Composable
import com.musafira2z.store.web.ui.utils.toClasses
import org.jetbrains.compose.web.ExperimentalComposeWebSvgApi
import org.jetbrains.compose.web.svg.Path
import org.jetbrains.compose.web.svg.Svg

@OptIn(ExperimentalComposeWebSvgApi::class)
@Composable
fun IcAccountCircle(vararg cls: String) {
    Svg(attrs = {
        attr("xmlns", "http://www.w3.org/2000/svg")
        attr("viewBox", "0 96 960 960")
        classes(*cls)
    }) {
        Path(
            d =
            "M222 801q63-44 125-67.5T480 710q71 0 133.5 23.5T739 801q44-54 62.5-109T820 576q0-145-97.5-242.5T480 236q-145 0-242.5 97.5T140 576q0 61 19 116t63 109Zm257.814-195Q422 606 382.5 566.314q-39.5-39.686-39.5-97.5t39.686-97.314q39.686-39.5 97.5-39.5t97.314 39.686q39.5 39.686 39.5 97.5T577.314 566.5q-39.686 39.5-97.5 39.5Zm.654 370Q398 976 325 944.5q-73-31.5-127.5-86t-86-127.266Q80 658.468 80 575.734T111.5 420.5q31.5-72.5 86-127t127.266-86q72.766-31.5 155.5-31.5T635.5 207.5q72.5 31.5 127 86t86 127.032q31.5 72.532 31.5 155T848.5 731q-31.5 73-86 127.5t-127.032 86q-72.532 31.5-155 31.5ZM480 916q55 0 107.5-16T691 844q-51-36-104-55t-107-19q-54 0-107 19t-104 55q51 40 103.5 56T480 916Zm0-370q34 0 55.5-21.5T557 469q0-34-21.5-55.5T480 392q-34 0-55.5 21.5T403 469q0 34 21.5 55.5T480 546Zm0-77Zm0 374Z"
        )
    }
}


@OptIn(ExperimentalComposeWebSvgApi::class)
@Composable
fun IcShoppingCart(
    width: String = "24",
    height: String = "24"
) {
    Svg(attrs = {
        attr("xmlns", "http://www.w3.org/2000/svg")
        attr("height", height)
        attr("viewBox", "0 96 960 960")
        attr("width", width)
    }) {
        Path(d = "M286.788 975Q257 975 236 953.788q-21-21.213-21-51Q215 873 236.212 852q21.213-21 51-21Q317 831 338 852.212q21 21.213 21 51Q359 933 337.788 954q-21.213 21-51 21Zm400 0Q657 975 636 953.788q-21-21.213-21-51Q615 873 636.212 852q21.213-21 51-21Q717 831 738 852.212q21 21.213 21 51Q759 933 737.788 954q-21.213 21-51 21ZM235 315l110 228h288l125-228H235Zm-30-60h589.074q22.964 0 34.945 21Q841 297 829 318L694 561q-11 19-28.559 30.5Q647.881 603 627 603H324l-56 104h491v60H277q-42 0-60.5-28t.5-63l64-118-152-322H51v-60h117l37 79Zm140 288h288-288Z")
    }
}

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

@OptIn(ExperimentalComposeWebSvgApi::class)
@Composable
fun CaretLeft() {
    Svg(attrs = {
        attr("width", "24")
        attr("height", "24")
        attr("viewBox", "0 0 15 15")
        attr("fill", "none")
        attr("xmlns", "http://www.w3.org/2000/svg")
    }) {
        Path(
            attrs = {
                attr("fill-rule", "evenodd")
                attr("clip-rule", "evenodd")
                attr("fill", "currentColor")
            },
            d = "M8.81809 4.18179C8.99383 4.35753 8.99383 4.64245 8.81809 4.81819L6.13629 7.49999L8.81809 10.1818C8.99383 10.3575 8.99383 10.6424 8.81809 10.8182C8.64236 10.9939 8.35743 10.9939 8.1817 10.8182L5.1817 7.81819C5.09731 7.73379 5.0499 7.61933 5.0499 7.49999C5.0499 7.38064 5.09731 7.26618 5.1817 7.18179L8.1817 4.18179C8.35743 4.00605 8.64236 4.00605 8.81809 4.18179Z"
        )
    }
}


@OptIn(ExperimentalComposeWebSvgApi::class)
@Composable
fun CaretRight() {
    Svg(attrs = {
        attr("width", "24")
        attr("height", "24")
        attr("viewBox", "0 0 15 15")
        attr("fill", "none")
        attr("xmlns", "http://www.w3.org/2000/svg")
    }) {
        Path(
            attrs = {
                attr("fill-rule", "evenodd")
                attr("clip-rule", "evenodd")
                attr("fill", "currentColor")
            },
            d = "M6.18194 4.18185C6.35767 4.00611 6.6426 4.00611 6.81833 4.18185L9.81833 7.18185C9.90272 7.26624 9.95013 7.3807 9.95013 7.50005C9.95013 7.6194 9.90272 7.73386 9.81833 7.81825L6.81833 10.8182C6.6426 10.994 6.35767 10.994 6.18194 10.8182C6.0062 10.6425 6.0062 10.3576 6.18194 10.1819L8.86374 7.50005L6.18194 4.81825C6.0062 4.64251 6.0062 4.35759 6.18194 4.18185Z"
        )
    }
}

@OptIn(ExperimentalComposeWebSvgApi::class)
@Composable
fun CaretDown() {
    Svg(attrs = {
        attr("width", "15")
        attr("height", "15")
        attr("viewBox", "0 0 15 15")
        attr("fill", "none")
        attr("xmlns", "http://www.w3.org/2000/svg")
    }) {
        Path(
            attrs = {
                attr("fill-rule", "evenodd")
                attr("clip-rule", "evenodd")
                attr("fill", "currentColor")
            },
            d = "M4.18179 6.18181C4.35753 6.00608 4.64245 6.00608 4.81819 6.18181L7.49999 8.86362L10.1818 6.18181C10.3575 6.00608 10.6424 6.00608 10.8182 6.18181C10.9939 6.35755 10.9939 6.64247 10.8182 6.81821L7.81819 9.81821C7.73379 9.9026 7.61934 9.95001 7.49999 9.95001C7.38064 9.95001 7.26618 9.9026 7.18179 9.81821L4.18179 6.81821C4.00605 6.64247 4.00605 6.35755 4.18179 6.18181Z"
        )
    }
}

@OptIn(ExperimentalComposeWebSvgApi::class)
@Composable
fun OutlineLogin() {
    Svg(attrs = {
        attr("xmlns", "http://www.w3.org/2000/svg")
        classes("icon")
        attr("viewBox", "0 0 1024 1024")
        attr("width", "16")
        attr("height", "16")
    }) {
        Path(
            d = "M521.7 82c-152.5-.4-286.7 78.5-363.4 197.7-3.4 5.3.4 12.3 6.7 12.3h70.3c4.8 0 9.3-2.1 12.3-5.8 7-8.5 14.5-16.7 22.4-24.5 32.6-32.5 70.5-58.1 112.7-75.9 43.6-18.4 90-27.8 137.9-27.8 47.9 0 94.3 9.3 137.9 27.8 42.2 17.8 80.1 43.4 112.7 75.9 32.6 32.5 58.1 70.4 76 112.5C865.7 417.8 875 464.1 875 512c0 47.9-9.4 94.2-27.8 137.8-17.8 42.1-43.4 80-76 112.5s-70.5 58.1-112.7 75.9A352.8 352.8 0 0 1 520.6 866c-47.9 0-94.3-9.4-137.9-27.8A353.84 353.84 0 0 1 270 762.3c-7.9-7.9-15.3-16.1-22.4-24.5-3-3.7-7.6-5.8-12.3-5.8H165c-6.3 0-10.2 7-6.7 12.3C234.9 863.2 368.5 942 520.6 942c236.2 0 428-190.1 430.4-425.6C953.4 277.1 761.3 82.6 521.7 82zM395.02 624v-76h-314c-4.4 0-8-3.6-8-8v-56c0-4.4 3.6-8 8-8h314v-76c0-6.7 7.8-10.5 13-6.3l141.9 112a8 8 0 0 1 0 12.6l-141.9 112c-5.2 4.1-13 .4-13-6.3z"
        )
    }
}

@OptIn(ExperimentalComposeWebSvgApi::class)
@Composable
fun OutlineHelp() {
    Svg(attrs = {
        attr("xmlns", "http://www.w3.org/2000/svg")
        attr("viewBox", "0 0 24 24")
        attr("width", "24")
        attr("height", "24")
    }) {
        Path(
            d = "M11 18H13V16H11V18M12 6C9.8 6 8 7.8 8 10H10C10 8.9 10.9 8 12 8S14 8.9 14 10C14 12 11 11.8 11 15H13C13 12.8 16 12.5 16 10C16 7.8 14.2 6 12 6M19 5V19H5V5H19M19 3H5C3.9 3 3 3.9 3 5V19C3 20.1 3.9 21 5 21H19C20.1 21 21 20.1 21 19V5C21 3.9 20.1 3 19 3Z"
        )
    }
}








