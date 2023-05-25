package com.example.mvvmhilt.utils

import android.content.Context

class ResValidator {

    fun isEqual(context: Context, resId: Int, stringValue: String): Boolean =
        context.getString(resId) == stringValue
}