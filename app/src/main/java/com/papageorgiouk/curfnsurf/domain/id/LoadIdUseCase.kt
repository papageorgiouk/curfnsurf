package com.papageorgiouk.curfnsurf.domain.id

import com.papageorgiouk.curfnsurf.data.Storage
import com.papageorgiouk.curfnsurf.domain.KEY_ID

class LoadIdUseCase(private val storage: Storage) {

    fun execute(): String? = storage.loadString(KEY_ID)

}