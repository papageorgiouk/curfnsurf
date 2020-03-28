package com.papageorgiouk.curfnsurf.ui.form.id

import androidx.lifecycle.ViewModel
import com.papageorgiouk.curfnsurf.data.FormManager

class IdViewModel(private val formManager: FormManager) : ViewModel() {

    var id: String? = null
        set(value) {
            field = value
            formManager.id = value
        }

}
