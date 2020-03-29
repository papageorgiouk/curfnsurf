package com.papageorgiouk.curfnsurf.ui.form.purpose

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.papageorgiouk.curfnsurf.data.FormManager
import com.papageorgiouk.curfnsurf.data.Purpose
import com.papageorgiouk.curfnsurf.data.PurposeProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class PurposeViewModel(private val purposeProvider: PurposeProvider, private val formManager: FormManager) : ViewModel() {

    val purposesFlow: Flow<List<Purpose>> = purposeProvider.getPurposes()

    fun onPurposeClicked(purpose: Purpose) {
        formManager.purpose = purpose
    }

}
