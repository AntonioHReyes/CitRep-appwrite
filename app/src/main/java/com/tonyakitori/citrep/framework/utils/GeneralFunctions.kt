package com.tonyakitori.citrep.framework.utils

import androidx.fragment.app.Fragment
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener

fun Fragment.checkPermissions(list: List<String>, callback: ((Boolean) -> Unit) = {}) {
    Dexter.withActivity(this.requireActivity())
        .withPermissions(list)
        .withListener(object : MultiplePermissionsListener {
            override fun onPermissionsChecked(report: MultiplePermissionsReport?) {
                if (report?.grantedPermissionResponses?.size!! > 0) {
                    callback(true)
                } else {
                    callback(false)
                }
            }

            override fun onPermissionRationaleShouldBeShown(
                permissions: MutableList<PermissionRequest>?,
                token: PermissionToken?
            ) {
                token?.continuePermissionRequest()
            }

        }).check()
}