package com.nano.lottery.feature.main

import android.arch.lifecycle.MutableLiveData
import com.nano.lottery.App
import com.nano.lottery.R
import com.nano.lottery.base.RxAwareViewModel
import com.nano.lottery.common.*
import com.nano.lottery.model.HomeGame
import com.nano.lottery.model.MeMenu
import com.pacific.adapter.SimpleRecyclerItem
import com.pacific.arch.rx.applySingle
import io.reactivex.Single
import java.util.*
import javax.inject.Inject

class MainViewModel @Inject constructor(app: App) : RxAwareViewModel(app) {
    val menus = MutableLiveData<MutableList<SimpleRecyclerItem>>()
    val games = MutableLiveData<MutableList<SimpleRecyclerItem>>()

    fun createGame(): Single<MutableList<SimpleRecyclerItem>> {
        return Single.fromCallable {
            val types = arrayOf(
                    TYPE_INSTANT_LOTTERY, TYPE_5_IN_11,
                    TYPE_QUICK_3, TYPE_BEI_JING_RACING,
                    TYPE_QUICK_SHIP, TYPE_F_1,
                    TYPE_BEI_JING_HAPPY_8, TYPE_NIU_NIU,
                    TYPE_HK_LOTTERY, TYPE_MARK_6,
                    TYPE_KENO, TYPE_HAI_NAN_7_STAR,
                    TYPE_DOUBLE_COLORFUL_BALL, TYPE_LOTTERY_3D,
                    TYPE_SEQUENCE_3_5, TYPE_CHATTING
            )
            val titles = arrayOf(
                    R.string.type_instant_lottery, R.string.type_5_in_11,
                    R.string.type_quick_3, R.string.type_bei_jing_racing,
                    R.string.type_happy_quick_ship, R.string.type_f_1,
                    R.string.type_bei_jing_happy_8, R.string.type_niu_niu,
                    R.string.type_hk_lottery, R.string.type_mark_6,
                    R.string.type_keno, R.string.type_hai_nan_7_star,
                    R.string.type_double_colorful_ball, R.string.type_lottery_3d,
                    R.string.type_sequence_3_5, R.string.type_chatting
            )
            val descriptions = arrayOf(
                    R.string.high_frequency, R.string.high_frequency,
                    R.string.high_frequency, R.string.high_frequency,
                    R.string.high_frequency, R.string.high_frequency,
                    R.string.high_frequency, R.string.high_frequency,
                    R.string.high_frequency, R.string.high_frequency,
                    R.string.high_frequency, R.string.low_frequency,
                    R.string.high_frequency, R.string.low_frequency,
                    R.string.low_frequency, R.string.low_frequency,
                    R.string.low_frequency, R.string.nope
            )
            val icons = arrayOf(
                    R.drawable.type_instant_lottery, R.drawable.type_5_in_11,
                    R.drawable.type_quick_3, R.drawable.type_bei_jing_racing,
                    R.drawable.type_happy_quick_ship, R.drawable.type_f_1,
                    R.drawable.type_bei_jing_happy_8, R.drawable.type_niu_niu,
                    R.drawable.type_hk_lottery, R.drawable.type_mark_6,
                    R.drawable.type_keno, R.drawable.type_hai_nan_7_star,
                    R.drawable.type_double_colorful_ball, R.drawable.type_lottery_3d,
                    R.drawable.type_sequence_3_5, R.drawable.type_chatting
            )
            val codes = arrayOf(
                    arrayOf(Pair(CODE_CHONG_QING_A, R.string.code_chong_qing_a),
                            Pair(CODE_CHONG_QING_B, R.string.code_chong_qing_b),
                            Pair(CODE_HAPPY_A, R.string.code_happy_a),
                            Pair(CODE_HAPPY_B, R.string.code_happy_b),
                            Pair(CODE_AUSTRALIA_A, R.string.code_australia_a),
                            Pair(CODE_AUSTRALIA_B, R.string.code_australia_b)),
                    arrayOf(Pair(CODE_GUANG_DONG_5_IN_11, R.string.code_guang_dong_5_in_11),
                            Pair(CODE_AUSTRALIA_5_IN_11, R.string.code_australia_5_in_11),
                            Pair(CODE_SHAN_DONG_5_IN_11, R.string.code_shan_dong_5_in_11),
                            Pair(CODE_JIANG_XI_5_IN_11, R.string.code_jiang_xi_5_in_11),
                            Pair(CODE_BEI_JING_5_IN_11, R.string.code_bei_jing_5_in_11),
                            Pair(CODE_HAPPY_5_IN_11, R.string.code_happy_5_in_11)),
                    arrayOf(Pair(CODE_JIANG_SU_QUICK_3, R.string.code_jiang_su_quick_3),
                            Pair(CODE_AUSTRALIA_QUICK_3, R.string.code_australia_quick_3),
                            Pair(CODE_HU_BEI_QUICK_3, R.string.code_hu_bei_quick_3),
                            Pair(CODE_FU_JIAN_QUICK_3, R.string.code_fu_jian__quick_3),
                            Pair(CODE_BEI_JING_QUICK_3, R.string.code_bei_jing_quick_3),
                            Pair(CODE_HAPPY_QUICK_3, R.string.code_happy_quick_3)),
                    arrayOf(Pair(CODE_BEI_JING_RACING_A, R.string.code_bei_jing_racing_a),
                            Pair(CODE_BEI_JING_RACING_B, R.string.code_bei_jing_racing_b),
                            Pair(CODE_BEI_JING_RACING_C, R.string.code_bei_jing_racing_c)),
                    arrayOf(Pair(CODE_HAPPY_QUICK_SHIP_A, R.string.code_happy_quick_ship_a),
                            Pair(CODE_HAPPY_QUICK_SHIP_B, R.string.code_happy_quick_ship_b)),
                    arrayOf(Pair(CODE_AUSTRALIA_F_1_A, R.string.code_australia_f_1_a),
                            Pair(CODE_AUSTRALIA_F_1_B, R.string.code_australia_f_1_b)),
                    arrayOf(Pair(CODE_BEI_JING_HAPPY_8, R.string.code_bei_jing_happy_8),
                            Pair(CODE_AUSTRALIA_ACT_HAPPY_8, R.string.code_australia_act_happy_8)),
                    null,
                    arrayOf(Pair(CODE_HAPPY_5_SCORE_HK_LOTTERY, R.string.code_happy_5_score_hk_lottery),
                            Pair(CODE_AUSTRLIA_165_HK_LOTTERY, R.string.code_australia_165_lottery)),
                    null, null, null, null, null, null, null
            )

            val list: MutableList<SimpleRecyclerItem> = ArrayList()
            (0 until titles.size).filter { it % 2 == 1 }.forEach { it ->
                val left = it - 1
                list.add(HomeGame(
                        types[left],
                        titles[left],
                        descriptions[left],
                        icons[left],
                        codes[left],
                        types[it],
                        titles[it],
                        descriptions[it],
                        icons[it],
                        codes[it])
                )
            }
            games.postValue(list)
            return@fromCallable list
        }.applySingle()
    }

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
            menus.postValue(list)
            return@fromCallable list
        }.applySingle()
    }
}
