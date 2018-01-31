package com.pacific.arch.example

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import com.baidu.mapapi.model.LatLng
import com.baidu.mapapi.search.core.SearchResult
import com.baidu.mapapi.search.geocode.*
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MainActivity : AppCompatActivity() {
    private val geoCoder = GeoCoder.newInstance()

    private val progressBar: ProgressBar by lazy {
        findViewById<ProgressBar>(R.id.progress)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        geoCoder.setOnGetGeoCodeResultListener(object : OnGetGeoCoderResultListener {
            override fun onGetGeoCodeResult(result: GeoCodeResult?) {
                progressBar.visibility = View.GONE
                if (result == null) {
                    toast("没有检索到经纬度,请确保网络正常")
                    return
                }
                if (result.error == SearchResult.ERRORNO.NO_ERROR) {
                    toast("经纬度(${result.location.latitude},${result.location.longitude})")
                    return
                }

                toast("检索出错：${result.error}")
            }

            override fun onGetReverseGeoCodeResult(result: ReverseGeoCodeResult?) {
                progressBar.visibility = View.GONE
                if (result == null) {
                    toast("没有解析到地址,请确保网络正常")
                    return
                }
                if (result.error == SearchResult.ERRORNO.NO_ERROR) {
                    toast("地址:${result.addressDetail.countryName},"
                            + "${result.addressDetail.province},"
                            + "${result.addressDetail.city}")
                    return
                }

                toast("解析出错：${result.error}")
            }
        })

        //通过经纬度获取位置
        findViewById<View>(R.id.btn_1).setOnClickListener({
            progressBar.visibility = View.VISIBLE
            geoCoder.reverseGeoCode(ReverseGeoCodeOption()
                    .newVersion(1)
                    .location(LatLng(40.00, 118.00))
            )
        })

        //通过位置获取经纬度
        findViewById<View>(R.id.btn_2).setOnClickListener({
            progressBar.visibility = View.VISIBLE
            geoCoder.geocode(GeoCodeOption().city("北京").address("海淀区上地十街10号"))
        })


        //ABC线程并发操作数据库
        findViewById<View>(R.id.btn_3).setOnClickListener({
            progressBar.visibility = View.VISIBLE
            Observable.just(0)
                    .map {
                        val threadA = Thread(ThreadA())
                        val threadB = Thread(ThreadB())
                        val threadC = Thread(ThreadC())
                        threadA.name = "A"
                        threadB.name = "B"
                        threadC.name = "C"
                        threadA.start()
                        threadB.start()
                        threadC.start()
                        try {
                            Thread.sleep(3000)
                        } catch (e: InterruptedException) {
                            e.printStackTrace()
                        }
                    }
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        toast("ABC线程并发操作数据库完成！")
                        progressBar.visibility = View.GONE
                    })

        })
    }

    fun toast(msg: String) = Toast.makeText(this, msg, Toast.LENGTH_LONG).show()

    override fun onDestroy() {
        super.onDestroy()
        geoCoder.destroy()
    }
}
