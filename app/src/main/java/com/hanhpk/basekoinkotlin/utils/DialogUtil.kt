package com.dmm.ptown.utils

import android.content.Context
import androidx.appcompat.app.AlertDialog

object DialogUtil {
    fun displayDialog(
        context: Context,
        message: String,
        action: () -> Unit
    ) {

        val alertDialog = AlertDialog.Builder(context)
            .create()

        alertDialog.setMessage(message)
        alertDialog.setButton(
            AlertDialog.BUTTON_POSITIVE, context.getString(android.R.string.ok)
        ) { dialog, _ ->
            action()
            dialog.dismiss()
        }
        alertDialog.show()
    }
}