package com.tonyakitori.citrep.framework.utils

import androidx.fragment.app.Fragment
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import java.text.SimpleDateFormat
import java.util.*

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

fun Long.getFormattedDate(onlyDate: Boolean = true): String {
    val messageTime = Calendar.getInstance()
    messageTime.minimalDaysInFirstWeek = 4
    messageTime.timeInMillis = this
    val now = Calendar.getInstance()
    val timeFormatString = "HH:mm"
    val dateTimeFormatString = "dd/MM/yy"
    val dateTodayFormatString = "dd/MM/yy"
    val formatToday = SimpleDateFormat(dateTodayFormatString, Locale.getDefault())

    return if (formatToday.format(now.timeInMillis) == formatToday.format(messageTime.timeInMillis)) {
        if (onlyDate) {
            "Hoy"
        } else {
            val format = SimpleDateFormat(timeFormatString, Locale.getDefault())
            format.format(Date(messageTime.timeInMillis))
        }
    } else if (now.get(Calendar.DAY_OF_YEAR) - messageTime.get(Calendar.DAY_OF_YEAR) == 1 &&
        now.get(Calendar.YEAR) == messageTime.get(Calendar.YEAR)
    ) {
        if (onlyDate) {
            "Ayer"
        } else {
            val format = SimpleDateFormat(timeFormatString, Locale.getDefault())
            format.format(Date(messageTime.timeInMillis))
        }
    } else if (now.get(Calendar.YEAR) == messageTime.get(Calendar.YEAR)) {
        if (onlyDate) {
            val format = SimpleDateFormat(dateTimeFormatString, Locale.getDefault())
            format.format(Date(messageTime.timeInMillis))
        } else {
            val format = SimpleDateFormat(timeFormatString, Locale.getDefault())
            format.format(Date(messageTime.timeInMillis))
        }
    } else {
        if (onlyDate) {
            val format = SimpleDateFormat("dd/MM/yy", Locale.getDefault())
            format.format(Date(messageTime.timeInMillis))
        } else {
            val format = SimpleDateFormat(timeFormatString, Locale.getDefault())
            format.format(Date(messageTime.timeInMillis))
        }
    }
}