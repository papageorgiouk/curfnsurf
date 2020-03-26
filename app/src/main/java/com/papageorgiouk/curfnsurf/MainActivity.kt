package com.papageorgiouk.curfnsurf

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.papageorgiouk.curfnsurf.data.Form
import com.papageorgiouk.curfnsurf.data.FormManager
import com.papageorgiouk.curfnsurf.data.FormState
import com.papageorgiouk.curfnsurf.ui.withLatestFrom
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

internal const val SMS_NUMBER = 8998

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    private val formManager by inject<FormManager>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
//        val appBarConfiguration = AppBarConfiguration(
//            setOf(
//                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications
//            )
//        )
//        setupActionBarWithNavController(navController, appBarConfiguration)
//        navView.setupWithNavController(navController)

        lifecycleScope.launch {

            formManager.observeSend()
                .withLatestFrom(formManager.observeForm()) { _, formState -> formState }
                .collect { handleFormState(it) }

        }
    }

    private fun handleFormState(state: FormState) {
        when (state) {
            is FormState.Complete -> sendSms(state.form)
        }

    }

    private fun sendSms(form: Form) {
        val intent= Intent(Intent.ACTION_VIEW, Uri.parse("smsto:$SMS_NUMBER"))
            .apply { putExtra("sms_body", form.toSms()) }

        startActivity(intent)
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}
