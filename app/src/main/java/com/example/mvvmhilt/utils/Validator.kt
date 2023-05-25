package com.example.mvvmhilt.utils

object Validator {

    /**
     * the input is not valid if...
     * ...the username/password is empty
     * ...the username is less than 6 char
     * ...the password is less than 6 char
     * ...the password contains less than 2 digits
     */
    fun validateLoginInput(
        username: String,
        password: String,
    ): Boolean {
        if (username.isEmpty() || password.isEmpty()) {
            return false
        }
        if (username.length < 6) {
            return false
        }
        if (password.length < 6) {
            return false
        }
        if (password.count { it.isDigit() } < 2) {
            return false
        }
        return true
    }
}