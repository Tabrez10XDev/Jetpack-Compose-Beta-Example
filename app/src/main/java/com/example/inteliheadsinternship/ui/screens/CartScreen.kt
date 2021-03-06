package com.example.inteliheadsinternship.ui.screens

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp

@Composable
fun CartScreen(){
    Scaffold {
        AnimationScreen(CartAnimations.Cart)
        Text(
            "Cart is empty",
            fontFamily = FontFamily.SansSerif,
            fontStyle = FontStyle.Italic,
            fontSize = 30.sp,
            modifier = Modifier
                .fillMaxSize()
                .fillMaxHeight(),
            textAlign = TextAlign.Center
        )
    }
}