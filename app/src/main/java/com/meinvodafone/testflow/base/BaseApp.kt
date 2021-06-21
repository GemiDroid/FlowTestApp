package com.meinvodafone.testflow.base

import android.app.Application
import com.meinvodafone.testflow.di.moviesModule
import com.meinvodafone.testflow.di.retrofitModule
import org.koin.android.ext.android.startKoin
import org.koin.android.logger.AndroidLogger

class BaseApp : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin(
            applicationContext,
            modules = listOf(retrofitModule, moviesModule),
            logger = AndroidLogger(true))
    }
}