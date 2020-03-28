package com.papageorgiouk.curfnsurf.ui.form.postcode

import androidx.lifecycle.ViewModel
import com.papageorgiouk.curfnsurf.data.FormManager

class PostCodeViewModel(private val formManager: FormManager) : ViewModel() {

    fun setPostCode(postCode: Long) {
        formManager.postCode = postCode
    }

}
