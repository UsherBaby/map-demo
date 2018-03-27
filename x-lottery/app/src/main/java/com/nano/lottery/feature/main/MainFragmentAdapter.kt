package com.nano.lottery.feature.main

import android.support.v4.app.Fragment
import com.nano.lottery.R
import com.nano.lottery.feature.history.HistoryFragment
import com.nano.lottery.feature.home.HomeFragment
import com.nano.lottery.feature.me.MeFragment
import com.nano.lottery.feature.moment.MomentFragment
import com.nano.lottery.views.FragmentPagerAdapter2
import java.lang.UnsupportedOperationException
import javax.inject.Inject

class MainFragmentAdapter @Inject constructor(mainActivity: MainActivity)
    : FragmentPagerAdapter2(mainActivity.supportFragmentManager) {

    private val titles: Array<String> = mainActivity.resources.getStringArray(R.array.main_tab_titles)

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> MeFragment.newInstance()
            1 -> HomeFragment.newInstance()
            2 -> MomentFragment.newInstance()
            3 -> HistoryFragment.newInstance()
            4 -> MeFragment.newInstance()
            5 -> HomeFragment.newInstance()
            else -> throw UnsupportedOperationException()
        }
    }

    override fun getPageTitle(position: Int) = titles[position]

    override fun getCount() = 6
}