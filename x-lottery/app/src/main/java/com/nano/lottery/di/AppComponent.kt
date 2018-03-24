package com.nano.lottery.di

import com.nano.lottery.App
import dagger.Component
import dagger.android.AndroidInjector
import javax.inject.Singleton

@Singleton
@Component(modules = [(AppModule::class)])
interface AppComponent : AndroidInjector<App> {
    fun glideComponentBuilder(): GlideComponent.Builder

    @Component.Builder
    abstract class Builder : AndroidInjector.Builder<App>() {
        // hack kotlin-apt warning
        abstract override fun build(): AppComponent
    }
}