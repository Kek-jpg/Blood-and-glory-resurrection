/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.io.ByteArrayInputStream
 *  java.io.DataInputStream
 *  java.io.IOException
 *  java.io.InputStream
 *  java.lang.Class
 *  java.lang.IllegalArgumentException
 *  java.lang.Iterable
 *  java.lang.Object
 *  java.lang.String
 *  java.net.InetAddress
 *  java.net.URI
 *  java.net.URL
 *  java.nio.charset.Charset
 *  java.util.ArrayList
 *  java.util.Currency
 *  java.util.Locale
 *  java.util.TimeZone
 *  java.util.UUID
 *  java.util.regex.Pattern
 */
package com.flurry.org.codehaus.jackson.map.deser.std;

import com.flurry.org.codehaus.jackson.JsonParser;
import com.flurry.org.codehaus.jackson.JsonProcessingException;
import com.flurry.org.codehaus.jackson.JsonToken;
import com.flurry.org.codehaus.jackson.map.DeserializationContext;
import com.flurry.org.codehaus.jackson.map.JsonMappingException;
import com.flurry.org.codehaus.jackson.map.deser.std.StdScalarDeserializer;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.URI;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Currency;
import java.util.Locale;
import java.util.TimeZone;
import java.util.UUID;
import java.util.regex.Pattern;

public abstract class FromStringDeserializer<T>
extends StdScalarDeserializer<T> {
    protected FromStringDeserializer(Class<?> class_) {
        super(class_);
    }

    public static Iterable<FromStringDeserializer<?>> all() {
        ArrayList arrayList = new ArrayList();
        arrayList.add((Object)new UUIDDeserializer());
        arrayList.add((Object)new URLDeserializer());
        arrayList.add((Object)new URIDeserializer());
        arrayList.add((Object)new CurrencyDeserializer());
        arrayList.add((Object)new PatternDeserializer());
        arrayList.add((Object)new LocaleDeserializer());
        arrayList.add((Object)new InetAddressDeserializer());
        arrayList.add((Object)new TimeZoneDeserializer());
        arrayList.add((Object)new CharsetDeserializer());
        return arrayList;
    }

    protected abstract T _deserialize(String var1, DeserializationContext var2) throws IOException, JsonProcessingException;

    protected T _deserializeEmbedded(Object object, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        throw deserializationContext.mappingException("Don't know how to convert embedded Object of type " + object.getClass().getName() + " into " + this._valueClass.getName());
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public final T deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        T t;
        if (jsonParser.getCurrentToken() != JsonToken.VALUE_STRING) {
            if (jsonParser.getCurrentToken() != JsonToken.VALUE_EMBEDDED_OBJECT) throw deserializationContext.mappingException(this._valueClass);
            Object object = jsonParser.getEmbeddedObject();
            t = null;
            if (object == null) return t;
            if (!this._valueClass.isAssignableFrom(object.getClass())) return this._deserializeEmbedded(object, deserializationContext);
            return (T)object;
        }
        String string = jsonParser.getText().trim();
        int n = string.length();
        t = null;
        if (n == 0) {
            return t;
        }
        try {}
        catch (IllegalArgumentException illegalArgumentException) {
            throw deserializationContext.weirdStringException(this._valueClass, "not a valid textual representation");
        }
        T t2 = this._deserialize(string, deserializationContext);
        t = t2;
        if (t != null) return t;
        throw deserializationContext.weirdStringException(this._valueClass, "not a valid textual representation");
    }

    protected static class CharsetDeserializer
    extends FromStringDeserializer<Charset> {
        public CharsetDeserializer() {
            super(Charset.class);
        }

        @Override
        protected Charset _deserialize(String string, DeserializationContext deserializationContext) throws IOException {
            return Charset.forName((String)string);
        }
    }

    public static class CurrencyDeserializer
    extends FromStringDeserializer<Currency> {
        public CurrencyDeserializer() {
            super(Currency.class);
        }

        @Override
        protected Currency _deserialize(String string, DeserializationContext deserializationContext) throws IllegalArgumentException {
            return Currency.getInstance((String)string);
        }
    }

    protected static class InetAddressDeserializer
    extends FromStringDeserializer<InetAddress> {
        public InetAddressDeserializer() {
            super(InetAddress.class);
        }

        @Override
        protected InetAddress _deserialize(String string, DeserializationContext deserializationContext) throws IOException {
            return InetAddress.getByName((String)string);
        }
    }

    protected static class LocaleDeserializer
    extends FromStringDeserializer<Locale> {
        public LocaleDeserializer() {
            super(Locale.class);
        }

        @Override
        protected Locale _deserialize(String string, DeserializationContext deserializationContext) throws IOException {
            int n = string.indexOf(95);
            if (n < 0) {
                return new Locale(string);
            }
            String string2 = string.substring(0, n);
            String string3 = string.substring(n + 1);
            int n2 = string3.indexOf(95);
            if (n2 < 0) {
                return new Locale(string2, string3);
            }
            return new Locale(string2, string3.substring(0, n2), string3.substring(n2 + 1));
        }
    }

    public static class PatternDeserializer
    extends FromStringDeserializer<Pattern> {
        public PatternDeserializer() {
            super(Pattern.class);
        }

        @Override
        protected Pattern _deserialize(String string, DeserializationContext deserializationContext) throws IllegalArgumentException {
            return Pattern.compile((String)string);
        }
    }

    protected static class TimeZoneDeserializer
    extends FromStringDeserializer<TimeZone> {
        public TimeZoneDeserializer() {
            super(TimeZone.class);
        }

        @Override
        protected TimeZone _deserialize(String string, DeserializationContext deserializationContext) throws IOException {
            return TimeZone.getTimeZone((String)string);
        }
    }

    public static class URIDeserializer
    extends FromStringDeserializer<URI> {
        public URIDeserializer() {
            super(URI.class);
        }

        @Override
        protected URI _deserialize(String string, DeserializationContext deserializationContext) throws IllegalArgumentException {
            return URI.create((String)string);
        }
    }

    public static class URLDeserializer
    extends FromStringDeserializer<URL> {
        public URLDeserializer() {
            super(URL.class);
        }

        @Override
        protected URL _deserialize(String string, DeserializationContext deserializationContext) throws IOException {
            return new URL(string);
        }
    }

    public static class UUIDDeserializer
    extends FromStringDeserializer<UUID> {
        public UUIDDeserializer() {
            super(UUID.class);
        }

        @Override
        protected UUID _deserialize(String string, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
            return UUID.fromString((String)string);
        }

        @Override
        protected UUID _deserializeEmbedded(Object object, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
            if (object instanceof byte[]) {
                byte[] arrby = (byte[])object;
                if (arrby.length != 16) {
                    deserializationContext.mappingException("Can only construct UUIDs from 16 byte arrays; got " + arrby.length + " bytes");
                }
                DataInputStream dataInputStream = new DataInputStream((InputStream)new ByteArrayInputStream(arrby));
                return new UUID(dataInputStream.readLong(), dataInputStream.readLong());
            }
            super._deserializeEmbedded(object, deserializationContext);
            return null;
        }
    }

}

