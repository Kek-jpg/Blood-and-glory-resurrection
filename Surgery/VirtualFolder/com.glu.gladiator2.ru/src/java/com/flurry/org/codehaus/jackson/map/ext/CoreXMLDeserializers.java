/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.io.IOException
 *  java.lang.Class
 *  java.lang.IllegalArgumentException
 *  java.lang.Object
 *  java.lang.RuntimeException
 *  java.lang.String
 *  java.lang.Throwable
 *  java.util.Arrays
 *  java.util.Collection
 *  java.util.Date
 *  java.util.GregorianCalendar
 *  javax.xml.datatype.DatatypeConfigurationException
 *  javax.xml.datatype.DatatypeFactory
 *  javax.xml.datatype.Duration
 *  javax.xml.datatype.XMLGregorianCalendar
 *  javax.xml.namespace.QName
 */
package com.flurry.org.codehaus.jackson.map.ext;

import com.flurry.org.codehaus.jackson.JsonParser;
import com.flurry.org.codehaus.jackson.JsonProcessingException;
import com.flurry.org.codehaus.jackson.map.DeserializationContext;
import com.flurry.org.codehaus.jackson.map.deser.std.FromStringDeserializer;
import com.flurry.org.codehaus.jackson.map.deser.std.StdDeserializer;
import com.flurry.org.codehaus.jackson.map.deser.std.StdScalarDeserializer;
import com.flurry.org.codehaus.jackson.map.util.Provider;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.Duration;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.namespace.QName;

public class CoreXMLDeserializers
implements Provider<StdDeserializer<?>> {
    static final DatatypeFactory _dataTypeFactory;

    static {
        try {
            _dataTypeFactory = DatatypeFactory.newInstance();
            return;
        }
        catch (DatatypeConfigurationException datatypeConfigurationException) {
            throw new RuntimeException((Throwable)datatypeConfigurationException);
        }
    }

    @Override
    public Collection<StdDeserializer<?>> provide() {
        Object[] arrobject = new StdDeserializer[]{new DurationDeserializer(), new GregorianCalendarDeserializer(), new QNameDeserializer()};
        return Arrays.asList((Object[])arrobject);
    }

    public static class DurationDeserializer
    extends FromStringDeserializer<Duration> {
        public DurationDeserializer() {
            super(Duration.class);
        }

        @Override
        protected Duration _deserialize(String string, DeserializationContext deserializationContext) throws IllegalArgumentException {
            return CoreXMLDeserializers._dataTypeFactory.newDuration(string);
        }
    }

    public static class GregorianCalendarDeserializer
    extends StdScalarDeserializer<XMLGregorianCalendar> {
        public GregorianCalendarDeserializer() {
            super(XMLGregorianCalendar.class);
        }

        @Override
        public XMLGregorianCalendar deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
            Date date = this._parseDate(jsonParser, deserializationContext);
            if (date == null) {
                return null;
            }
            GregorianCalendar gregorianCalendar = new GregorianCalendar();
            gregorianCalendar.setTime(date);
            return CoreXMLDeserializers._dataTypeFactory.newXMLGregorianCalendar(gregorianCalendar);
        }
    }

    public static class QNameDeserializer
    extends FromStringDeserializer<QName> {
        public QNameDeserializer() {
            super(QName.class);
        }

        @Override
        protected QName _deserialize(String string, DeserializationContext deserializationContext) throws IllegalArgumentException {
            return QName.valueOf((String)string);
        }
    }

}

