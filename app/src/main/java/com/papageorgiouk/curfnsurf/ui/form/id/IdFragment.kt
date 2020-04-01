package com.papageorgiouk.curfnsurf.ui.form.id

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.lifecycle.lifecycleScope
import com.papageorgiouk.curfnsurf.R
import com.papageorgiouk.curfnsurf.ui.form.FormFragment
import kotlinx.android.synthetic.main.id_fragment.*
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.android.viewmodel.ext.android.viewModel
import reactivecircus.flowbinding.android.widget.editorActionEvents
import reactivecircus.flowbinding.android.widget.textChanges

class IdFragment : FormFragment(R.layout.id_fragment) {

    private val viewModel: IdViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        input_id.editorActionEvents {
            return@editorActionEvents if (it.actionId == EditorInfo.IME_ACTION_NEXT) {
                proceed()
                true
            } else false
        }.launchIn(lifecycleScope)

        input_id.textChanges(true)
            .debounce(200)
            .onEach { viewModel.id = it.toString() }
            .launchIn(lifecycleScope)
    }

    override fun onResume() {
        super.onResume()

        if (input_id.requestFocus()) {
            val imm = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(input_id, InputMethodManager.SHOW_IMPLICIT)
        }
    }

}
