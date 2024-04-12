package com.constructor.quasi

import org.junit.jupiter.api.Test

class KotlinColorStockTest {

    @Test
    fun `primary ctr works`() {
        val colors = ColorStock(
            mapOf(
                Color.RED to Amount(10),
                Color.BLUE to Amount(20),
                Color.YELLOW to Amount(30)
            )
        )

        assert(colors.colors.size == 3)
    }

    @Test
    fun `secondary ctr works`() {
        val colors = ColorStock(
            Color.RED to Amount(10),
            Color.BLUE to Amount(20),
            Color.YELLOW to Amount(30)
        )

        assert(colors.colors.size == 3)
    }

    @Test
    fun `quasi primary ctr works`() {
        val colors = ColorStock(
            mapOf(
                "RED" to Amount(10),
                "BLUE" to Amount(20),
                "YELLOW" to Amount(30)
            )
        )

        assert(colors.colors.size == 3)
    }

    @Test
    fun `quasi secondary ctr works`() {
        val colors = ColorStock(
            "RED" to Amount(10),
            "BLUE" to Amount(20),
            "YELLOW" to Amount(30)
        )

        assert(colors.colors.size == 3)
    }
}