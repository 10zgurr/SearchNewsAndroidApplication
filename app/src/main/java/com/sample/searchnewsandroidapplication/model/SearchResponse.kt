package com.sample.searchnewsandroidapplication.model

data class SearchResponse(
    val status: String?,
    val totalResults: Int?,
    val articles: MutableList<NewsArticleItem>?,
    val sources: MutableList<NewsSourceItem>?,
    val errorMessage: String?
)