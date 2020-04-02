package com.papageorgiouk.curfnsurf.ui.form.postcode

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.lifecycle.lifecycleScope
import com.papageorgiouk.curfnsurf.R
import com.papageorgiouk.curfnsurf.ui.form.FormFragment
import kotlinx.android.synthetic.main.post_code_fragment.*
import kotlinx.coroutines.flow.*
import org.koin.android.viewmodel.ext.android.viewModel
import reactivecircus.flowbinding.android.widget.editorActionEvents
import reactivecircus.flowbinding.android.widget.textChanges

class PostCodeFragment : FormFragment(R.layout.post_code_fragment) {

    private val viewModel: PostCodeViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.pcErrorFlow
            .map { it?.let { getString(it) } }
            .onEach { box_postcode.error = it }
            .launchIn(lifecycleScope)

        input_post_code.editorActionEvents {
            if (it.actionId == EditorInfo.IME_ACTION_SEND) {
                if (box_postcode.error != null) return@editorActionEvents false
                else {
                    proceed()
                    return@editorActionEvents true
                }
            } else return@editorActionEvents false
        }.debounce(200)
            .launchIn(lifecycleScope)

        input_post_code.textChanges(true)
            .debounce(200)
            .drop(1)
            .onEach { viewModel.setPostCodeInput(it.toString()) }
            .launchIn(lifecycleScope)

        input_post_code.setText(viewModel.getPostCode())
    }

    override fun onResume() {
        super.onResume()

        if (input_post_code.requestFocus()) {
            val imm = context!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(input_post_code, InputMethodManager.SHOW_IMPLICIT)
        }
    }
}
