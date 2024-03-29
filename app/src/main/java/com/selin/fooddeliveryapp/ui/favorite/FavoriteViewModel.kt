package com.selin.fooddeliveryapp.ui.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.selin.fooddeliveryapp.data.repo.FoodRepo
import com.selin.fooddeliveryapp.domain.FavoriteFood
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val repo: FoodRepo
) : ViewModel() {

    private val list = MutableLiveData<List<FavoriteFood>>()
    val _list: LiveData<List<FavoriteFood>> = list

    fun getAllFavoriteFoods() {
        viewModelScope.launch(Dispatchers.IO) {
            val favoriteFoods = repo.getFoodToFavorite()
            withContext(Dispatchers.Main) {
                list.value = favoriteFoods
            }
        }
    }

    fun deleteFoodFromFavorite(id: Int) {
        val updateList = list.value?.filter { it.id != id } ?: emptyList()
        list.value = updateList

        viewModelScope.launch {
            repo.deleteFoodFromFavorite(id,)
        }
    }
}