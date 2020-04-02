package com.papageorgiouk.curfnsurf.data

import android.content.SharedPreferences

class StorageImpl(private val sharedPreferences: SharedPreferences) : Storage {
    override fun saveString(key: String, value: String) {
        sharedPreferences.edit()
            .putString(key, value)
            .apply()
    }

    override fun loadString(key: String): String? =
        sharedPreferences.getString(key, null)

    override fun saveLong(key: String, value: Long) {
        sharedPreferences.edit()
            .putLong(key, value)
            .apply()
    }

    override fun loadLong(key: String, defaultValue: Long): Long =
        sharedPreferences.getLong(key, defaultValue)
}