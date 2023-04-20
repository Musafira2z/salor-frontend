package com.musafira2z.store.web.ui.router

import com.copperleaf.ballast.navigation.routing.Route
import com.copperleaf.ballast.navigation.routing.RouteAnnotation
import com.copperleaf.ballast.navigation.routing.RouteMatcher

enum class WebPage(
    routeFormat: String,
    override val annotations: Set<RouteAnnotation> = emptySet(),
) : Route {
    HomePage("/"),
    Category("/category/{slug}"),
    Search("/search?filter={?}"),
    ProductDetails("/product/{slug}?variantId={?}"),
    Page("/page/{slug}"),
    Checkout("/checkout"),
    OrderSuccess("/complete/{slug}"),
    PasswordReset("/reset-password?email={?}&token={?}"),
    Profile("/dashboard/profile"),
    Orders("/dashboard/orders"),
    OrderDetails("/dashboard/order/{order}"),
    ;

    override val matcher: RouteMatcher = RouteMatcher.create(routeFormat)
}