package com.constructor.quasi

fun main() {
    val primaryCtr = ColorStock(
        mapOf(
            Color.RED to 10,
            Color.WHITE to 20,
            Color.GREEN to 30
        )
    )

    val quasiPrimaryCtr = ColorStock(
        mapOf(
            "RED" to 10,
            "BLUE" to 20,
            "YELLOW" to 30
        )
    )
}

