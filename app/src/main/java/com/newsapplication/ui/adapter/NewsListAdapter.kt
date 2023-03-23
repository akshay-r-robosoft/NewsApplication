package com.newsapplication.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.newsapplication.R
import com.newsapplication.data.model.Article
import com.newsapplication.databinding.ItemLoadingBinding
import com.newsapplication.databinding.ItemNewsInDetailsBinding
import com.newsapplication.databinding.ItemPopularNewsBinding
import com.newsapplication.databinding.ItemTopNewsBinding
import com.newsapplication.util.Constant
import com.newsapplication.util.Constant.LOADING
import com.newsapplication.util.Constant.NEWS_IN_DETAILS
import com.newsapplication.util.Constant.POPULAR_NEWS
import com.newsapplication.util.Constant.TOP_NEWS

class NewsListAdapter(private val articleList: MutableList<Article>, var itemClickCallback: (Article) -> Unit) : RecyclerView.Adapter<ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        when (viewType) {
            TOP_NEWS -> {
                val binding = DataBindingUtil.inflate(
                    LayoutInflater.from(parent.context),
                    R.layout.item_top_news,
                    parent,
                    false
                ) as ViewDataBinding
                return TopNewsViewHolder(binding)
            }
            NEWS_IN_DETAILS -> {
                val binding = DataBindingUtil.inflate(
                    LayoutInflater.from(parent.context),
                    R.layout.item_news_in_details,
                    parent,
                    false
                ) as ViewDataBinding
                return NewsInDetailsViewHolder(binding)
            }
            POPULAR_NEWS -> {
                val binding = DataBindingUtil.inflate(
                    LayoutInflater.from(parent.context),
                    R.layout.item_popular_news,
                    parent,
                    false
                ) as ViewDataBinding
                return PopularNewsViewHolder(binding)
            }
            LOADING -> {
                val binding = DataBindingUtil.inflate(
                    LayoutInflater.from(parent.context),
                    R.layout.item_loading,
                    parent,
                    false
                ) as ViewDataBinding
                return LoadingViewHolder(binding)
            }
            else -> {
                val binding = DataBindingUtil.inflate(
                    LayoutInflater.from(parent.context),
                    R.layout.item_popular_news,
                    parent,
                    false
                ) as ViewDataBinding
                return PopularNewsViewHolder(binding)
            }
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val article = articleList[position]
        when (holder) {
            is TopNewsViewHolder -> {
                holder.bind(article)
            }
            is PopularNewsViewHolder -> {
                holder.bind(article)
            }
            is NewsInDetailsViewHolder -> {
                holder.bind(article)
            }
            is LoadingViewHolder -> {
                holder.bind(article)
            }
        }
        holder.itemView.setOnClickListener {
            itemClickCallback(article)
        }
    }

    override fun getItemCount(): Int = articleList.size

    override fun getItemViewType(position: Int): Int {
        return if (articleList.size > 0) {
            when (articleList[position].newsTypeEnum) {
                Constant.NewsTypeEnum.TOP_NEWS -> TOP_NEWS
                Constant.NewsTypeEnum.POPULAR_NEWS -> POPULAR_NEWS
                Constant.NewsTypeEnum.NEWS_IN_DETAILS -> NEWS_IN_DETAILS
                Constant.NewsTypeEnum.LOADING -> LOADING
            }
        } else {
            0
        }
    }

    fun addLoader() {
        articleList.add(Article(newsTypeEnum = Constant.NewsTypeEnum.LOADING))
        notifyItemInserted(articleList.size - 1)
    }
    fun removeLoader() {
        val position = articleList.size - 1
        articleList.removeAt(position)
        notifyItemRemoved(position)
    }

    class TopNewsViewHolder(private val itemViewBinding: ViewDataBinding) :
        ViewHolder(itemViewBinding.root) {

        fun bind(article: Article) {
            (itemViewBinding as ItemTopNewsBinding).data = article
            itemViewBinding.executePendingBindings()
        }
    }

    class PopularNewsViewHolder(private val itemViewBinding: ViewDataBinding) : ViewHolder(itemViewBinding.root) {

        fun bind(article: Article) {
            (itemViewBinding as ItemPopularNewsBinding).data = article
            itemViewBinding.executePendingBindings()
        }
    }

    class NewsInDetailsViewHolder(private val itemViewBinding: ViewDataBinding) :
        ViewHolder(itemViewBinding.root) {

        fun bind(article: Article) {
            (itemViewBinding as ItemNewsInDetailsBinding).data = article
            itemViewBinding.executePendingBindings()
        }
    }

    class LoadingViewHolder(private val itemViewBinding: ViewDataBinding) :
        ViewHolder(itemViewBinding.root) {

        fun bind(article: Article) {
            (itemViewBinding as ItemLoadingBinding).data = article
            itemViewBinding.executePendingBindings()
        }
    }
}