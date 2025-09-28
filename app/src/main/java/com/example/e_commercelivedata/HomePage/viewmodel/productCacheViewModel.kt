package com.example.e_commercelivedata.HomePage.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.e_commercelivedata.ApiRepo.Repository
import com.example.e_commercelivedata.HomePage.data.ProductResponseClass
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class productCacheViewModel @Inject constructor(val repository: Repository) : ViewModel() {

    private val _response = MutableLiveData<List<ProductResponseClass>>()
    val response: LiveData<List<ProductResponseClass>> = _response

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error
    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> get() = _loading

    init {
        fetchProduct()
    }

    fun fetchProduct() {
        viewModelScope.launch {
            _loading.value = true
            try {
                val responseData = repository.repoCall()
                _response.value = responseData
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                _loading.value = false
            }
        }
    }
}