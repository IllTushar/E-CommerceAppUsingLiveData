package com.example.e_commercelivedata.HomePage

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.e_commercelivedata.R
import com.example.e_commercelivedata.Room.Cart.CartEntity
import com.example.e_commercelivedata.Room.ProductCache.ProductCacheEntity
import com.example.e_commercelivedata.Room.Queries.Queries
import com.example.e_commercelivedata.Room.WishListEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomePageProductLayoutAdapter(
    private val context: Context,
    private val productList: MutableList<ProductCacheEntity>,
    private val dao: Queries,
    private var cartMap: MutableMap<Int, Int> = mutableMapOf()
) : RecyclerView.Adapter<HomePageProductLayoutAdapter.MyViewHolder>() {

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.title)
        val category: TextView = itemView.findViewById(R.id.category)
        val productImage: ImageView = itemView.findViewById(R.id.imageView)
        val price: TextView = itemView.findViewById(R.id.price)
        val addToCart: AppCompatButton = itemView.findViewById(R.id.addToCart)
        val productQuantityLayout: ConstraintLayout =
            itemView.findViewById(R.id.productQuantityLayout)
        val add: TextView = itemView.findViewById(R.id.productAdd)
        val subtract: TextView = itemView.findViewById(R.id.productDecrease)
        val productQuantity: TextView = itemView.findViewById(R.id.productQuantity)
        val saveItem: ImageView = itemView.findViewById(R.id.save_item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view =
            LayoutInflater.from(context).inflate(R.layout.product_single_row_xml, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int = productList.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val product = productList[position]

        // Load product info
        holder.title.text = product.title.uppercase()
        holder.category.text = product.category.uppercase()
        holder.price.text = "$ ${product.price}"

        Glide.with(context)
            .load(product.imageURI)
            .placeholder(R.drawable.baseline_search_24)
            .into(holder.productImage)

        // Get current quantity from cartMap
        var temp = cartMap[product.productId] ?: 0


        (context as? LifecycleOwner)?.lifecycleScope?.launch(Dispatchers.IO) {
            val isInWishList = checkProductItemPresent(productId = product.productId)
            withContext(Dispatchers.Main) {
                if (isInWishList) {
                    Glide.with(context).load(R.drawable.filled_heart).into(holder.saveItem)
                } else {
                    Glide.with(context).load(R.drawable.un_filled_heart).into(holder.saveItem)
                }
            }
        }



        holder.saveItem.setOnClickListener {
            (context as? LifecycleOwner)?.lifecycleScope?.launch(Dispatchers.IO) {
                val isInWishlist = checkProductItemPresent(productId = product.productId)

                // Switch to Main thread for UI updates
                withContext(Dispatchers.Main) {
                    if (isInWishlist) {
                        Glide.with(context).load(R.drawable.un_filled_heart).into(holder.saveItem)
                    } else {
                        Glide.with(context).load(R.drawable.filled_heart).into(holder.saveItem)
                    }
                }

                // Back to IO thread for database operation
                if (isInWishlist) {
                    removerItemFromWishList(productId = product.productId)
                } else {
                    addWishList(productId = product.productId)
                }
            }
        }
        // Set UI based on quantity
        if (temp > 0) {
            holder.addToCart.visibility = View.GONE
            holder.productQuantityLayout.visibility = View.VISIBLE
            holder.productQuantity.text = temp.toString()
        } else {
            holder.addToCart.visibility = View.VISIBLE
            holder.productQuantityLayout.visibility = View.GONE
        }

        // Add button click
        holder.add.setOnClickListener {
            if (temp < product.totalQuantity) {
                temp += 1
                cartMap[product.productId] = temp
                holder.productQuantity.text = temp.toString()
                updateCartInDB(product.productId, temp)
            }
        }

        // Subtract button click
        holder.subtract.setOnClickListener {
            if (temp > 1) {
                temp -= 1
                cartMap[product.productId] = temp
                holder.productQuantity.text = temp.toString()
                updateCartInDB(product.productId, temp)
            } else if (temp == 1) {
                temp = 0
                cartMap.remove(product.productId)
                holder.productQuantityLayout.visibility = View.GONE
                holder.addToCart.visibility = View.VISIBLE
                removeFromCartInDB(product.productId)
            }
        }

        // Add to cart click
        holder.addToCart.setOnClickListener {
            if (product.totalQuantity > 0) {
                temp = 1
                cartMap[product.productId] = 1
                holder.addToCart.visibility = View.GONE
                holder.productQuantityLayout.visibility = View.VISIBLE
                holder.productQuantity.text = "1"
                updateCartInDB(product.productId, 1)
            }
        }
    }


    private fun removerItemFromWishList(productId: Int) {
        (context as? LifecycleOwner)?.lifecycleScope?.launch(Dispatchers.IO) {
            dao.removeFromWishList(productId)
        }
    }

    private suspend fun checkProductItemPresent(productId: Int): Boolean {
        return withContext(Dispatchers.IO) {
            dao.getWishListItem(productId)
        }
    }

    private fun addWishList(productId: Int) {
        val wishlist = WishListEntity(productId)
        (context as? LifecycleOwner)?.lifecycleScope?.launch(Dispatchers.IO) {
            dao.insertIntoWishList(wishlist)
        }
    }

    private fun removeFromCartInDB(productId: Int) {
        (context as? LifecycleOwner)?.lifecycleScope?.launch(Dispatchers.IO) {
            dao.deleteProduct(productId)
        }
    }

    private fun updateCartInDB(productId: Int, quantity: Int) {
        val cartItem = CartEntity(productId = productId, cartQuantity = quantity)
        (context as? LifecycleOwner)?.lifecycleScope?.launch(Dispatchers.IO) {
            dao.insertCartItems(cartItem)
        }
    }


}
