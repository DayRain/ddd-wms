package com.dayrain.wms.common.key;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

class KeyGeneratorFactoryTest {

    @Test
    void getUniqueKey() {
        Assertions.assertNotEquals(KeyGeneratorFactory.getUniqueKey(), KeyGeneratorFactory.getUniqueKey());
        Assertions.assertEquals(19, KeyGeneratorFactory.getUniqueKey().length());
    }
}