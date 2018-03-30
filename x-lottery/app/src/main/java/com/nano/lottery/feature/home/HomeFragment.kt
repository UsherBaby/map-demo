package com.nano.lottery.feature.home

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.nano.lottery.R
import com.nano.lottery.databinding.FragmentHomeBinding
import com.nano.lottery.feature.ScrollingActivity
import com.nano.lottery.feature.main.MainFragment
import com.pacific.adapter.RecyclerAdapter
import com.pacific.arch.presentation.fragmentDataBinding
import com.pacific.arch.presentation.fragmentViewModel
import com.pacific.arch.presentation.start2
import com.thepacific.divider.RecyclerViewDivider
import io.reactivex.functions.Consumer

class HomeFragment : MainFragment(), View.OnClickListener {
    private val model by fragmentViewModel(HomeViewModel::class.java)
    private val adapter = RecyclerAdapter()
    private lateinit var binding: FragmentHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adapter.onClickListener = this
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = inflater.fragmentDataBinding(R.layout.fragment_home, container)
        binding.recycler.adapter = adapter
        binding.recycler.layoutManager = LinearLayoutManager(activity)
        binding.recycler.addItemDecoration(RecyclerViewDivider.builder(activity)
                .color(R.color.me_menu_divider, R.dimen.item_me_menu_divider)
                .hideLastDivider()
                .build())
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (allowInitialize) {
            allowInitialize = false
            mainActivity.model.createGame().subscribe(Consumer {
                adapter.replaceAll(it.toList())
            })
        } else {
            mainActivity.model.games.observe(this, Observer {
                adapter.replaceAll(it!!.toList())
            })
        }
    }

    override fun onClick(v: View?) {
        start2(this, ScrollingActivity::class.java)
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (!isVisibleToUser && isViewCreated()) {
            binding.recycler.smoothScrollToPosition(0)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        allowInitialize = true
    }

    companion object {
        private var allowInitialize = true
        fun newInstance() = HomeFragment()
    }
}