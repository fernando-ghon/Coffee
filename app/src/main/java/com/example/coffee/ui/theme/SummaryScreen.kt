package com.example.coffee.ui.theme

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.example.coffee.R
import com.example.coffee.data.OrderUiState
import com.example.coffee.ui.theme.components.FormattedPriceLabel
import com.example.coffee.ui.theme.CoffeeTheme


@Composable
fun OrderSummaryScreen(
    orderUiState: OrderUiState,
    onCancelButtonClicked: () -> Unit,
    onNextButtonClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    val resources = LocalContext.current.resources

    val numberOfItems = resources.getQuantityString(
        R.plurals.coffees,
        orderUiState.quantity,
        orderUiState.quantity
    )

    val orderSummary = stringResource(
        R.string.order_details,
        numberOfItems,
        orderUiState.flavor,
        orderUiState.date,
        orderUiState.quantity
    )
    val newOrder = stringResource(R.string.new_coffee_order)

    val items = listOf(
        Pair(stringResource(R.string.quantity), numberOfItems),
        Pair(stringResource(R.string.flavor), orderUiState.flavor),
        Pair(stringResource(R.string.pickup_date), orderUiState.date)
    )

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            modifier = Modifier.padding(dimensionResource(R.dimen.padding_medium)),
            verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_small))
        ) {
            items.forEach { item ->
                Text(item.first.uppercase())
                Text(text = item.second, fontWeight = FontWeight.Bold)
                Divider(thickness = dimensionResource(R.dimen.thickness_divider))
            }
            Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_small)))
            FormattedPriceLabel(
                subtotal = orderUiState.price,
                modifier = Modifier.align(Alignment.End)
            )
        }
        Row(
            modifier = Modifier.padding(dimensionResource(R.dimen.padding_medium))
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_small))
            ) {
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = onNextButtonClicked
                ) {
                    Text(stringResource(R.string.submit))
                }
                OutlinedButton(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = onCancelButtonClicked
                ) {
                    Text(stringResource(R.string.cancel))
                }
            }
        }
    }
}

@Preview
@Composable
fun OrderSummaryPreview() {
    CoffeeTheme {
        OrderSummaryScreen(
            orderUiState = OrderUiState(0, "Test", "Test", "$300.00"),
            onNextButtonClicked = {},
            onCancelButtonClicked = {},
            modifier = Modifier.fillMaxHeight()
        )
    }
}
