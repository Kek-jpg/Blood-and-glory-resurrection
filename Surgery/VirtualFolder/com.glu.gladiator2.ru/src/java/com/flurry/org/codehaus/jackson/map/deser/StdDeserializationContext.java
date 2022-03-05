/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.io.IOException
 *  java.lang.Class
 *  java.lang.Exception
 *  java.lang.IllegalArgumentException
 *  java.lang.IllegalStateException
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.Throwable
 *  java.text.DateFormat
 *  java.text.ParseException
 *  java.util.Calendar
 *  java.util.Date
 */
package com.flurry.org.codehaus.jackson.map.deser;

import com.flurry.org.codehaus.jackson.JsonParser;
import com.flurry.org.codehaus.jackson.JsonProcessingException;
import com.flurry.org.codehaus.jackson.JsonToken;
import com.flurry.org.codehaus.jackson.map.BeanProperty;
import com.flurry.org.codehaus.jackson.map.DeserializationConfig;
import com.flurry.org.codehaus.jackson.map.DeserializationContext;
import com.flurry.org.codehaus.jackson.map.DeserializationProblemHandler;
import com.flurry.org.codehaus.jackson.map.DeserializerProvider;
import com.flurry.org.codehaus.jackson.map.InjectableValues;
import com.flurry.org.codehaus.jackson.map.JsonDeserializer;
import com.flurry.org.codehaus.jackson.map.JsonMappingException;
import com.flurry.org.codehaus.jackson.map.exc.UnrecognizedPropertyException;
import com.flurry.org.codehaus.jackson.map.util.ArrayBuilders;
import com.flurry.org.codehaus.jackson.map.util.ClassUtil;
import com.flurry.org.codehaus.jackson.map.util.LinkedNode;
import com.flurry.org.codehaus.jackson.map.util.ObjectBuffer;
import com.flurry.org.codehaus.jackson.type.JavaType;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

