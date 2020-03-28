package com.papageorgiouk.curfnsurf.data

import android.content.Context
import com.papageorgiouk.curfnsurf.R
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.channels.sendBlocking
import kotlinx.coroutines.flow.asFlow

@FlowPreview
@ExperimentalCoroutinesApi
class FormManager(private val context: Context) {

    var purpose: Purpose? = null
        set(value) {
            field = value
            update()
        }
    var postCode: Long? = null
        set(value) {
            field = value
            update()
        }
    var id: String? = null
        set(value) {
            field = value
            update()
        }


    fun getFormState(): FormState {
        return if (purpose == null || postCode == null || id == null) {
            FormState.Incomplete(context.getString(R.string.form_null_msg))
        }
        else FormState.Complete(Form(purpose!!, postCode!!, id!!))
    }

    private val stateBroadcastChannel = BroadcastChannel<FormState>(1)

    private fun update() {
        val state = getFormState()

        stateBroadcastChannel.sendBlocking(state)
    }

    fun observeFormStatus() = stateBroadcastChannel.asFlow()

}

sealed class FormState {
    class Incomplete(val message: String) : FormState()
    class Complete(val form: Form) : FormState()
}

data class Form(
    val purpose: Purpose,
    val postCode: Long,
    val id: String
) {
    fun toSms() = "${purpose.number} $id $postCode"
}