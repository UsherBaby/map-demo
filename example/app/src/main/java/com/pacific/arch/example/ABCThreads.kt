package com.pacific.arch.example

import android.util.Log
import java.util.concurrent.locks.Condition
import java.util.concurrent.locks.ReentrantLock

val lock = ReentrantLock()
val conditionA: Condition = lock.newCondition()
val conditionB: Condition = lock.newCondition()
val conditionC: Condition = lock.newCondition()

var currentThread = "A"

var alphabet = arrayOf(
        "a", "b", "c", "d", "e", "f", "g", "h", "i", "j",
        "k", "l", "m", "n", "o", "p", "q", "r", "s", "t",
        "u", "v", "w", "x", "y", "z")

var zh = arrayOf(
        "一", "二", "三", "四", "五", "六", "七", "八", "九", "十",
        "十一", "十二", "十三", "十四", "十五", "十六", "十七", "十八", "十九", "二十",
        "二十一", "二十二", "二十三", "二十四", "二十五", "二十六", "二十七", "二十八", "二十九", "三十",
        "三十一", "三十二", "三十三", "三十四", "三十五", "三十六", "三十七", "三十八", "三十九", "四十",
        "四十一", "四十二", "四十三", "四十四", "四十五", "四十六", "四十七", "四十八", "四十九", "五十",
        "五十一", "五十二")

class ThreadA : Runnable {
    override fun run() {
        try {
            lock.lock()
            for (i in 0..25) {
                while (Thread.currentThread().name != currentThread) {
                    conditionA.await()
                }

                val seed = i * 4
                log((
                        seed + 1).toString() + ", "
                        + (seed + 2).toString() + ", "
                        + (seed + 3).toString() + ", "
                        + (seed + 4).toString())

                MyDatabase.getInstance(APP.instance!!)!!
                        .contentDao()
                        .save(MyContent((seed + 1).toString()))
                MyDatabase.getInstance(APP.instance!!)!!
                        .contentDao()
                        .save(MyContent((seed + 2).toString()))
                MyDatabase.getInstance(APP.instance!!)!!
                        .contentDao()
                        .save(MyContent((seed + 3).toString()))
                MyDatabase.getInstance(APP.instance!!)!!
                        .contentDao()
                        .save(MyContent((seed + 4).toString()))

                currentThread = "B"
                conditionB.signal()
            }
        } catch (e: InterruptedException) {
            e.printStackTrace()
        } finally {
            lock.unlock()
        }
    }
}

class ThreadB : Runnable {
    override fun run() {
        try {
            lock.lock()
            for (i in 0..25) {
                while (Thread.currentThread().name != currentThread) {
                    conditionB.await()
                }

                val seedIndex = i * 2
                log(zh[seedIndex] + ", " + zh[seedIndex + 1])

                MyDatabase.getInstance(APP.instance!!)!!
                        .contentDao()
                        .save(MyContent(zh[seedIndex]))
                MyDatabase.getInstance(APP.instance!!)!!
                        .contentDao()
                        .save(MyContent(zh[seedIndex + 1]))

                currentThread = "C"

                conditionC.signal()
            }
        } catch (e: InterruptedException) {
            e.printStackTrace()
        } finally {
            lock.unlock()
        }
    }
}

class ThreadC : Runnable {
    override fun run() {
        try {
            lock.lock()
            for (i in 0..25) {
                while (Thread.currentThread().name != currentThread) {
                    conditionC.await()
                }

                log(alphabet[i])
                MyDatabase.getInstance(APP.instance!!)!!
                        .contentDao()
                        .save(MyContent(alphabet[i]))

                currentThread = "A"

                conditionA.signal()
            }
        } catch (e: InterruptedException) {
            e.printStackTrace()
        } finally {
            lock.unlock()
        }
    }
}

fun log(msg: String) = Log.e("线程$currentThread-->", msg)