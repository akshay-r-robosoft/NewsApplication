package com.newsapplication.ui.activity

import androidx.recyclerview.widget.LinearLayoutManager
import com.newsapplication.R
import com.newsapplication.base.BaseAppCompatActivity
import com.newsapplication.data.model.Article
import com.newsapplication.databinding.ActivityNewsListBinding
import com.newsapplication.ui.adapter.NewsListAdapter
import com.newsapplication.util.Constant
import com.newsapplication.util.Constant.ARTICLE_DATA
import com.newsapplication.util.PaginationListener
import com.newsapplication.util.launchActivity
import com.newsapplication.util.showToastMessage
import com.newsapplication.viewmodel.NewsListViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class NewsListActivity : BaseAppCompatActivity<ActivityNewsListBinding, NewsListViewModel>(), (Article) -> Unit {

    private lateinit var paginationListener: PaginationListener
    private var articleList: MutableList<Article> = mutableListOf()
    private var currentPage: Int = 1
    private var isLoading: Boolean = false
    private var isLastPage: Boolean = false
    private lateinit var adapter: NewsListAdapter
    override val viewModel: NewsListViewModel by viewModel()

    override fun getLayoutResId(): Int = R.layout.activity_news_list

    override fun initialize() {
        super.initialize()
        setAdapter()
        viewModel.callNewsApi(currentPage)
    }

    private fun setAdapter() {
        adapter = NewsListAdapter(articleList, this)
        val recyclerView = binding.recyclerView
        recyclerView.adapter = adapter
        paginationListener = object : PaginationListener(recyclerView.layoutManager as LinearLayoutManager) {
            override fun loadMoreItems() {
                isLoadingVisible = true
                adapter.addLoader()
                viewModel.callNewsApi(currentPage)
            }

            override var isLastPageVisible: Boolean = isLastPage
            override var isLoadingVisible: Boolean = isLoading
        }
        recyclerView.addOnScrollListener(paginationListener)
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
                        currentPage += 1
                        val articles = result.data.articles
                        if (articleList.isEmpty()) {
                            for (i in articles.indices) {
                                if (i == 0) {
                                    articles[0].wantsToShowHeader = true
                                    articles[0].newsTypeEnum = Constant.NewsTypeEnum.TOP_NEWS
                                } else if (i == 1) {
                                    articles[1].wantsToShowHeader = true
                                }
                            }
                        }
                        articleList.addAll(articles)
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
        launchActivity<NewsDetailActivity> {
            putExtra(ARTICLE_DATA, article)
        }
    }
}