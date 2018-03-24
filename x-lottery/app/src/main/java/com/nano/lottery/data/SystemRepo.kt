package com.nano.lottery.data

import android.content.Context
import android.content.SharedPreferences
import android.content.pm.PackageManager
import androidx.content.edit
import com.nano.lottery.common.PRE_VERSION
import com.pacific.arch.rx.applyNewThread
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SystemRepo @Inject constructor(private val context: Context,
                                     private val sharedPreferences: SharedPreferences,
                                     private val systemDatabase: SystemDatabase) {
    fun isNewVersionName(): Single<Boolean> {
        return Single
                .fromCallable {
                    val preVersionName = sharedPreferences.getString(PRE_VERSION, "")
                    try {
                        val pi = context.packageManager.getPackageInfo(context.packageName,
                                PackageManager.GET_CONFIGURATIONS)
                        preVersionName != pi.versionName
                    } catch (e: Exception) {
                        false
                    }
                }
                .onErrorReturn { false }
                .applyNewThread()
    }

    fun updateVersionName() {
        Completable
                .fromAction {
                    try {
                        val pi = context.packageManager.getPackageInfo(context.packageName,
                                PackageManager.GET_CONFIGURATIONS)
                        sharedPreferences.edit(true) {
                            putString(PRE_VERSION, pi.versionName)
                        }
                    } catch (e: Exception) {
                        sharedPreferences.edit(true) {
                            putString(PRE_VERSION, "")
                        }
                    }
                }
                .applyNewThread()
                .subscribe()
    }
}