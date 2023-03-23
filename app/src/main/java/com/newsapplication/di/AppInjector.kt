package com.newsapplication.di

import com.newsapplication.viewmodel.NewsListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val networkModule = module {
    single { createAuthService() }
}

val viewModelModule = module {
    viewModel {
        NewsListViewModel(get())
    }
}
