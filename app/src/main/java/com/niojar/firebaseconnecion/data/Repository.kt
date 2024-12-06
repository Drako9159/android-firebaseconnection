package com.niojar.firebaseconnecion.data

import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.firebase.remoteconfig.remoteConfigSettings
import com.niojar.firebaseconnecion.FirebaseConnecion.Companion.context
import kotlinx.coroutines.tasks.await

class Repository {

    companion object {
        private const val MIN_VERSION = "min_version"
    }

    private val remoteConfig = Firebase.remoteConfig.apply {
        setConfigSettingsAsync(remoteConfigSettings { minimumFetchIntervalInSeconds = 3600 })
        fetchAndActivate()
    }

    fun getCurrentVersion(): List<Int> {
        return try {
            val packageInfo = context.packageManager.getPackageInfo(context.packageName, 0)
            packageInfo.versionName.split(".").map { it.toInt() }
        } catch (e: Exception) {
            listOf(0, 0, 0)
        }
    }

    suspend fun getMinAllowedVersion(): List<Int> {
        remoteConfig.fetch(0)
        remoteConfig.activate().await()
        val minVersion = remoteConfig.getString(MIN_VERSION)
        if (minVersion.isEmpty()) return listOf(0, 0, 0)
        return minVersion.split(".").map { it.toInt() }
    }
}