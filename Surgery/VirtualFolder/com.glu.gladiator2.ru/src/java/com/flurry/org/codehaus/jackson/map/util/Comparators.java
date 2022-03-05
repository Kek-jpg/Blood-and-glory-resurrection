/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Class
 *  java.lang.Object
 *  java.lang.reflect.Array
 */
package com.flurry.org.codehaus.jackson.map.util;

import java.lang.reflect.Array;

public class Comparators {
    public static Object getArrayComparator(final Object object) {
        return new Object(Array.getLength((Object)object)){
            final /* synthetic */ int val$length;
            {
                this.val$length = n;
            }

            /*
             * Enabled aggressive block sorting
             */
            public boolean equals(Object object4) {
                if (object4 != this) {
                    if (object4 == null || object4.getClass() != object.getClass()) {
                        return false;
                    }
                    if (Array.getLength((Object)object4) != this.val$length) {
                        return false;
                    }
                    for (int i = 0; i < this.val$length; ++i) {
                        Object object2;
                        Object object3 = Array.get((Object)object, (int)i);
                        if (object3 == (object2 = Array.get((Object)object4, (int)i)) || object3 == null || object3.equals(object2)) continue;
                        return false;
                    }
                }
                return true;
            }
        };
    }

}

