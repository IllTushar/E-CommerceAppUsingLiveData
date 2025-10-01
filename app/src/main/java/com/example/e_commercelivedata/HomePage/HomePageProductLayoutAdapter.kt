package com.example.e_commercelivedata.HomePage

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.constraintlayout.widget.ConstraintLayout
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
        val productQuantityLayout: ConstraintLayout =
            itemView.findViewById(R.id.productQuantityLayout);
        val add: TextView = itemView.findViewById(R.id.productAdd)
        val subtract: TextView = itemView.findViewById(R.id.productDecrease)
        val productQuantity: TextView = itemView.findViewById(R.id.productQuantity)
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
        val product = productList[position]

        holder.price.text = "$ ${product.price}"
        Glide.with(context)
            .load(product.imageURI)
            .placeholder(R.drawable.baseline_search_24)
            .into(holder.productImage)

        holder.title.text = "Title: ${product.title.uppercase()}"
        holder.category.text = "Category: ${product.category.uppercase()}"

        // Set visibility based on selectedQuantity
        if (product.quantity > 0) {
            holder.addToCart.visibility = View.GONE
            holder.productQuantityLayout.visibility = View.VISIBLE
            holder.productQuantity.text = product.quantity.toString()
        } else {
            holder.addToCart.visibility = View.VISIBLE
            holder.productQuantityLayout.visibility = View.GONE
        }

        holder.add.setOnClickListener {
            if (product.quantity < product.totalQuantity) {
                product.quantity += 1
                productList[position].quantity = product.quantity
                holder.productQuantity.text = product.quantity.toString()
                notifyItemChanged(position)
            }
        }

        holder.subtract.setOnClickListener {
            if (product.quantity > 1) {
                product.quantity -= 1
                productList[position].quantity = product.quantity
                holder.productQuantity.text = product.quantity.toString()
                notifyItemChanged(position)
            } else if (product.quantity == 1) {
                product.quantity = 0
                productList[position].quantity = product.quantity
                holder.productQuantityLayout.visibility = View.GONE
                holder.addToCart.visibility = View.VISIBLE
                notifyItemChanged(position)
            }
        }


        holder.addToCart.setOnClickListener {
            if (product.totalQuantity > 0) {
                product.quantity = 1
                productList[position].quantity = product.quantity
                holder.addToCart.visibility = View.GONE
                holder.productQuantityLayout.visibility = View.VISIBLE
                holder.productQuantity.text = "1"
                notifyItemChanged(position)
            }
        }
    }

}