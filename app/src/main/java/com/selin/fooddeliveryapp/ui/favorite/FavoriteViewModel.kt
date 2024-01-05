package com.selin.fooddeliveryapp.ui.favorite

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.selin.fooddeliveryapp.data.model.local.FavoriteFood
import com.selin.fooddeliveryapp.data.repo.FoodRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val repo: FoodRepo) : ViewModel() {

    private val list = MutableLiveData<List<FavoriteFood>>()
    val _list = MutableLiveData<List<FavoriteFood>>()

    init {
        getAllFavoriteFoods()
    }

    fun getAllFavoriteFoods() {
        viewModelScope.launch {
            try {
                val allFavoriteFoods = repo.getFoodToFavorite()
                list.value = allFavoriteFoods
                _list.value = allFavoriteFoods
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun deleteFoodFromFavorite(food: FavoriteFood) {
        viewModelScope.launch {
            repo.deleteFoodFromFavorite(food.id)
            getAllFavoriteFoods()
        }
    }
}