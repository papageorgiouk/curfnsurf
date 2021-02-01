package com.papageorgiouk.curfnsurf

import android.app.ActivityOptions
import android.app.Application
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.lifecycleScope
import cafe.adriel.krumbsview.model.Krumb
import com.microsoft.appcenter.analytics.Analytics
import com.papageorgiouk.curfnsurf.data.FormManager
import com.papageorgiouk.curfnsurf.data.FormState
import com.papageorgiouk.curfnsurf.ui.*
import com.papageorgiouk.curfnsurf.ui.about.AboutActivity
import com.papageorgiouk.curfnsurf.ui.form.FormFragment
import com.papageorgiouk.curfnsurf.ui.form.id.IdFragment
import com.papageorgiouk.curfnsurf.ui.form.postcode.PostCodeFragment
import com.papageorgiouk.curfnsurf.ui.form.purpose.PurposeFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.koin.android.viewmodel.ext.android.viewModel
import reactivecircus.flowbinding.android.view.clicks


internal const val SMS_NUMBER = 8998

class MainActivity : AppCompatActivity(R.layout.activity_main), FormFragmentListener {

    private val viewModel by viewModel<MainViewModel>()

    private val pagerAdapter by lazy { FormFragmentsAdapter(supportFragmentManager, this.lifecycle) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)

        pager.apply {
            adapter = pagerAdapter
            directionalNavigationListener(this@MainActivity) { direction, position ->
                handleButtonVisibility(position)

                when (direction) {
                    Direction.BACK -> onScrolledBack(position)
                    Direction.FORWARD -> onScrolledForward(position)
                }
            }
        }

        krumbs.setOnPreviousItemClickListener { moveBack() }

        btn_send.clicks()
            .debounce(200)
            .onEach { viewModel.onSend() }
            .launchIn(lifecycleScope)

        viewModel.smsReadyChannel
            .asFlow()
            .onEach { sendSms(it) }
            .launchIn(lifecycleScope)

        btn_info.clicks()
            .debounce(200)
            .onEach { startAboutActivity() }
            .launchIn(lifecycleScope)
    }

    override fun onNext(fragment: FormFragment) {
        when (fragment) {
            is PurposeFragment -> moveForward()
            is IdFragment -> moveForward()
            is PostCodeFragment -> lifecycleScope.launch { viewModel.onSend() }
        }
    }

    private fun startAboutActivity() {
        val intent  = Intent(this, AboutActivity::class.java)
        startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle())
    }

    private fun handleButtonVisibility(position: Int) {
        when (position) {
            2 -> btn_send.showMove()
            else -> btn_send.hideMove()
        }
    }

    private fun sendSms(sms: String) {
        val intent= Intent(Intent.ACTION_VIEW, Uri.parse("smsto:$SMS_NUMBER"))
            .apply { putExtra("sms_body", sms) }

        Analytics.trackEvent("SMS sent")
        startActivity(intent)
    }

    private fun moveBack() {
        pager.currentItem = pager.currentItem - 1
    }

    private fun moveForward() {
        if (pager.currentItem == pagerAdapter.itemCount - 1) end()  //  counts as "Send"
        else pager.setCurrentItem(pager.currentItem + 1, true)
    }

    private fun onScrolledBack(position: Int) {
        //  if we scrolled from the viewpager, we need to remove the last item from the krumbs
        //  if the krumb was clicked, then the last one was automatically removed so we do nothing
        if (krumbs.getCurrentItem()?.title != viewModel.screenTitles[position]) { krumbs.removeLastItem() }
    }

    private fun onScrolledForward(position: Int) {
        val title = viewModel.screenTitles[position]
        krumbs.addItem(Krumb(title))

    }

    private fun end() {
        btn_send.callOnClick()
    }

    override fun onBackPressed() {
        if (pager.hasBackStack()) pager.popBackStack()
        else super.onBackPressed()
    }

}

interface FormFragmentListener {

    fun onNext(fragment: FormFragment)

}

class MainViewModel(app: Application, val formManager: FormManager) : AndroidViewModel(app) {

    val screenTitles: Array<String> by lazy { app.resources.getStringArray(R.array.screen_titles)}

    val smsReadyChannel = BroadcastChannel<String>(1)

    suspend fun onSend() {
        val formState = formManager.getFormState()

        if (formState is FormState.Complete) {
            val sms = formState.form.toSms()
            smsReadyChannel.send(sms)
        }
    }

}

