package com.booble.eazybooking.mitra.utils

import android.content.Context
import android.content.Intent
import android.graphics.Paint
import android.os.Bundle
import android.text.Editable
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import com.booble.eazybooking.mitra.R

/**
 * Created by rivaldy on 01/07/21
 * Find me on my Github -> https://github.com/im-o
 **/

object UtilExtensions {
    fun <T> Context.openActivity(it: Class<T>, extras: Bundle.() -> Unit = {}) {
        val intent = Intent(this, it)
        intent.putExtras(Bundle().apply(extras))
        startActivity(intent)
    }

    fun View.isVisible(isVisible: Boolean) {
        visibility = if (isVisible) View.VISIBLE else View.GONE
    }

    fun View.isInVisible(isInVisible: Boolean) {
        visibility = if (isInVisible) View.INVISIBLE else View.VISIBLE
    }

    fun Context.myToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    fun Context.myError(message: String) {
        Toast.makeText(this, "ERROR : $message", Toast.LENGTH_LONG).show()
    }

    fun View.showSnackBar(message: String, action: (() -> Unit)? = null) {
        val snackBar = Snackbar.make(this, message, Snackbar.LENGTH_LONG)
        action?.let {
            snackBar.setAction(context.getString(R.string.retry)) {
                it()
            }
        }
        snackBar.show()
    }

    fun EditText.setTextEditable(text: String) {
        this.text = Editable.Factory.getInstance().newEditable(text)
    }

    fun TextView.setPaintFlag() {
        this.paintFlags = paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
    }

    fun Context.isValidate(editText: EditText): Boolean {
        if (editText.text.isNullOrEmpty()) {
            editText.error = getString(R.string.form_required)
            return false
        }
        return true
    }

    fun String.fromCommaToWords() = trim().splitToSequence(',').filter { it.isNotEmpty() }.toList()

    fun String.removeWhitespaces() = replace(" ", "")
}