package com.selin.fooddeliveryapp.ui.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.selin.fooddeliveryapp.data.model.local.FavoriteFood
import com.selin.fooddeliveryapp.databinding.FragmentFavoriteBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteFragment : Fragment() {
    private lateinit var binding: FragmentFavoriteBinding
    private lateinit var adapter: FavoriteAdapter
    private val viewModel: FavoriteViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initVariables()
        initViews()
        observe()
    }

    private fun initVariables() {
        adapter = FavoriteAdapter(
            favoriteFoods = mutableListOf(),
            foodCallbacks = object : FavoriteAdapter.FavoriteCallback {
                override fun onClickDelete(favoriteFood: FavoriteFood) {
                    viewModel.deleteFoodFromFavorite(favoriteFood)
                }
            }
        )
    }

    private fun initViews() {
        binding.recyclerView.layoutManager = StaggeredGridLayoutManager(1, LinearLayoutManager.VERTICAL)
        binding.recyclerView.adapter = adapter
    }

    private fun observe() {
        viewModel._list.observe(viewLifecycleOwner) {
            adapter.updateData(it)
        }
    }
}