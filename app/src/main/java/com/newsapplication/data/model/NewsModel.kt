package com.newsapplication.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.newsapplication.util.Constant
import kotlinx.parcelize.Parcelize

data class NewsModel(
    @SerializedName("articles")
    var articles: MutableList<Article> = mutableListOf(),
    @SerializedName("status")
    var status: String? = "",
    @SerializedName("totalResults")
    var totalResults: Int = 0
)
@Parcelize
data class Article(
    @SerializedName("author")
    var author: String? = "",
    @SerializedName("content")
    var content: String? = "",
    @SerializedName("description")
    var description: String? = "",
    @SerializedName("publishedAt")
    var publishedAt: String? = "",
    @SerializedName("source")
    var source: Source = Source(),
    @SerializedName("title")
    var title: String? = "",
    @SerializedName("url")
    var url: String? = "",
    @SerializedName("urlToImage")
    var urlToImage: String? = "",
    var wantsToShowHeader: Boolean = false,
    var newsTypeEnum: Constant.NewsTypeEnum = Constant.NewsTypeEnum.POPULAR_NEWS
): Parcelable
@Parcelize
data class Source(
    @SerializedName("id")
    var id: String? = "",
    @SerializedName("name")
    var name: String? = ""
): Parcelable