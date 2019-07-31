package com.tanyayuferova.lifestylenews.ui.main

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.tanyayuferova.lifestylenews.ui.favorites.FavoritesFragment
import com.tanyayuferova.lifestylenews.ui.list.ListFragment
import timber.log.Timber

/**
 * Author: Tanya Yuferova
 * Date: 7/29/19
 */
class MainTabsAdapter(
    fragmentManager: FragmentManager
) : FragmentStatePagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getCount() = 2

    override fun getItem(position: Int): Fragment {
        return when(position) {
            0 -> ListFragment()
            1 -> FavoritesFragment()
            else -> throw Exception("Unknown fragment position $position")
        }
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when(position) {
            0 -> "Recent" //todo resources
            1 -> "Saved"
            else -> throw Exception("Unknown fragment position $position")
        }
    }

}