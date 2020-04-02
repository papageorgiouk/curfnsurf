package com.papageorgiouk.curfnsurf.domain.id

import com.papageorgiouk.curfnsurf.data.Storage
import com.papageorgiouk.curfnsurf.domain.KEY_ID

class SaveIdUseCase(private val storage: Storage) {

    fun execute(id: String) {
        storage.saveString(KEY_ID, id)
    }

}