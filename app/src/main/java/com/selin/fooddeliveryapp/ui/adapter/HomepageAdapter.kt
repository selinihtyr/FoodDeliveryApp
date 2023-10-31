package com.selin.fooddeliveryapp.ui.adapter

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.selin.fooddeliveryapp.R
import com.selin.fooddeliveryapp.data.entity.Foods
import com.selin.fooddeliveryapp.data.entity.Username
import com.selin.fooddeliveryapp.databinding.CardDesignBinding
import com.selin.fooddeliveryapp.retrofit.FoodsApi
import com.selin.fooddeliveryapp.retrofit.RetrofitClient
import com.selin.fooddeliveryapp.ui.viewModel.HomepageViewModel
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomepageAdapter(
    private val context: Context,
    private var foodsList: List<Foods>,
    private val viewModel: HomepageViewModel) : RecyclerView.Adapter<HomepageAdapter.CardDesignHolder>(){

    inner class CardDesignHolder(val binding: CardDesignBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardDesignHolder {
        val binding = CardDesignBinding.inflate(LayoutInflater.from(context), parent, false)
        return CardDesignHolder(binding)
    }

    fun updateData(newData: List<Foods>) {
        foodsList = newData
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = foodsList.size

    override fun onBindViewHolder(holder: CardDesignHolder, position: Int) {
        val food = foodsList.get(position)
        val card = holder.binding

        setupFoodInfo(card, food)
        setAddToCartListener(card, food)
        setDetailClickListener(card, food)
        setLikeClickListener(card)
        loadImage(context, food, card)
    }

    private fun setupFoodInfo(card: CardDesignBinding, food: Foods) {
        val priceText = context.getString(R.string.price, food.yemek_fiyat)
        card.tvPrice.text = priceText
    }

    private fun setAddToCartListener(card: CardDesignBinding, food: Foods) {
        card.ibAdd.setOnClickListener {
            addToCart(food)
        }
    }

    private fun setDetailClickListener(card: CardDesignBinding, food: Foods) {
        card.ivFood.setOnClickListener {
            val bundle = Bundle().apply {
                putString("yemek_adi", food.yemek_adi)
                putString("yemek_fiyat", food.yemek_fiyat.toString())
                putString("yemek_resim_adi", food.yemek_resim_adi)
            }
            it.findNavController().navigate(R.id.transitationDetail, bundle)
        }
    }

    private fun setLikeClickListener(card: CardDesignBinding) {
        var isLiked = false
        card.ibLike.setOnClickListener {
            isLiked = !isLiked
            card.ibLike.setImageResource(if (isLiked) R.drawable.red_heart else R.drawable.like)
        }
    }

    private fun loadImage(context: Context, food: Foods, card: CardDesignBinding) {
        val imageUrl = "http://kasimadalan.pe.hu/yemekler/resimler/${food.yemek_resim_adi}"
        Glide.with(context)
            .load(imageUrl)
            .override(300, 300)
            .into(card.ivFood)
    }

    @OptIn(DelicateCoroutinesApi::class)
    private fun addToCart(food: Foods) {
        GlobalScope.launch(Dispatchers.IO) {
            val response = RetrofitClient.getClient("http://kasimadalan.pe.hu/")
                .create(FoodsApi::class.java)
                .addFoodToCart(food.yemek_adi, food.yemek_resim_adi, food.yemek_fiyat.toInt(), 1, Username.username)

            if (response.success == 1) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(context, "Item added to cart successfully", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }
}