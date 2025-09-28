package com.example.e_commercelivedata.HomePage

import android.os.Bundle
import android.view.View

import androidx.appcompat.app.AppCompatActivity

import com.example.e_commercelivedata.Room.ProductInfo.ProductQueries
import com.example.e_commercelivedata.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    @Inject
    lateinit var dao: ProductQueries

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.progressBar.visibility = View.VISIBLE
        dao.getAllUser().observe(this, { product ->
            binding.progressBar.visibility = View.GONE
            val adapter = HomePageProductLayoutAdapter(this@MainActivity, product)
            binding.productList.adapter = adapter
        })
    }
}