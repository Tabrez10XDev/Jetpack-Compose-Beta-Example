package com.example.inteliheadsinternship.app

import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.Observer
import com.example.inteliheadsinternship.ui.theme.InteliheadsInternshipTheme
import com.example.inteliheadsinternship.util.Resource
import com.example.inteliheadsinternship.util.Status
import com.example.inteliheadsinternship.viewmodel.MainViewmodel

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewmodel = MainViewmodel()
        viewmodel.CartData.observe(this, Observer { response ->
            when (response.status){
                Status.SUCCESS ->{
                    Log.d("Lj",response.data.toString())
                }
                Status.LOADING -> {
                    Log.d("Lj","Loading")

                }
                Status.ERROR->{
                    Log.d("Lj",response.message.toString())

                }
            }
        })
        setContent {
            InteliheadsInternshipTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    Greeting("Android")
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    InteliheadsInternshipTheme {
        Greeting("Android")
    }
}