package com.nano.lottery.feature.me

import com.nano.lottery.App
import com.nano.lottery.R
import com.nano.lottery.base.RxAwareViewModel
import com.nano.lottery.model.MeMenu
import com.nano.lottery.model.MeMenuDivider
import com.pacific.adapter.SimpleRecyclerItem
import com.pacific.arch.rx.applySingle
import io.reactivex.Single
import java.util.*
import javax.inject.Inject

class MeViewModel @Inject constructor(app: App) : RxAwareViewModel(app) {

    fun createMenu(): Single<MutableList<SimpleRecyclerItem>> {
        return Single.fromCallable {
            val titles = arrayOf(
                    R.string.menu_game_history, R.string.menu_security,
                    R.string.menu_account_flow, R.string.menu_deposit_history,
                    R.string.menu_share, R.string.menu_wallet,
                    R.string.menu_msg, R.string.menu_wiki,
                    R.string.menu_gift, R.string.menu_sister,
                    R.string.menu_about
            )
            val descriptions = arrayOf(
                    R.string.menu_game_history_desc, R.string.menu_security_desc,
                    R.string.menu_account_flow_desc, R.string.menu_deposit_history_desc,
                    R.string.menu_share_desc, R.string.menu_wallet_desc,
                    R.string.menu_msg_desc, R.string.menu_wiki_desc,
                    R.string.menu_gift_desc, R.string.menu_sister,
                    R.string.menu_about_desc
            )

            val icons = arrayOf(
                    R.drawable.menu_game_history, R.drawable.menu_security,
                    R.drawable.menu_account_flow, R.drawable.menu_deposit_history,
                    R.drawable.menu_share, R.drawable.menu_wallet,
                    R.drawable.menu_msg, R.drawable.menu_wiki,
                    R.drawable.menu_gift, R.drawable.menu_sister,
                    R.drawable.menu_about
            )
            val list: MutableList<SimpleRecyclerItem> = ArrayList()

            (0 until titles.size).filter { it % 2 == 1 }.forEach { it ->
                val left = it - 1
                list.add(MeMenu(
                        titles[left],
                        descriptions[left],
                        icons[left],
                        titles[it],
                        descriptions[it],
                        icons[it])
                )
            }
            list.add(MeMenu(
                    R.string.menu_about,
                    R.string.menu_about_desc,
                    R.drawable.menu_about,
                    R.string.menu_about,
                    R.string.menu_about_desc,
                    R.drawable.menu_about)
            )
            return@fromCallable list
        }.applySingle()
    }
}