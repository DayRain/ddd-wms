package com.dayrain.wms.common.utils;

public class BeanUtils {

    public static void copy(Object source, Object target) {
        org.springframework.beans.BeanUtils.copyProperties(source, target);
    }
}
