package com.niojar.firebaseconnecion.domain

import com.niojar.firebaseconnecion.data.Repository

class CanAccessToApp {

    val repository = Repository()

    suspend fun invoke(): Boolean {
        val currentVersion = repository.getCurrentVersion()
        val minAllowedVersion = repository.getMinAllowedVersion()
        return true
    }

}