package com.papageorgiouk.curfnsurf.ui.form.id

import androidx.lifecycle.ViewModel
import com.papageorgiouk.curfnsurf.data.FormManager
import com.papageorgiouk.curfnsurf.domain.id.LoadIdUseCase
import com.papageorgiouk.curfnsurf.domain.id.SaveIdUseCase

class IdViewModel(
    private val formManager: FormManager,
    private val saveIdUseCase: SaveIdUseCase,
    private val loadIdUseCase: LoadIdUseCase
) : ViewModel() {

    var id: String? = loadIdUseCase.execute()
        set(value) {
            field = value
            formManager.id = value
            value?.let {
                saveIdUseCase.execute(it)
            }
        }

}
