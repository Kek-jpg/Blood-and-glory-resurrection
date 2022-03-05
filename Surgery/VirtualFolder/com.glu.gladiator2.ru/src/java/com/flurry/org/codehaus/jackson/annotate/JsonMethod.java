/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Enum
 *  java.lang.Object
 *  java.lang.String
 */
package com.flurry.org.codehaus.jackson.annotate;

public final class JsonMethod
extends Enum<JsonMethod> {
    private static final /* synthetic */ JsonMethod[] $VALUES;
    public static final /* enum */ JsonMethod ALL;
    public static final /* enum */ JsonMethod CREATOR;
    public static final /* enum */ JsonMethod FIELD;
    public static final /* enum */ JsonMethod GETTER;
    public static final /* enum */ JsonMethod IS_GETTER;
    public static final /* enum */ JsonMethod NONE;
    public static final /* enum */ JsonMethod SETTER;

    static {
        GETTER = new JsonMethod();
        SETTER = new JsonMethod();
        CREATOR = new JsonMethod();
        FIELD = new JsonMethod();
        IS_GETTER = new JsonMethod();
        NONE = new JsonMethod();
        ALL = new JsonMethod();
        JsonMethod[] arrjsonMethod = new JsonMethod[]{GETTER, SETTER, CREATOR, FIELD, IS_GETTER, NONE, ALL};
        $VALUES = arrjsonMethod;
    }

    public static JsonMethod valueOf(String string) {
        return (JsonMethod)Enum.valueOf(JsonMethod.class, (String)string);
    }

    public static JsonMethod[] values() {
        return (JsonMethod[])$VALUES.clone();
    }

    public boolean creatorEnabled() {
        return this == CREATOR || this == ALL;
    }

    public boolean fieldEnabled() {
        return this == FIELD || this == ALL;
    }

    public boolean getterEnabled() {
        return this == GETTER || this == ALL;
    }

    public boolean isGetterEnabled() {
        return this == IS_GETTER || this == ALL;
    }

    public boolean setterEnabled() {
        return this == SETTER || this == ALL;
    }
}

