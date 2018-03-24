package com.nano.lottery.feature.zygote

import android.app.Application
import android.os.SystemClock
import com.nano.lottery.base.RxAwareViewModel
import com.nano.lottery.data.SystemRepo
import com.nano.lottery.model.GuideImage
import com.pacific.arch.rx.applyNewThread
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

class SplashViewModel @Inject constructor(app: Application, private val systemRepo: SystemRepo)
    : RxAwareViewModel(app) {

    fun loadGuideImage(): Single<List<GuideImage>> {
        return Single
                .fromCallable {
                    listOf(GuideImage("file:///android_asset/guide_1.png"),
                            GuideImage("file:///android_asset/guide_2.png"),
                            GuideImage("file:///android_asset/guide_3.png"))
                }
                .applyNewThread()
    }

    fun initialize(): Completable {
        return Completable
                .fromAction({
                    Thread.sleep(10000)
                })
                .applyNewThread()
    }

    fun isNewVersionName(): Single<Boolean> {
        return systemRepo.isNewVersionName()
    }

    fun updateVersionName() {
        systemRepo.updateVersionName()
    }
}