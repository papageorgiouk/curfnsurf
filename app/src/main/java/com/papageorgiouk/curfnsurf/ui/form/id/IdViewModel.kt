package com.papageorgiouk.curfnsurf.ui.form.id

import androidx.lifecycle.ViewModel
import com.papageorgiouk.curfnsurf.R
import com.papageorgiouk.curfnsurf.data.FormManager
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.channels.sendBlocking
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.map

@ExperimentalCoroutinesApi
@FlowPreview
class IdViewModel(private val formManager: FormManager) : ViewModel() {

    private val idInputChannel = BroadcastChannel<String>(1)

    /**
     * Returns the string resource (Int) of the error message
     */
    val idErrorFlow: Flow<Int?> = idInputChannel.asFlow()
        .map { if (it.isNullOrBlank()) R.string.cant_be_empty else null }

    var id: String? = null
        set(value) {
            field = value
            value?.let { idInputChannel.sendBlocking(value) }
            formManager.id = value
        }

}
