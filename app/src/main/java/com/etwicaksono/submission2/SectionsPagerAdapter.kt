package com.etwicaksono.submission2

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class SectionsPagerAdapter(fm: Fragment) : FragmentStateAdapter(fm) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> fragment = UsersListFragment("Followers")
            1 -> fragment = UsersListFragment("Followings")
        }

        return fragment as Fragment
    }
}