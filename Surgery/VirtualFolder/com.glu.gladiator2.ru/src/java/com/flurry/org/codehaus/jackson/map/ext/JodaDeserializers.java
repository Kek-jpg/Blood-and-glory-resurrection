/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.io.IOException
 *  java.lang.Class
 *  java.lang.NoSuchFieldError
 *  java.lang.Object
 *  java.lang.String
 *  java.util.Arrays
 *  java.util.Collection
 *  org.joda.time.DateMidnight
 *  org.joda.time.DateTime
 *  org.joda.time.DateTimeZone
 *  org.joda.time.LocalDate
 *  org.joda.time.LocalDateTime
 *  org.joda.time.Period
 *  org.joda.time.ReadableDateTime
 *  org.joda.time.ReadableInstant
 *  org.joda.time.ReadablePeriod
 *  org.joda.time.format.DateTimeFormatter
 *  org.joda.time.format.ISODateTimeFormat
 */
package com.flurry.org.codehaus.jackson.map.ext;

import com.flurry.org.codehaus.jackson.JsonParser;
import com.flurry.org.codehaus.jackson.JsonProcessingException;
import com.flurry.org.codehaus.jackson.JsonToken;
import com.flurry.org.codehaus.jackson.map.DeserializationContext;
import com.flurry.org.codehaus.jackson.map.JsonMappingException;
import com.flurry.org.codehaus.jackson.map.deser.std.StdDeserializer;
import com.flurry.org.codehaus.jackson.map.deser.std.StdScalarDeserializer;
import com.flurry.org.codehaus.jackson.map.util.Provider;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import org.joda.time.DateMidnight;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.joda.time.Period;
import org.joda.time.ReadableDateTime;
import org.joda.time.ReadableInstant;
import org.joda.time.ReadablePeriod;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;

