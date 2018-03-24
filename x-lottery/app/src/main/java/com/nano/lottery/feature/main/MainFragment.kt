package com.nano.lottery.feature.main

import com.nano.lottery.base.BaseFragment
import com.pacific.arch.presentation.ViewModelSource
import javax.inject.Inject

abstract class MainFragment : BaseFragment() {
    @Inject
    lateinit var mainActivity: MainActivity

    override fun viewModelSource(): ViewModelSource {
        return ViewModelSource.NONE
    }
}
