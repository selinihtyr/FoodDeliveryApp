package com.selin.fooddeliveryapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import com.selin.fooddeliveryapp.databinding.FragmentLoginBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginTabFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding
    private val loginArray = arrayOf("Sign In", "Sign Up")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    private fun initViews() {
        binding.viewPagerLogin.adapter = LoginTabAdapter(childFragmentManager, lifecycle)

        TabLayoutMediator(binding.tabLayout, binding.viewPagerLogin) { tab, position ->
            tab.text = loginArray[position]
        }.attach()
    }
}