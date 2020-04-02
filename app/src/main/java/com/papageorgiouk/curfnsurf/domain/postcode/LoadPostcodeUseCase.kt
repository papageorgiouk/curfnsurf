package com.papageorgiouk.curfnsurf.domain.postcode

import com.papageorgiouk.curfnsurf.data.Storage
import com.papageorgiouk.curfnsurf.domain.KEY_POSTCODE

private const val DEFAULT_POSTCODE = -222L

class LoadPostcodeUseCase(private val storage: Storage) {

    fun execute(): String = when (val loadedPostcode = storage.loadLong(KEY_POSTCODE, DEFAULT_POSTCODE)) {
        DEFAULT_POSTCODE -> ""
        else -> loadedPostcode.toString()
    }

}