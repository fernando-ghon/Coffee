package com.example.coffee.data

import com.example.coffee.ui.theme.MenuItemType

data class OrderUiState(
    val quantity: Int = 0,
    val flavor: String = "",
    val date: String = "",
    val price: String = "",
    val pickupOptions: List<String> = listOf(),
    val itemType: MenuItemType = MenuItemType.COFFEE

)