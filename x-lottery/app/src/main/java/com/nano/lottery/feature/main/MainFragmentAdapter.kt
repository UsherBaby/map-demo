package com.nano.lottery.feature.main

import android.support.v4.app.Fragment
import com.nano.lottery.R
import com.nano.lottery.views.FragmentPagerAdapter2
import javax.inject.Inject

class MainFragmentAdapter @Inject constructor(mainActivity: MainActivity)
    : FragmentPagerAdapter2(mainActivity.supportFragmentManager) {

    private val titles: Array<String> = mainActivity.resources.getStringArray(R.array.main_tab_titles)

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> throw UnsupportedOperationException("Illegal position")
            1 -> throw UnsupportedOperationException("Illegal position")
            2 -> throw UnsupportedOperationException("Illegal position")
            3 -> throw UnsupportedOperationException("Illegal position")
            4 -> throw UnsupportedOperationException("Illegal position")
            else -> throw UnsupportedOperationException("Illegal position")
        }
    }

    override fun getPageTitle(position: Int) = titles[position]

    override fun getCount() = 5
}