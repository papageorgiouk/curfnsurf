package com.papageorgiouk.curfnsurf.ui.form.id

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import com.papageorgiouk.curfnsurf.R
import kotlinx.android.synthetic.main.id_fragment.*
import org.koin.android.viewmodel.ext.android.viewModel

class IdFragment(val onNext: (() -> Unit)) : Fragment(R.layout.id_fragment) {

    private val viewModel: IdViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        input_id.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_NEXT) {
                onNext()
                return@setOnEditorActionListener true
            }

            false
        }
    }

    override fun onResume() {
        super.onResume()

        if (input_id.requestFocus()) {
            val imm = context!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(input_id, InputMethodManager.SHOW_IMPLICIT)
        }
    }

}
