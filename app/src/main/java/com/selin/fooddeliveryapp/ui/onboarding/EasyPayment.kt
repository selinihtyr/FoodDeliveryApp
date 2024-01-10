package com.selin.fooddeliveryapp.ui.onboarding

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.selin.fooddeliveryapp.R
import com.selin.fooddeliveryapp.databinding.FragmentOnboardingEasyPaymentBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EasyPayment : Fragment() {
    private lateinit var binding: FragmentOnboardingEasyPaymentBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentOnboardingEasyPaymentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    private fun initViews() {
        binding.btnFinish.setOnClickListener {
            findNavController().navigate(R.id.loginTabFragment)
            onBoardingFinished()
        }
    }

    private fun onBoardingFinished() {
        val sharedPref = activity?.getSharedPreferences("onBoarding", Context.MODE_PRIVATE)
        val editor = sharedPref?.edit()
        editor?.putBoolean("Finished", true)
        editor?.apply()
    }
}