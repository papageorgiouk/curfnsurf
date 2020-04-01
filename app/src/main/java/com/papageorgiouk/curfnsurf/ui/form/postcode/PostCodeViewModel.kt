package com.papageorgiouk.curfnsurf.ui.form.postcode

import androidx.lifecycle.ViewModel
import com.papageorgiouk.curfnsurf.data.FormManager
import com.papageorgiouk.curfnsurf.domain.postcode.LoadPostcodeUseCase
import com.papageorgiouk.curfnsurf.domain.postcode.SavePostcodeUseCase

class PostCodeViewModel(
    private val formManager: FormManager,
    private val savePostcodeUseCase: SavePostcodeUseCase,
    private val loadPostcodeUseCase: LoadPostcodeUseCase
) : ViewModel() {

    fun setPostCode(postCode: Long) {
        formManager.postCode = postCode
        savePostcodeUseCase.execute(postCode)
    }

    fun getPostCode() = loadPostcodeUseCase.execute()
}
