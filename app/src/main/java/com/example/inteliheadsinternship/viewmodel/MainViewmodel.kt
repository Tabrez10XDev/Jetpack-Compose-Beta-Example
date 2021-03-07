package com.example.inteliheadsinternship.viewmodel

import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.inteliheadsinternship.api.RetrofitInstance.Companion.api
import com.example.inteliheadsinternship.data.CartItems
import com.example.inteliheadsinternship.data.Data
import com.example.inteliheadsinternship.util.ExpandableCardModel
import com.example.inteliheadsinternship.util.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Response

class MainViewmodel() : ViewModel() {

    val CartData : MutableLiveData<Resource<CartItems>> = MutableLiveData()


    private val _cards = MutableStateFlow(listOf<ExpandableCardModel>())
    val cards: StateFlow<List<ExpandableCardModel>> get() = _cards

    private val _expandedCardIdsList = MutableStateFlow(listOf<Int>())
    val expandedCardIdsList: StateFlow<List<Int>> get() = _expandedCardIdsList


    init {
        viewModelScope.launch {
            getCartItems()
        }
    }

    private suspend fun getCartItems(){
        CartData.postValue(Resource.loading(null))
        try {
                val response = api.getData()
                CartData.postValue(handleCartData(response))
        }catch(e : Exception){
            CartData.postValue(Resource.error(e.message.toString(),null))
        }
    }

    private fun handleCartData(response: Response<CartItems>) : Resource<CartItems> {
        if(response.isSuccessful){
            response.body()?.let {
                return Resource.success(it)
            }
        }
        return Resource.error(response.message(), null)

    }

    fun onCardArrowClicked(cardId: Int) {
        _expandedCardIdsList.value = _expandedCardIdsList.value.toMutableList().also { list ->
            if (list.contains(cardId)) list.remove(cardId) else list.add(cardId)
        }
    }
}