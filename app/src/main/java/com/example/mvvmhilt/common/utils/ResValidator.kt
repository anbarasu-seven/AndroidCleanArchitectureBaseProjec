package com.example.mvvmhilt.common.utils

import android.content.Context

class ResValidator {

    fun isEqual(context: Context, resId: Int, stringValue: String): Boolean =
        context.getString(resId) == stringValue
}