public class StdDeserializationContext
extends DeserializationContext {
    static final int MAX_ERROR_STR_LEN = 500;
    protected ArrayBuilders _arrayBuilders;
    protected DateFormat _dateFormat;
    protected final DeserializerProvider _deserProvider;
    protected final InjectableValues _injectableValues;
    protected ObjectBuffer _objectBuffer;
    protected JsonParser _parser;

    public StdDeserializationContext(DeserializationConfig deserializationConfig, JsonParser jsonParser, DeserializerProvider deserializerProvider, InjectableValues injectableValues) {
        super(deserializationConfig);
        this._parser = jsonParser;
        this._deserProvider = deserializerProvider;
        this._injectableValues = injectableValues;
    }

    protected String _calcName(Class<?> class_) {
        if (class_.isArray()) {
            return this._calcName(class_.getComponentType()) + "[]";
        }
        return class_.getName();
    }

    protected String _desc(String string) {
        if (string.length() > 500) {
            string = string.substring(0, 500) + "]...[" + string.substring(-500 + string.length());
        }
        return string;
    }

    protected String _valueDesc() {
        try {
            String string = this._desc(this._parser.getText());
            return string;
        }
        catch (Exception exception) {
            return "[N/A]";
        }
    }

    @Override
    public Calendar constructCalendar(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar;
    }

    protected String determineClassName(Object object) {
        return ClassUtil.getClassDescription(object);
    }

    @Override
    public Object findInjectableValue(Object object, BeanProperty beanProperty, Object object2) {
        if (this._injectableValues == null) {
            throw new IllegalStateException("No 'injectableValues' configured, can not inject value with id [" + object + "]");
        }
        return this._injectableValues.findInjectableValue(object, (DeserializationContext)this, beanProperty, object2);
    }

    @Override
    public final ArrayBuilders getArrayBuilders() {
        if (this._arrayBuilders == null) {
            this._arrayBuilders = new ArrayBuilders();
        }
        return this._arrayBuilders;
    }

    protected DateFormat getDateFormat() {
        if (this._dateFormat == null) {
            this._dateFormat = (DateFormat)this._config.getDateFormat().clone();
        }
        return this._dateFormat;
    }

    @Override
    public DeserializerProvider getDeserializerProvider() {
        return this._deserProvider;
    }

    @Override
    public JsonParser getParser() {
        return this._parser;
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    @Override
    public boolean handleUnknownProperty(JsonParser jsonParser, JsonDeserializer<?> jsonDeserializer, Object object, String string) throws IOException, JsonProcessingException {
        LinkedNode<DeserializationProblemHandler> linkedNode = this._config.getProblemHandlers();
        if (linkedNode == null) return false;
        JsonParser jsonParser2 = this._parser;
        this._parser = jsonParser;
        do {
            if (linkedNode == null) {
                this._parser = jsonParser2;
                return false;
            }
            boolean bl = linkedNode.value().handleUnknownProperty((DeserializationContext)this, jsonDeserializer, object, string);
            if (!bl) break block5;
            break;
        } while (true);
        catch (Throwable throwable) {
            this._parser = jsonParser2;
            throw throwable;
        }
        {
            block5 : {
                this._parser = jsonParser2;
                return true;
            }
            LinkedNode<DeserializationProblemHandler> linkedNode2 = linkedNode.next();
            linkedNode = linkedNode2;
            continue;
        }
    }

    @Override
    public JsonMappingException instantiationException(Class<?> class_, String string) {
        return JsonMappingException.from(this._parser, "Can not construct instance of " + class_.getName() + ", problem: " + string);
    }

    @Override
    public JsonMappingException instantiationException(Class<?> class_, Throwable throwable) {
        return JsonMappingException.from(this._parser, "Can not construct instance of " + class_.getName() + ", problem: " + throwable.getMessage(), throwable);
    }

    @Override
    public final ObjectBuffer leaseObjectBuffer() {
        ObjectBuffer objectBuffer = this._objectBuffer;
        if (objectBuffer == null) {
            return new ObjectBuffer();
        }
        this._objectBuffer = null;
        return objectBuffer;
    }

    @Override
    public JsonMappingException mappingException(Class<?> class_) {
        return this.mappingException(class_, this._parser.getCurrentToken());
    }

    @Override
    public JsonMappingException mappingException(Class<?> class_, JsonToken jsonToken) {
        String string = this._calcName(class_);
        return JsonMappingException.from(this._parser, "Can not deserialize instance of " + string + " out of " + (Object)((Object)jsonToken) + " token");
    }

    @Override
    public Date parseDate(String string) throws IllegalArgumentException {
        try {
            Date date = this.getDateFormat().parse(string);
            return date;
        }
        catch (ParseException parseException) {
            throw new IllegalArgumentException(parseException.getMessage());
        }
    }

    @Override
    public final void returnObjectBuffer(ObjectBuffer objectBuffer) {
        if (this._objectBuffer == null || objectBuffer.initialCapacity() >= this._objectBuffer.initialCapacity()) {
            this._objectBuffer = objectBuffer;
        }
    }

    @Override
    public JsonMappingException unknownFieldException(Object object, String string) {
        return UnrecognizedPropertyException.from(this._parser, object, string);
    }

    @Override
    public JsonMappingException unknownTypeException(JavaType javaType, String string) {
        return JsonMappingException.from(this._parser, "Could not resolve type id '" + string + "' into a subtype of " + javaType);
    }

    @Override
    public JsonMappingException weirdKeyException(Class<?> class_, String string, String string2) {
        return JsonMappingException.from(this._parser, "Can not construct Map key of type " + class_.getName() + " from String \"" + this._desc(string) + "\": " + string2);
    }

    @Override
    public JsonMappingException weirdNumberException(Class<?> class_, String string) {
        return JsonMappingException.from(this._parser, "Can not construct instance of " + class_.getName() + " from number value (" + this._valueDesc() + "): " + string);
    }

    @Override
    public JsonMappingException weirdStringException(Class<?> class_, String string) {
        return JsonMappingException.from(this._parser, "Can not construct instance of " + class_.getName() + " from String value '" + this._valueDesc() + "': " + string);
    }

    @Override
    public JsonMappingException wrongTokenException(JsonParser jsonParser, JsonToken jsonToken, String string) {
        return JsonMappingException.from(jsonParser, "Unexpected token (" + (Object)((Object)jsonParser.getCurrentToken()) + "), expected " + (Object)((Object)jsonToken) + ": " + string);
    }
}

