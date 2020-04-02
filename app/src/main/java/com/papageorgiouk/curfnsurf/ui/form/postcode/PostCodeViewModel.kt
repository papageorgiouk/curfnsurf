package com.papageorgiouk.curfnsurf.ui.form.postcode

import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel
import com.papageorgiouk.curfnsurf.R
import com.papageorgiouk.curfnsurf.data.FormManager
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.channels.sendBlocking
import kotlinx.coroutines.flow.*
import com.papageorgiouk.curfnsurf.domain.postcode.LoadPostcodeUseCase
import com.papageorgiouk.curfnsurf.domain.postcode.SavePostcodeUseCase

class PostCodeViewModel(
    private val formManager: FormManager,
    private val savePostcodeUseCase: SavePostcodeUseCase,
    private val loadPostcodeUseCase: LoadPostcodeUseCase
) : ViewModel() {

    private val pcInputChannel = BroadcastChannel<String>(1)

    val pcErrorFlow: Flow<Int?> = pcInputChannel.asFlow()
        .map { validate(it) }
        .map {
            when (it) {
                is Validity.Invalid -> it.message
                is Validity.Valid -> null
            }
    }

    fun setPostCodeInput(input: String) {
        pcInputChannel.sendBlocking(input)

        input.toLongOrNull()?.let {
            formManager.postCode = it
            savePostcodeUseCase.execute(it)}
    }

    fun getPostCode() = loadPostcodeUseCase.execute()

    private fun validate(input: String): Validity {
        return when {
            input.isNullOrBlank() -> Validity.Invalid(R.string.cant_be_empty)
            input.toLongOrNull() == null -> Validity.Invalid(R.string.numbers_only)
            else -> Validity.Valid
        }
    }

}

sealed class Validity {
    object Valid : Validity()
    data class Invalid(@StringRes val message: Int) : Validity()
}