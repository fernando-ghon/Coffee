package com.example.coffee.ui.theme

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.paddingFromBaseline
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.coffee.R
import androidx.compose.foundation.clickable

@Composable
fun MenuElement(
    @DrawableRes drawable: Int,
    @StringRes text: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.clickable { onClick() },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(drawable),
            contentDescription = stringResource(text),
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(88.dp)
                .clip(CircleShape)
        )
        Text(
            text = stringResource(text),
            modifier = Modifier.paddingFromBaseline(top = 24.dp, bottom = 8.dp),
            style = MaterialTheme.typography.bodyMedium,
        )
    }
}

@Composable
fun MenuRow(
    onItemClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(horizontal = 16.dp),
        modifier = modifier
    ) {
        items(MenuData) { item ->
            MenuElement(
                drawable = item.drawable,
                text = item.text,
                onClick = { onItemClick(item.routeId) }
            )
        }
    }
}

@Composable
fun MenuFoodRow(
    onItemClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(horizontal = 16.dp),
        modifier = modifier
    ) {
        items(MenuFoodData) { item ->
            MenuElement(
                drawable = item.drawable,
                text = item.text,
                onClick = { onItemClick(item.routeId) }
            )
        }
    }
}

@Composable
fun MenuSection(
    @StringRes title: Int,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Column(modifier) {
        Text(
            text = stringResource(title),
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .paddingFromBaseline(top = 40.dp, bottom = 16.dp)
        )
        content()
    }
}

@Composable
fun MenuScreen(
    onCoffeeItemClick: (String) -> Unit,
    onFoodItemClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.verticalScroll(rememberScrollState())
    ) {
        MenuSection(title = R.string.app_name) {
            MenuRow(onItemClick = onCoffeeItemClick)
        }

        MenuSection(title = R.string.food_title) {
            MenuFoodRow(onItemClick = onFoodItemClick)
        }
    }
}

private data class DrawableStringPair(
    val routeId: String,
    @DrawableRes val drawable: Int,
    @StringRes val text: Int
)

private val MenuData = listOf(
    DrawableStringPair(
        routeId = "frappuccino",
        drawable = R.drawable.frappuccino,
        text = R.string.frappuccino
    ),

    DrawableStringPair(
        routeId = "caffe americano",
        drawable = R.drawable.caffe_americano_2,
        text = R.string.caffe_americano
    ),

    DrawableStringPair(
        routeId = "caffe mocha",
        drawable = R.drawable.caffe_mocha,
        text = R.string.caffe_mocha
    )
)

private val MenuFoodData = listOf(
    DrawableStringPair(
        routeId = "bacon egg",
        drawable = R.drawable.bacon_egg,
        text = R.string.bacon_egg
    )
)


                            /* Previews */
@Preview(showBackground = true, backgroundColor = 0xFFF5F0EE)
@Composable
fun MenuElementPreview() {
    CoffeeTheme {
        MenuElement(
            drawable = R.drawable.frappuccino,
            text = R.string.frappuccino,
            onClick = {},
            modifier = Modifier.padding(8.dp)
        )
    }
}


@Preview(showBackground = true, backgroundColor = 0xFFF5F0EE)
@Composable
fun MenuRowPreview() {
    CoffeeTheme {
        MenuRow(
            onItemClick = {}
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFF5F0EE)
@Composable
fun MenuSectionPreview() {
    CoffeeTheme {
        MenuSection(R.string.frappuccino) {
            MenuRow(
                onItemClick = {}
            )
        }
    }
}
































