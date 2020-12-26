package com.example.firebaselivedatahilt.di

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class BaseHiltApplication : Application(){

    override fun onCreate() {
        super.onCreate()
    }
}