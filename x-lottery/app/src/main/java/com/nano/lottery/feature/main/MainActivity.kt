package com.nano.lottery.feature.main

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.View
import com.nano.lottery.R
import com.nano.lottery.base.BaseActivity
import com.nano.lottery.databinding.ActivityMainBinding
import com.nano.lottery.databinding.NavigationTabBinding
import com.nano.lottery.views.CubeOutTransformer
import com.pacific.arch.presentation.activityViewModel
import com.pacific.arch.presentation.contentView
import com.pacific.arch.views.widget.OnTabSelected
import javax.inject.Inject

class MainActivity : BaseActivity() {
    val model by activityViewModel(MainViewModel::class.java)
    private val binding: ActivityMainBinding by contentView(R.layout.activity_main)
    private var swallowOnPageSelectedEvent = false
    private var swallowOnTabSelected = false

    @Inject
    lateinit var mainFragmentAdapter: MainFragmentAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(binding.toolbar)
        binding.viewPager.offscreenPageLimit = mainFragmentAdapter.count
        (0 until mainFragmentAdapter.count - 2).forEach { i ->
            val tab = binding.tabLayout.newTab()
            tab.customView = inflaterTabView(i)
            binding.tabLayout.addTab(tab)
        }
        binding.viewPager.setPageTransformer(true, CubeOutTransformer())
        binding.viewPager.adapter = mainFragmentAdapter
        binding.viewPager.setCurrentItem(1, false)

        // add Listeners after all ViewPager settings
        binding.viewPager.addOnPageChangeListener(object : ViewPager.SimpleOnPageChangeListener() {
            override fun onPageScrollStateChanged(state: Int) {
                // if we don't use postDelayed(), we can hack here by
                // if (state == ViewPager.SCROLL_STATE_IDLE) {------}
            }

            override fun onPageSelected(position: Int) {
                if (swallowOnTabSelected) {
                    swallowOnTabSelected = false
                    return
                }
                val fragmentPosition: Int
                when (position) {
                    0 -> {
                        fragmentPosition = 4
                        binding.viewPager.postDelayed({
                            binding.viewPager.setCurrentItem(fragmentPosition, false)
                        }, 300)
                    }
                    5 -> {
                        fragmentPosition = 1
                        binding.viewPager.postDelayed({
                            binding.viewPager.setCurrentItem(fragmentPosition, false)
                        }, 300)
                    }
                    else -> fragmentPosition = position
                }

                // if the tab is selected, the OnTabSelectedListener won't be triggered
                if (!binding.tabLayout.getTabAt(fragmentPosition - 1)!!.isSelected) {
                    swallowOnPageSelectedEvent = true
                    binding.tabLayout.getTabAt(fragmentPosition - 1)?.select()
                }
            }
        })

        binding.tabLayout.addOnTabSelectedListener(object : OnTabSelected() {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                if (swallowOnPageSelectedEvent) {
                    swallowOnPageSelectedEvent = false
                    return
                }

                // if the page is selected, the OnPageChangeListener won't be triggered
                if (tab!!.position + 1 != mainFragmentAdapter.currentPosition) {
                    swallowOnTabSelected = true
                    binding.viewPager.setCurrentItem(tab!!.position + 1, false)
                }
            }
        })
    }

    private fun inflaterTabView(position: Int): View {
        val binding = DataBindingUtil.inflate<NavigationTabBinding>(
                LayoutInflater.from(this),
                R.layout.navigation_tab,
                null,
                false
        )
        when (position) {
            0 -> binding.imgIcon.setImageResource(R.drawable.selector_home_tab)
            1 -> binding.imgIcon.setImageResource(R.drawable.selector_moment_tab)
            2 -> binding.imgIcon.setImageResource(R.drawable.selector_history_tab)
            else -> binding.imgIcon.setImageResource(R.drawable.selector_me_tab)
        }
        binding.textTitle.text = mainFragmentAdapter.getPageTitle(position)
        return binding.root
    }
}
