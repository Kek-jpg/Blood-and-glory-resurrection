/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.io.IOException
 *  java.lang.Object
 *  java.util.ArrayList
 *  java.util.List
 */
package com.flurry.org.codehaus.jackson.util;

import com.flurry.org.codehaus.jackson.JsonParseException;
import com.flurry.org.codehaus.jackson.JsonParser;
import com.flurry.org.codehaus.jackson.JsonToken;
import com.flurry.org.codehaus.jackson.util.JsonParserDelegate;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JsonParserSequence
extends JsonParserDelegate {
    protected int _nextParser;
    protected final JsonParser[] _parsers;

    protected JsonParserSequence(JsonParser[] arrjsonParser) {
        super(arrjsonParser[0]);
        this._parsers = arrjsonParser;
        this._nextParser = 1;
    }

    /*
     * Enabled aggressive block sorting
     */
    public static JsonParserSequence createFlattened(JsonParser jsonParser, JsonParser jsonParser2) {
        if (!(jsonParser instanceof JsonParserSequence) && !(jsonParser2 instanceof JsonParserSequence)) {
            return new JsonParserSequence(new JsonParser[]{jsonParser, jsonParser2});
        }
        ArrayList arrayList = new ArrayList();
        if (jsonParser instanceof JsonParserSequence) {
            ((JsonParserSequence)jsonParser).addFlattenedActiveParsers((List<JsonParser>)arrayList);
        } else {
            arrayList.add((Object)jsonParser);
        }
        if (jsonParser2 instanceof JsonParserSequence) {
            ((JsonParserSequence)jsonParser2).addFlattenedActiveParsers((List<JsonParser>)arrayList);
            return new JsonParserSequence((JsonParser[])arrayList.toArray((Object[])new JsonParser[arrayList.size()]));
        }
        arrayList.add((Object)jsonParser2);
        return new JsonParserSequence((JsonParser[])arrayList.toArray((Object[])new JsonParser[arrayList.size()]));
    }

    /*
     * Enabled aggressive block sorting
     */
    protected void addFlattenedActiveParsers(List<JsonParser> list) {
        int n = -1 + this._nextParser;
        int n2 = this._parsers.length;
        while (n < n2) {
            JsonParser jsonParser = this._parsers[n];
            if (jsonParser instanceof JsonParserSequence) {
                ((JsonParserSequence)jsonParser).addFlattenedActiveParsers(list);
            } else {
                list.add((Object)jsonParser);
            }
            ++n;
        }
        return;
    }

    @Override
    public void close() throws IOException {
        do {
            this.delegate.close();
        } while (this.switchToNext());
    }

    public int containedParsersCount() {
        return this._parsers.length;
    }

    @Override
    public JsonToken nextToken() throws IOException, JsonParseException {
        JsonToken jsonToken = this.delegate.nextToken();
        if (jsonToken != null) {
            return jsonToken;
        }
        while (this.switchToNext()) {
            JsonToken jsonToken2 = this.delegate.nextToken();
            if (jsonToken2 == null) continue;
            return jsonToken2;
        }
        return null;
    }

    protected boolean switchToNext() {
        if (this._nextParser >= this._parsers.length) {
            return false;
        }
        JsonParser[] arrjsonParser = this._parsers;
        int n = this._nextParser;
        this._nextParser = n + 1;
        this.delegate = arrjsonParser[n];
        return true;
    }
}

