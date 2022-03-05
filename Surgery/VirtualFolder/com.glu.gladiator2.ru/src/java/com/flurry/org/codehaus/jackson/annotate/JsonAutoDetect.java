/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Enum
 *  java.lang.NoSuchFieldError
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.annotation.Annotation
 *  java.lang.annotation.ElementType
 *  java.lang.annotation.Retention
 *  java.lang.annotation.RetentionPolicy
 *  java.lang.annotation.Target
 *  java.lang.reflect.Member
 *  java.lang.reflect.Modifier
 */
package com.flurry.org.codehaus.jackson.annotate;

import com.flurry.org.codehaus.jackson.annotate.JacksonAnnotation;
import com.flurry.org.codehaus.jackson.annotate.JsonMethod;
import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Member;
import java.lang.reflect.Modifier;

@JacksonAnnotation
@Retention(value=RetentionPolicy.RUNTIME)
@Target(value={ElementType.TYPE})
public @interface JsonAutoDetect {
    public Visibility creatorVisibility() default Visibility.DEFAULT;

    public Visibility fieldVisibility() default Visibility.DEFAULT;

    public Visibility getterVisibility() default Visibility.DEFAULT;

    public Visibility isGetterVisibility() default Visibility.DEFAULT;

    public Visibility setterVisibility() default Visibility.DEFAULT;

    public JsonMethod[] value() default {JsonMethod.ALL};

    public static final class Visibility
    extends Enum<Visibility> {
        private static final /* synthetic */ Visibility[] $VALUES;
        public static final /* enum */ Visibility ANY = new Visibility();
        public static final /* enum */ Visibility DEFAULT;
        public static final /* enum */ Visibility NONE;
        public static final /* enum */ Visibility NON_PRIVATE;
        public static final /* enum */ Visibility PROTECTED_AND_PUBLIC;
        public static final /* enum */ Visibility PUBLIC_ONLY;

        static {
            NON_PRIVATE = new Visibility();
            PROTECTED_AND_PUBLIC = new Visibility();
            PUBLIC_ONLY = new Visibility();
            NONE = new Visibility();
            DEFAULT = new Visibility();
            Visibility[] arrvisibility = new Visibility[]{ANY, NON_PRIVATE, PROTECTED_AND_PUBLIC, PUBLIC_ONLY, NONE, DEFAULT};
            $VALUES = arrvisibility;
        }

        public static Visibility valueOf(String string) {
            return (Visibility)Enum.valueOf(Visibility.class, (String)string);
        }

        public static Visibility[] values() {
            return (Visibility[])$VALUES.clone();
        }

        /*
         * Enabled aggressive block sorting
         * Lifted jumps to return sites
         */
        public boolean isVisible(Member member) {
            boolean bl = true;
            switch (1.$SwitchMap$org$codehaus$jackson$annotate$JsonAutoDetect$Visibility[this.ordinal()]) {
                default: {
                    bl = false;
                }
                case 1: {
                    return bl;
                }
                case 2: {
                    return false;
                }
                case 3: {
                    if (!Modifier.isPrivate((int)member.getModifiers())) return bl;
                    return false;
                }
                case 4: {
                    if (Modifier.isProtected((int)member.getModifiers())) return bl;
                }
                case 5: 
            }
            return Modifier.isPublic((int)member.getModifiers());
        }
    }

}

