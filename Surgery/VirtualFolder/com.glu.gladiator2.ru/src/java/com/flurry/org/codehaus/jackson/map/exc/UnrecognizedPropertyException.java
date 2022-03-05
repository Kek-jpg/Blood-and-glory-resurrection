/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Class
 *  java.lang.IllegalArgumentException
 *  java.lang.Object
 *  java.lang.String
 */
package com.flurry.org.codehaus.jackson.map.exc;

import com.flurry.org.codehaus.jackson.JsonLocation;
import com.flurry.org.codehaus.jackson.JsonParser;
import com.flurry.org.codehaus.jackson.map.JsonMappingException;

public class UnrecognizedPropertyException
extends JsonMappingException {
    private static final long serialVersionUID = 1L;
    protected final Class<?> _referringClass;
    protected final String _unrecognizedPropertyName;

    public UnrecognizedPropertyException(String string, JsonLocation jsonLocation, Class<?> class_, String string2) {
        super(string, jsonLocation);
        this._referringClass = class_;
        this._unrecognizedPropertyName = string2;
    }

    /*
     * Enabled aggressive block sorting
     */
    public static UnrecognizedPropertyException from(JsonParser jsonParser, Object object, String string) {
        if (object == null) {
            throw new IllegalArgumentException();
        }
        Class class_ = object instanceof Class ? (Class)object : object.getClass();
        UnrecognizedPropertyException unrecognizedPropertyException = new UnrecognizedPropertyException("Unrecognized field \"" + string + "\" (Class " + class_.getName() + "), not marked as ignorable", jsonParser.getCurrentLocation(), class_, string);
        unrecognizedPropertyException.prependPath(object, string);
        return unrecognizedPropertyException;
    }

    public Class<?> getReferringClass() {
        return this._referringClass;
    }

    public String getUnrecognizedPropertyName() {
        return this._unrecognizedPropertyName;
    }
}

