package com.nano.lottery.feature.me

import android.support.annotation.DrawableRes
import android.support.annotation.StringRes
import android.support.v4.content.ContextCompat
import android.view.View
import com.nano.lottery.R
import com.nano.lottery.databinding.ItemMeMenuBinding
import com.pacific.adapter.SimpleRecyclerItem
import com.pacific.adapter.ViewHolder

class Subject(@StringRes private val leftTitle: Int,
              @StringRes private val leftDesc: Int,
              @DrawableRes private val leftIconId: Int,
              @StringRes private val rightTitle: Int,
              @StringRes private val rightDesc: Int,
              @DrawableRes private val rightIconId: Int,
              var securityLevel: String = "",
              var hasNewVersion: Boolean = false) : SimpleRecyclerItem() {
    override fun getLayout(): Int = R.layout.item_me_menu

    override fun bind(holder: ViewHolder?) {
        val binding: ItemMeMenuBinding = holder!!.binding()
        binding.imgLeft.setImageResource(leftIconId)
        binding.txtTitleLeft.setText(leftTitle)
        binding.txtDescLeft.setText(leftDesc)

        if (holder.currentPosition == 0) {
            binding.txtDescRight.setTextColor(ContextCompat.getColor(holder.itemView.context,
                    R.color.me_menu_security_desc))
            binding.txtSubTitleRight.setTextColor(ContextCompat.getColor(holder.itemView.context,
                    R.color.me_menu_security_level))
            binding.txtSubTitleRight.visibility = View.VISIBLE
            binding.txtSubTitleRight.text = securityLevel
        } else {
            binding.txtDescLeft.setTextColor(ContextCompat.getColor(holder.itemView.context,
                    R.color.me_menu_desc))
            binding.txtSubTitleRight.visibility = View.GONE
        }
        if (holder.currentPosition == 2) {
            binding.imgNotifyLeft.setImageResource(R.drawable.hot)
            binding.imgNotifyLeft.visibility = View.VISIBLE
            binding.txtDescLeft.setTextColor(ContextCompat.getColor(holder.itemView.context,
                    R.color.primary))
        } else {
            binding.imgNotifyLeft.visibility = View.GONE
            binding.txtDescLeft.setTextColor(ContextCompat.getColor(holder.itemView.context,
                    R.color.me_menu_desc))
        }

        if (holder.currentPosition == 3) {
            binding.txtTitleLeft.setTextColor(ContextCompat.getColor(holder.itemView.context,
                    R.color.primary))
        } else {
            binding.txtTitleLeft.setTextColor(ContextCompat.getColor(holder.itemView.context,
                    R.color.me_menu_title))
        }

        if (holder.isLastItem) {
            binding.layoutRight.visibility = View.INVISIBLE
            if (hasNewVersion) {
                binding.imgNotifyLeft.setImageResource(R.drawable.new_version)
                binding.imgNotifyLeft.visibility = View.VISIBLE
            } else {
                binding.imgNotifyLeft.visibility = View.GONE
            }
        } else {
            binding.layoutRight.visibility = View.VISIBLE
            binding.imgRight.setImageResource(rightIconId)
            binding.txtTitleRight.setText(rightTitle)
            binding.txtDescRight.setText(rightDesc)
        }
    }

    class Divider : SimpleRecyclerItem() {
        override fun getLayout(): Int = R.layout.item_me_menu_divider

        override fun bind(holder: ViewHolder?) {
        }
    }
}