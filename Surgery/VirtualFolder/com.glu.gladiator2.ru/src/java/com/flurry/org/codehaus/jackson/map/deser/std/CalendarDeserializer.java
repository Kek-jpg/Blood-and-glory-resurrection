/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.io.IOException
 *  java.lang.Class
 *  java.lang.Exception
 *  java.lang.Object
 *  java.lang.Throwable
 *  java.util.Calendar
 *  java.util.Date
 */
package com.flurry.org.codehaus.jackson.map.deser.std;

import com.flurry.org.codehaus.jackson.JsonParser;
import com.flurry.org.codehaus.jackson.JsonProcessingException;
import com.flurry.org.codehaus.jackson.map.DeserializationContext;
import com.flurry.org.codehaus.jackson.map.JsonMappingException;
import com.flurry.org.codehaus.jackson.map.annotate.JacksonStdImpl;
import com.flurry.org.codehaus.jackson.map.deser.std.StdScalarDeserializer;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

@JacksonStdImpl
public class CalendarDeserializer
extends StdScalarDeserializer<Calendar> {
    protected final Class<? extends Calendar> _calendarClass;

    public CalendarDeserializer() {
        this(null);
    }

    public CalendarDeserializer(Class<? extends Calendar> class_) {
        super(Calendar.class);
        this._calendarClass = class_;
    }

    @Override
    public Calendar deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        Date date = this._parseDate(jsonParser, deserializationContext);
        if (date == null) {
            return null;
        }
        if (this._calendarClass == null) {
            return deserializationContext.constructCalendar(date);
        }
        try {
            Calendar calendar = (Calendar)this._calendarClass.newInstance();
            calendar.setTimeInMillis(date.getTime());
            return calendar;
        }
        catch (Exception exception) {
            throw deserializationContext.instantiationException(this._calendarClass, exception);
        }
    }
}

