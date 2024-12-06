package com.niojar.firebaseconnecion.data

import com.niojar.firebaseconnecion.FirebaseConnecion.Companion.context

class Repository {
    fun getCurrentVersion(): List<Int> {
        return try {
            val packageInfo = context.packageManager.getPackageInfo(context.packageName, 0)
            packageInfo.versionName.split(".").map { it.toInt() }
        } catch (e: Exception) {
            listOf(0, 0, 0)
        }
    }

    fun getMinAllowedVersion(): Any {

    }
}