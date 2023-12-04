package com.selin.fooddeliveryapp.ui.cart

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.selin.fooddeliveryapp.data.model.remote.FoodCart
import com.selin.fooddeliveryapp.databinding.ItemViewCartCardBinding

class CartAdapter(
    private var cartList: List<FoodCart>,
    private val cartCallback: CartCallback
) : RecyclerView.Adapter<CartViewHolder>() {

    interface CartCallback {
        fun onClickDelete(cart: FoodCart)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val binding = ItemViewCartCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CartViewHolder(binding, cartCallback)
    }

    fun updateData(newData: List<FoodCart>) {
        cartList = newData
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return cartList.size
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val cart = cartList[position]
        holder.bind(cart)
        /*
                    c.ibTrash.setOnClickListener {
                        Snackbar.make(
                            it,
                            "Are you sure you want to delete from the cart?",
                            Snackbar.LENGTH_LONG
                        )
                            .setAction("Yes") {
                                deleteFromCart(cart, position)
                            }.show()
         }*/
    }
}

