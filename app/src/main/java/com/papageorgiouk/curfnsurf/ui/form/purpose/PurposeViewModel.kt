package com.papageorgiouk.curfnsurf.ui.form.purpose

import androidx.lifecycle.ViewModel
import com.papageorgiouk.curfnsurf.data.FormManager
import com.papageorgiouk.curfnsurf.data.Purpose
import com.papageorgiouk.curfnsurf.data.PurposeProvider
import kotlinx.coroutines.flow.flowOf

class PurposeViewModel(private val purposeProvider: PurposeProvider, private val formManager: FormManager) : ViewModel() {

    val purposesFlow = flowOf(purposeProvider.purposes)

    fun onPurposeClicked(purpose: Purpose) {
        formManager.purpose = purpose
    }

}
