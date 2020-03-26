package com.papageorgiouk.curfnsurf

import android.content.Context

const val PREFS = "CurfnSurfPrefs"

class SharedPrefs(context: Context) {

    private val prefs = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)


}