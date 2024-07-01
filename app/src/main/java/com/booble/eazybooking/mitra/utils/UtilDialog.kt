package com.booble.eazybooking.mitra.utils

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import com.booble.eazybooking.mitra.R

object UtilDialog {

    @SuppressLint("InflateParams")
    fun setProgressDialog(context: Context): Dialog {
        val dialog = Dialog(context, android.R.style.Theme_Black_NoTitleBar_Fullscreen)
        val inflate = LayoutInflater.from(context).inflate(R.layout.dialog_progress_bar, null)
        dialog.setContentView(inflate)
        dialog.setCancelable(true)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        return dialog
    }
}