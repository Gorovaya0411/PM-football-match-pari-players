package com.footballmatch.sportselect.utill

import android.app.Activity
import android.content.Intent

inline fun <reified T> launchActivityWithFinish(fromContext: Activity) {
    val intent = Intent(fromContext, T::class.java)
    fromContext.startActivity(intent)
    fromContext.finish()
}