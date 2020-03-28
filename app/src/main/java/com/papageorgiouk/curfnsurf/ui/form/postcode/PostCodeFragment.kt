package com.papageorgiouk.curfnsurf.ui.form.postcode

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.papageorgiouk.curfnsurf.R
import com.papageorgiouk.curfnsurf.ui.inputOk
import kotlinx.android.synthetic.main.post_code_fragment.*
import kotlinx.coroutines.flow.*
import org.koin.android.viewmodel.ext.android.viewModel
import reactivecircus.flowbinding.android.widget.textChanges

class PostCodeFragment(private val onNext: (() -> Unit)) : Fragment(R.layout.post_code_fragment) {

    private val viewModel: PostCodeViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        input_post_code.setOnEditorActionListener { v, actionId, _ ->
            when (actionId) {
                EditorInfo.IME_ACTION_DONE -> {
                    if (input_post_code.inputOk()) onNext()
                    else input_post_code.error = getString(R.string.cant_be_empty)

                    false
                }
                else -> false
            }
        }

        input_post_code.textChanges(true)
            .debounce(200)
            .filterNotNull()
            .map { it.toString().toInt() }
            .catch {  }
            .onEach { viewModel.setPostCode(it) }
            .launchIn(lifecycleScope)

    }

    override fun onResume() {
        super.onResume()

        if (input_post_code.requestFocus()) {
            val imm = context!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(input_post_code, InputMethodManager.SHOW_IMPLICIT)
        }
    }
}
