package com.papageorgiouk.curfnsurf.data

interface Storage {

    fun saveString(key: String, value: String)

    fun loadString(key: String): String?

    fun saveLong(key: String, value: Long)

    fun loadLong(key: String, defaultValue: Long): Long

}