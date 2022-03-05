/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Class
 *  java.lang.Object
 *  java.lang.annotation.Annotation
 *  java.lang.annotation.ElementType
 *  java.lang.annotation.Retention
 *  java.lang.annotation.RetentionPolicy
 *  java.lang.annotation.Target
 */
package com.flurry.org.codehaus.jackson.map.annotate;

import com.flurry.org.codehaus.jackson.annotate.JacksonAnnotation;
import com.flurry.org.codehaus.jackson.map.JsonDeserializer;
import com.flurry.org.codehaus.jackson.map.KeyDeserializer;
import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@JacksonAnnotation
@Retention(value=RetentionPolicy.RUNTIME)
@Target(value={ElementType.METHOD, ElementType.FIELD, ElementType.TYPE, ElementType.PARAMETER})
public @interface JsonDeserialize {
    public Class<?> as() default "Lcom/flurry/org/codehaus/jackson/map/annotate/NoClass;";

    public Class<?> contentAs() default "Lcom/flurry/org/codehaus/jackson/map/annotate/NoClass;";

    public Class<? extends JsonDeserializer<?>> contentUsing() default "Lcom/flurry/org/codehaus/jackson/map/JsonDeserializer$None;";

    public Class<?> keyAs() default "Lcom/flurry/org/codehaus/jackson/map/annotate/NoClass;";

    public Class<? extends KeyDeserializer> keyUsing() default "Lcom/flurry/org/codehaus/jackson/map/KeyDeserializer$None;";

    public Class<? extends JsonDeserializer<?>> using() default "Lcom/flurry/org/codehaus/jackson/map/JsonDeserializer$None;";
}

