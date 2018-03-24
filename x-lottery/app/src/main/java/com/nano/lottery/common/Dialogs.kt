package com.nano.lottery.common

import android.support.annotation.StringRes
import android.widget.Toast
import com.nano.lottery.App
import com.pacific.arch.presentation.dismiss

private var toast: Toast? = null

fun toast(@StringRes stringRes: Int) {
    toast(App.get().getString(stringRes))
}

fun toast(text: CharSequence) {
    dismiss(toast)
    toast = Toast.makeText(App.get(), text, Toast.LENGTH_SHORT)
    toast!!.show()
}
