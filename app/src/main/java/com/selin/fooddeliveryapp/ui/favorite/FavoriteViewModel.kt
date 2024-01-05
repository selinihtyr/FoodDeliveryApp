package com.selin.fooddeliveryapp.ui.favorite

import androidx.lifecycle.ViewModel
import com.selin.fooddeliveryapp.data.repo.FoodRepo
import javax.inject.Inject

class FavoriteViewModel @Inject constructor(
    private val repo: FoodRepo
) : ViewModel(){


}