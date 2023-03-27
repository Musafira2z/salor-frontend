package com.musafira2z.store.web.ui.utils

import androidx.compose.web.attributes.SelectAttrsScope
import org.jetbrains.compose.web.attributes.AttrsScope
import org.w3c.dom.*
import org.w3c.dom.svg.SVGElement

fun AttrsScope<HTMLDivElement>.toClasses(clas: String) {
    val args = clas.trim().replace("\\s+".toRegex(), " ").split(" ").toTypedArray()
    classes(*args)
}

fun SelectAttrsScope.toClasses(clas: String) {
    val args = clas.trim().replace("\\s+".toRegex(), " ").split(" ").toTypedArray()
    classes(*args)
}

fun AttrsScope<HTMLImageElement>.toClasses(clas: String) {
    val args = clas.trim().replace("\\s+".toRegex(), " ").split(" ").toTypedArray()
    classes(*args)
}

fun AttrsScope<HTMLAnchorElement>.toClasses(clas: String) {
    val args = clas.trim().replace("\\s+".toRegex(), " ").split(" ").toTypedArray()
    classes(*args)
}

fun AttrsScope<HTMLParagraphElement>.toClasses(clas: String) {
    val args = clas.trim().replace("\\s+".toRegex(), " ").split(" ").toTypedArray()
    classes(*args)
}

fun AttrsScope<HTMLSpanElement>.toClasses(clas: String) {
    val args = clas.trim().replace("\\s+".toRegex(), " ").split(" ").toTypedArray()
    classes(*args)
}


fun AttrsScope<SVGElement>.toClasses(clas: String) {
    val args = clas.trim().replace("\\s+".toRegex(), " ").split(" ").toTypedArray()
    classes(*args)
}

fun AttrsScope<HTMLButtonElement>.toClasses(clas: String) {
    val args = clas.trim().replace("\\s+".toRegex(), " ").split(" ").toTypedArray()
    classes(*args)
}