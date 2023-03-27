package com.musafira2z.store.utils

import com.musafira2z.store.MainMenuQuery
import com.musafira2z.store.fragment.MenuItemWithChildrenFragment

sealed interface ResponseResource<out R> {
    class Success<out T>(val data: T) : ResponseResource<T>
    class Error<out T>(val exception: Throwable, val data: T? = null) : ResponseResource<T>
    object Loading : ResponseResource<Nothing>
    object Idle : ResponseResource<Nothing>
}


fun findItem(list: List<Any>, item: String): String? {
    for (element in list) {
        if (element is MainMenuQuery.Item && element.menuItemWithChildrenFragment.category?.slug == item) {
            return element.menuItemWithChildrenFragment.category.id
        } else if (element is MenuItemWithChildrenFragment.Child && element.menuItemFragment.category?.slug == item) {
            return element.menuItemFragment.category.id
        } else if (element is MenuItemWithChildrenFragment.Child1 && element.menuItemFragment.category?.slug == item) {
            return element.menuItemFragment.category.id
        } else if (element is List<*>) {
            val found = findItem(element as List<Any>, item)
            if (found != null) {
                return found
            }
        }
    }
    return null
}