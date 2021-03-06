package com.example.inteliheadsinternship.ui.screens

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import com.example.inteliheadsinternship.data.Data

@Composable
fun HomeScreen(listItems: SnapshotStateList<Data> = SnapshotStateList<Data>()){
    CategoriesLazyColumn(listItems = listItems)
}

@Composable
fun CategoriesLazyColumn(
    modifier: Modifier = Modifier,
    listItems: SnapshotStateList<Data>,
) {
    LazyColumn(
        modifier = modifier,
    ) { items(listItems){item->
        Text(item.name)
    }

    }
}