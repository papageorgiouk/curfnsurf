package com.papageorgiouk.curfnsurf.domain

import com.papageorgiouk.curfnsurf.domain.id.LoadIdUseCase
import com.papageorgiouk.curfnsurf.domain.id.SaveIdUseCase
import com.papageorgiouk.curfnsurf.domain.postcode.LoadPostcodeUseCase
import com.papageorgiouk.curfnsurf.domain.postcode.SavePostcodeUseCase
import org.koin.dsl.module

const val KEY_ID = "KEY_ID"
const val KEY_POSTCODE = "KEY_POSTCODE"

val domainModule = module {
    factory { SaveIdUseCase(get()) }
    factory { LoadIdUseCase(get()) }
    factory { SavePostcodeUseCase(get()) }
    factory { LoadPostcodeUseCase(get()) }
}