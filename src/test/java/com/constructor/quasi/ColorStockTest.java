package com.constructor.quasi;

import kotlin.Pair;
import org.junit.jupiter.api.Test;

import java.util.Map;

class ColorStockTest {

    @Test
    void primary_ctr_works() {
        var colors = Map.ofEntries(
                Map.entry(Color.RED, 1),
                Map.entry(Color.WHITE, 2),
                Map.entry(Color.GREEN, 3)
        );
        ColorStock colorStock = new ColorStock(colors);

        assert colorStock.getColors().size() == 3;
    }

    @Test
    void quasi_primary_ctr_works() {
        var colors = Map.ofEntries(
                Map.entry("RED", 1),
                Map.entry("WHITE", 2),
                Map.entry("GREEN", 3)
        );
        ColorStock colorStock = ColorStock.Companion.invoke(colors);

        assert colorStock.getColors().size() == 3;
    }
}
