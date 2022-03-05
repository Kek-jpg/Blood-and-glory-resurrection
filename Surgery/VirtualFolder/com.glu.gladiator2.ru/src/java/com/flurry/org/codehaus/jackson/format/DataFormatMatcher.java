/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  com.flurry.org.codehaus.jackson.io.IOContext
 *  com.flurry.org.codehaus.jackson.io.MergedStream
 *  java.io.ByteArrayInputStream
 *  java.io.IOException
 *  java.io.InputStream
 *  java.lang.Object
 *  java.lang.String
 */
package com.flurry.org.codehaus.jackson.format;

import com.flurry.org.codehaus.jackson.JsonFactory;
import com.flurry.org.codehaus.jackson.JsonParser;
import com.flurry.org.codehaus.jackson.format.MatchStrength;
import com.flurry.org.codehaus.jackson.io.IOContext;
import com.flurry.org.codehaus.jackson.io.MergedStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

public class DataFormatMatcher {
    protected final byte[] _bufferedData;
    protected final int _bufferedLength;
    protected final JsonFactory _match;
    protected final MatchStrength _matchStrength;
    protected final InputStream _originalStream;

    protected DataFormatMatcher(InputStream inputStream, byte[] arrby, int n2, JsonFactory jsonFactory, MatchStrength matchStrength) {
        this._originalStream = inputStream;
        this._bufferedData = arrby;
        this._bufferedLength = n2;
        this._match = jsonFactory;
        this._matchStrength = matchStrength;
    }

    public JsonParser createParserWithMatch() throws IOException {
        if (this._match == null) {
            return null;
        }
        if (this._originalStream == null) {
            return this._match.createJsonParser(this._bufferedData, 0, this._bufferedLength);
        }
        return this._match.createJsonParser(this.getDataStream());
    }

    public InputStream getDataStream() {
        if (this._originalStream == null) {
            return new ByteArrayInputStream(this._bufferedData, 0, this._bufferedLength);
        }
        return new MergedStream(null, this._originalStream, this._bufferedData, 0, this._bufferedLength);
    }

    public JsonFactory getMatch() {
        return this._match;
    }

    public MatchStrength getMatchStrength() {
        if (this._matchStrength == null) {
            return MatchStrength.INCONCLUSIVE;
        }
        return this._matchStrength;
    }

    public String getMatchedFormatName() {
        return this._match.getFormatName();
    }

    public boolean hasMatch() {
        return this._match != null;
    }
}

