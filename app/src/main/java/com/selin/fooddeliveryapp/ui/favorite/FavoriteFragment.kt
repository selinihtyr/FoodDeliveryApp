package com.selin.fooddeliveryapp.ui.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.selin.fooddeliveryapp.databinding.FragmentFavoriteBinding
import com.selin.fooddeliveryapp.domain.FavoriteFood
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteFragment : Fragment() {
    private lateinit var binding: FragmentFavoriteBinding
    private val viewModel: FavoriteViewModel by viewModels()
    private val adapter: FavoriteAdapter by lazy {
        FavoriteAdapter(
            favoriteFoods = emptyList(),
            foodCallbacks = object : FavoriteAdapter.FavoriteCallback {
                override fun onClickDelete(favoriteFood: FavoriteFood) {
                    viewModel.deleteFoodFromFavorite(favoriteFood.id)
                }
            }
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
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
        with(binding.rvFavorite) {
            val layoutManager = LinearLayoutManager(requireContext())
            addItemDecoration(
                DividerItemDecoration(
                    requireContext(), layoutManager.orientation
                )
            )
        }
    }

    private fun initView() = with(binding) {
        rvFavorite.adapter = adapter
        rvFavorite.layoutManager =
            StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL)
        ibBack.setOnClickListener {
            requireActivity().onBackPressed()
        }
    }

    private fun observe() {
        viewModel._list.observe(viewLifecycleOwner) { favoriteFoods ->
            (binding.rvFavorite.adapter as FavoriteAdapter).updateData(favoriteFoods)
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.getAllFavoriteFoods()
    }
}