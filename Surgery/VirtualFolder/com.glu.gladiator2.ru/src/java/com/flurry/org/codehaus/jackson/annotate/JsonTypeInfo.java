/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
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
package com.flurry.org.codehaus.jackson.annotate;

import com.flurry.org.codehaus.jackson.annotate.JacksonAnnotation;
import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@JacksonAnnotation
@Retention(value=RetentionPolicy.RUNTIME)
@Target(value={ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER})
public @interface JsonTypeInfo {
    public Class<?> defaultImpl() default "Lcom/flurry/org/codehaus/jackson/annotate/JsonTypeInfo$None;";

    public As include() default As.PROPERTY;

    public String property() default "";

    public Id use();

    public static final class As
    extends Enum<As> {
        private static final /* synthetic */ As[] $VALUES;
        public static final /* enum */ As EXTERNAL_PROPERTY;
        public static final /* enum */ As PROPERTY;
        public static final /* enum */ As WRAPPER_ARRAY;
        public static final /* enum */ As WRAPPER_OBJECT;

        static {
            PROPERTY = new As();
            WRAPPER_OBJECT = new As();
            WRAPPER_ARRAY = new As();
            EXTERNAL_PROPERTY = new As();
            As[] arras = new As[]{PROPERTY, WRAPPER_OBJECT, WRAPPER_ARRAY, EXTERNAL_PROPERTY};
            $VALUES = arras;
        }

        public static As valueOf(String string) {
            return (As)Enum.valueOf(As.class, (String)string);
        }

        public static As[] values() {
            return (As[])$VALUES.clone();
        }
    }

    public static final class Id
    extends Enum<Id> {
        private static final /* synthetic */ Id[] $VALUES;
        public static final /* enum */ Id CLASS;
        public static final /* enum */ Id CUSTOM;
        public static final /* enum */ Id MINIMAL_CLASS;
        public static final /* enum */ Id NAME;
        public static final /* enum */ Id NONE;
        private final String _defaultPropertyName;

        static {
            NONE = new Id(null);
            CLASS = new Id("@class");
            MINIMAL_CLASS = new Id("@c");
            NAME = new Id("@type");
            CUSTOM = new Id(null);
            Id[] arrid = new Id[]{NONE, CLASS, MINIMAL_CLASS, NAME, CUSTOM};
            $VALUES = arrid;
        }

        private Id(String string2) {
            this._defaultPropertyName = string2;
        }

        public static Id valueOf(String string) {
            return (Id)Enum.valueOf(Id.class, (String)string);
        }

        public static Id[] values() {
            return (Id[])$VALUES.clone();
        }

        public String getDefaultPropertyName() {
            return this._defaultPropertyName;
        }
    }

    public static abstract class None {
    }

}

