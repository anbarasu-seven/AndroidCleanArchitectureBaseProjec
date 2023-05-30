package com.example.mvvmhilt.utils.extn

import android.app.Activity
import android.content.Context
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment

/**
 * This extension function display a string as a toast msg in activity
 *@param message user-defined message
 */
fun Activity.showToast(message: String) {
    Toast.makeText(
        this,
        message,
        Toast.LENGTH_SHORT
    ).show()
}

fun Activity.hideKeyboard() {
    val token = currentFocus?.windowToken
    val imm: InputMethodManager? =
        ContextCompat.getSystemService(this, InputMethodManager::class.java)
    imm?.hideSoftInputFromWindow(token, 0)
}