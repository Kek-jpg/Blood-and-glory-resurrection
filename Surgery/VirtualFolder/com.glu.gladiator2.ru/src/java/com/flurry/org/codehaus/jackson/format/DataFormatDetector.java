/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.io.IOException
 *  java.io.InputStream
 *  java.lang.Object
 *  java.util.Collection
 */
package com.flurry.org.codehaus.jackson.format;

import com.flurry.org.codehaus.jackson.JsonFactory;
import com.flurry.org.codehaus.jackson.format.DataFormatMatcher;
import com.flurry.org.codehaus.jackson.format.InputAccessor;
import com.flurry.org.codehaus.jackson.format.MatchStrength;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;

public class DataFormatDetector {
    public static final int DEFAULT_MAX_INPUT_LOOKAHEAD = 64;
    protected final JsonFactory[] _detectors;
    protected final int _maxInputLookahead;
    protected final MatchStrength _minimalMatch;
    protected final MatchStrength _optimalMatch;

    public DataFormatDetector(Collection<JsonFactory> collection) {
        super((JsonFactory[])collection.toArray((Object[])new JsonFactory[collection.size()]));
    }

    public /* varargs */ DataFormatDetector(JsonFactory ... arrjsonFactory) {
        super(arrjsonFactory, MatchStrength.SOLID_MATCH, MatchStrength.WEAK_MATCH, 64);
    }

    private DataFormatDetector(JsonFactory[] arrjsonFactory, MatchStrength matchStrength, MatchStrength matchStrength2, int n2) {
        this._detectors = arrjsonFactory;
        this._optimalMatch = matchStrength;
        this._minimalMatch = matchStrength2;
        this._maxInputLookahead = n2;
    }

    /*
     * Enabled aggressive block sorting
     */
    private DataFormatMatcher _findFormat(InputAccessor.Std std) throws IOException {
        JsonFactory jsonFactory = null;
        MatchStrength matchStrength = null;
        for (JsonFactory jsonFactory2 : this._detectors) {
            std.reset();
            MatchStrength matchStrength2 = jsonFactory2.hasFormat(std);
            if (matchStrength2 == null || matchStrength2.ordinal() < this._minimalMatch.ordinal() || jsonFactory != null && matchStrength.ordinal() >= matchStrength2.ordinal()) continue;
            jsonFactory = jsonFactory2;
            matchStrength = matchStrength2;
            if (matchStrength2.ordinal() >= this._optimalMatch.ordinal()) break;
        }
        return std.createMatcher(jsonFactory, matchStrength);
    }

    public DataFormatMatcher findFormat(InputStream inputStream) throws IOException {
        return DataFormatDetector.super._findFormat(new InputAccessor.Std(inputStream, new byte[this._maxInputLookahead]));
    }

    public DataFormatMatcher findFormat(byte[] arrby) throws IOException {
        return DataFormatDetector.super._findFormat(new InputAccessor.Std(arrby));
    }

    public DataFormatDetector withMaxInputLookahead(int n2) {
        if (n2 == this._maxInputLookahead) {
            return this;
        }
        return new DataFormatDetector(this._detectors, this._optimalMatch, this._minimalMatch, n2);
    }

    public DataFormatDetector withMinimalMatch(MatchStrength matchStrength) {
        if (matchStrength == this._minimalMatch) {
            return this;
        }
        return new DataFormatDetector(this._detectors, this._optimalMatch, matchStrength, this._maxInputLookahead);
    }

    public DataFormatDetector withOptimalMatch(MatchStrength matchStrength) {
        if (matchStrength == this._optimalMatch) {
            return this;
        }
        return new DataFormatDetector(this._detectors, matchStrength, this._minimalMatch, this._maxInputLookahead);
    }
}

