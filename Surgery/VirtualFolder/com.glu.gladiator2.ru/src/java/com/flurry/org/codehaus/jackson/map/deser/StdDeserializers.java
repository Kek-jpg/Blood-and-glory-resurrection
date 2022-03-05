/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Boolean
 *  java.lang.Byte
 *  java.lang.CharSequence
 *  java.lang.Character
 *  java.lang.Class
 *  java.lang.Double
 *  java.lang.Float
 *  java.lang.Integer
 *  java.lang.Long
 *  java.lang.Object
 *  java.lang.Short
 *  java.lang.String
 *  java.util.Calendar
 *  java.util.GregorianCalendar
 *  java.util.HashMap
 *  java.util.Iterator
 */
package com.flurry.org.codehaus.jackson.map.deser;

import com.flurry.org.codehaus.jackson.map.JsonDeserializer;
import com.flurry.org.codehaus.jackson.map.deser.std.AtomicBooleanDeserializer;
import com.flurry.org.codehaus.jackson.map.deser.std.CalendarDeserializer;
import com.flurry.org.codehaus.jackson.map.deser.std.ClassDeserializer;
import com.flurry.org.codehaus.jackson.map.deser.std.DateDeserializer;
import com.flurry.org.codehaus.jackson.map.deser.std.FromStringDeserializer;
import com.flurry.org.codehaus.jackson.map.deser.std.JavaTypeDeserializer;
import com.flurry.org.codehaus.jackson.map.deser.std.StdDeserializer;
import com.flurry.org.codehaus.jackson.map.deser.std.StringDeserializer;
import com.flurry.org.codehaus.jackson.map.deser.std.TimestampDeserializer;
import com.flurry.org.codehaus.jackson.map.deser.std.TokenBufferDeserializer;
import com.flurry.org.codehaus.jackson.map.deser.std.UntypedObjectDeserializer;
import com.flurry.org.codehaus.jackson.map.type.ClassKey;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;

class StdDeserializers {
    final HashMap<ClassKey, JsonDeserializer<Object>> _deserializers = new HashMap();

    private StdDeserializers() {
        this.add(new UntypedObjectDeserializer());
        StringDeserializer stringDeserializer = new StringDeserializer();
        this.add(stringDeserializer, String.class);
        this.add(stringDeserializer, CharSequence.class);
        this.add(new ClassDeserializer());
        this.add(new StdDeserializer.BooleanDeserializer(Boolean.class, null));
        this.add(new StdDeserializer.ByteDeserializer(Byte.class, null));
        this.add(new StdDeserializer.ShortDeserializer(Short.class, null));
        this.add(new StdDeserializer.CharacterDeserializer(Character.class, null));
        this.add(new StdDeserializer.IntegerDeserializer(Integer.class, null));
        this.add(new StdDeserializer.LongDeserializer(Long.class, null));
        this.add(new StdDeserializer.FloatDeserializer(Float.class, null));
        this.add(new StdDeserializer.DoubleDeserializer(Double.class, null));
        this.add(new StdDeserializer.BooleanDeserializer((Class<Boolean>)Boolean.TYPE, Boolean.FALSE));
        this.add(new StdDeserializer.ByteDeserializer((Class<Byte>)Byte.TYPE, (byte)0));
        this.add(new StdDeserializer.ShortDeserializer((Class<Short>)Short.TYPE, (short)0));
        this.add(new StdDeserializer.CharacterDeserializer((Class<Character>)Character.TYPE, Character.valueOf((char)'\u0000')));
        this.add(new StdDeserializer.IntegerDeserializer((Class<Integer>)Integer.TYPE, 0));
        this.add(new StdDeserializer.LongDeserializer((Class<Long>)Long.TYPE, 0L));
        this.add(new StdDeserializer.FloatDeserializer((Class<Float>)Float.TYPE, Float.valueOf((float)0.0f)));
        this.add(new StdDeserializer.DoubleDeserializer((Class<Double>)Double.TYPE, 0.0));
        this.add(new StdDeserializer.NumberDeserializer());
        this.add(new StdDeserializer.BigDecimalDeserializer());
        this.add(new StdDeserializer.BigIntegerDeserializer());
        this.add(new CalendarDeserializer());
        this.add(new DateDeserializer());
        this.add(new CalendarDeserializer(GregorianCalendar.class), GregorianCalendar.class);
        this.add(new StdDeserializer.SqlDateDeserializer());
        this.add(new TimestampDeserializer());
        Iterator iterator = FromStringDeserializer.all().iterator();
        while (iterator.hasNext()) {
            this.add((FromStringDeserializer)iterator.next());
        }
        this.add(new StdDeserializer.StackTraceElementDeserializer());
        this.add(new AtomicBooleanDeserializer());
        this.add(new TokenBufferDeserializer());
        this.add(new JavaTypeDeserializer());
    }

    private void add(StdDeserializer<?> stdDeserializer) {
        StdDeserializers.super.add(stdDeserializer, stdDeserializer.getValueClass());
    }

    private void add(StdDeserializer<?> stdDeserializer, Class<?> class_) {
        this._deserializers.put((Object)new ClassKey(class_), stdDeserializer);
    }

    public static HashMap<ClassKey, JsonDeserializer<Object>> constructAll() {
        return new StdDeserializers()._deserializers;
    }
}

