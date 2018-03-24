package com.nano.lottery

import android.arch.lifecycle.*
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Handler
import android.os.IBinder
import android.os.Message
import android.os.Messenger
import com.facebook.stetho.Stetho
import com.nano.lottery.base.CrashReportingTree
import com.nano.lottery.common.DEBUG_APP
import com.nano.lottery.common.FLAG_LOCAL_MESSENGER
import com.nano.lottery.common.FLAG_SOCKET_DATA
import com.nano.lottery.data.APP_LIFECYCLE
import com.nano.lottery.data.SOCKET_SERVICE
import com.nano.lottery.data.SystemRepo
import com.nano.lottery.service.RemoteSocketService
import com.pacific.arch.rx.verifyWorkThread
import com.pacific.arch.views.compact.attachDebug
import com.squareup.leakcanary.internal.LeakCanaryInternals
import com.squareup.moshi.Moshi
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppInitializer @Inject constructor(private val app: App,
                                         private val moshi: Moshi,
                                         private val systemRepo: SystemRepo) : LifecycleObserver {
    private var inAppProcess = false
    private val messenger = Messenger(IncomingHandler())
    private lateinit var remoteMessenger: Messenger

    private var shouldUnbindService = false

    private val connection = object : ServiceConnection {
        override fun onServiceDisconnected(name: ComponentName?) {
        }

        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            // Send local messenger to remote service.
            // If you need to send obj, please use Bundle.
            remoteMessenger = Messenger(service)
            val msg = Message.obtain()
            msg.what = FLAG_LOCAL_MESSENGER
            msg.replyTo = messenger
            remoteMessenger.send(msg)
        }
    }

    fun onAppCreate() {
        DEBUG_APP = BuildConfig.DEBUG
        inAppProcess = !LeakCanaryInternals.isInServiceProcess(app, RemoteSocketService::class.java)
        attachDebug(app, Runnable {
            verifyWorkThread()
            if (DEBUG_APP) {
                Stetho.initializeWithDefaults(app)
                Timber.plant(Timber.DebugTree())
            } else {
                Timber.plant(CrashReportingTree())
            }
            if (inAppProcess) {
                ProcessLifecycleOwner.get().lifecycle.addObserver(this)
                val intent = Intent(app, RemoteSocketService::class.java)
                if (app.bindService(intent, connection, Context.BIND_AUTO_CREATE)) {
                    shouldUnbindService = true
                } else {
                    Timber.e("Error -> The requested service doesn't exist")
                }
            }
        }, DEBUG_APP)
    }

    fun onAppTerminate() {
        SOCKET_SERVICE.onComplete()
        APP_LIFECYCLE.onComplete()

        if (inAppProcess && shouldUnbindService) {
            app.unbindService(connection)
            shouldUnbindService = false
        }
    }

    // NOTE: ON_DESTROY will never be dispatched
    @OnLifecycleEvent(Lifecycle.Event.ON_ANY)
    fun onAppLifecycle(owner: LifecycleOwner, event: Lifecycle.Event) {
        Timber.d("Current event -> %s ", event)
        if (event == Lifecycle.Event.ON_START || event == Lifecycle.Event.ON_STOP) {
            APP_LIFECYCLE.onNext(event)
            return
        }
    }

    class IncomingHandler : Handler() {
        override fun handleMessage(msg: Message?) {
            when (msg!!.what) {
                FLAG_SOCKET_DATA -> {
                    SOCKET_SERVICE.onNext(msg.obj.toString())
                }
                else -> {
                    super.handleMessage(msg)
                }
            }
        }
    }
}