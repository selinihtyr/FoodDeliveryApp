package com.selin.fooddeliveryapp.ui.onboarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.selin.fooddeliveryapp.R
import com.selin.fooddeliveryapp.databinding.FragmentOnboardingFastDeliveryBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FastDelivery : Fragment() {
    private lateinit var binding: FragmentOnboardingFastDeliveryBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentOnboardingFastDeliveryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    private fun initViews() {
        binding.btnNextFastDelivery.setOnClickListener {
            val viewPager = activity?.findViewById<ViewPager2>(R.id.viewPager)
            viewPager?.currentItem = 2
        }
    }
}