package com.newsapplication.ui.activity

import android.os.Build
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.newsapplication.R
import com.newsapplication.base.BaseAppCompatActivity
import com.newsapplication.data.model.Article
import com.newsapplication.databinding.ActivityNewsDetailsBinding
import com.newsapplication.ui.adapter.NewsListAdapter
import com.newsapplication.util.Constant
import com.newsapplication.util.Constant.ARTICLE_DATA
import com.newsapplication.util.PaginationListener
import com.newsapplication.util.showToastMessage
import com.newsapplication.viewmodel.NewsListViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class NewsDetailActivity : BaseAppCompatActivity<ActivityNewsDetailsBinding, NewsListViewModel>(),
        (Article) -> Unit {
    private var currentPage = 1
    private var articleList: MutableList<Article> = mutableListOf()
    private var adapter = NewsListAdapter(articleList, this)
    private var isLoading = false
    private var isLastPage = false
    private lateinit var paginationListener: PaginationListener
    private var article: Article? = null
    override val viewModel: NewsListViewModel by viewModel()

    override fun getLayoutResId(): Int = R.layout.activity_news_details

    override fun initialize() {
        super.initialize()
        article = if (Build.VERSION.SDK_INT >= 33) {
            intent.getParcelableExtra(ARTICLE_DATA, Article::class.java)
        } else {
            intent.getParcelableExtra(ARTICLE_DATA)
        }
        article?.let {
            binding.toolBar.txtUrl.text = it.url
            it.newsTypeEnum = Constant.NewsTypeEnum.NEWS_IN_DETAILS
            articleList.add(it)
        }
        binding.toolBar.imgBack.setOnClickListener { finish() }
        viewModel.callNewsApi(currentPage)
        setAdapter()
    }

    private fun setAdapter() {
        binding.recyclerView.adapter = adapter
        paginationListener =
            object : PaginationListener(binding.recyclerView.layoutManager as LinearLayoutManager) {
                override fun loadMoreItems() {
                    isLoadingVisible = true
                    adapter.addLoader()
                    viewModel.callNewsApi(currentPage)
                }

                override var isLastPageVisible: Boolean = isLastPage
                override var isLoadingVisible: Boolean = isLoading
            }
        binding.recyclerView.addOnScrollListener(paginationListener)
    }

    override fun initializeObservers(viewModel: NewsListViewModel) {
        super.initializeObservers(viewModel)
        viewModel.apiResult.observe(this) { result ->
            when (result) {
                is NewsListViewModel.NewsApiResult.Success -> {
                    if (paginationListener.isLoadingVisible) {
                        paginationListener.isLoadingVisible = false
                        adapter.removeLoader()
                    }
                    if (result.data.status.equals("ok", true)) {
                        val articles = result.data.articles
                        if (articles.size > 1 && currentPage == 1) {
                            articles[0].wantsToShowHeader = true
                        }
                        articleList.addAll(articles)
                        currentPage += 1
                    } else {
                        paginationListener.isLoadingVisible = false
                        adapter.removeLoader()
                        paginationListener.isLastPageVisible = true
                    }
                    if (articleList.size == result.data.totalResults) {
                        paginationListener.isLastPageVisible = true
                    }
                    adapter.notifyDataSetChanged()
                }
                is NewsListViewModel.NewsApiResult.Failure -> {
                    if (currentPage > 1 && paginationListener.isLoadingVisible) {
                        paginationListener.isLoadingVisible = false
                        adapter.removeLoader()
                    } else {
                        showToastMessage(result.exception)
                    }
                    paginationListener.isLastPageVisible = true
                }
            }
        }
    }

    override fun invoke(article: Article) {
        //
    }
}