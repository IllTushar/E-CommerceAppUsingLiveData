package com.example.e_commercelivedata.HomePage

import android.os.Bundle
import android.view.View

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope

import com.example.e_commercelivedata.Room.Queries.Queries
import com.example.e_commercelivedata.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var dao: Queries

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: HomePageProductLayoutAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.progressBar.visibility = View.VISIBLE

        // Initialize adapter with empty list


        loadProductsAndCart()
    }

    private fun loadProductsAndCart() {
        lifecycleScope.launch(Dispatchers.IO) {


            val products = dao.getAllUser() // Load products
            val cartList = dao.getProductsInCart() // Load cart

            val cartMap = cartList.associate { it.productId to it.cartQuantity }.toMutableMap()

            // prevent the raised condition
            withContext(Dispatchers.Main) {
                if (products.isEmpty()) {
                    // Show loading until API populates DB
                    binding.progressBar.visibility = View.VISIBLE
                } else {
                    binding.progressBar.visibility = View.GONE
                    adapter = HomePageProductLayoutAdapter(
                        this@MainActivity,
                        products.toMutableList(),
                        dao,
                        cartMap
                    )
                    binding.productList.adapter = adapter
                }
            }
        }
    }

}
