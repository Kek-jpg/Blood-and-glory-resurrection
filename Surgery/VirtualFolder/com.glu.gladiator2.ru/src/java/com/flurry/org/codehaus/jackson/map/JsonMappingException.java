/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.io.Serializable
 *  java.lang.Class
 *  java.lang.NullPointerException
 *  java.lang.Object
 *  java.lang.Package
 *  java.lang.String
 *  java.lang.StringBuilder
 *  java.lang.Throwable
 *  java.util.Collections
 *  java.util.Iterator
 *  java.util.LinkedList
 *  java.util.List
 */
package com.flurry.org.codehaus.jackson.map;

import com.flurry.org.codehaus.jackson.JsonLocation;
import com.flurry.org.codehaus.jackson.JsonParser;
import com.flurry.org.codehaus.jackson.JsonProcessingException;
import java.io.Serializable;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class JsonMappingException
extends JsonProcessingException {
    static final int MAX_REFS_TO_LIST = 1000;
    private static final long serialVersionUID = 1L;
    protected LinkedList<Reference> _path;

    public JsonMappingException(String string) {
        super(string);
    }

    public JsonMappingException(String string, JsonLocation jsonLocation) {
        super(string, jsonLocation);
    }

    public JsonMappingException(String string, JsonLocation jsonLocation, Throwable throwable) {
        super(string, jsonLocation, throwable);
    }

    public JsonMappingException(String string, Throwable throwable) {
        super(string, throwable);
    }

    public static JsonMappingException from(JsonParser jsonParser, String string) {
        return new JsonMappingException(string, jsonParser.getTokenLocation());
    }

    public static JsonMappingException from(JsonParser jsonParser, String string, Throwable throwable) {
        return new JsonMappingException(string, jsonParser.getTokenLocation(), throwable);
    }

    /*
     * Enabled aggressive block sorting
     */
    public static JsonMappingException wrapWithPath(Throwable throwable, Reference reference) {
        JsonMappingException jsonMappingException;
        if (throwable instanceof JsonMappingException) {
            jsonMappingException = (JsonMappingException)throwable;
        } else {
            String string = throwable.getMessage();
            if (string == null || string.length() == 0) {
                string = "(was " + throwable.getClass().getName() + ")";
            }
            jsonMappingException = new JsonMappingException(string, null, throwable);
        }
        jsonMappingException.prependPath(reference);
        return jsonMappingException;
    }

    public static JsonMappingException wrapWithPath(Throwable throwable, Object object, int n) {
        return JsonMappingException.wrapWithPath(throwable, new Reference(object, n));
    }

    public static JsonMappingException wrapWithPath(Throwable throwable, Object object, String string) {
        return JsonMappingException.wrapWithPath(throwable, new Reference(object, string));
    }

    protected void _appendPathDesc(StringBuilder stringBuilder) {
        Iterator iterator = this._path.iterator();
        while (iterator.hasNext()) {
            stringBuilder.append(((Reference)iterator.next()).toString());
            if (!iterator.hasNext()) continue;
            stringBuilder.append("->");
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public String getMessage() {
        String string = super.getMessage();
        if (this._path == null) {
            return string;
        }
        StringBuilder stringBuilder = string == null ? new StringBuilder() : new StringBuilder(string);
        stringBuilder.append(" (through reference chain: ");
        this._appendPathDesc(stringBuilder);
        stringBuilder.append(')');
        return stringBuilder.toString();
    }

    public List<Reference> getPath() {
        if (this._path == null) {
            return Collections.emptyList();
        }
        return Collections.unmodifiableList(this._path);
    }

    public void prependPath(Reference reference) {
        if (this._path == null) {
            this._path = new LinkedList();
        }
        if (this._path.size() < 1000) {
            this._path.addFirst((Object)reference);
        }
    }

    public void prependPath(Object object, int n) {
        this.prependPath(new Reference(object, n));
    }

    public void prependPath(Object object, String string) {
        this.prependPath(new Reference(object, string));
    }

    @Override
    public String toString() {
        return this.getClass().getName() + ": " + this.getMessage();
    }

    public static class Reference
    implements Serializable {
        private static final long serialVersionUID = 1L;
        protected String _fieldName;
        protected Object _from;
        protected int _index;

        protected Reference() {
            this._index = -1;
        }

        public Reference(Object object) {
            this._index = -1;
            this._from = object;
        }

        public Reference(Object object, int n) {
            this._index = -1;
            this._from = object;
            this._index = n;
        }

        public Reference(Object object, String string) {
            this._index = -1;
            this._from = object;
            if (string == null) {
                throw new NullPointerException("Can not pass null fieldName");
            }
            this._fieldName = string;
        }

        public String getFieldName() {
            return this._fieldName;
        }

        public Object getFrom() {
            return this._from;
        }

        public int getIndex() {
            return this._index;
        }

        public void setFieldName(String string) {
            this._fieldName = string;
        }

        public void setFrom(Object object) {
            this._from = object;
        }

        public void setIndex(int n) {
            this._index = n;
        }

        /*
         * Enabled aggressive block sorting
         */
        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            Class class_ = this._from instanceof Class ? (Class)this._from : this._from.getClass();
            Package package_ = class_.getPackage();
            if (package_ != null) {
                stringBuilder.append(package_.getName());
                stringBuilder.append('.');
            }
            stringBuilder.append(class_.getSimpleName());
            stringBuilder.append('[');
            if (this._fieldName != null) {
                stringBuilder.append('\"');
                stringBuilder.append(this._fieldName);
                stringBuilder.append('\"');
            } else if (this._index >= 0) {
                stringBuilder.append(this._index);
            } else {
                stringBuilder.append('?');
            }
            stringBuilder.append(']');
            return stringBuilder.toString();
        }
    }

}

