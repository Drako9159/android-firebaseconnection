package com.niojar.firebaseconnecion.domain

import com.niojar.firebaseconnecion.data.Repository

class CanAccessToApp {

    val repository = Repository()

    suspend operator fun invoke(): Boolean {
        val currentVersion = repository.getCurrentVersion()
        val minAllowedVersion = repository.getMinAllowedVersion()

        for ((currentPart, minPart) in currentVersion.zip(minAllowedVersion)) {
            if (currentPart != minPart) {
                return currentPart > minPart
            }
        }
        return true
    }
}