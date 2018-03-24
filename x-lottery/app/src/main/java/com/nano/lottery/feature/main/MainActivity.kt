package com.nano.lottery.feature.main

import android.arch.lifecycle.Lifecycle
import android.os.Bundle
import com.nano.lottery.R
import com.nano.lottery.base.BaseActivity
import com.nano.lottery.databinding.ActivityMainBinding
import com.pacific.arch.presentation.activityViewModel
import com.pacific.arch.presentation.contentView
import com.uber.autodispose.android.lifecycle.scope
import com.uber.autodispose.kotlin.autoDisposable
import io.reactivex.functions.Consumer
import timber.log.Timber
import javax.inject.Inject

class MainActivity : BaseActivity() {
    val model by activityViewModel(MainViewModel::class.java)
    private val binding: ActivityMainBinding by contentView(R.layout.activity_main)

    @Inject
    lateinit var mainFragmentAdapter: MainFragmentAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        model.user().autoDisposable(scope(Lifecycle.Event.ON_DESTROY)).subscribe(Consumer {
            Timber.e(it)
        })
    }
}
