package com.papageorgiouk.curfnsurf.ui.form.postcode

import androidx.lifecycle.ViewModel
import com.papageorgiouk.curfnsurf.data.Form
import com.papageorgiouk.curfnsurf.data.FormManager
import com.papageorgiouk.curfnsurf.data.FormState

class PostCodeViewModel(private val formManager: FormManager) : ViewModel() {

    fun setPostCode(postCode: Int) {
        formManager.postCode = postCode
    }

    fun onSend() {
        formManager.onSendClicked()
    }

}
