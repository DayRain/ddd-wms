package com.dayrain.wms.common.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static com.dayrain.wms.common.utils.DecimalUtils.is;


class DecimalUtilsTest {

    @Test
    public void testEq() {
        Assertions.assertTrue(is(new BigDecimal(1)).eqIgnoreScale(new BigDecimal("1.0")));
    }

    @Test
    public void testLt() {
        Assertions.assertTrue(is(BigDecimal.ONE).lt(BigDecimal.TEN));
        Assertions.assertFalse(is(BigDecimal.TEN).lt(BigDecimal.ONE));
    }

    @Test
    public void testGt() {
        Assertions.assertFalse(is(BigDecimal.ONE).gt(BigDecimal.TEN));
        Assertions.assertTrue(is(BigDecimal.TEN).gt(BigDecimal.ONE));
    }

    @Test
    public void testNegative() {
        Assertions.assertFalse(is(new BigDecimal(1)).isNegative());
        Assertions.assertTrue(is(new BigDecimal(-1)).isNegative());
    }

    @Test
    public void testPositive() {
        Assertions.assertTrue(is(new BigDecimal(1)).isPositive());
        Assertions.assertFalse(is(new BigDecimal(-1)).isPositive());
    }
}