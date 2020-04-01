package com.papageorgiouk.curfnsurf.ui

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.papageorgiouk.curfnsurf.ui.form.id.IdFragment
import com.papageorgiouk.curfnsurf.ui.form.postcode.PostCodeFragment
import com.papageorgiouk.curfnsurf.ui.form.purpose.PurposeFragment

class FormFragmentsAdapter(fm: FragmentManager, lifecycle: Lifecycle) : FragmentStateAdapter(fm, lifecycle) {

    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> PurposeFragment()
            1 -> IdFragment()
            2 -> PostCodeFragment()
            else -> throw IllegalStateException("Invalid viewpager position")
        }
    }

}

fun ViewPager2.hasBackStack() = this.currentItem > 0

fun ViewPager2.popBackStack() {
    this.currentItem = this.currentItem - 1
}

inline fun ViewPager2.directionalNavigationListener(lifecycleOwner: LifecycleOwner, crossinline direction: (Direction, Int) -> Unit) {

    val pageChangeCallback = object : ViewPager2.OnPageChangeCallback() {

        private var previousSelected: Int = -1

        override fun onPageSelected(position: Int) {
            val isBack = position < previousSelected
            val isForward = position > previousSelected

            previousSelected = position

            if (isBack) direction(Direction.BACK, position)
            else if (isForward)  direction(Direction.FORWARD, position)
        }
    }

    lifecycleOwner.lifecycle.addObserver(object : LifecycleObserver {
        @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
        fun onCreated(source: LifecycleOwner?) {
            this@directionalNavigationListener.registerOnPageChangeCallback(pageChangeCallback)

        }

        @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
        fun onDestroy() {
            this@directionalNavigationListener.unregisterOnPageChangeCallback(pageChangeCallback)
        }
    })

}

enum class Direction { BACK, FORWARD }

