package com.selin.fooddeliveryapp.ui.home

import android.app.AlertDialog
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isGone
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.bumptech.glide.Glide
import com.selin.fooddeliveryapp.R
import com.selin.fooddeliveryapp.data.model.response.FoodResponse
import com.selin.fooddeliveryapp.databinding.FragmentHomepageBinding
import com.selin.fooddeliveryapp.ui.shared.SharedViewModel
import com.selin.fooddeliveryapp.utils.constans.AppConstants
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomePageFragment : Fragment() {
    private lateinit var binding: FragmentHomepageBinding
    private val viewModel: FoodViewModel by viewModels()
    private val sharedViewModel: SharedViewModel by activityViewModels()
    private val adapter: FoodAdapter by lazy {
        FoodAdapter(
            foods = mutableListOf(),
            foodCallbacks = object : FoodAdapter.FoodCallback {
                override fun onClickDetail(food: FoodResponse) {
                    val bundle = Bundle().apply { putParcelable(AppConstants.KEY_FOOD, food) }
                    findNavController().navigate(R.id.action_home_to_detail, bundle)
                }

                override fun onClickAddToCart(food: FoodResponse) {
                    viewModel.addToCart(food)
                }
            }
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomepageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        observe()
        observeSharedViewModel()
    }

    private fun initViews() = with(binding) {
        ibMap.setOnClickListener { findNavController().navigate(R.id.mapFragment) }
        etSearch.addTextChangedListener {
            viewModel.searchFoods(it.toString())
        }
        rvHome.layoutManager =
            StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.HORIZONTAL)
        rvHome.adapter = adapter
        ibMenu.setOnClickListener {
            drawerLayout.openDrawer(binding.navigationDrawer)
        }
        navigationDrawer.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.menuSetting -> Navigation.findNavController(requireView()).navigate(R.id.home_to_settings)
                R.id.menuAbout -> Navigation.findNavController(requireView()).navigate(R.id.home_to_about)
                R.id.menuLogout -> viewModel.onLogoutClicked()
            }
            true
        }
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

    private fun observeSharedViewModel() {
        val navigationView = binding.navigationDrawer
        val headerView = navigationView.getHeaderView(0)
        val headerPhoto = headerView.findViewById<ImageView>(R.id.ivProfilePicture)
        val userNameTextView: TextView = headerView.findViewById(R.id.etUserName)
        val mailTextView: TextView = headerView.findViewById(R.id.tvUserMail)

        sharedViewModel.loadUserName()

        sharedViewModel.userName.observe(viewLifecycleOwner) { userName ->
            userNameTextView.text = userName
        }

        sharedViewModel.loadMail()

        sharedViewModel.mail.observe(viewLifecycleOwner) { mail ->
            mailTextView.text = mail
        }

        sharedViewModel.loadPhoto()

        sharedViewModel.photo.observe(viewLifecycleOwner) { photo ->
            if (photo != null) {
                Glide.with(requireContext()).load(Uri.parse(photo)).into(headerPhoto)
            } else {
                headerPhoto.setImageResource(R.drawable.user_profile)
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
}
