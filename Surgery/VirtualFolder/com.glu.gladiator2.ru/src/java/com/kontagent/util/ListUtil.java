/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Integer
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.StringBuilder
 *  java.util.List
 */
package com.kontagent.util;

import java.util.List;

public class ListUtil {
    public static String listToString(List<Integer> list) {
        StringBuilder stringBuilder = new StringBuilder();
        for (Integer n : list) {
            if (stringBuilder.length() > 0) {
                stringBuilder.append(",");
            }
            stringBuilder.append(n.toString());
        }
        return stringBuilder.toString();
    }
}

