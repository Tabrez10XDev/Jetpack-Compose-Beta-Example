package com.example.inteliheadsinternship.app

import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.inteliheadsinternship.R
import com.example.inteliheadsinternship.data.Data
import com.example.inteliheadsinternship.ui.screens.HomeScreen
import com.example.inteliheadsinternship.ui.screens.MainScreen
import com.example.inteliheadsinternship.ui.theme.InteliheadsInternshipTheme
import com.example.inteliheadsinternship.util.Status
import com.example.inteliheadsinternship.viewmodel.MainViewmodel

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewmodel = MainViewmodel()

        setContent {
            InteliheadsInternshipTheme {
                var data = mutableStateListOf<Data>()
                viewmodel.CartData.observe(this, Observer { response ->
                    when (response.status){
                        Status.SUCCESS ->{
                            val cartItems = response.data!!
                            data.addAll(cartItems.data)

                        }
                        Status.LOADING -> {
                            Log.d("Lj","Loading")

                        }
                        Status.ERROR->{
                            Log.d("Lj",response.message.toString())
                        }
                    }
                })
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    MainScreen(data)
                }
            }
        }
    }
}
