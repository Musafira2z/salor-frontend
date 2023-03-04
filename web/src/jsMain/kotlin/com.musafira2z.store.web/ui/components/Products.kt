package com.musafira2z.store.web.ui.components

import androidx.compose.runtime.Composable
import com.musafira2z.store.ProductCollectionQuery
import com.musafira2z.store.web.ui.utils.toClasses
import org.jetbrains.compose.web.dom.Div

@Composable
fun Products(products: ProductCollectionQuery.Data) {
    Div(attrs = {
        classes("py-10")
    }) {
        Div(attrs = {
            toClasses("grid grid-cols-12 gap-5")
        }) {
            products.products?.edges?.forEach { _product ->
                _product.node.productDetailsFragment.variants?.forEach {
                    Product(product = _product, variant = it)
                }
            }
        }
    }
}

