package com.example.mvvmhilt.common.utils.extn

import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar

fun Fragment.close() = fragmentManager?.popBackStack()

/**
 * This extension function display a string as a toast msg in fragment
 *@param message user-defined message
 */
fun Fragment.showToast(message: String) {
    Toast.makeText(
        requireContext(),
        message,
        Toast.LENGTH_SHORT
    ).show()
}

/**
 * This extension function display a string as a snack bar msg in fragment
 *@param message user-defined message
 */
fun Fragment.showSnackbar(message: String) {
    view?.let {
        Snackbar.make(it, message, Snackbar.LENGTH_SHORT).show()
    }
}

fun Fragment.hideKeyboard() {
    val token = requireActivity().currentFocus?.windowToken
    val imm: InputMethodManager? =
        ContextCompat.getSystemService(requireActivity(), InputMethodManager::class.java)
    imm?.hideSoftInputFromWindow(token, 0)
}