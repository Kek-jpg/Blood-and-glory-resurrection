/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.String
 *  java.lang.StringBuilder
 */
package com.flurry.org.codehaus.jackson.impl;

import com.flurry.org.codehaus.jackson.JsonStreamContext;

public class JsonWriteContext
extends JsonStreamContext {
    public static final int STATUS_EXPECT_NAME = 5;
    public static final int STATUS_EXPECT_VALUE = 4;
    public static final int STATUS_OK_AFTER_COLON = 2;
    public static final int STATUS_OK_AFTER_COMMA = 1;
    public static final int STATUS_OK_AFTER_SPACE = 3;
    public static final int STATUS_OK_AS_IS;
    protected JsonWriteContext _child = null;
    protected String _currentName;
    protected final JsonWriteContext _parent;

    protected JsonWriteContext(int n, JsonWriteContext jsonWriteContext) {
        this._type = n;
        this._parent = jsonWriteContext;
        this._index = -1;
    }

    public static JsonWriteContext createRootContext() {
        return new JsonWriteContext(0, null);
    }

    private final JsonWriteContext reset(int n) {
        this._type = n;
        this._index = -1;
        this._currentName = null;
        return this;
    }

    /*
     * Enabled aggressive block sorting
     */
    protected final void appendDesc(StringBuilder stringBuilder) {
        if (this._type == 2) {
            stringBuilder.append('{');
            if (this._currentName != null) {
                stringBuilder.append('\"');
                stringBuilder.append(this._currentName);
                stringBuilder.append('\"');
            } else {
                stringBuilder.append('?');
            }
            stringBuilder.append('}');
            return;
        }
        if (this._type == 1) {
            stringBuilder.append('[');
            stringBuilder.append(this.getCurrentIndex());
            stringBuilder.append(']');
            return;
        }
        stringBuilder.append("/");
    }

    public final JsonWriteContext createChildArrayContext() {
        JsonWriteContext jsonWriteContext = this._child;
        if (jsonWriteContext == null) {
            JsonWriteContext jsonWriteContext2;
            this._child = jsonWriteContext2 = new JsonWriteContext(1, this);
            return jsonWriteContext2;
        }
        return jsonWriteContext.reset(1);
    }

    public final JsonWriteContext createChildObjectContext() {
        JsonWriteContext jsonWriteContext = this._child;
        if (jsonWriteContext == null) {
            JsonWriteContext jsonWriteContext2;
            this._child = jsonWriteContext2 = new JsonWriteContext(2, this);
            return jsonWriteContext2;
        }
        return jsonWriteContext.reset(2);
    }

    @Override
    public final String getCurrentName() {
        return this._currentName;
    }

    @Override
    public final JsonWriteContext getParent() {
        return this._parent;
    }

    public final String toString() {
        StringBuilder stringBuilder = new StringBuilder(64);
        this.appendDesc(stringBuilder);
        return stringBuilder.toString();
    }

    public final int writeFieldName(String string) {
        if (this._type != 2 || this._currentName != null) {
            return 4;
        }
        this._currentName = string;
        return this._index >= 0;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public final int writeValue() {
        int n;
        if (this._type == 2) {
            if (this._currentName == null) {
                return 5;
            }
            this._currentName = null;
            this._index = 1 + this._index;
            return 2;
        }
        if (this._type == 1) {
            int n2 = this._index;
            this._index = 1 + this._index;
            n = 0;
            if (n2 < 0) return n;
            return 1;
        }
        int n3 = this._index = 1 + this._index;
        n = 0;
        if (n3 == 0) return n;
        return 3;
    }
}

