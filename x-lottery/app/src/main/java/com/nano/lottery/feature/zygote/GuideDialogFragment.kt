package com.nano.lottery.feature.zygote

import android.arch.lifecycle.Lifecycle
import android.net.Uri
import android.os.Bundle
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.nano.lottery.R
import com.nano.lottery.databinding.DialogGuideBinding
import com.nano.lottery.di.GlideApp
import com.nano.lottery.feature.main.MainActivity
import com.pacific.adapter.PagerAdapter2
import com.pacific.arch.presentation.AppCompatDialogFragment
import com.pacific.arch.presentation.dialogFragmentViewModel
import com.pacific.arch.presentation.fragmentDataBinding
import com.pacific.arch.presentation.jumpTo
import com.uber.autodispose.android.lifecycle.scope
import com.uber.autodispose.kotlin.autoDisposable
import io.reactivex.functions.Consumer
import javax.inject.Inject

class GuideDialogFragment : AppCompatDialogFragment(), View.OnClickListener,
        ViewPager.PageTransformer {
    private val model by dialogFragmentViewModel(SplashViewModel::class.java)
    private val adapter = PagerAdapter2()

    private lateinit var binding: DialogGuideBinding

    @Inject
    lateinit var splashActivity: SplashActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isShowing = true
        setStyle(STYLE_NO_TITLE, R.style.FullScreenDialogFragment)
        adapter.setImageLoader { imageView, holder ->
            GlideApp.with(this@GuideDialogFragment)
                    .load(Uri.parse(holder.getItem<Image>().url))
                    .centerCrop()
                    .into(imageView)
        }
        adapter.onClickListener = this
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = inflater.fragmentDataBinding(R.layout.dialog_guide, container)
        binding.pager.setPageTransformer(false, this)
        binding.pager.adapter = adapter
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        isCancelable = false
        model.loadGuideImage().autoDisposable(scope(Lifecycle.Event.ON_DESTROY)).subscribe(
                Consumer {
                    adapter.replaceAll(it)
                }
        )
    }

    override fun onClick(v: View?) {
        if (v!!.id == R.id.btn_start) {
            dismiss()
            isShowing = false
            if (splashActivity.isInitialized) {
                jumpTo(splashActivity, MainActivity::class.java)
            }
        }
    }

    override fun transformPage(page: View, position: Float) {
        page.findViewById<View>(R.id.img_guide).translationX = -position * (page.width / 2)
        page.findViewById<View>(R.id.btn_start).translationX = -position * (page.width / 2)
    }

    companion object {
        var isShowing = false
            private set

        fun newInstance() = GuideDialogFragment()
    }
}