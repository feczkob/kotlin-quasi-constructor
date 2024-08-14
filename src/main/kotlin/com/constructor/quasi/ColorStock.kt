package com.constructor.quasi

import com.constructor.quasi.Color.valueOf

class ColorStock constructor(private val values: Map<Color, Int>) {

    val colors: Set<Color>
        get() = values.keys

    fun amountFor(color: Color) = values[color] ?: error("Color not found")

    // * http://www.angelikalanger.com/GenericsFAQ/FAQSections/ProgrammingIdioms.html#FAQ300
    @SafeVarargs
    constructor(vararg varargs: Pair<Color, Int>) : this(mapOf(*varargs))

    // Overload resolution ambiguity.
//    constructor(values: Map<String, Int>) : this(values.mapKeys { valueOf(it.key) })

    companion object {
        operator fun invoke(values: Map<String, Int>) = ColorStock(values.mapKeys { valueOf(it.key) })
        @SafeVarargs
        operator fun invoke(vararg varargs: Pair<String, Int>) = ColorStock(mapOf(*varargs))

        fun fromStringMap(values: Map<String, Int>) = ColorStock(values.mapKeys { valueOf(it.key) })
    }
}

enum class Color {
    RED,
    WHITE,
    GREEN;
}
