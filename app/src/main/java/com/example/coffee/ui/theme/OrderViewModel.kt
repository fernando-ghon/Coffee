package com.example.coffee.ui.theme

import android.view.MenuItem
import androidx.lifecycle.ViewModel
import com.example.coffee.data.OrderUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

private const val COFFEE_PRICE_PER_ITEM = 2.00
private const val FOOD_PRICE_PER_ITEM = 4.50
private const val PRICE_FOR_SAME_DAY_PICKUP = 3.00

enum class MenuItemType {
    COFFEE, FOOD
}

class OrderViewModel : ViewModel() {


    private val _uiState = MutableStateFlow(OrderUiState(pickupOptions = pickupOptions()))
    val uiState: StateFlow<OrderUiState> = _uiState.asStateFlow()

    private var selectedItemType: MenuItemType = MenuItemType.COFFEE

    fun setItemType(type: MenuItemType) {
        selectedItemType = type
        _uiState.update { currentState ->
            currentState.copy(
                itemType = type,
                price = calculatePrice(
                    quantity = currentState.quantity,
                    pickupDate = currentState.date
                )
            )
        }
    }

    fun setQuantity(numberItems: Int) {
        _uiState.update { currentState ->
            currentState.copy(
                quantity = numberItems,
                price = calculatePrice(quantity = numberItems)
            )
        }
    }


    fun setFlavor(desiredFlavor: String) {
        _uiState.update { currentState ->
            currentState.copy(flavor = desiredFlavor)
        }
    }


    fun setDate(pickupDate: String) {
        _uiState.update { currentState ->
            currentState.copy(
                date = pickupDate,
                price = calculatePrice(pickupDate = pickupDate)
            )
        }
    }


    fun resetOrder() {
        selectedItemType = MenuItemType.COFFEE
        _uiState.value = OrderUiState(pickupOptions = pickupOptions())
    }


    private fun calculatePrice(
        quantity: Int = _uiState.value.quantity,
        pickupDate: String = _uiState.value.date
    ): String {
        val unitPrice = when (selectedItemType) {
            MenuItemType.COFFEE -> COFFEE_PRICE_PER_ITEM
            MenuItemType.FOOD -> FOOD_PRICE_PER_ITEM
        }
        var calculatedPrice = quantity * unitPrice
        if (pickupOptions()[0] == pickupDate) {
            calculatedPrice += PRICE_FOR_SAME_DAY_PICKUP
        }
        return NumberFormat.getCurrencyInstance().format(calculatedPrice)
    }

    private fun pickupOptions(): List<String> {
        val dateOptions = mutableListOf<String>()
        val formatter = SimpleDateFormat("E MMM d", Locale.getDefault())
        val calendar = Calendar.getInstance()
        repeat(4) {
            dateOptions.add(formatter.format(calendar.time))
            calendar.add(Calendar.DATE, 1)
        }
        return dateOptions
    }
}
