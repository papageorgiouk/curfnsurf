package com.papageorgiouk.curfnsurf.data

import android.content.Context
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

private const val SHARED_PREFERENCES_STORAGE = "SHARED_PREFERENCES_STORAGE"

val dataModule = module {
    factory<Storage> { StorageImpl(androidContext().getSharedPreferences(SHARED_PREFERENCES_STORAGE, Context.MODE_PRIVATE)) }
}