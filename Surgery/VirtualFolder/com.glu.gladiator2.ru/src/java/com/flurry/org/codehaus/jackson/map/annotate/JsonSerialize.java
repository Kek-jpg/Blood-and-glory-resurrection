/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  com.flurry.org.codehaus.jackson.annotate.JacksonAnnotation
 *  java.lang.Class
 *  java.lang.Enum
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.annotation.Annotation
 *  java.lang.annotation.ElementType
 *  java.lang.annotation.Retention
 *  java.lang.annotation.RetentionPolicy
 *  java.lang.annotation.Target
 */
package com.flurry.org.codehaus.jackson.map.annotate;

import com.flurry.org.codehaus.jackson.annotate.JacksonAnnotation;
import com.flurry.org.codehaus.jackson.map.JsonSerializer;
import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@JacksonAnnotation
@Retention(value=RetentionPolicy.RUNTIME)
@Target(value={ElementType.METHOD, ElementType.FIELD, ElementType.TYPE, ElementType.PARAMETER})
public @interface JsonSerialize {
    public Class<?> as() default "Lcom/flurry/org/codehaus/jackson/map/annotate/NoClass;";

    public Class<?> contentAs() default "Lcom/flurry/org/codehaus/jackson/map/annotate/NoClass;";

    public Class<? extends JsonSerializer<?>> contentUsing() default "Lcom/flurry/org/codehaus/jackson/map/JsonSerializer$None;";

    public Inclusion include() default Inclusion.ALWAYS;

    public Class<?> keyAs() default "Lcom/flurry/org/codehaus/jackson/map/annotate/NoClass;";

    public Class<? extends JsonSerializer<?>> keyUsing() default "Lcom/flurry/org/codehaus/jackson/map/JsonSerializer$None;";

    public Typing typing() default Typing.DYNAMIC;

    public Class<? extends JsonSerializer<?>> using() default "Lcom/flurry/org/codehaus/jackson/map/JsonSerializer$None;";

    public static final class Inclusion
    extends Enum<Inclusion> {
        private static final /* synthetic */ Inclusion[] $VALUES;
        public static final /* enum */ Inclusion ALWAYS = new Inclusion();
        public static final /* enum */ Inclusion NON_DEFAULT;
        public static final /* enum */ Inclusion NON_EMPTY;
        public static final /* enum */ Inclusion NON_NULL;

        static {
            NON_NULL = new Inclusion();
            NON_DEFAULT = new Inclusion();
            NON_EMPTY = new Inclusion();
            Inclusion[] arrinclusion = new Inclusion[]{ALWAYS, NON_NULL, NON_DEFAULT, NON_EMPTY};
            $VALUES = arrinclusion;
        }

        public static Inclusion valueOf(String string) {
            return (Inclusion)Enum.valueOf(Inclusion.class, (String)string);
        }

        public static Inclusion[] values() {
            return (Inclusion[])$VALUES.clone();
        }
    }

    public static final class Typing
    extends Enum<Typing> {
        private static final /* synthetic */ Typing[] $VALUES;
        public static final /* enum */ Typing DYNAMIC = new Typing();
        public static final /* enum */ Typing STATIC = new Typing();

        static {
            Typing[] arrtyping = new Typing[]{DYNAMIC, STATIC};
            $VALUES = arrtyping;
        }

        public static Typing valueOf(String string) {
            return (Typing)Enum.valueOf(Typing.class, (String)string);
        }

        public static Typing[] values() {
            return (Typing[])$VALUES.clone();
        }
    }

}

