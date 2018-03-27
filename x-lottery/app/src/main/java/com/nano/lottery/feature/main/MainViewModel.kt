package com.nano.lottery.feature.main

import android.arch.lifecycle.MutableLiveData
import com.nano.lottery.App
import com.nano.lottery.base.RxAwareViewModel
import com.pacific.adapter.SimpleRecyclerItem
import javax.inject.Inject

class MainViewModel @Inject constructor(app: App) : RxAwareViewModel(app) {
    val menus = MutableLiveData<MutableList<SimpleRecyclerItem>>()
}
