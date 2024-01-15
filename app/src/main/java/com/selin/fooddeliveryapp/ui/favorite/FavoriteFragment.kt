package com.selin.fooddeliveryapp.ui.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.selin.fooddeliveryapp.data.model.entity.FavoriteEntity
import com.selin.fooddeliveryapp.databinding.FragmentFavoriteBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteFragment : Fragment() {
    private lateinit var binding: FragmentFavoriteBinding
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
        initView()
        observe()
    }

    private fun initVariables() {
        val layoutManager = LinearLayoutManager(requireContext())
        binding.rvFavorite.layoutManager = layoutManager
    }

    private fun initView() {
        val adapter = FavoriteAdapter(
            favoriteFoods = emptyList(),
            foodCallbacks = object : FavoriteAdapter.FavoriteCallback {
                override fun onClickDelete(favoriteFood: FavoriteEntity) {
                    viewModel.deleteFoodFromFavorite(favoriteFood.id)
                }
            }
        )
        binding.rvFavorite.adapter = adapter
        binding.rvFavorite.layoutManager =
            StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL)
    }

    private fun observe() {
        viewModel._list.observe(viewLifecycleOwner) { favoriteFoods ->
            FavoriteAdapter(
                favoriteFoods = favoriteFoods,
                foodCallbacks = object : FavoriteAdapter.FavoriteCallback {
                    override fun onClickDelete(favoriteFood: FavoriteEntity) {
                        viewModel.deleteFoodFromFavorite(favoriteFood.id)
                    }
                }
            )
            (binding.rvFavorite.adapter as FavoriteAdapter).updateData(favoriteFoods)
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.getAllFavoriteFoods()
    }
}