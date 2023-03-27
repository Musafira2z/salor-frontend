package com.musafira2z.store.data

import com.musafira2z.store.MainMenuQuery
import com.musafira2z.store.fragment.MenuItemWithChildrenFragment

data class CategoryItem(
    val slug: String,
    val id: String,
    val title: String,
    val child: List<CategoryItem>,
    val image: String?
)

fun List<MainMenuQuery.Item>.categoryItems(): List<CategoryItem> {
    val cats = this.map {
        println(it.menuItemWithChildrenFragment.name)
        it.toCategoryItem()
    }
    return cats
}


fun List<MenuItemWithChildrenFragment.Child>?.categoryItems(): List<CategoryItem> {
    return this?.map {
        println("   " + it.menuItemFragment.name)
        it.toCategoryItem()
    } ?: emptyList()
}


fun List<MenuItemWithChildrenFragment.Child1>?.categoryItems(): List<CategoryItem> {
    return this?.map {
        println("       " + it.menuItemFragment.name)
        it.toCategoryItem()
    } ?: emptyList()
}

fun MainMenuQuery.Item.toCategoryItem(): CategoryItem {

    val child = this.menuItemWithChildrenFragment.children

    return CategoryItem(
        slug = this.menuItemWithChildrenFragment.category?.slug ?: "",
        id = this.menuItemWithChildrenFragment.category?.id ?: "",
        title = this.menuItemWithChildrenFragment.name,
        child = child?.categoryItems() ?: emptyList(),
        image = this.menuItemWithChildrenFragment.category?.backgroundImage?.url ?: ""
    )
}

fun MenuItemWithChildrenFragment.Child.toCategoryItem(): CategoryItem {
    return CategoryItem(
        slug = this.menuItemFragment.category?.slug ?: "",
        id = this.menuItemFragment.category?.id ?: "",
        title = this.menuItemFragment.name,
        child = this.children?.categoryItems() ?: emptyList(),
        image = this.menuItemFragment.category?.backgroundImage?.url ?: ""
    )
}

fun MenuItemWithChildrenFragment.Child1.toCategoryItem(): CategoryItem {
    return CategoryItem(
        slug = this.menuItemFragment.category!!.slug,
        id = this.menuItemFragment.category.id,
        title = this.menuItemFragment.name,
        child = emptyList(),
        image = this.menuItemFragment.category.backgroundImage?.url
    )
}