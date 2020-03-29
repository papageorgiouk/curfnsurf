package com.papageorgiouk.curfnsurf.ui.about

import android.net.Uri
import android.os.Bundle
import android.transition.TransitionInflater
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.browser.customtabs.CustomTabsIntent
import androidx.recyclerview.widget.RecyclerView
import com.papageorgiouk.curfnsurf.R
import com.papageorgiouk.curfnsurf.ui.widget.ElasticDragDismissFrameLayout
import kotlinx.android.synthetic.main.about_activity.*
import kotlinx.android.synthetic.main.about_app.view.*
import java.security.InvalidParameterException

class AboutActivity : AppCompatActivity(R.layout.about_activity) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        content_card.addListener(object : ElasticDragDismissFrameLayout.ElasticDragDismissCallback() {
            override fun onDragDismissed() {
                // if we drag dismiss downward then the default reversal of the enter
                // transition would slide content upward which looks weird. So reverse it.
                if (content_card.translationY > 0) {
                    window.returnTransition = TransitionInflater.from(this@AboutActivity)
                        .inflateTransition(R.transition.about_return_downward)
                }
                finishAfterTransition()
            }
        })

        createSwipeablePages()

    }

    private fun createSwipeablePages() {

        val customTabIntent = CustomTabsIntent.Builder()
            .build()

        about_pager.apply {
            adapter = AboutAdapter() { customTabIntent.launchUrl(this@AboutActivity, Uri.parse("https://github.com/papageorgiouk/curfnsurf")) }

            val pageMargin = resources.getDimensionPixelSize(R.dimen.spacing_normal)
            setPageTransformer { page, position -> page.translationX = position * pageMargin }
        }
    }

}

class AboutAdapter(val onClick: () -> Unit) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun getItemViewType(position: Int): Int = position

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        @LayoutRes val layout = when (viewType) {
            0 -> R.layout.about_app
            1 -> R.layout.about_libraries
            else -> throw InvalidParameterException()
        }

        return AboutVH(
            LayoutInflater.from(parent.context)
                .inflate(layout, parent, false)
        )
    }

    override fun getItemCount(): Int = 2

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder.itemView.btn_github?.setOnClickListener { onClick() }
    }

    class AboutVH(itemView: View) : RecyclerView.ViewHolder(itemView)

}
