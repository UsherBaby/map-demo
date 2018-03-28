package com.nano.lottery.model

import android.support.annotation.DrawableRes
import android.support.annotation.StringRes
import android.support.v4.content.ContextCompat
import android.view.View
import com.nano.lottery.R
import com.nano.lottery.databinding.ItemGuideBinding
import com.nano.lottery.databinding.ItemHomeGameBinding
import com.nano.lottery.databinding.ItemMeMenuBinding
import com.pacific.adapter.SimpleRecyclerItem
import com.pacific.adapter.ViewHolder

class GuideImage(val url: String) : SimpleRecyclerItem() {
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

class MeMenu(@StringRes private val leftTitle: Int,
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
}

class MeMenuDivider : SimpleRecyclerItem() {
    override fun getLayout(): Int = R.layout.item_me_menu_divider

    override fun bind(holder: ViewHolder?) {
    }
}

class HomeGame(private val typeLeft: Int,
               @StringRes private val leftTitle: Int,
               @StringRes private val leftDesc: Int,
               @DrawableRes private val leftIconId: Int,
               private val codesLeft: Array<Pair<Int, Int>>?,
               private val typeRight: Int,
               @StringRes private val rightTitle: Int,
               @StringRes private val rightDesc: Int,
               @DrawableRes private val rightIconId: Int,
               private val codesRight: Array<Pair<Int, Int>>?) : SimpleRecyclerItem() {

    override fun getLayout(): Int = R.layout.item_home_game

    override fun bind(holder: ViewHolder?) {
        val binding: ItemHomeGameBinding = holder!!.binding()
        binding.imgLeft.setImageResource(leftIconId)
        binding.txtTitleLeft.setText(leftTitle)
        binding.txtDescLeft.setText(leftDesc)
        binding.imgRight.setImageResource(rightIconId)
        binding.txtTitleRight.setText(rightTitle)
        binding.txtDescRight.setText(rightDesc)

        val clickListener = ClickListener(this, holder, binding)
        binding.layoutLeft.setOnClickListener(clickListener)
        binding.layoutRight.setOnClickListener(clickListener)
    }

    class ClickListener(private val item: HomeGame,
                        private val holder: ViewHolder,
                        private val binding: ItemHomeGameBinding) : View.OnClickListener {
        override fun onClick(v: View?) {
            when (holder.currentPosition) {
                0 -> {
                    if (v == binding.layoutLeft) {
                        special6Cells()
                    } else {
                        normal6Cells(false)
                    }
                }
                1 -> {
                    if (v == binding.layoutLeft) {
                        normal6Cells(true)
                    } else {
                        normal3Cells(false)
                    }
                }
                2 -> normal2Cells(v == binding.layoutLeft)
                3 -> {
                    if (v == binding.layoutLeft) {
                        normal2Cells(true)
                    } else {
                        // handle
                    }
                }
                4 -> {
                    if (v == binding.layoutLeft) {
                        normal2Cells(true)
                    } else {
                        // handle
                    }
                }
                else -> {
                    // handle
                }
            }
        }

        private fun special6Cells() {
            showArrow(true)
            val views = arrayOf(
                    binding.txtGameOne, binding.txtGameTwo, binding.txtGameFour,
                    binding.txtGameFive, binding.txtGameSeven, binding.txtGameEight,
                    binding.txtTxtGameThree, binding.txtTxtGameSix, binding.txtTxtGameNine
            )
            (0 until views.size).forEach {
                if (it < 6) {
                    views[it].visibility = View.VISIBLE
                    views[it].setText(item.codesLeft!![it].second)
                } else {
                    views[it].visibility = View.INVISIBLE
                }
            }
            binding.layoutGameRowTwo.visibility = View.VISIBLE
            binding.layoutGameRowThree.visibility = View.VISIBLE
        }

        private fun normal6Cells(isLeft: Boolean) {
            showArrow(isLeft)
            val views = arrayOf(
                    binding.txtGameOne, binding.txtGameTwo, binding.txtTxtGameThree,
                    binding.txtGameFour, binding.txtGameFive, binding.txtTxtGameSix
            )
            (0 until views.size).forEach {
                views[it].visibility = View.VISIBLE
                views[it].setText(if (isLeft) {
                    item.codesLeft!![it].second
                } else {
                    item.codesRight!![it].second
                })
            }
            binding.layoutGameRowTwo.visibility = View.VISIBLE
            binding.layoutGameRowThree.visibility = View.GONE
        }

        private fun normal3Cells(isLeft: Boolean) {
            showArrow(isLeft)
            val views = arrayOf(binding.txtGameOne, binding.txtGameTwo, binding.txtTxtGameThree)
            (0 until views.size).forEach {
                views[it].visibility = View.VISIBLE
                views[it].setText(if (isLeft) {
                    item.codesLeft!![it].second
                } else {
                    item.codesRight!![it].second
                })
            }
            binding.layoutGameRowTwo.visibility = View.GONE
            binding.layoutGameRowThree.visibility = View.GONE
        }

        private fun normal2Cells(isLeft: Boolean) {
            showArrow(isLeft)
            val views = arrayOf(binding.txtGameOne, binding.txtGameTwo)
            (0 until views.size).forEach {
                views[it].visibility = View.VISIBLE
                views[it].setText(if (isLeft) {
                    item.codesLeft!![it].second
                } else {
                    item.codesRight!![it].second
                })

            }
            binding.txtTxtGameThree.visibility = View.INVISIBLE
            binding.layoutGameRowTwo.visibility = View.GONE
            binding.layoutGameRowThree.visibility = View.GONE
        }

        private fun showArrow(isLeft: Boolean) {
            binding.expandable.toggle()
            if (!binding.expandable.isExpanded) {
                binding.imgArrowLeft.visibility = View.GONE
                binding.imgArrowRight.visibility = View.GONE
                return
            }
            if (isLeft) {
                binding.imgArrowLeft.visibility = View.VISIBLE
                binding.imgArrowRight.visibility = View.GONE
            } else {
                binding.imgArrowLeft.visibility = View.GONE
                binding.imgArrowRight.visibility = View.VISIBLE
            }
        }
    }
}