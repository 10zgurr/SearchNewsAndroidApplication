package com.sample.searchnewsandroidapplication.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sample.searchnewsandroidapplication.BuildConfig
import com.sample.searchnewsandroidapplication.model.SearchResponse
import com.sample.searchnewsandroidapplication.network.RestControllerFactory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.sample.searchnewsandroidapplication.util.Constants
import com.sample.searchnewsandroidapplication.util.Utils

open class SearchNewsViewModel : ViewModel() {

    val isLoading = MutableLiveData<Boolean>()
    val showWarning = MutableLiveData<Boolean>()

    lateinit var categoryList: Array<String>
    lateinit var languageList: Array<String>
    lateinit var countryList: Array<String>

    var categorySelectedItemPosition = 0
    var languageSelectedItemPosition = 0
    var countrySelectedItemPosition = 0

    private var searchTerm: String? = null

    private val newsList: MutableLiveData<SearchResponse> by lazy {
        MutableLiveData<SearchResponse>().also {
            getNewsFromSources()
        }
    }

    fun getNewsListResult(): MutableLiveData<SearchResponse> {
        return newsList
    }

    fun getNewsFromSources() {
        isLoading.value = true
        RestControllerFactory.getSearchNewsFactory()?.searchSourceNews(
            if (categorySelectedItemPosition == 0) null else categoryList[categorySelectedItemPosition],
            if (languageSelectedItemPosition == 0) null else languageList[languageSelectedItemPosition],
            if (countrySelectedItemPosition == 0) null else countryList[countrySelectedItemPosition],
            BuildConfig.API_KEY
        )?.enqueue(object : Callback<SearchResponse> {
            override fun onFailure(call: Call<SearchResponse>, t: Throwable) {
                setResultFailed()
            }

            override fun onResponse(call: Call<SearchResponse>, response: Response<SearchResponse>) {
                setResultRespond(response)
            }
        })
    }

    fun getAllNews() {
        if (searchTerm.isNullOrEmpty())
            showWarning.value = true
        else {
            showWarning.value = false
            isLoading.value = true
            RestControllerFactory.getSearchNewsFactory()?.searchAllNews(
                searchTerm,
                if (languageSelectedItemPosition == 0) null else languageList[languageSelectedItemPosition],
                Constants.SHORT_BY,
                Constants.DEFAULT_PAGE_SIZE,
                BuildConfig.API_KEY
            )?.enqueue(object : Callback<SearchResponse> {
                override fun onFailure(call: Call<SearchResponse>, t: Throwable) {
                    setResultFailed()
                }

                override fun onResponse(call: Call<SearchResponse>, response: Response<SearchResponse>) {
                    setResultRespond(response)
                }
            })
        }
    }

    fun getTopHeadlineNews() {
        if (searchTerm.isNullOrEmpty())
            showWarning.value = true
        else {
            showWarning.value = false
            isLoading.value = true
            RestControllerFactory.getSearchNewsFactory()?.searchTopHeadlinesNews(
                searchTerm,
                if (categorySelectedItemPosition == 0) null else categoryList[categorySelectedItemPosition],
                if (countrySelectedItemPosition == 0) null else countryList[countrySelectedItemPosition],
                Constants.DEFAULT_PAGE_SIZE,
                BuildConfig.API_KEY
            )?.enqueue(object : Callback<SearchResponse> {
                override fun onFailure(call: Call<SearchResponse>, t: Throwable) {
                    setResultFailed()
                }

                override fun onResponse(call: Call<SearchResponse>, response: Response<SearchResponse>) {
                    setResultRespond(response)
                }
            })
        }
    }

    private fun setResultFailed() {
        isLoading.value = false
        newsList.value = SearchResponse(Constants.CONNECTION_STATUS_ERROR, null, null, null, Constants.CONNECTION_ERROR)
    }

    private fun setResultRespond(response: Response<SearchResponse>) {
        isLoading.value = false
        if (response.isSuccessful) {
            if (response.code() == Constants.CONNECTION_STATUS_CODE_OK) {
                if (response.body() != null && response.body()!!.sources != null && response.body()!!.sources!!.size > 0)
                    newsList.value = response.body()!!
                else if (response.body() != null && response.body()!!.articles != null && response.body()!!.articles!!.size > 0)
                    newsList.value = response.body()!!
                else
                    newsList.value = SearchResponse(
                        Constants.CONNECTION_STATUS_ERROR,
                        null,
                        null,
                        null,
                        Constants.NO_RESULT
                    )
            } else
                newsList.value = SearchResponse(
                    Constants.CONNECTION_STATUS_ERROR,
                    null,
                    null,
                    null,
                    Utils.getErrorMessage(response.errorBody())
                )
        } else
            newsList.value = SearchResponse(
                Constants.CONNECTION_STATUS_ERROR,
                null,
                null,
                null,
                Utils.getErrorMessage(response.errorBody())
            )
    }

    fun updateSearchTerm(charSequence: CharSequence) {
        this.searchTerm = charSequence.toString()
    }
}