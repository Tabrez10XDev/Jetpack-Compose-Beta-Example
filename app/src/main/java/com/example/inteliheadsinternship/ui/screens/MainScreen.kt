package com.example.inteliheadsinternship.ui.screens


import android.icu.text.Transliterator
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import androidx.navigation.compose.rememberNavController
import com.airbnb.lottie.LottieAnimationView
import com.airbnb.lottie.LottieDrawable
import com.example.inteliheadsinternship.R
import com.example.inteliheadsinternship.data.CartItems
import com.example.inteliheadsinternship.data.Data
import com.example.inteliheadsinternship.viewmodel.MainViewmodel
import java.util.*

sealed class BottomNavigationScreens(val route: String,val resourceId: Int, val icon: ImageVector) {
    object Home : BottomNavigationScreens("Home", R.string.home_route, Icons.Filled.Terrain)
    object Deals : BottomNavigationScreens("Deals", R.string.deals_route, Icons.Filled.Fireplace)
    object Cart : BottomNavigationScreens("Cart", R.string.cart_route, Icons.Filled.Cake)
}

sealed class CartAnimations(val animId: Int){
    object Home: CartAnimations(R.raw.frankensteindroid)
    object Deals: CartAnimations(R.raw.ghost)
    object Cart: CartAnimations(R.raw.box)
}

@ExperimentalAnimationApi
@Composable
fun MainScreen(listData : SnapshotStateList<Data>, viewModel: MainViewmodel) {

    val navController = rememberNavController()

    val bottomNavigationItems = listOf(
        BottomNavigationScreens.Home,
        BottomNavigationScreens.Deals,
        BottomNavigationScreens.Cart,
    )
    Scaffold(
        Modifier.background(Color(0xFFF6F6F6)),
        bottomBar = {
            AppBottomNavigation(navController, bottomNavigationItems)
        },
        topBar = {
            TopAppBar()
        }
    ) {
        MainScreenNavigationConfigurations(navController, listData, viewModel)
    }
}

@ExperimentalAnimationApi
@Composable
private fun MainScreenNavigationConfigurations(
    navController: NavHostController,
    listData: SnapshotStateList<Data>,
    viewModel: MainViewmodel
) {
    NavHost(navController, startDestination = BottomNavigationScreens.Home.route) {
        composable(BottomNavigationScreens.Home.route) {
            HomeScreen(listItems = listData, viewModel )
        }
        composable(BottomNavigationScreens.Deals.route) {
            DealsScreen()
        }
        composable(BottomNavigationScreens.Cart.route) {
            CartScreen()
        }

    }
}

@Composable
fun TopAppBar(){
    TopAppBar(
        title = { Text(
            text = "Intelikart",
            fontStyle = FontStyle.Normal,
            color = Color.Black) },
        backgroundColor = Color(0xFFF6F6F6),
    )
}



@Composable
fun AnimationScreen(
    CartAnimation: CartAnimations
) {
    val context = LocalContext.current
    val customView = remember { LottieAnimationView(context) }
    AndroidView({ customView },
        modifier = Modifier.background(Color(0xFFF6F6F6))
    ) { view ->
        with(view) {
            setAnimation(CartAnimation.animId)
            playAnimation()
            repeatMode = LottieDrawable.REVERSE
        }
    }
}

@Composable
private fun AppBottomNavigation(
    navController: NavHostController,
    items: List<BottomNavigationScreens>,
    backgroundColor: Color = Color(0xFF645D5D)
) {
    BottomNavigation(backgroundColor = backgroundColor) {
        val currentRoute = currentRoute(navController)
        items.forEach { screen ->
            BottomNavigationItem(
                icon = { Icon(screen.icon, null) },
                label = { Text(stringResource(id = screen.resourceId)) },
                selected = currentRoute == screen.route,
                onClick = {
                    if (currentRoute != screen.route) {
                        navController.navigate(screen.route)
                    }
                }
            )
        }
    }
}

@Composable
private fun currentRoute(navController: NavHostController): String? {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    return navBackStackEntry?.arguments?.getString(KEY_ROUTE)
}

