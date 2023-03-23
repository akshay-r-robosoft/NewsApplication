package com.newsapplication.base

import android.os.Bundle
import com.newsapplication.BR
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch

abstract class BaseAppCompatActivity<Binding : ViewDataBinding, ViewModel : BaseViewModel> :
    AppCompatActivity() {

    protected lateinit var binding: Binding
    protected abstract val viewModel: ViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindViewModel()
        initializeObservers(viewModel)
    }

    open suspend fun setLanguageResources() {}

    open fun initializeObservers(viewModel: ViewModel) {}

    open fun initialize() {}

    @LayoutRes
    abstract fun getLayoutResId(): Int

    private fun bindViewModel() {
        binding = DataBindingUtil.setContentView(this, getLayoutResId())
        binding.apply {
            lifecycleOwner = this@BaseAppCompatActivity
            setVariable(BR.viewModel, viewModel)
        }
        binding.executePendingBindings()
        lifecycleScope.launch {
            setLanguageResources()
            initialize()
        }
    }
}
