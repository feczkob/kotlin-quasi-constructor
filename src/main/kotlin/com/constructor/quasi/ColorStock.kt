package com.constructor.quasi

import com.constructor.quasi.Color.valueOf

class ColorStock constructor(private val values: Map<Color, Int>) {

    val colors: Set<Color>
        get() = values.keys

    fun amountFor(color: Color) = values[color] ?: error("Color not found")

    // Overload resolution ambiguity.
//    constructor(values: Map<String, Int>) : this(values.mapKeys { valueOf(it.key) })

    companion object {
        operator fun invoke(values: Map<String, Int>) = ColorStock(values.mapKeys { valueOf(it.key) })
    }
}

enum class Color {
    RED,
    WHITE,
    GREEN;
}
