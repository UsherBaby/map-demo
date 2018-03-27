package com.nano.lottery.feature.main

import android.os.Bundle
import android.support.annotation.CallSuper
import android.view.View
import com.nano.lottery.base.BaseFragment
import com.pacific.arch.presentation.ViewModelSource
import java.lang.UnsupportedOperationException
import javax.inject.Inject

abstract class MainFragment : BaseFragment() {
    private var viewCreated = false
    private var lazyLoaded = false
    private var lockLazyLoad = true

    @Inject
    protected lateinit var mainActivity: MainActivity

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewCreated = true
        onLoad()
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        onLoad()
    }

    override fun viewModelSource() = ViewModelSource.NONE

    private fun onLoad() {
        if (userVisibleHint && viewCreated && !lazyLoaded) {
            lazyLoaded = true
            lockLazyLoad = false
            lazyLoad()
        }
    }

    // begin to load data here, instead of in onViewCreated() method
    @CallSuper
    protected open fun lazyLoad() {
        if (lockLazyLoad) throw UnsupportedOperationException()
        lockLazyLoad = false
    }
}
