package com.sample.searchnewsandroidapplication.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import com.sample.searchnewsandroidapplication.BR
import com.sample.searchnewsandroidapplication.databinding.ActivityNewsDetailBinding
import com.sample.searchnewsandroidapplication.model.NewsArticleItem
import com.sample.searchnewsandroidapplication.model.NewsSourceItem

class NewsDetailActivity : AppCompatActivity() {

    val isArticleNews = MutableLiveData<Boolean>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityNewsDetailBinding.inflate(layoutInflater)
        binding.lifecycleOwner = this
        setContentView(binding.root)

        val receivedObject = intent?.extras?.get("news_item")

        if (receivedObject is NewsSourceItem) {
            isArticleNews.value = false
            binding.setVariable(BR.sourceDetail, receivedObject)
            binding.setVariable(BR.articleDetail, NewsArticleItem())
        } else if (receivedObject is NewsArticleItem) {
            isArticleNews.value = true
            binding.setVariable(BR.sourceDetail, NewsSourceItem())
            binding.setVariable(BR.articleDetail, receivedObject)
        }
        binding.setVariable(BR.activity, this)
        binding.executePendingBindings()
    }
}
