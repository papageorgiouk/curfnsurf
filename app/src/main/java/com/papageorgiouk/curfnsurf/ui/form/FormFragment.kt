package com.papageorgiouk.curfnsurf.ui.form

import android.content.Context
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import com.papageorgiouk.curfnsurf.FormFragmentListener

abstract class FormFragment(@LayoutRes layoutId: Int) : Fragment(layoutId) {

    lateinit var listener: FormFragmentListener

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context is FormFragmentListener) listener = context
        else throw IllegalStateException("FormFragment must have a FormActivity as parent")
    }

    fun proceed() {
        listener.onNext(this)
    }

}