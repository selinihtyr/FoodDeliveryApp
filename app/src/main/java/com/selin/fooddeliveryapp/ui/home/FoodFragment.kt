package com.selin.fooddeliveryapp.ui.home

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isGone
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.selin.fooddeliveryapp.R
import com.selin.fooddeliveryapp.data.model.response.FoodResponse
import com.selin.fooddeliveryapp.databinding.FragmentHomepageBinding
import com.selin.fooddeliveryapp.ui.detail.DetailFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomePageFragment : Fragment() {
    private lateinit var binding: FragmentHomepageBinding
    private lateinit var adapter: FoodAdapter
    private val viewModel: FoodViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomepageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initVariables()
        initViews()
        observe()
    }

    private fun initVariables() {
        adapter = FoodAdapter(
            foods = mutableListOf(),
            foodCallbacks = object : FoodAdapter.FoodCallback {
                override fun onClickDetail(food: FoodResponse) {
                    val bundle = Bundle().apply { putParcelable(DetailFragment.KEY_FOOD, food) }
                    findNavController().navigate(R.id.action_home_to_detail, bundle)
                }

                override fun onClickAddToCart(food: FoodResponse) {
                    viewModel.addToCart(food)
                }
            }
        )
    }

    private fun initViews() = with(binding) {
        ibMap.setOnClickListener { navigateToMapFragment() }
        ibLogout.setOnClickListener {
            viewModel.onLogoutClicked()
        }
        etSearch.addTextChangedListener {
            viewModel.searchFoods(it.toString())
        }
        rvHome.layoutManager =
            StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.HORIZONTAL)
        rvHome.adapter = adapter
    }

    private fun observe() {
        viewModel._list.observe(viewLifecycleOwner) { foods ->
            binding.progressBar.isGone = true
            adapter.updateData(foods)
        }

        lifecycleScope.launch {
            viewModel.showMessage.collectLatest { message ->
                Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
            }
        }

        viewModel.showLogoutConfirmationDialog.observe(viewLifecycleOwner) { shouldShowDialog ->
            if (shouldShowDialog) {
                showLogoutConfirmationDialog()
                viewModel.onLogoutConfirmationShow()
            }
        }
    }

    private fun showLogoutConfirmationDialog() {
        val alertDialogBuilder = AlertDialog.Builder(requireContext())
        alertDialogBuilder.apply {
            setTitle("Logout")
            setMessage("Are you sure you want to logout?")
            setPositiveButton("Yes") { _, _ ->
                viewModel.logout()
                findNavController().navigate(R.id.home_to_login)
            }
            setNegativeButton("No") { dialog, _ ->
                dialog.dismiss()
            }
            setCancelable(true)
        }

        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }

    private fun navigateToMapFragment() {
        findNavController().navigate(R.id.mapFragment)
    }
}
