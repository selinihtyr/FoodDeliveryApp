package com.selin.fooddeliveryapp.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.selin.fooddeliveryapp.data.model.response.FoodResponse
import com.selin.fooddeliveryapp.data.source.remote.FoodApi
import com.selin.fooddeliveryapp.data.repo.FoodRepo
import com.selin.fooddeliveryapp.utils.constans.AppConstants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FoodViewModel @Inject constructor(
    private val repository: FoodRepo,
    private val api: FoodApi
) : ViewModel() {
    private val list = MutableLiveData<List<FoodResponse>>()
    val _list = MutableLiveData<List<FoodResponse>>()
    val showMessage = MutableSharedFlow<String>()

    private val _showLogoutConfirmationDialog = MutableLiveData<Boolean>()
    val showLogoutConfirmationDialog: LiveData<Boolean> get() = _showLogoutConfirmationDialog

    init {
        getAllFoods()
    }

    private fun getAllFoods() {
        viewModelScope.launch {
            try {
                val allFoods = repository.getAllFoods()
                list.value = allFoods
                _list.value = allFoods
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun addToCart(food: FoodResponse) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = api
                .addFoodToCart(
                    food.name,
                    food.imageName,
                    food.price.toInt(),
                    1,
                    AppConstants.USERNAME
                )
            if (response.isSuccess) {
                showMessage.emit("Item added to cart successfully")
            }
        }
    }

    fun searchFoods(query: String) {
        val allFoods = list.value ?: emptyList()
        val filteredResults = allFoods.filter { food ->
            food.name.contains(query, ignoreCase = true)
        }
        _list.value = filteredResults
    }

    fun onLogoutClicked() {
        _showLogoutConfirmationDialog.value = true
    }

    fun onLogoutConfirmationShow() {
        _showLogoutConfirmationDialog.value = false
    }

    fun logout() {
        Firebase.auth.signOut()
    }
}