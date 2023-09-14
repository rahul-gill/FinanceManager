package io.github.gill.rahul.financemanager

import android.app.Application

class FinanceManagerApp : Application() {
    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    companion object {
        lateinit var instance: Application
            private set
    }
}
