package com.papageorgiouk.curfnsurf.domain.postcode

import com.papageorgiouk.curfnsurf.data.Storage
import com.papageorgiouk.curfnsurf.domain.KEY_POSTCODE

class SavePostcodeUseCase(private val storage: Storage) {

    fun execute(postcode: Long) {
        storage.saveLong(KEY_POSTCODE, postcode)
    }

}