package com.example.inteliheadsinternship.data

data class SubCategory(
    val icon: String,
    val id: Int,
    val name: String,
    val parent_id: Int,
    val subCategory: List<Any>
)