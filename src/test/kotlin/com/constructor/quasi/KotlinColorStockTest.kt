package com.constructor.quasi

import org.junit.jupiter.api.Test

class KotlinColorStockTest {

    @Test
    fun `primary ctr works`() {
        val colors = ColorStock(
            mapOf(
                Color.RED to 10,
                Color.WHITE to 20,
                Color.GREEN to 30
            )
        )

        assert(colors.colors.size == 3)
    }

    @Test
    fun `secondary ctr works`() {
        val colors = ColorStock(
            Color.RED to 10,
            Color.WHITE to 20,
            Color.GREEN to 30
        )

        assert(colors.colors.size == 3)
    }

    @Test
    fun `quasi primary ctr works`() {
        val colors = ColorStock(
            mapOf(
                "RED" to 10,
                "WHITE" to 20,
                "GREEN" to 30
            )
        )

        assert(colors.colors.size == 3)
    }

    @Test
    fun `quasi secondary ctr works`() {
        val colors = ColorStock(
            "RED" to 10,
            "WHITE" to 20,
            "GREEN" to 30
        )

        assert(colors.colors.size == 3)
    }
}
