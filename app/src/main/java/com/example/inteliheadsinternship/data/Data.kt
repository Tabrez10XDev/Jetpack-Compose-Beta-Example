package com.example.inteliheadsinternship.data

data class  Data(
    val icon: String,
    val id: Int,
    val name: String,
    val parent_id: Int,
    val subCategory: List<SubCategory>
)