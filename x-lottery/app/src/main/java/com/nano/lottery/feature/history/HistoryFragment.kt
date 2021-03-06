package com.nano.lottery.feature.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.nano.lottery.R
import com.nano.lottery.databinding.FragmentHistoryBinding
import com.nano.lottery.feature.film.HistoryViewModel
import com.nano.lottery.feature.main.MainFragment
import com.pacific.adapter.RecyclerAdapter
import com.pacific.arch.presentation.fragmentDataBinding
import com.pacific.arch.presentation.fragmentViewModel

class HistoryFragment : MainFragment() {
    private val model by fragmentViewModel(HistoryViewModel::class.java)
    private val recyclerAdapter = RecyclerAdapter()
    private lateinit var binding: FragmentHistoryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = inflater.fragmentDataBinding(R.layout.fragment_history, container)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    companion object {
        fun newInstance() = HistoryFragment()
    }
}