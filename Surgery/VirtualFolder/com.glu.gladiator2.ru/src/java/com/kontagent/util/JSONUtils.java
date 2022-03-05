/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Class
 *  java.lang.Iterable
 *  java.lang.Object
 *  java.lang.String
 *  java.util.Iterator
 *  java.util.Map
 *  java.util.Set
 *  org.json.JSONArray
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package com.kontagent.util;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JSONUtils {
    public static boolean isEqual(Object object, Object object2, boolean bl) throws JSONException {
        if (!bl) {
            return false;
        }
        if (object.getClass() != object2.getClass()) {
            return false;
        }
        if (object instanceof JSONObject && object2 instanceof JSONObject) {
            Iterator iterator = ((JSONObject)object).keys();
            while (iterator.hasNext()) {
                String string2 = (String)iterator.next();
                if (((JSONObject)object2).has(string2)) {
                    if (bl &= JSONUtils.isEqual(((JSONObject)object).get(string2), ((JSONObject)object2).get(string2), bl)) continue;
                    return false;
                }
                return false;
            }
        } else if (object instanceof JSONArray && object2 instanceof JSONArray) {
            for (int i2 = 0; i2 < ((JSONArray)object).length(); ++i2) {
                if (bl &= JSONUtils.isEqual(((JSONArray)object).get(i2), ((JSONArray)object2).get(i2), bl)) continue;
                return false;
            }
        } else {
            return object.equals(object2);
        }
        return bl;
    }

    public static Object toJSON(Object object) throws JSONException {
        Object object2;
        if (object instanceof Map) {
            object2 = new JSONObject();
            Map map = (Map)object;
            for (Object object3 : map.keySet()) {
                object2.put(object3.toString(), JSONUtils.toJSON(map.get(object3)));
            }
        } else if (object instanceof Iterable) {
            object2 = new JSONArray();
            Iterator iterator = ((Iterable)object).iterator();
            while (iterator.hasNext()) {
                object2.put(iterator.next());
            }
        } else if (object instanceof Object[]) {
            object2 = new JSONArray();
            Object[] arrobject = (Object[])object;
            int n = arrobject.length;
            for (int i2 = 0; i2 < n; ++i2) {
                object2.put(arrobject[i2]);
            }
        } else {
            object2 = object;
        }
        return object2;
    }
}

