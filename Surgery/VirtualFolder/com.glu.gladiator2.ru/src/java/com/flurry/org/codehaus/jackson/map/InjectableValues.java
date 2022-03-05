/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Class
 *  java.lang.IllegalArgumentException
 *  java.lang.Object
 *  java.lang.String
 *  java.util.HashMap
 *  java.util.Map
 */
package com.flurry.org.codehaus.jackson.map;

import com.flurry.org.codehaus.jackson.map.BeanProperty;
import com.flurry.org.codehaus.jackson.map.DeserializationContext;
import java.util.HashMap;
import java.util.Map;

public abstract class InjectableValues {
    public abstract Object findInjectableValue(Object var1, DeserializationContext var2, BeanProperty var3, Object var4);

    public static class Std
    extends InjectableValues {
        protected final Map<String, Object> _values;

        public Std() {
            this((Map<String, Object>)new HashMap());
        }

        public Std(Map<String, Object> map) {
            this._values = map;
        }

        public Std addValue(Class<?> class_, Object object) {
            this._values.put((Object)class_.getName(), object);
            return this;
        }

        public Std addValue(String string, Object object) {
            this._values.put((Object)string, object);
            return this;
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        @Override
        public Object findInjectableValue(Object object, DeserializationContext deserializationContext, BeanProperty beanProperty, Object object2) {
            if (!(object instanceof String)) {
                String string;
                if (object == null) {
                    string = "[null]";
                    do {
                        throw new IllegalArgumentException("Unrecognized inject value id type (" + string + "), expecting String");
                        break;
                    } while (true);
                }
                string = object.getClass().getName();
                throw new IllegalArgumentException("Unrecognized inject value id type (" + string + "), expecting String");
            }
            String string = (String)object;
            Object object3 = this._values.get((Object)string);
            if (object3 != null || this._values.containsKey((Object)string)) return object3;
            throw new IllegalArgumentException("No injectable id with value '" + string + "' found (for property '" + beanProperty.getName() + "')");
        }
    }

}

