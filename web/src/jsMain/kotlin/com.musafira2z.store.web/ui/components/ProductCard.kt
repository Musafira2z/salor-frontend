package com.musafira2z.store.web.ui.components

import androidx.compose.runtime.Composable
import com.musafira2z.store.ProductCollectionQuery
import com.musafira2z.store.fragment.MenuItemWithChildrenFragmentProducts
import com.musafira2z.store.fragment.ProductDetailsFragment
import com.musafira2z.store.web.ui.utils.toFormatPrice
import com.musafira2z.store.web.ui.utils.toUnDiscountFormatPrice
import org.jetbrains.compose.web.dom.*

@Composable
fun Product(
    product: ProductCollectionQuery.Edge,
    variant: ProductDetailsFragment.Variant,
    onProductDetails: (productSlug: String, variantId: String?) -> Unit,
    onAdd: (String) -> Unit
) {
    val productNode = product.node.productDetailsFragment
    Div(attrs = {
        classes(
            "col-span-12",
            "sm:col-span-6",
            "md:col-span-6",
            "lg:col-span-3",
            "border",
            "p-5",
            "rounded-lg",
            "hover:shadow-green-200",
            "relative",
            "hover:no-underline",
            "hover:text-slate-700",
            "shadow-lg",
            "hover:shadow-xl",
            "hover:transform",
            "hover:scale-105",
            "duration-300",
            "h-fit"
        )
        onClick {
            onProductDetails(
                product.node.productDetailsFragment.slug,
                variant.productVariantDetailsFragment.id
            )
        }
    }) {
        variant.productVariantDetailsFragment.pricing?.onSale?.let {
            if (it) {
                Div(attrs = {
                    classes("absolute", "right-0", "pr-1")
                }) {
                    Button(attrs = {
                        classes(
                            "bg-gradient-to-r",
                            "from-yellow-300",
                            "via-red-400",
                            "to-red-500",
                            "rounded-md",
                            "py-1",
                            "px-2",
                            "text-slate-50",
                            "font-bold"
                        )
                    }) {
                        Text("On Sold")
                    }
                }
            }
        }

        Div {
            Div(attrs = {
                classes("flex", "justify-center", "w-full", "aspect-square")
            }) {
                val image = productNode.thumbnail?.imageFragment?.url?.replace(
                    "http://localhost:8000",
                    "https://musafirtd.sgp1.cdn.digitaloceanspaces.com"
                )
                Img(
                    src = image
                        ?: "",
                    alt = "",
                )
            }
            H1(attrs = {
                classes("text-lg", "py-7", "truncate", "hover:text-clip")
            }) {
                Text(productNode.name)
            }
        }
        Div {
            variant.productVariantDetailsFragment.pricing?.price?.gross?.priceFragment.toUnDiscountFormatPrice(
                variant.productVariantDetailsFragment.pricing?.price?.gross?.priceFragment?.amount
            )?.let {
                Div(attrs = {
                    classes("flex", "justify-end")
                }) {
                    P(attrs = {
                        classes("text-red-500", "font-bold", "line-through")
                    }) {
                        Text(it)
                    }
                }
            }

            Div(attrs = {
                classes("flex", "justify-between", "font-bold", "pb-4")
            }) {
                P {
                    val attributes = variant.productVariantDetailsFragment.attributes.map {
                        it.selectedAttributeDetailsFragment.values.mapNotNull { it.name }
                    }.flatten().joinToString("| ")
                    Text(attributes)
                }
                P(attrs = {
                    classes("text-green-500")
                }) {
                    Text("${variant.productVariantDetailsFragment.pricing?.price?.gross?.priceFragment?.toFormatPrice()}")
                }
            }
            Div {
                AddToCartButton() {
                    onAdd(variant.productVariantDetailsFragment.id)
                }
            }
        }
    }
}

@Composable
fun AddToCartButton(
    onAdd: () -> Unit
) {
    Button(attrs = {
        classes(
            "border-2",
            "border-green-500",
            "rounded-lg",
            "text-green-500",
            "hover:bg-green-500",
            "hover:text-slate-50",
            "font-bold",
            "hover:duration-500",
            "duration-500",
            "w-full",
            "py-3",
            "px-6"
        )
        onClick {
            onAdd()
        }
    }) {
        Text("Add to Cart")
    }
}


@Composable
fun Product(
    product: MenuItemWithChildrenFragmentProducts.Edge,
    variant: ProductDetailsFragment.Variant,
    onProductDetails: (productSlug: String, variantId: String?) -> Unit,
    onAdd: (String) -> Unit
) {
    val productNode = product.node.productDetailsFragment
    Div(attrs = {
        classes(
            "col-span-12",
            "sm:col-span-6",
            "md:col-span-6",
            "lg:col-span-3",
            "border",
            "p-5",
            "rounded-lg",
            "hover:shadow-green-200",
            "relative",
            "hover:no-underline",
            "hover:text-slate-700",
            "shadow-lg",
            "hover:shadow-xl",
            "hover:transform",
            "hover:scale-105",
            "duration-300",
            "h-fit"
        )
        onClick {
            onProductDetails(
                product.node.productDetailsFragment.slug,
                variant.productVariantDetailsFragment.id
            )
        }
    }) {
        variant.productVariantDetailsFragment.pricing?.onSale?.let {
            if (it) {
                Div(attrs = {
                    classes("absolute", "right-0", "pr-1")
                }) {
                    Button(attrs = {
                        classes(
                            "bg-gradient-to-r",
                            "from-yellow-300",
                            "via-red-400",
                            "to-red-500",
                            "rounded-md",
                            "py-1",
                            "px-2",
                            "text-slate-50",
                            "font-bold"
                        )
                    }) {
                        Text("On Sold")
                    }
                }
            }
        }

        Div {
            Div(attrs = {
                classes("flex", "justify-center", "w-full", "aspect-square")
            }) {
                val image = productNode.thumbnail?.imageFragment?.url?.replace(
                    "http://localhost:8000",
                    "https://musafirtd.sgp1.cdn.digitaloceanspaces.com"
                )
                Img(
                    src = image
                        ?: "",
                    alt = "",
                )
            }
            H1(attrs = {
                classes("text-lg", "py-7", "truncate", "hover:text-clip")
            }) {
                Text(productNode.name)
            }
        }
        Div {
            variant.productVariantDetailsFragment.pricing?.price?.gross?.priceFragment.toUnDiscountFormatPrice(
                variant.productVariantDetailsFragment.pricing?.price?.gross?.priceFragment?.amount
            )?.let {
                Div(attrs = {
                    classes("flex", "justify-end")
                }) {
                    P(attrs = {
                        classes("text-red-500", "font-bold", "line-through")
                    }) {
                        Text("৳${it}")
                    }
                }
            }

            Div(attrs = {
                classes("flex", "justify-between", "font-bold", "pb-4")
            }) {
                P {
                    val attributes = variant.productVariantDetailsFragment.attributes.map {
                        it.selectedAttributeDetailsFragment.values.mapNotNull { it.name }
                    }.flatten().joinToString("| ")
                    Text(attributes)
                }
                P(attrs = {
                    classes("text-green-500")
                }) {
                    Text("৳${variant.productVariantDetailsFragment.pricing?.price?.gross?.priceFragment?.toFormatPrice()}")
                }
            }
            Div {
                AddToCartButton() {
                    onAdd(variant.productVariantDetailsFragment.id)
                }
            }
        }
    }
}