package com.musafira2z.store.web.ui.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import dev.petuska.kmdc.button.MDCButton
import dev.petuska.kmdc.button.MDCButtonType
import dev.petuska.kmdc.drawer.*
import dev.petuska.kmdc.list.MDCNavList
import dev.petuska.kmdc.list.item.Graphic
import dev.petuska.kmdc.list.item.ListItem
import dev.petuska.kmdc.top.app.bar.*
import dev.petuska.kmdcx.icons.MDCIcon
import dev.petuska.kmdcx.icons.mdcIcon
import org.jetbrains.compose.web.css.Position
import org.jetbrains.compose.web.css.position
import org.jetbrains.compose.web.dom.Text

private class MDCTopAppBarVM {
    var type by mutableStateOf(MDCTopAppBarType.Default)
    var drawerType by mutableStateOf(MDCDrawerType.Dismissible)
    var open by mutableStateOf(false)
}

@Composable
fun HomeScreen() {
    val viewModel = MDCTopAppBarVM()
    MDCTopAppBar(type = viewModel.type) {
        TopAppBar(attrs = {
            registerEvents()
            onNav { viewModel.open = !viewModel.open }
            style {
                position(Position.Relative)
                if (viewModel.open) variable("--mdc-theme-primary", "#494451")
            }
        }) {
            Row {
                Section(align = MDCTopAppBarSectionAlign.Start) {
                    NavButton(attrs = { mdcIcon() }) { Text(if (viewModel.open) MDCIcon.Close.type else MDCIcon.Menu.type) }
                    Title("Home")
                }
                Section(
                    align = MDCTopAppBarSectionAlign.End,
                    attrs = {
                        attr("role", "toolbar")
                    }
                ) {
                    ActionButton(attrs = { mdcIcon() }) { Text(MDCIcon.Share.type) }
                    ActionButton(attrs = { mdcIcon() }) { Text(MDCIcon.Delete.type) }
                    ActionButton(attrs = { mdcIcon() }) { Text(MDCIcon.MoreVert.type) }
                }
            }
        }
        Main {
            MDCDrawer(
                open = viewModel.open,
                type = viewModel.drawerType,
                attrs = {
                    registerEvents()
                    onOpened { viewModel.open = true }
                    onClosed { viewModel.open = false }
                    style {
                        property("height", "fit-content")
                    }
                }
            ) {
                Header {
                    MDCButton(type = MDCButtonType.Raised, attrs = {
                        style {
                            property("width", "100%")
                        }
                    }) {
                        Text("Offers")
                    }
                }
                Content {
                    MDCNavList(
                        wrapFocus = true,
                    ) {
                        ListItem(selected = true) {
                            Graphic(attrs = { mdcIcon() }) { Text(MDCIcon.Inbox.type) }
                            Text("Inbox")
                        }
                        ListItem {
                            Graphic(attrs = { mdcIcon() }) { Text(MDCIcon.Send.type) }
                            Text("Outgoing")
                        }
                        ListItem {
                            Graphic(attrs = { mdcIcon() }) { Text(MDCIcon.Drafts.type) }
                            Text("Drafts")
                        }
                    }
                }
            }
        }
    }
}

private fun MDCTopAppBarAttrsScope.registerEvents() {
    onNav { console.log("MDCTopAppBar#onNav", it.detail) }
}

private fun MDCDrawerAttrsScope.registerEvents() {
    onOpened { console.log("MDCDrawer#onOpened", it.detail) }
    onClosed { console.log("MDCDrawer#onClosed", it.detail) }
}