package com.sample.searchnewsandroidapplication.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.sample.searchnewsandroidapplication.BR
import com.sample.searchnewsandroidapplication.R
import com.sample.searchnewsandroidapplication.databinding.ListItemSearchNewsBinding
import com.sample.searchnewsandroidapplication.model.NewsArticleItem
import com.sample.searchnewsandroidapplication.model.NewsSourceItem

class SearchNewsAdapter(
    private var newsSourceList: MutableList<NewsSourceItem>?,
    private var newsArticleList: MutableList<NewsArticleItem>?
) : RecyclerView.Adapter<SearchNewsAdapter.NewsViewHolder>() {

    val isArticleNews = MutableLiveData<Boolean>()

    fun setNewData(
        newsSourceList: MutableList<NewsSourceItem>?,
        newsArticleList: MutableList<NewsArticleItem>?
    ) {
        this.newsSourceList = newsSourceList
        this.newsArticleList = newsArticleList
        if (newsSourceList != null)
            isArticleNews.value = false
        else if (newsArticleList != null)
            isArticleNews.value = true
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        if (newsSourceList != null)
            isArticleNews.value = false
        else if (newsArticleList != null)
            isArticleNews.value = true
        return NewsViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.list_item_search_news,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return when {
            newsSourceList != null -> newsSourceList!!.size
            newsArticleList != null -> newsArticleList!!.size
            else -> 0
        }
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        if (newsSourceList == null && newsArticleList == null)
            return
        if (newsSourceList != null)
            holder.dataBinding.setVariable(BR.sourceitem, newsSourceList!![position])
        else if (newsArticleList != null)
            holder.dataBinding.setVariable(BR.articleitem, newsArticleList!![position])
        holder.dataBinding.setVariable(BR.adapter, this)
        holder.dataBinding.executePendingBindings()
    }

    class NewsViewHolder(binding: ListItemSearchNewsBinding) : RecyclerView.ViewHolder(binding.root) {
        var dataBinding: ListItemSearchNewsBinding = binding
    }
}