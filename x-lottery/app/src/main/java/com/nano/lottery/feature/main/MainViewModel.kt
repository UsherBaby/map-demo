package com.nano.lottery.feature.main

import com.nano.lottery.App
import com.nano.lottery.base.RxAwareViewModel
import com.nano.lottery.data.SOCKET_SERVICE
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import javax.inject.Inject

class MainViewModel @Inject constructor(app: App) : RxAwareViewModel(app) {

    fun user(): Flowable<String> {
        return SOCKET_SERVICE.observeOn(AndroidSchedulers.mainThread())
    }
}
