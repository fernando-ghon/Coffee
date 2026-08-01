package com.example.coffee


import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding

//import androidx.compose.material.icons.Icons

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

import com.example.coffee.data.DataSource
//import com.example.coffee.data.DataSource.flavors

import com.example.coffee.ui.theme.StartOrderScreen
import com.example.coffee.ui.theme.OrderViewModel


import com.example.coffee.ui.theme.SelectOptionScreen
import com.example.coffee.ui.theme.OrderSummaryScreen

import com.example.coffee.R
import com.example.coffee.ui.theme.MenuScreen
import com.example.coffee.ui.theme.CoffeeDetailScreen
import com.example.coffee.ui.theme.FoodDetailScreen


enum class CoffeeScreen(@StringRes val title: Int) {
    /* add new screens here */
    Menu(title = R.string.app_name),
    Start(title = R.string.app_name),
    Flavor(title = R.string.choose_flavor),
    Pickup(title = R.string.choose_pickup_date),
    Summary(title = R.string.order_summary)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CoffeeAppBar(
    currentScreen: CoffeeScreen,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = { Text(stringResource(currentScreen.title)) },
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        ),
        modifier = modifier,
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        painter = painterResource(R.drawable.arrow_back_24dp_e3e3e3_fill0_wght400_grad0_opsz24),
                        contentDescription = null
                    )
                }
            }
        }
    )
}

@Composable
fun CoffeeApp(
    viewModel: OrderViewModel = viewModel(),
    navController: NavHostController = rememberNavController()
) {

    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route

    val currentScreen = when (currentRoute) {
        CoffeeScreen.Menu.name -> CoffeeScreen.Menu
        CoffeeScreen.Start.name -> CoffeeScreen.Start
        CoffeeScreen.Flavor.name -> CoffeeScreen.Flavor
        CoffeeScreen.Pickup.name -> CoffeeScreen.Pickup
        CoffeeScreen.Summary.name -> CoffeeScreen.Summary
        "coffee/{routeId}" -> CoffeeScreen.Menu
        else -> CoffeeScreen.Menu
    }


    Scaffold(
        topBar = {
            CoffeeAppBar(
                currentScreen = currentScreen,
                canNavigateBack = navController.previousBackStackEntry != null,
                navigateUp = { navController.navigateUp() }
            )
        }
    ) { innerPadding ->
        val uiState by viewModel.uiState.collectAsState()

        NavHost(
            navController = navController,
            startDestination = CoffeeScreen.Menu.name,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(route = CoffeeScreen.Menu.name) {
                MenuScreen(
                    onCoffeeItemClick = { routeId -> navController.navigate("coffee/$routeId") },
                    onFoodItemClick = { routeId -> navController.navigate("food/$routeId") },
                    modifier = Modifier.fillMaxSize()
                )
            }


            composable(route = "coffee/{routeId}") { backStackEntry ->
                val routeId = backStackEntry.arguments?.getString("routeId").orEmpty()
                CoffeeDetailScreen(
                    routeId = routeId,
                    onBack = { navController.navigateUp() },
                    onNext = { quantity ->
                        viewModel.setQuantity(quantity)
                        navController.navigate(CoffeeScreen.Flavor.name)
                    }
                )
            }

            composable(route = "food/{routeId}") { backStackEntry ->
                val routeId = backStackEntry.arguments?.getString("routeId").orEmpty()
                FoodDetailScreen(
                    routeId = routeId,
                    onBack = { navController.navigateUp() },
                    onNext = { quantity ->
                        viewModel.setQuantity(quantity)
                        navController.navigate(CoffeeScreen.Flavor.name)
                    }
                )
            }

            composable(route = CoffeeScreen.Start.name) {
                StartOrderScreen(
                    quantityOptions = DataSource.quantityOptions, // DataSource singleton object in DataSource.kt
                    onNextButtonClicked = {
                        viewModel.setQuantity(it)
                        navController.navigate(CoffeeScreen.Flavor.name)
                    },
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(dimensionResource(R.dimen.padding_medium))
                )


            }
            composable(route = CoffeeScreen.Flavor.name) {
                val context = LocalContext.current
                SelectOptionScreen(
                    subtotal = uiState.price,
                    onNextButtonClicked = { navController.navigate(CoffeeScreen.Pickup.name) },
                    onCancelButtonClicked = {
                        cancelOrderAndNavigateToStart(viewModel, navController)
                    },
                    options = DataSource.flavors.map { id -> context.resources.getString(id) },
                    onSelectionChanged = { viewModel.setFlavor(it) },
                    modifier = Modifier.fillMaxHeight()
                )

            }


            composable(route = CoffeeScreen.Pickup.name) {
                SelectOptionScreen(
                    subtotal = uiState.price,
                    onNextButtonClicked = { navController.navigate(CoffeeScreen.Summary.name) },
                    onCancelButtonClicked = {
                        cancelOrderAndNavigateToStart(viewModel, navController)
                    },
                    options = uiState.pickupOptions,
                    onSelectionChanged = { viewModel.setDate(it) },
                    modifier = Modifier.fillMaxHeight()
                )
            }

            composable(route = CoffeeScreen.Summary.name) {
                OrderSummaryScreen(
                    orderUiState = uiState,
                    onCancelButtonClicked = {
                        cancelOrderAndNavigateToStart(viewModel, navController)
                    },
                    onNextButtonClicked = {
                        viewModel.resetOrder()
                        navController.popBackStack(CoffeeScreen.Start.name, inclusive = false)

                    },
                    modifier = Modifier.fillMaxHeight()
                )
            }


        }


    }
}

private fun cancelOrderAndNavigateToStart(
    viewModel: OrderViewModel,
    navController: NavHostController
) {
    viewModel.resetOrder()
    navController.popBackStack(CoffeeScreen.Menu.name, inclusive = false)


}

