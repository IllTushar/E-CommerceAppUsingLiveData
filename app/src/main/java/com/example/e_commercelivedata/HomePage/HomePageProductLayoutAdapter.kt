package com.example.e_commercelivedata.HomePage

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.e_commercelivedata.R
import com.example.e_commercelivedata.Room.ProductInfo.ProductCacheTable

class HomePageProductLayoutAdapter(val context: Context, val productList: List<ProductCacheTable>) :
    RecyclerView.Adapter<HomePageProductLayoutAdapter.myViewHolder>() {
    class myViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.title)
        val category: TextView = itemView.findViewById(R.id.category)
        val productImage: ImageView = itemView.findViewById(R.id.imageView)
        val price: TextView = itemView.findViewById(R.id.price)
        val addToCart: AppCompatButton = itemView.findViewById(R.id.addToCart)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): myViewHolder {
        val view =
            LayoutInflater.from(context).inflate(R.layout.product_single_row_xml, parent, false)
        return myViewHolder(view)
    }

    override fun getItemCount(): Int {
        return productList.size
    }

    override fun onBindViewHolder(holder: myViewHolder, position: Int) {
        holder.price.text = "$ ${productList[position].price}"
        Glide.with(context).load(productList[position].imageURI)
            .placeholder(R.drawable.baseline_search_24).into(holder.productImage)
        holder.title.text = "Title: ${productList[position].title.toUpperCase()}"
        holder.category.text = "Category: ${productList[position].category.toUpperCase()}"

        holder.addToCart.setOnClickListener {

        }
    }
}