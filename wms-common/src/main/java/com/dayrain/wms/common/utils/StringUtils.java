package com.dayrain.wms.common.utils;

public class StringUtils {
    public static boolean isEmpty(String str) {
        return str == null || "".equals(str.trim());
    }
}
