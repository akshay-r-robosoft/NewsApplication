package com.newsapplication.util


object Constant {
    const val CATEGORY = "technology"
    const val COUNTRY = "in"
    const val PAGE_SIZE = 10
    const val ARTICLE_DATA = "ARTICLE_DATA"
    enum class NewsTypeEnum {
        TOP_NEWS, POPULAR_NEWS, NEWS_IN_DETAILS, LOADING
    }

    const val TOP_NEWS = 1
    const val POPULAR_NEWS = 2
    const val NEWS_IN_DETAILS = 3
    const val LOADING = 4
}