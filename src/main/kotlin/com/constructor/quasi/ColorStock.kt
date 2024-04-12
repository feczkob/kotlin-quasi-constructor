package com.constructor.quasi

class ColorStock(private val values: Map<Color, Amount>) {

    val colors: Set<Color>
        get() = values.keys

    // * http://www.angelikalanger.com/GenericsFAQ/FAQSections/ProgrammingIdioms.html#FAQ300
    @SafeVarargs
    constructor(vararg varargs: Pair<Color, Amount>) : this(mapOf(*varargs))

    companion object {
        operator fun invoke(values: Map<String, Amount>) = ColorStock(values.mapKeys { Color.valueOf(it.key) })
        @SafeVarargs
        operator fun invoke(vararg varargs: Pair<String, Amount>) = ColorStock(mapOf(*varargs))
    }
}

class Amount(private val quantity: Int)

enum class Color {
    RED,
    BLUE,
    YELLOW
}