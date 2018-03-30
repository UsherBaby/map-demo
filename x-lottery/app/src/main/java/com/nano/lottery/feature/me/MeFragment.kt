package com.nano.lottery.feature.me

import android.arch.lifecycle.Observer
import android.graphics.PorterDuff
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.nano.lottery.R
import com.nano.lottery.databinding.FragmentMeBinding
import com.nano.lottery.feature.main.MainFragment
import com.pacific.adapter.RecyclerAdapter
import com.pacific.arch.presentation.fragmentDataBinding
import com.pacific.arch.presentation.fragmentViewModel
import com.pacific.arch.views.widget.verifySDK
import com.thepacific.divider.RecyclerViewDivider
import io.reactivex.functions.Consumer

class MeFragment : MainFragment() {
    private val model by fragmentViewModel(MeViewModel::class.java)
    private val adapter = RecyclerAdapter()
    private lateinit var binding: FragmentMeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = inflater.fragmentDataBinding(R.layout.fragment_me, container)
        binding.recycler.adapter = adapter
        binding.recycler.layoutManager = LinearLayoutManager(activity)
        binding.recycler.addItemDecoration(RecyclerViewDivider.builder(activity)
                .drawableFactory(object : RecyclerViewDivider.DrawableFactory {
                    override fun getStrokeWidth(position: Int): Int {
                        return if (position == 2) {
                            resources.getDimensionPixelSize(R.dimen.item_me_menu_divider_x)
                        } else {
                            resources.getDimensionPixelSize(R.dimen.item_me_menu_divider)
                        }
                    }

                    override fun getDrawable(position: Int): Drawable {
                        return ColorDrawable(ContextCompat.getColor(activity!!, R.color.me_menu_divider))
                    }
                })
                .hideLastDivider()
                .build())
        if (!verifySDK(21)) {
            binding.layoutInfo!!.progressBalance.indeterminateDrawable.setColorFilter(
                    ContextCompat.getColor(activity!!, R.color.icons), PorterDuff.Mode.SRC_IN
            )
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (allowInitialize) {
            allowInitialize = false
            mainActivity.model.createSubject().subscribe(Consumer {
                adapter.replaceAll(it.toList())
            })
        } else {
            mainActivity.model.subjects.observe(this, Observer {
                adapter.replaceAll(it!!.toList())
            })
        }
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
        fun newInstance() = MeFragment()
    }
}