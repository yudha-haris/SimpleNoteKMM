package com.example.simplenoteapp.android

import android.app.Application
import com.example.simplenoteapp.presentation.note.di.noteModule
import di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@MyApplication)
            modules(
                appModule,
                noteModule,
            )
        }


    }
}