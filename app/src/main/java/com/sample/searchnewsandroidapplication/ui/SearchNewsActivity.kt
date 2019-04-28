package com.sample.searchnewsandroidapplication.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.sample.searchnewsandroidapplication.R
import com.sample.searchnewsandroidapplication.databinding.ActivitySearchNewsBinding
import com.sample.searchnewsandroidapplication.util.Constants
import com.sample.searchnewsandroidapplication.viewmodel.SearchNewsViewModel
import kotlinx.android.synthetic.main.activity_search_news.*

class SearchNewsActivity : AppCompatActivity() {

    companion object {
        private val TAG: String = SearchNewsActivity::class.java.simpleName
    }

    private lateinit var newsAdapter: SearchNewsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val searchNewsViewModel: SearchNewsViewModel =
            ViewModelProviders.of(this).get(SearchNewsViewModel()::class.java)
        searchNewsViewModel.categoryList = resources.getStringArray(R.array.category)
        searchNewsViewModel.languageList = resources.getStringArray(R.array.language)
        searchNewsViewModel.countryList = resources.getStringArray(R.array.country)

        val binding = ActivitySearchNewsBinding.inflate(layoutInflater)
        binding.viewModel = searchNewsViewModel
        binding.lifecycleOwner = this
        setContentView(binding.root)

        spinner_category.adapter =
            ArrayAdapter(
                this,
                R.layout.support_simple_spinner_dropdown_item,
                resources.getStringArray(R.array.category)
            )
        spinner_language.adapter =
            ArrayAdapter(
                this,
                R.layout.support_simple_spinner_dropdown_item,
                resources.getStringArray(R.array.language)
            )
        spinner_country.adapter =
            ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, resources.getStringArray(R.array.country))

        recycler_view_news.layoutManager = LinearLayoutManager(this)
        newsAdapter = SearchNewsAdapter(null, null)
        recycler_view_news.adapter = newsAdapter

        // observe new search data
        searchNewsViewModel.getNewsListResult().observe(this, Observer { searchResult ->
            if (searchResult != null) {
                if (searchResult.status == Constants.CONNECTION_STATUS_OK) {
                    recycler_view_news.smoothScrollToPosition(0)
                    newsAdapter.setNewData(searchResult.sources, searchResult.articles)
                } else {
                    Toast.makeText(this, searchResult.errorMessage, Toast.LENGTH_SHORT).show()
                    newsAdapter.setNewData(null, null)
                }
                newsAdapter.notifyDataSetChanged()
            }
        })

        // obverse empty input case
        searchNewsViewModel.showWarning.observe(this, Observer { showWarning ->
            if (showWarning)
                Toast.makeText(this, getString(R.string.empty_input_desc), Toast.LENGTH_SHORT).show()
        })
    }
}
