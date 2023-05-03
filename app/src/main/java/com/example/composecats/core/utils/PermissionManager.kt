package com.example.composecats.core.utils

import kotlinx.coroutines.flow.MutableSharedFlow

class PermissionModel(
    val permissions: Array<String>,
    val actionGranted: () -> Unit,
    val actionDenied: (() -> Unit)? = null
)

object permissionManager {

    private val permissionEventObservable = MutableSharedFlow<PermissionModel>()

    fun observePermissions(): MutableSharedFlow<PermissionModel> {
        return permissionEventObservable
    }

    suspend fun requestPermission(
        permissions: Array<String>,
        actionGranted: () -> Unit,
        actionDenied: (() -> Unit)? = null
    ) {
        permissionEventObservable.emit((PermissionModel(permissions, actionGranted, actionDenied)))
    }

}