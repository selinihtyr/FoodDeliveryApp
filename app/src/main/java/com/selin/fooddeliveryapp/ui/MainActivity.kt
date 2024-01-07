package com.selin.fooddeliveryapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.selin.fooddeliveryapp.R
import com.selin.fooddeliveryapp.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private val navHostFragment: NavHostFragment by lazy {
        supportFragmentManager.findFragmentById(R.id.splashFragmentContainerView) as NavHostFragment
    }

    private val navController: NavController by lazy {
        navHostFragment.navController
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupBottomNavigation()

        navController.addOnDestinationChangedListener { _, destination, _ ->
            handleBottomNavigationVisibility(destination)
        }
    }

    private fun setupBottomNavigation() {
        NavigationUI.setupWithNavController(binding.bottomNavView, navController)
    }

    private fun handleBottomNavigationVisibility(destination: NavDestination) {
        binding.bottomNavView.isVisible = when (destination.id) {
            R.id.homepageFragment, R.id.cartFragment, R.id.favoriteFragment ->
                true
            else -> false
        }
    }
}