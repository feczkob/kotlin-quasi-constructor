package com.constructor.quasi;

import kotlin.Pair;
import org.junit.jupiter.api.Test;

import java.util.Map;

class ColorStockTest {

    @Test
    void primary_ctr_works() {
        var colors = Map.ofEntries(
                Map.entry(Color.RED, new Amount(1)),
                Map.entry(Color.BLUE, new Amount(2)),
                Map.entry(Color.YELLOW, new Amount(3))
        );
        ColorStock colorStock = new ColorStock(colors);

        assert colorStock.getColors().size() == 3;
    }

    @Test
    void secondary_ctr_works() {
        ColorStock colorStock = new ColorStock(
                new Pair<>(Color.RED, new Amount(1)),
                new Pair<>(Color.BLUE, new Amount(2)),
                new Pair<>(Color.YELLOW, new Amount(3))
        );

        assert colorStock.getColors().size() == 3;
    }

    @Test
    void quasi_primary_ctr_works() {
        var colors = Map.ofEntries(
                Map.entry("RED", new Amount(1)),
                Map.entry("BLUE", new Amount(2)),
                Map.entry("YELLOW", new Amount(3))
        );
        ColorStock colorStock = ColorStock.Companion.invoke(colors);

        assert colorStock.getColors().size() == 3;
    }

    @Test
    void quasi_secondary_ctr_works() {
        ColorStock colorStock = ColorStock.Companion.invoke(
                new Pair<>("RED", new Amount(1)),
                new Pair<>("BLUE", new Amount(2)),
                new Pair<>("YELLOW", new Amount(3))
        );

        assert colorStock.getColors().size() == 3;
    }
}
