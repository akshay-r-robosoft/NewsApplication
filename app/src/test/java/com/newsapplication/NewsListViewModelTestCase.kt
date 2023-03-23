package com.newsapplication

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.newsapplication.data.model.NewsModel
import com.newsapplication.data.remote.ApiService
import com.newsapplication.viewmodel.NewsListViewModel
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.whenever
import junit.framework.TestCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.Response


@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class NewsListViewModelTestCase : TestCase() {

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @Mock
    private lateinit var apiService: ApiService

    @Mock
    private lateinit var apiResult: NewsListViewModel.NewsApiResult

    @Mock
    private lateinit var newsResponseObserver: Observer<NewsListViewModel.NewsApiResult>

    @Before
    public override fun setUp() {
        super.setUp()
    }

    @Test
    fun testGetNewsListResponse() {
        testCoroutineRule.runBlockingTest {
            whenever(apiService.getNews(
                "in",
                "technology", "1221312",
                10, 1)) doReturn (Response.success(
                NewsModel(articles = mutableListOf(), status = "ok", totalResults = 100)
            ))
            val viewModel = NewsListViewModel(apiService)
            viewModel.apiResult.observeForever(newsResponseObserver)
            viewModel.callNewsApi(1)
            val result = viewModel.apiResult.value
            assertEquals(result, NewsModel(articles = mutableListOf(), status = "ok", totalResults = 100))
            viewModel.apiResult.removeObserver(newsResponseObserver)
        }
    }

    @After
    public override fun tearDown() {}
}