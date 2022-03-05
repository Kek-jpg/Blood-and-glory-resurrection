/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.StringBuilder
 */
package com.flurry.org.codehaus.jackson.impl;

import com.flurry.org.codehaus.jackson.JsonLocation;
import com.flurry.org.codehaus.jackson.JsonStreamContext;
import com.flurry.org.codehaus.jackson.util.CharTypes;

public final class JsonReadContext
extends JsonStreamContext {
    protected JsonReadContext _child = null;
    protected int _columnNr;
    protected String _currentName;
    protected int _lineNr;
    protected final JsonReadContext _parent;

    public JsonReadContext(JsonReadContext jsonReadContext, int n, int n2, int n3) {
        this._type = n;
        this._parent = jsonReadContext;
        this._lineNr = n2;
        this._columnNr = n3;
        this._index = -1;
    }

    public static JsonReadContext createRootContext() {
        return new JsonReadContext(null, 0, 1, 0);
    }

    public static JsonReadContext createRootContext(int n, int n2) {
        return new JsonReadContext(null, 0, n, n2);
    }

    public final JsonReadContext createChildArrayContext(int n, int n2) {
        JsonReadContext jsonReadContext = this._child;
        if (jsonReadContext == null) {
            JsonReadContext jsonReadContext2;
            this._child = jsonReadContext2 = new JsonReadContext((JsonReadContext)this, 1, n, n2);
            return jsonReadContext2;
        }
        jsonReadContext.reset(1, n, n2);
        return jsonReadContext;
    }

    public final JsonReadContext createChildObjectContext(int n, int n2) {
        JsonReadContext jsonReadContext = this._child;
        if (jsonReadContext == null) {
            JsonReadContext jsonReadContext2;
            this._child = jsonReadContext2 = new JsonReadContext((JsonReadContext)this, 2, n, n2);
            return jsonReadContext2;
        }
        jsonReadContext.reset(2, n, n2);
        return jsonReadContext;
    }

    public final boolean expectComma() {
        int n;
        this._index = n = 1 + this._index;
        return this._type != 0 && n > 0;
    }

    @Override
    public final String getCurrentName() {
        return this._currentName;
    }

    @Override
    public final JsonReadContext getParent() {
        return this._parent;
    }

    public final JsonLocation getStartLocation(Object object) {
        return new JsonLocation(object, -1L, this._lineNr, this._columnNr);
    }

    protected final void reset(int n, int n2, int n3) {
        this._type = n;
        this._index = -1;
        this._lineNr = n2;
        this._columnNr = n3;
        this._currentName = null;
    }

    public void setCurrentName(String string) {
        this._currentName = string;
    }

    /*
     * Enabled aggressive block sorting
     */
    public final String toString() {
        StringBuilder stringBuilder = new StringBuilder(64);
        switch (this._type) {
            case 0: {
                stringBuilder.append("/");
                return stringBuilder.toString();
            }
            case 1: {
                stringBuilder.append('[');
                stringBuilder.append(this.getCurrentIndex());
                stringBuilder.append(']');
                return stringBuilder.toString();
            }
            case 2: {
                stringBuilder.append('{');
                if (this._currentName != null) {
                    stringBuilder.append('\"');
                    CharTypes.appendQuoted(stringBuilder, this._currentName);
                    stringBuilder.append('\"');
                } else {
                    stringBuilder.append('?');
                }
                stringBuilder.append('}');
                return stringBuilder.toString();
            }
        }
        return stringBuilder.toString();
    }
}

