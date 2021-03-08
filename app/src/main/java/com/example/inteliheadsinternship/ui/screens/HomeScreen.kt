package com.example.inteliheadsinternship.ui.screens

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.util.Log
import androidx.annotation.DrawableRes
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.example.inteliheadsinternship.data.Data
import com.example.inteliheadsinternship.data.SubCategory
import com.example.inteliheadsinternship.util.ExpandableCard
import com.example.inteliheadsinternship.viewmodel.MainViewmodel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import com.example.inteliheadsinternship.R



@ExperimentalAnimationApi
@Composable
fun HomeScreen(listItems: SnapshotStateList<Data> = SnapshotStateList<Data>(), viewModel: MainViewmodel){
    val expandedCardIds = viewModel.expandedCardIdsList.collectAsState()

    Scaffold {
    CategoriesLazyColumn(listItems = listItems, viewModel = viewModel, expandedCardIds = expandedCardIds )

    }
}

@ExperimentalAnimationApi
@Composable
fun CategoriesLazyColumn(
    modifier: Modifier = Modifier,
    listItems: SnapshotStateList<Data>,
    viewModel: MainViewmodel,
    expandedCardIds : State<List<Int>>
) {
//    LazyColumn(
//        modifier = modifier,
//    ) { items(listItems){item->
//        CategoriesCard(item)
//    }
//
//    }
    LazyColumn {
        itemsIndexed(listItems) { _, card ->
            ExpandableCard(
                card = card,
                onCardArrowClick = { viewModel.onCardArrowClicked(card.id) },
                expanded = expandedCardIds.value.contains(card.id),
            )
        }
    }
}

@ExperimentalAnimationApi
@Composable
fun CategoriesCard(cards : Data) {
//    Card(
//        elevation = 12.dp,
//        shape = RoundedCornerShape(8.dp),
//        modifier = Modifier
//            .padding(12.dp)
//            .fillMaxWidth()
//    ) {
//        Column(
//            horizontalAlignment = Alignment.Start
//        ) {
//            Text(
//                text = item.name,
//                fontStyle = FontStyle.Italic,
//                fontFamily = FontFamily.Serif,
//                fontSize = 16.sp,
//                textAlign = TextAlign.Start,
//                modifier = Modifier.padding(5.dp)
//            )
//        }
//
//        Column(
//            horizontalAlignment = Alignment.End
//        ) {
//
//
//            IconButton(
//                onClick =
//                { Log.d("Lj", "Buttons") },
//            ) {
//                Icon(
//                    imageVector = Icons.Filled.Add, "Sub-Categories"
//                )
//            }
//
//        }
//
//        Spacer(Modifier.height(4.dp))
//
//    }

}


@Composable
fun subCategories(data : SubCategory){
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(16.dp)
    ) {

        val img = loadPicture(url = data.icon, defaultImage = R.drawable.ic_launcher_background).value
        img?.let {  Image(
            bitmap = img.asImageBitmap(),
            contentDescription = "Sub-Categories",
            modifier = Modifier
                .height(100.dp)
                .width(100.dp)
                .shadow(28.dp)
        )
        Text(
            text = data.name,
            modifier = Modifier
                .fillMaxWidth()
                .padding(6.dp),
            textAlign = TextAlign.Center,
        )
    }
    }
}


@ExperimentalCoroutinesApi
@Composable
fun loadPicture(url: String, @DrawableRes defaultImage: Int): MutableState<Bitmap?> {

    val bitmapState: MutableState<Bitmap?> = mutableStateOf(null)

    // show default image while image loads
    Glide.with(LocalContext.current)
        .asBitmap()
        .load(defaultImage)
        .into(object : CustomTarget<Bitmap>() {
            override fun onLoadCleared(placeholder: Drawable?) { }
            override fun onResourceReady(
                resource: Bitmap,
                transition: Transition<in Bitmap>?
            ) {
                bitmapState.value = resource
            }
        })

    // get network image
    Glide.with(LocalContext.current)
        .asBitmap()
        .load(url)
        .into(object : CustomTarget<Bitmap>() {
            override fun onLoadCleared(placeholder: Drawable?) { }
            override fun onResourceReady(
                resource: Bitmap,
                transition: Transition<in Bitmap>?
            ) {
                bitmapState.value = resource
                Log.d("Lj",resource.toString())
            }
        })

    return bitmapState
}
