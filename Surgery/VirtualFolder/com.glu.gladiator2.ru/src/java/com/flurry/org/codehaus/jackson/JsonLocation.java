/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.io.Serializable
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.StringBuilder
 */
package com.flurry.org.codehaus.jackson;

import com.flurry.org.codehaus.jackson.annotate.JsonCreator;
import com.flurry.org.codehaus.jackson.annotate.JsonProperty;
import java.io.Serializable;

public class JsonLocation
implements Serializable {
    public static final JsonLocation NA = new JsonLocation("N/A", -1L, -1L, -1, -1);
    private static final long serialVersionUID = 1L;
    final int _columnNr;
    final int _lineNr;
    final Object _sourceRef;
    final long _totalBytes;
    final long _totalChars;

    public JsonLocation(Object object, long l2, int n2, int n3) {
        super(object, -1L, l2, n2, n3);
    }

    @JsonCreator
    public JsonLocation(@JsonProperty(value="sourceRef") Object object, @JsonProperty(value="byteOffset") long l2, @JsonProperty(value="charOffset") long l3, @JsonProperty(value="lineNr") int n2, @JsonProperty(value="columnNr") int n3) {
        this._sourceRef = object;
        this._totalBytes = l2;
        this._totalChars = l3;
        this._lineNr = n2;
        this._columnNr = n3;
    }

    /*
     * Enabled aggressive block sorting
     */
    public boolean equals(Object object) {
        boolean bl = true;
        if (object == this) {
            return bl;
        }
        boolean bl2 = false;
        if (object == null) return bl2;
        boolean bl3 = object instanceof JsonLocation;
        bl2 = false;
        if (!bl3) return bl2;
        JsonLocation jsonLocation = (JsonLocation)object;
        if (this._sourceRef == null) {
            Object object2 = jsonLocation._sourceRef;
            bl2 = false;
            if (object2 != null) return bl2;
        } else if (!this._sourceRef.equals(jsonLocation._sourceRef)) {
            return false;
        }
        if (this._lineNr != jsonLocation._lineNr) return false;
        if (this._columnNr != jsonLocation._columnNr) return false;
        if (this._totalChars != jsonLocation._totalChars) return false;
        if (this.getByteOffset() != jsonLocation.getByteOffset()) return false;
        return bl;
    }

    public long getByteOffset() {
        return this._totalBytes;
    }

    public long getCharOffset() {
        return this._totalChars;
    }

    public int getColumnNr() {
        return this._columnNr;
    }

    public int getLineNr() {
        return this._lineNr;
    }

    public Object getSourceRef() {
        return this._sourceRef;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public int hashCode() {
        int n2;
        if (this._sourceRef == null) {
            n2 = 1;
            do {
                return ((n2 ^ this._lineNr) + this._columnNr ^ (int)this._totalChars) + (int)this._totalBytes;
                break;
            } while (true);
        }
        n2 = this._sourceRef.hashCode();
        return ((n2 ^ this._lineNr) + this._columnNr ^ (int)this._totalChars) + (int)this._totalBytes;
    }

    /*
     * Enabled aggressive block sorting
     */
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder(80);
        stringBuilder.append("[Source: ");
        if (this._sourceRef == null) {
            stringBuilder.append("UNKNOWN");
        } else {
            stringBuilder.append(this._sourceRef.toString());
        }
        stringBuilder.append("; line: ");
        stringBuilder.append(this._lineNr);
        stringBuilder.append(", column: ");
        stringBuilder.append(this._columnNr);
        stringBuilder.append(']');
        return stringBuilder.toString();
    }
}

