package com.selin.fooddeliveryapp.ui

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.selin.fooddeliveryapp.ui.signIn.SignInFragment
import com.selin.fooddeliveryapp.ui.signUp.SignUpFragment

private const val NUM_PAGES = 2
class LoginTabAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) : FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount(): Int {
        return NUM_PAGES
    }

    override fun createFragment(position: Int): Fragment {
        when (position) {
            0 -> return SignInFragment()
            1 -> return SignUpFragment()
        }
        return LoginTabFragment()
    }
}