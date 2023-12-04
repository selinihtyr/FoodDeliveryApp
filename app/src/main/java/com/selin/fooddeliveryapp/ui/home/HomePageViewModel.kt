package com.selin.fooddeliveryapp.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.selin.fooddeliveryapp.data.model.remote.Food
import com.selin.fooddeliveryapp.data.model.local.Username
import com.selin.fooddeliveryapp.data.repo.FoodRepo
import com.selin.fooddeliveryapp.data.remote.FoodApi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomePageViewModel @Inject constructor(
    private val repository: FoodRepo,
    private val api: FoodApi
) : ViewModel() {
    private val list = MutableLiveData<List<Food>>()
    val filteredFoods = MutableLiveData<List<Food>>()
    val showMessage = MutableSharedFlow<String>()

    init {
        getAllFoods()
    }

    private fun getAllFoods() {
        viewModelScope.launch {
            try {
                val allFoods = repository.getAllFoods()
                list.value = allFoods
                filteredFoods.value = allFoods
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun addToCart(food: Food) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = api
                .addFoodToCart(
                    food.name,
                    food.imageName,
                    food.price.toInt(),
                    1,
                    Username.username
                )
            if (response.success == 1) {
                showMessage.emit("Item added to cart successfully")
            }
        }
    }

    fun searchFoods(query: String) {
        val allFoods = list.value ?: emptyList()
        val filteredResults = allFoods.filter { food ->
            food.name.contains(query, ignoreCase = true)
        }
        filteredFoods.value = filteredResults
    }
}