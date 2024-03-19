package com.dayrain.wms.common.utils;

import com.dayrain.wms.common.policy.Policy;

import java.lang.annotation.Annotation;

public class ReflectUtils {

    public static <T extends Annotation> T getAnnotation(Class<?> clazz, Class<T> annotationClass) {
        if(clazz.isAnnotationPresent(Policy.class)) {
            return clazz.getAnnotation(annotationClass);
        }

        return null;
    }
}
