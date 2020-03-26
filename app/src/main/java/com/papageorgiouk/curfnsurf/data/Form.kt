package com.papageorgiouk.curfnsurf.data

import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.channels.sendBlocking
import kotlinx.coroutines.flow.asFlow

class FormManager {

    var purpose: Purpose? = null
        set(value) {
            field = value
            check()
        }
    var postCode: Int? = null
        set(value) {
            field = value
            check()
        }
    var id: String? = null
        set(value) {
            field = value
            check()
        }

    private val stateBroadcastChannel = BroadcastChannel<FormState>(1)

    private val onSendBroadcastChannel = BroadcastChannel<Unit>(1)

    fun onSendClicked() {
        onSendBroadcastChannel.sendBlocking(Unit)
    }

    private fun check() {
        val state = if (purpose  == null || postCode == null || id == null) FormState.Incomplete("Some null data")
        else FormState.Complete(Form(purpose!!, postCode!!, id!!))

        stateBroadcastChannel.sendBlocking(state)
    }

    fun observeForm() = stateBroadcastChannel.asFlow()

    fun observeSend() = onSendBroadcastChannel.asFlow()

}

sealed class FormState {
    class Incomplete(val message: String) : FormState()
    class Complete(val form: Form) : FormState()
}

data class Form(
    val purpose: Purpose,
    val postCode: Int,
    val id: String
) {
    fun toSms() = "${purpose.number} $id $postCode"
}