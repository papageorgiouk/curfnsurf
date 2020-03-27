package com.papageorgiouk.curfnsurf.ui.form.id

import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.Fragment
import com.papageorgiouk.curfnsurf.R
import com.papageorgiouk.curfnsurf.ui.inputOk
import kotlinx.android.synthetic.main.id_fragment.*
import org.koin.android.viewmodel.ext.android.viewModel

class IdFragment(val onNext: (() -> Unit)) : Fragment(R.layout.id_fragment) {

    private val viewModel: IdViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        input_id.setOnEditorActionListener { v, actionId, event ->
            when (actionId) {
                EditorInfo.IME_ACTION_DONE -> {
                    if (input_id.inputOk()) {
                        onDone(v.text)
                        onNext()
                    } else  {
                        v.error = getString(R.string.cant_be_empty)
                    }
                    true
                }
                else -> false
            }
        }
    }

    private fun onDone(idText: CharSequence) {
        viewModel.onIdSet(idText.toString())
        onNext.invoke()
    }
}
