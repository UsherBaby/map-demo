package com.pacific.arch.views.widget

import android.support.v4.view.ViewPager

abstract class OnPageSelected : ViewPager.OnPageChangeListener {
    override fun onPageScrollStateChanged(state: Int) {
    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
    }
}