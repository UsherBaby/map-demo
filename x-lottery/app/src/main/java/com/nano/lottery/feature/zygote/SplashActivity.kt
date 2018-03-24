package com.nano.lottery.feature.zygote

import android.arch.lifecycle.Lifecycle
import android.os.Bundle
import com.nano.lottery.R
import com.nano.lottery.base.BaseActivity
import com.nano.lottery.databinding.ActivitySplashBinding
import com.nano.lottery.di.GlideApp
import com.nano.lottery.feature.main.MainActivity
import com.pacific.arch.presentation.activityViewModel
import com.pacific.arch.presentation.contentView
import com.pacific.arch.presentation.jumpTo
import com.pacific.arch.presentation.showDialogFragment
import com.uber.autodispose.android.lifecycle.scope
import com.uber.autodispose.kotlin.autoDisposable
import io.reactivex.functions.Consumer

class SplashActivity : BaseActivity() {
    val model by activityViewModel(SplashViewModel::class.java)
    var isInitialized = false
        private set
    private val binding: ActivitySplashBinding by contentView(R.layout.activity_splash)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        GlideApp.with(this@SplashActivity)
                .load("file:///android_asset/app_welcome.png")
                .centerCrop()
                .into(binding.imgWelcome)

        model.isNewVersionName().autoDisposable(scope(Lifecycle.Event.ON_DESTROY)).subscribe(
                Consumer {
                    if (it) {
                        model.updateVersionName()
                        showDialogFragment(supportFragmentManager, GuideDialogFragment.newInstance())
                    }
                })

        model.initialize().autoDisposable(scope(Lifecycle.Event.ON_DESTROY)).subscribe({
            isInitialized = true
            if (!GuideDialogFragment.isShowing) {
                jumpTo(this@SplashActivity, MainActivity::class.java)
            }
        })
    }
}