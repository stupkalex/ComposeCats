package com.example.composecats.core.navigation

sealed class Screen(
    val route: String
) {

    object Feed : Screen(ROUTE_NEWS_FEED)

    object Home : Screen(ROUTE_HOME)

    object Detail : Screen(ROUTE_DETAIL) {

        private const val ROUTE_FOR_ARGS = "detail"

        fun getRouteWithArgs(catId: String): String {
            return "$ROUTE_FOR_ARGS/${catId}"
        }
    }

    companion object {

        const val KEY_CAT_ID = "cat_id"

        const val ROUTE_DETAIL = "detail/{$KEY_CAT_ID}"
        const val ROUTE_NEWS_FEED = "news_feed"
        const val ROUTE_HOME = "home"
    }
}