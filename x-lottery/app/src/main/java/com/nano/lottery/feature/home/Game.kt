package com.nano.lottery.feature.home

import android.support.annotation.DrawableRes
import android.support.annotation.StringRes
import android.view.View
import com.nano.lottery.R
import com.nano.lottery.databinding.ItemHomeGameBinding
import com.pacific.adapter.SimpleRecyclerItem
import com.pacific.adapter.ViewHolder
import net.cachapa.expandablelayout.ExpandableLayout

class Game(private val typeLeft: Int,
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

        holder.attachOnClickListener(R.id.txt_game_one)
        holder.attachOnClickListener(R.id.txt_game_two)
        holder.attachOnClickListener(R.id.txt_game_three)
        holder.attachOnClickListener(R.id.txt_game_four)
        holder.attachOnClickListener(R.id.txt_game_five)
        holder.attachOnClickListener(R.id.txt_game_six)
        holder.attachOnClickListener(R.id.txt_game_seven)
        holder.attachOnClickListener(R.id.txt_game_eight)
        holder.attachOnClickListener(R.id.txt_game_nine)

        val listener = ClickListener(this, holder, binding)
        binding.layoutLeft.setOnClickListener(listener)
        binding.layoutRight.setOnClickListener(listener)
        binding.expandable.setOnExpansionUpdateListener(listener)
    }

    class ClickListener(private val game: Game,
                        private val holder: ViewHolder,
                        private val binding: ItemHomeGameBinding)
        : View.OnClickListener, ExpandableLayout.OnExpansionUpdateListener {
        private var previewId = 0
        private var currentId = 0
        private var expandable = true

        override fun onClick(v: View?) {
            currentId = v!!.id
            if (binding.expandable.isExpanded) {
                if (handleExpandableLayout()) {
                    expandable = previewId != currentId
                    binding.expandable.collapse()
                    hideArrow()
                    previewId = currentId
                } else {
                    performClickEvent()
                }
            } else {
                if (previewId == currentId) {
                    if (handleExpandableLayout()) {
                        binding.expandable.expand()
                    } else {
                        performClickEvent()
                    }
                } else {
                    expandLayout(currentId == R.id.layout_left, holder.currentPosition)
                }
                previewId = currentId
            }
        }

        override fun onExpansionUpdate(expansionFraction: Float, state: Int) {
            if (state == ExpandableLayout.State.COLLAPSED && expandable) {
                expandable = false
                expandLayout(currentId == R.id.layout_left, holder.currentPosition)
            }
        }

        private fun expandLayout(isLeft: Boolean, position: Int) {
            when (position) {
                0 -> {
                    if (isLeft) {
                        onSpecial6Cells(isLeft)
                    } else {
                        onNormal6Cells(isLeft)
                    }
                }
                1 -> {
                    if (isLeft) {
                        onNormal6Cells(isLeft)
                    } else {
                        onNormal3Cells(isLeft)
                    }
                }
                2 -> onNormal2Cells(isLeft)
                3 -> {
                    if (isLeft) {
                        onNormal2Cells(true)
                    } else {
                        performClickEvent()
                    }
                }
                4 -> {
                    if (isLeft) {
                        onNormal2Cells(isLeft)
                    } else {
                        performClickEvent()
                    }
                }
                else -> performClickEvent()
            }
        }

        private fun performClickEvent() {
            if (handleExpandableLayout()) {
                if (binding.expandable.isExpanded) {
                    expandable = false
                    binding.expandable.collapse()
                    hideArrow()
                }
            }
            binding.txtGameOne.performClick()
        }

        private fun handleExpandableLayout(): Boolean {
            val p = holder.currentPosition
            return (p == 0 || p == 1 || p == 2 || (p == 3 && currentId == R.id.layout_left)
                    || (p == 4 && currentId == R.id.layout_left))

        }

        private fun onSpecial6Cells(isLeft: Boolean) {
            val views = arrayOf(
                    binding.txtGameOne, binding.txtGameTwo, binding.txtGameFour,
                    binding.txtGameFive, binding.txtGameSeven, binding.txtGameEight,
                    binding.txtGameThree, binding.txtGameSix, binding.txtGameNine
            )
            (0 until views.size).forEach {
                if (it < 6) {
                    views[it].visibility = View.VISIBLE
                    views[it].setText(game.codesLeft!![it].second)
                } else {
                    views[it].visibility = View.INVISIBLE
                }
            }
            binding.layoutGameRowTwo.visibility = View.VISIBLE
            binding.layoutGameRowThree.visibility = View.VISIBLE
            onExpanding(isLeft)
        }

        private fun onNormal6Cells(isLeft: Boolean) {
            val views = arrayOf(
                    binding.txtGameOne, binding.txtGameTwo, binding.txtGameThree,
                    binding.txtGameFour, binding.txtGameFive, binding.txtGameSix
            )
            (0 until views.size).forEach {
                views[it].visibility = View.VISIBLE
                views[it].setText(if (isLeft) {
                    game.codesLeft!![it].second
                } else {
                    game.codesRight!![it].second
                })
            }
            binding.layoutGameRowTwo.visibility = View.VISIBLE
            binding.layoutGameRowThree.visibility = View.GONE
            onExpanding(isLeft)
        }

        private fun onNormal3Cells(isLeft: Boolean) {
            val views = arrayOf(binding.txtGameOne, binding.txtGameTwo, binding.txtGameThree)
            (0 until views.size).forEach {
                views[it].visibility = View.VISIBLE
                views[it].setText(if (isLeft) {
                    game.codesLeft!![it].second
                } else {
                    game.codesRight!![it].second
                })
            }
            binding.layoutGameRowTwo.visibility = View.GONE
            binding.layoutGameRowThree.visibility = View.GONE
            onExpanding(isLeft)
        }

        private fun onNormal2Cells(isLeft: Boolean) {
            val views = arrayOf(binding.txtGameOne, binding.txtGameTwo)
            (0 until views.size).forEach {
                views[it].visibility = View.VISIBLE
                views[it].setText(if (isLeft) {
                    game.codesLeft!![it].second
                } else {
                    game.codesRight!![it].second
                })
            }
            binding.txtGameThree.visibility = View.INVISIBLE
            binding.layoutGameRowTwo.visibility = View.GONE
            binding.layoutGameRowThree.visibility = View.GONE
            onExpanding(isLeft)
        }

        private fun onExpanding(isLeft: Boolean) {
            binding.expandable.expand()
            binding.expandable.postDelayed({
                if (isLeft) {
                    binding.imgArrowLeft.visibility = View.VISIBLE
                    binding.imgArrowRight.visibility = View.INVISIBLE
                } else {
                    binding.imgArrowLeft.visibility = View.INVISIBLE
                    binding.imgArrowRight.visibility = View.VISIBLE
                }
            }, 200)
        }

        private fun hideArrow() {
            binding.expandable.postDelayed({
                binding.imgArrowLeft.visibility = View.INVISIBLE
                binding.imgArrowRight.visibility = View.INVISIBLE
            }, 200)
        }
    }
}