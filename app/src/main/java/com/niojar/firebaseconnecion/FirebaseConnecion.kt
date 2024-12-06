package com.niojar.firebaseconnecion

import android.app.Application
import android.content.Context

class FirebaseConnecion : Application() {
    companion object {
        lateinit var context: Context
    }

    override fun onCreate() {
        super.onCreate()
        context = this
    }
}