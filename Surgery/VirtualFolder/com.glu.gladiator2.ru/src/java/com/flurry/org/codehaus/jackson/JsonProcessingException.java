/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.io.IOException
 *  java.lang.Class
 *  java.lang.String
 *  java.lang.StringBuilder
 *  java.lang.Throwable
 */
package com.flurry.org.codehaus.jackson;

import com.flurry.org.codehaus.jackson.JsonLocation;
import java.io.IOException;

public class JsonProcessingException
extends IOException {
    static final long serialVersionUID = 123L;
    protected JsonLocation mLocation;

    protected JsonProcessingException(String string) {
        super(string);
    }

    protected JsonProcessingException(String string, JsonLocation jsonLocation) {
        super(string, jsonLocation, null);
    }

    protected JsonProcessingException(String string, JsonLocation jsonLocation, Throwable throwable) {
        super(string);
        if (throwable != null) {
            this.initCause(throwable);
        }
        this.mLocation = jsonLocation;
    }

    protected JsonProcessingException(String string, Throwable throwable) {
        super(string, null, throwable);
    }

    protected JsonProcessingException(Throwable throwable) {
        super(null, null, throwable);
    }

    public JsonLocation getLocation() {
        return this.mLocation;
    }

    public String getMessage() {
        JsonLocation jsonLocation;
        String string = super.getMessage();
        if (string == null) {
            string = "N/A";
        }
        if ((jsonLocation = this.getLocation()) != null) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(string);
            stringBuilder.append('\n');
            stringBuilder.append(" at ");
            stringBuilder.append(jsonLocation.toString());
            string = stringBuilder.toString();
        }
        return string;
    }

    public String toString() {
        return this.getClass().getName() + ": " + this.getMessage();
    }
}

