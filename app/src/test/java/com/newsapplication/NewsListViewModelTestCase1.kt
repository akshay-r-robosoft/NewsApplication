//package com.newsapplication
//
//import com.newsapplication.data.remote.ApiService
//import com.newsapplication.viewmodel.NewsListViewModel
//import io.kotest.core.spec.style.DescribeSpec
//import io.kotest.matchers.shouldBe
//import io.mockk.coEvery
//import io.mockk.every
//import io.mockk.mockk
//
//class NewsListViewModelTestCase1 : DescribeSpec({
//
//    val apiService = mockk<ApiService>(relaxed = true)
//
//    lateinit var newsListViewModel: NewsListViewModel
//
//    beforeSpec {
//        newsListViewModel = NewsListViewModel(apiService)
//    }
//
//    describe("viewModelTest") {
//        context("TestFunction") {
//            it("should set apiResult to Success if api response is successful") {
//                // given
//                val page = 1
//                coEvery { apiService.getNews(any(), any(), any(), any(), any()) } returns mockk {
//                    every { isSuccessful } returns true
//                    every { body() } returns mockk()
//                }
//
//                // when
//                newsListViewModel.callNewsApi(page)
//
//                // then
//                newsListViewModel.apiResult.value shouldBe NewsListViewModel.NewsApiResult.Success(
//                    mockk()
//                )
//            }
//
//            it("should set apiResult to Failure if api response is not successful") {
//                // given
//                val page = 1
//                coEvery { apiService.getNews(any(), any(), any(), any(), any()) } returns mockk {
//                    every { isSuccessful } returns false
//                    every { body()?.status } returns "error"
//                }
//
//                // when
//                newsListViewModel.callNewsApi(page)
//
//                // then
//                newsListViewModel.apiResult.value shouldBe NewsListViewModel.NewsApiResult.Failure("error")
//            }
//
//            it("should set apiResult to Failure if api request fails") {
//                // given
//                val page = 1
//                val errorMessage = "Network error"
//                coEvery {
//                    apiService.getNews(
//                        any(),
//                        any(),
//                        any(),
//                        any(),
//                        any()
//                    )
//                } throws RuntimeException(errorMessage)
//
//                // when
//                newsListViewModel.callNewsApi(page)
//
//                // then
//                newsListViewModel.apiResult.value shouldBe NewsListViewModel.NewsApiResult.Failure(
//                    errorMessage
//                )
//            }
//
//        }
//    }
//})