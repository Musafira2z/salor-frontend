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
    ;

    override val matcher: RouteMatcher = RouteMatcher.create(routeFormat)
}