package com.papageorgiouk.curfnsurf.ui.home

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import cafe.adriel.krumbsview.model.Krumb
import com.papageorgiouk.curfnsurf.R
import com.papageorgiouk.curfnsurf.ui.form.id.IdFragment
import com.papageorgiouk.curfnsurf.ui.form.postcode.PostCodeFragment
import com.papageorgiouk.curfnsurf.ui.form.purpose.PurposeFragment
import kotlinx.android.synthetic.main.fragment_home.*
import org.koin.android.viewmodel.ext.android.viewModel

class HomeFragment : Fragment(R.layout.fragment_home) {

    private val homeViewModel: HomeViewModel by viewModel()

    private val screenTitles by lazy { resources.getStringArray(R.array.screen_titles)}

    private val pagerAdapter by lazy { FragmentsAdapter(parentFragmentManager, this.lifecycle) { moveForward() } }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        pager.adapter = pagerAdapter
        pager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {

            private var previousSelected: Int = -1

            override fun onPageScrollStateChanged(state: Int) {
                super.onPageScrollStateChanged(state)
            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels)
            }

            override fun onPageSelected(position: Int) {
                val isBack = position < previousSelected
                val isForward = position > previousSelected

                previousSelected = position

                if (isBack) onScrolledBack(position)
                else if (isForward)  onScrolledForward(position)
            }
        })

        krumbs.setOnPreviousItemClickListener { moveBack() }

    }

    private fun moveBack() {
        pager.currentItem = pager.currentItem - 1
    }

    private fun moveForward() {
        if (pager.currentItem == pagerAdapter.itemCount - 1) end()
        else pager.currentItem = pager.currentItem + 1
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

}

class FragmentsAdapter(fm: FragmentManager, lifecycle: Lifecycle, val onNext: (() -> Unit)) : FragmentStateAdapter(fm, lifecycle) {

    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> PurposeFragment(onNext)
            1 -> IdFragment(onNext)
            2 -> PostCodeFragment(onNext)
            else -> throw IllegalStateException("Invalid viewpager position")
        }
    }

}
