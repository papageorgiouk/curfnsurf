package com.papageorgiouk.curfnsurf

import com.papageorgiouk.curfnsurf.data.FormManager
import com.papageorgiouk.curfnsurf.data.PurposeProvider
import com.papageorgiouk.curfnsurf.ui.form.id.IdViewModel
import com.papageorgiouk.curfnsurf.ui.form.postcode.PostCodeViewModel
import com.papageorgiouk.curfnsurf.ui.form.purpose.PurposeViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    single { SharedPrefs(androidContext()) }

    single { PurposeProvider(androidContext()) }

    single { FormManager(androidContext()) }

    viewModel { MainViewModel(get(), get()) }

    viewModel { PurposeViewModel(get(), get()) }

    viewModel { IdViewModel(get()) }

    viewModel { PostCodeViewModel(get()) }

}