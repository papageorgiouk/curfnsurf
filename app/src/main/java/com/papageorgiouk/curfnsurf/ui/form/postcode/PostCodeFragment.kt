package com.papageorgiouk.curfnsurf.ui.form.postcode

import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.Fragment
import com.papageorgiouk.curfnsurf.R
import com.papageorgiouk.curfnsurf.ui.inputOk
import kotlinx.android.synthetic.main.post_code_fragment.*
import org.koin.android.viewmodel.ext.android.viewModel

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

        btn_send.setOnClickListener {
            if (input_post_code.inputOk()) {
                try {
                    val parsedPostcode = input_post_code.text.toString().toInt()
                    viewModel.setPostCode(parsedPostcode)
                    viewModel.onSend()
                } catch (e: NumberFormatException) {
                    input_post_code.error = getString(R.string.error_needs_to_be_number)
                }
            } else {
                input_post_code.error = getString(R.string.cant_be_empty)
            }

        }
    }

}
