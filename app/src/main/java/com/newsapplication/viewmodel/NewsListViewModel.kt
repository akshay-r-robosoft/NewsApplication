package com.newsapplication.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.newsapplication.BuildConfig
import com.newsapplication.base.BaseViewModel
import com.newsapplication.data.model.NewsModel
import com.newsapplication.data.remote.ApiService
import com.newsapplication.util.Constant.CATEGORY
import com.newsapplication.util.Constant.COUNTRY
import com.newsapplication.util.Constant.PAGE_SIZE
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class NewsListViewModel(private val apiService: ApiService) : BaseViewModel() {

    private val _apiResult = MutableLiveData<NewsApiResult>()
    val apiResult: LiveData<NewsApiResult>
        get() = _apiResult

    fun callNewsApi(page: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                apiService.getNews(COUNTRY, CATEGORY, BuildConfig.NEWS_API_KEY, PAGE_SIZE, page)
            }.onSuccess {
                withContext(Dispatchers.Main) {
                    if (it.isSuccessful) {
                        _apiResult.postValue(it.body()?.let { it1 -> NewsApiResult.Success(it1) })
                    } else {
                        _apiResult.postValue(
                            it.body()?.status?.let { it1 -> NewsApiResult.Failure(it1) })
                    }
                }
            }.onFailure {
                withContext(Dispatchers.Main) {
                    _apiResult.postValue(it.message?.let { it1 -> NewsApiResult.Failure(it1) })
                }
            }
        }
    }

    sealed class NewsApiResult {
        data class Success(val data: NewsModel) : NewsApiResult()
        data class Failure(val exception: String) : NewsApiResult()
    }
}