package com.papageorgiouk.curfnsurf

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import cafe.adriel.krumbsview.model.Krumb
import com.papageorgiouk.curfnsurf.data.Form
import com.papageorgiouk.curfnsurf.data.FormManager
import com.papageorgiouk.curfnsurf.data.FormState
import com.papageorgiouk.curfnsurf.ui.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

internal const val SMS_NUMBER = 8998

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    private val formManager by inject<FormManager>()

    private val screenTitles by lazy { resources.getStringArray(R.array.screen_titles)}

    private val pagerAdapter by lazy { FormFragmentsAdapter(supportFragmentManager, this.lifecycle) { moveForward() } }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN

        lifecycleScope.launch {

            formManager.observeSend()
                .withLatestFrom(formManager.observeForm()) { _, formState -> formState }
                .collect { handleFormState(it) }

        }

        pager.apply {
            adapter = pagerAdapter
            directionalNavigationListener(this@MainActivity) { direction, position ->
                handleButton(position)

                when (direction) {
                    Direction.BACK -> onScrolledBack(position)
                    Direction.FORWARD -> onScrolledForward(position)
                }
            }
        }

        krumbs.setOnPreviousItemClickListener { moveBack() }
    }

    private fun handleFormState(state: FormState) {
        when (state) {
            is FormState.Complete -> sendSms(state.form)
        }

    }

    private fun handleButton(position: Int) {
        when (position) {
            2 -> btn_send.showMove()
            else -> btn_send.hideMove()
        }
    }

    private fun sendSms(form: Form) {
        val intent= Intent(Intent.ACTION_VIEW, Uri.parse("smsto:$SMS_NUMBER"))
            .apply { putExtra("sms_body", form.toSms()) }

        startActivity(intent)
    }

    private fun moveBack() {
        pager.currentItem = pager.currentItem - 1
    }

    private fun moveForward() {
        if (pager.currentItem == pagerAdapter.itemCount - 1) end()
        else pager.setCurrentItem(pager.currentItem + 1, true)
    }

    private fun onScrolledBack(position: Int) {
        //  if we scrolled from the viewpager, we need to remove the last item from the krumbs
        //  if the krumb was clicked, then the last one was automatically removed so we do nothing
        if (krumbs.getCurrentItem()?.title != screenTitles[position]) { krumbs.removeLastItem() }
        Log.d("Krumbs", "items are now: ${krumbs.getItems().map { it.title }}")

    }

    private fun onScrolledForward(position: Int) {
        val title = screenTitles[position]
//        Log.d("Krumbs", "adding $title item to ${krumbs.getItems().map { it.title }}")
        krumbs.addItem(Krumb(title))
        Log.d("Krumbs", "items are now: ${krumbs.getItems().map { it.title }}")

    }

    private fun end() {

    }


    override fun onBackPressed() {
        if (pager.hasBackStack()) pager.popBackStack()
        else super.onBackPressed()
    }
}