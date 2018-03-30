package com.nano.lottery.feature.zygote

import android.view.View
import com.nano.lottery.R
import com.nano.lottery.databinding.ItemGuideBinding
import com.pacific.adapter.SimpleRecyclerItem
import com.pacific.adapter.ViewHolder

class Image(val url: String) : SimpleRecyclerItem() {
    override fun getLayout(): Int = R.layout.item_guide

    override fun bind(holder: ViewHolder?) {
        val binding: ItemGuideBinding = holder!!.binding()
        if (holder.currentPosition == 2) {
            binding.btnStart.visibility = View.VISIBLE
        } else {
            binding.btnStart.visibility = View.INVISIBLE
        }
        holder.attachImageLoader(R.id.img_guide)
        holder.attachOnClickListener(R.id.btn_start)
    }
}