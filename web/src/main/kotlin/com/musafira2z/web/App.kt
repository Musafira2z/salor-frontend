package com.musafira2z.web

import com.musafira2z.store.di.appModule
import com.musafira2z.store.ui.home.HomeContract
import com.musafira2z.store.ui.home.HomeViewModel
import io.kvision.*
import io.kvision.core.*
import io.kvision.html.button
import io.kvision.html.div
import io.kvision.html.h1
import io.kvision.html.image
import io.kvision.panel.hPanel
import io.kvision.panel.root
import io.kvision.panel.vPanel
import io.kvision.routing.KVRouter
import io.kvision.state.ObservableValue
import io.kvision.utils.px
import io.kvision.utils.vw
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.context.startKoin
import org.koin.core.parameter.parametersOf
import kotlin.coroutines.CoroutineContext

class App : Application(), KoinComponent, CoroutineScope {

    val parentJob = Job()
    val handler = CoroutineExceptionHandler { _, exception ->
        println("Caught $exception")
    }

    // CoroutineScope
    val coroutineScope = CoroutineScope(Dispatchers.Default + parentJob + handler)

    private val homeViewModel: HomeViewModel by inject { parametersOf(coroutineScope) }
    private val routing: KVRouter by inject()

    val categories = listOf("Electronics", "Fashion", "Home", "Sports")

    val selectedCategory = ObservableValue(categories[0])

    val products = listOf(
        Product("Product 1", "$100", "https://picsum.photos/id/1/200/200"),
        Product("Product 2", "$200", "https://picsum.photos/id/2/200/200"),
        Product("Product 3", "$300", "https://picsum.photos/id/3/200/200"),
        Product("Product 4", "$400", "https://picsum.photos/id/4/200/200"),
    )

    val cartItems = mutableListOf<Product>()

    val cartVisible = ObservableValue(false)

    override fun start() {
        root("kvapp") {
            homeViewModel.trySend(HomeContract.Inputs.Initialize)

            vPanel(justify = JustifyContent.CENTER) {
                padding = 30.px
                width = 100.vw

                h1("Welcome to our eCommerce store!") {
                    textAlign = TextAlign.CENTER
                    marginBottom = 30.px
                }

                hPanel(justify = JustifyContent.CENTER) {
                    alignItems = AlignItems.CENTER
                    marginBottom = 30.px

                    for (category in categories) {
                        button(category) {
                            onEvent {
                                click?.let {
                                    selectedCategory.value = category
                                }
                            }
                        }
                    }
                }

                hPanel(justify = JustifyContent.CENTER) {
                    flexWrap = FlexWrap.WRAP

                    for (product in products) {
                        if (product.category == selectedCategory.value) {
                            vPanel(justify = JustifyContent.CENTER) {
                                width = 300.px
                                borderRadius = 10.px

                                image(product.image) {
                                    width = 100.px
                                    height = 200.px
                                    borderRadius = CssSize(10, UNIT.px)
                                }

                                div(product.name) {
                                    fontWeight = FontWeight.BOLD
                                    fontSize = 20.px
                                    padding = CssSize(10, UNIT.px)
                                    textAlign = TextAlign.CENTER
                                }

                                div(product.price) {
                                    fontWeight = FontWeight.BOLD
                                    fontSize = 16.px
                                    textAlign = TextAlign.CENTER
                                }

                                button("Add to Cart") {
                                    padding = CssSize(10, UNIT.px)
                                    width = 100.px
                                    color = Color("#fff")

                                    onClick {
                                        println("Default button clicked")
                                        cartItems.add(product)
                                        println(cartItems)
                                    }
                                }
                            }
                        }
                    }
                }

                vPanel(justify = JustifyContent.FLEXEND) {
                    width = 100.px
                    alignItems = AlignItems.FLEXEND
                    position = Position.FIXED
                    right = 0.px
                    bottom = 0.px
                    zIndex = 1

                    button("Cart (${cartItems.size})") {
                        width = 100.px
                        color = Color("#fff")
                        padding = CssSize(15, UNIT.px)
                        cursor = Cursor.POINTER

                        onEvent {
                            click?.let {
                                cartVisible.value = !cartVisible.value
                            }
                        }
                    }

                    vPanel(justify = JustifyContent.FLEXEND) {
                        width = 300.px
                        padding = 20.px
                        display = Display.FLEX
                        flexDirection = FlexDirection.COLUMN

                        if (cartItems.isEmpty()) {
                            div("Your cart is empty.")
                        } else {
                            for (cartItem in cartItems) {
                                div("${cartItem.name} - ${cartItem.price}")
                            }

                            button("Checkout") {
                                marginTop = 20.px
                                width = 100.px
                                borderRadius = 10.px
                            }
                        }
                    }

                    if (cartVisible.value) {
                        setStyle("width", "300px")
                        setStyle("transition", "width 0.5s")
                    } else {
                        setStyle("width", "50px")
                        setStyle("transition", "width 0.5s")
                    }
                }
            }
        }
    }

    override val coroutineContext: CoroutineContext
        get() = parentJob
}

fun main() {
    startKoin {
        modules(appModule)
    }
    startApplication(
        ::App,
        module.hot,
        BootstrapModule,
        BootstrapCssModule,
        TomSelectModule,
        ImaskModule,
        ToastifyModule,
        FontAwesomeModule,
        CoreModule
    )
}

data class Product(
    val name: String,
    val price: String,
    val image: String,
    val category: String = "Electronics"
)