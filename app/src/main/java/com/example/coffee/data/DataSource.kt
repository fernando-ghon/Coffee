package com.example.coffee.data

import com.example.coffee.R

object DataSource {

    val flavors = listOf(
        R.string.vanilla,
        R.string.chocolate,
        R.string.caramel,
        R.string.snicker_doodle,
        R.string.cinnamon
    )

    val quantityOptions = listOf(
        Pair(R.string.one_coffee, 1),
        Pair(R.string.six_coffees, 6),
        Pair(R.string.twelve_coffees, 12)
    )
}