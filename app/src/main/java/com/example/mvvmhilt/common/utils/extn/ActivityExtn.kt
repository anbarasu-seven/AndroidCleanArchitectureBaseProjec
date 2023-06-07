package com.example.mvvmhilt.common.utils.extn

import android.app.Activity
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar

/**
 * This extension function display a string as a toast msg in activity
 *@param message user-defined message
 */
@Composable
fun ShowToast(message: String) {
    Toast.makeText(
        LocalContext.current,
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