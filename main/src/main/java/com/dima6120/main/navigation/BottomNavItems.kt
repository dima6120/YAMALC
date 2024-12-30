package com.dima6120.main.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.dima6120.anime_lists_api.AnimeListsRoute
import com.dima6120.core_api.navigation.Route
import com.dima6120.main.R
import com.dima6120.profile_api.ProfileRoute
import com.dima6120.search_api.SearchRoute

internal sealed class BottomNavItems(
    @StringRes val title: Int,
    @DrawableRes val icon: Int,
    val route: Route
) {

    data object Search: BottomNavItems(
        title = R.string.searchItemTitle,
        icon = R.drawable.ic_search_item,
        route = SearchRoute
    )

    data object MyLists: BottomNavItems(
        title = R.string.myListsItemTitle,
        icon = R.drawable.ic_my_lists_item,
        route = AnimeListsRoute
    )

    data object Profile: BottomNavItems(
        title = R.string.profileItemTitle,
        icon = R.drawable.ic_profile_item,
        route = ProfileRoute
    )

    companion object {

        val items = listOf(Search, MyLists, Profile)
    }
}