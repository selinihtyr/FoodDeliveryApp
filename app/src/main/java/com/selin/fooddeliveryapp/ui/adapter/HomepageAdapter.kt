package com.selin.fooddeliveryapp.ui.adapter

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.selin.fooddeliveryapp.R
import com.selin.fooddeliveryapp.data.entity.Foods
import com.selin.fooddeliveryapp.data.entity.Username
import com.selin.fooddeliveryapp.databinding.CardDesignBinding
import com.selin.fooddeliveryapp.retrofit.FoodsApi
import com.selin.fooddeliveryapp.retrofit.RetrofitClient
import com.selin.fooddeliveryapp.ui.viewModel.HomepageViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomepageAdapter(private val mContext:Context, private var foodsList: List<Foods>, val viewModel: HomepageViewModel)
    : RecyclerView.Adapter<HomepageAdapter.CardDesignHolder>(){

    inner class CardDesignHolder(val design: CardDesignBinding) : RecyclerView.ViewHolder(design.root){
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardDesignHolder {
        val binding = CardDesignBinding.inflate(LayoutInflater.from(mContext), parent, false)
        return CardDesignHolder(binding)
    }

    fun updateData(newData: List<Foods>) {
        foodsList = newData
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return foodsList.size
    }

    override fun onBindViewHolder(holder: CardDesignHolder, position: Int) {
        val food = foodsList.get(position)
        val f = holder.design
        f.tvFoodName.text = food.yemek_adi
        f.tvPrice.text = food.yemek_fiyat.toString() + "₺"

        f.ibAdd.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                val response = RetrofitClient.getClient("http://kasimadalan.pe.hu/")
                    .create(FoodsApi::class.java)
                    .addFoodToCart(food.yemek_adi, food.yemek_resim_adi, food.yemek_fiyat.toInt(), 1, Username.username)

                if (response.success == 1) {
                    Log.d("AddToCart", "Item added to cart")
                } else {
                    Log.e("AddToCart", "Failed to add item to cart")
                }
            }
            Toast.makeText(mContext, "Item added to cart successfuly", Toast.LENGTH_SHORT).show()
        }

        f.ivFood.setOnClickListener {
            val food = foodsList.get(position)

            val bundle = Bundle()
            bundle.putString("yemek_adi", food.yemek_adi)
            bundle.putString("yemek_fiyat", food.yemek_fiyat)
            bundle.putString("yemek_resim_adi", food.yemek_resim_adi)
            Navigation.findNavController(it).navigate(R.id.transitationDetail, bundle)
        }

        var isLiked = false
        f.ibLike.setOnClickListener {
            if (isLiked) {
                f.ibLike.setImageResource(R.drawable.like)
            } else {
                f.ibLike.setImageResource(R.drawable.red_heart)
            }
            isLiked = !isLiked
        }

        val url = "http://kasimadalan.pe.hu/yemekler/resimler/${food.yemek_resim_adi}"
        Glide.with(mContext)
            .load(url)
            .override(300, 300)
            .into(f.ivFood)
    }
}