package com.example.composecats.features.main_screen

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.composecats.core.utils.permissionManager
import com.example.composecats.ui.theme.ComposeCatsTheme
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class BaseActivity : ComponentActivity() {

    val coroutineScope = CoroutineScope(Dispatchers.Default)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeCatsTheme {
               MainScreen()
            }
        }

        coroutineScope.launch {
            permissionManager.observePermissions()
                .collect {
                    requestPermission(it.permissions.first(), it.actionGranted, it.actionDenied)
                }
        }

    }

    private fun requestPermission(
        permission: String,
        actionGranted: () -> Unit,
        actionDenied: (() -> Unit)? = null
    ) {
        Dexter.withActivity(this)
            .withPermission(permission)
            .withListener(object : PermissionListener {

                override fun onPermissionRationaleShouldBeShown(
                    permission: PermissionRequest,
                    token: PermissionToken
                ) {
                    token.continuePermissionRequest()
                }

                override fun onPermissionGranted(response: PermissionGrantedResponse) {
                    actionGranted.invoke()
                }

                override fun onPermissionDenied(response: PermissionDeniedResponse) {
                    actionDenied?.invoke()
                }
            })
            .withErrorListener {
            }
            .check()
    }


}