public class JodaDeserializers
implements Provider<StdDeserializer<?>> {
    @Override
    public Collection<StdDeserializer<?>> provide() {
        Object[] arrobject = new StdDeserializer[]{new DateTimeDeserializer<DateTime>(DateTime.class), new DateTimeDeserializer<ReadableDateTime>(ReadableDateTime.class), new DateTimeDeserializer<ReadableInstant>(ReadableInstant.class), new LocalDateDeserializer(), new LocalDateTimeDeserializer(), new DateMidnightDeserializer(), new PeriodDeserializer()};
        return Arrays.asList((Object[])arrobject);
    }

    public static class DateMidnightDeserializer
    extends JodaDeserializer<DateMidnight> {
        public DateMidnightDeserializer() {
            super(DateMidnight.class);
        }

        @Override
        public DateMidnight deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
            if (jsonParser.isExpectedStartArrayToken()) {
                jsonParser.nextToken();
                int n = jsonParser.getIntValue();
                jsonParser.nextToken();
                int n2 = jsonParser.getIntValue();
                jsonParser.nextToken();
                int n3 = jsonParser.getIntValue();
                if (jsonParser.nextToken() != JsonToken.END_ARRAY) {
                    throw deserializationContext.wrongTokenException(jsonParser, JsonToken.END_ARRAY, "after DateMidnight ints");
                }
                return new DateMidnight(n, n2, n3);
            }
            switch (1.$SwitchMap$org$codehaus$jackson$JsonToken[jsonParser.getCurrentToken().ordinal()]) {
                default: {
                    throw deserializationContext.wrongTokenException(jsonParser, JsonToken.START_ARRAY, "expected JSON Array, Number or String");
                }
                case 1: {
                    return new DateMidnight(jsonParser.getLongValue());
                }
                case 2: 
            }
            DateTime dateTime = this.parseLocal(jsonParser);
            if (dateTime == null) {
                return null;
            }
            return dateTime.toDateMidnight();
        }
    }

    public static class DateTimeDeserializer<T extends ReadableInstant>
    extends JodaDeserializer<T> {
        public DateTimeDeserializer(Class<T> class_) {
            super(class_);
        }

        @Override
        public T deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
            JsonToken jsonToken = jsonParser.getCurrentToken();
            if (jsonToken == JsonToken.VALUE_NUMBER_INT) {
                return (T)new DateTime(jsonParser.getLongValue(), DateTimeZone.UTC);
            }
            if (jsonToken == JsonToken.VALUE_STRING) {
                String string = jsonParser.getText().trim();
                if (string.length() == 0) {
                    return null;
                }
                return (T)new DateTime((Object)string, DateTimeZone.UTC);
            }
            throw deserializationContext.mappingException(this.getValueClass());
        }
    }

    static abstract class JodaDeserializer<T>
    extends StdScalarDeserializer<T> {
        static final DateTimeFormatter _localDateTimeFormat = ISODateTimeFormat.localDateOptionalTimeParser();

        protected JodaDeserializer(Class<T> class_) {
            super(class_);
        }

        protected DateTime parseLocal(JsonParser jsonParser) throws IOException, JsonProcessingException {
            String string = jsonParser.getText().trim();
            if (string.length() == 0) {
                return null;
            }
            return _localDateTimeFormat.parseDateTime(string);
        }
    }

    public static class LocalDateDeserializer
    extends JodaDeserializer<LocalDate> {
        public LocalDateDeserializer() {
            super(LocalDate.class);
        }

        @Override
        public LocalDate deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
            if (jsonParser.isExpectedStartArrayToken()) {
                jsonParser.nextToken();
                int n = jsonParser.getIntValue();
                jsonParser.nextToken();
                int n2 = jsonParser.getIntValue();
                jsonParser.nextToken();
                int n3 = jsonParser.getIntValue();
                if (jsonParser.nextToken() != JsonToken.END_ARRAY) {
                    throw deserializationContext.wrongTokenException(jsonParser, JsonToken.END_ARRAY, "after LocalDate ints");
                }
                return new LocalDate(n, n2, n3);
            }
            switch (1.$SwitchMap$org$codehaus$jackson$JsonToken[jsonParser.getCurrentToken().ordinal()]) {
                default: {
                    throw deserializationContext.wrongTokenException(jsonParser, JsonToken.START_ARRAY, "expected JSON Array, String or Number");
                }
                case 1: {
                    return new LocalDate(jsonParser.getLongValue());
                }
                case 2: 
            }
            DateTime dateTime = this.parseLocal(jsonParser);
            if (dateTime == null) {
                return null;
            }
            return dateTime.toLocalDate();
        }
    }

    public static class LocalDateTimeDeserializer
    extends JodaDeserializer<LocalDateTime> {
        public LocalDateTimeDeserializer() {
            super(LocalDateTime.class);
        }

        @Override
        public LocalDateTime deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
            if (jsonParser.isExpectedStartArrayToken()) {
                jsonParser.nextToken();
                int n = jsonParser.getIntValue();
                jsonParser.nextToken();
                int n2 = jsonParser.getIntValue();
                jsonParser.nextToken();
                int n3 = jsonParser.getIntValue();
                jsonParser.nextToken();
                int n4 = jsonParser.getIntValue();
                jsonParser.nextToken();
                int n5 = jsonParser.getIntValue();
                jsonParser.nextToken();
                int n6 = jsonParser.getIntValue();
                JsonToken jsonToken = jsonParser.nextToken();
                JsonToken jsonToken2 = JsonToken.END_ARRAY;
                int n7 = 0;
                if (jsonToken != jsonToken2) {
                    n7 = jsonParser.getIntValue();
                    jsonParser.nextToken();
                }
                if (jsonParser.getCurrentToken() != JsonToken.END_ARRAY) {
                    throw deserializationContext.wrongTokenException(jsonParser, JsonToken.END_ARRAY, "after LocalDateTime ints");
                }
                return new LocalDateTime(n, n2, n3, n4, n5, n6, n7);
            }
            switch (1.$SwitchMap$org$codehaus$jackson$JsonToken[jsonParser.getCurrentToken().ordinal()]) {
                default: {
                    throw deserializationContext.wrongTokenException(jsonParser, JsonToken.START_ARRAY, "expected JSON Array or Number");
                }
                case 1: {
                    return new LocalDateTime(jsonParser.getLongValue());
                }
                case 2: 
            }
            DateTime dateTime = this.parseLocal(jsonParser);
            if (dateTime == null) {
                return null;
            }
            return dateTime.toLocalDateTime();
        }
    }

    public static class PeriodDeserializer
    extends JodaDeserializer<ReadablePeriod> {
        public PeriodDeserializer() {
            super(ReadablePeriod.class);
        }

        @Override
        public ReadablePeriod deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
            switch (1.$SwitchMap$org$codehaus$jackson$JsonToken[jsonParser.getCurrentToken().ordinal()]) {
                default: {
                    throw deserializationContext.wrongTokenException(jsonParser, JsonToken.START_ARRAY, "expected JSON Number or String");
                }
                case 1: {
                    return new Period(jsonParser.getLongValue());
                }
                case 2: 
            }
            return new Period((Object)jsonParser.getText());
        }
    }

}

