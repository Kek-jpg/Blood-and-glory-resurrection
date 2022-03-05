/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Class
 *  java.lang.ClassLoader
 *  java.lang.Exception
 *  java.lang.IllegalArgumentException
 *  java.lang.Object
 *  java.lang.RuntimeException
 *  java.lang.String
 *  java.lang.Thread
 *  java.util.ArrayList
 *  java.util.List
 *  java.util.StringTokenizer
 */
package com.flurry.org.codehaus.jackson.map.type;

import com.flurry.org.codehaus.jackson.map.type.TypeBindings;
import com.flurry.org.codehaus.jackson.map.type.TypeFactory;
import com.flurry.org.codehaus.jackson.type.JavaType;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class TypeParser {
    final TypeFactory _factory;

    public TypeParser(TypeFactory typeFactory) {
        this._factory = typeFactory;
    }

    protected IllegalArgumentException _problem(MyTokenizer myTokenizer, String string) {
        return new IllegalArgumentException("Failed to parse type '" + myTokenizer.getAllInput() + "' (remaining: '" + myTokenizer.getRemainingInput() + "'): " + string);
    }

    protected Class<?> findClass(String string, MyTokenizer myTokenizer) {
        try {
            Class class_ = Class.forName((String)string, (boolean)true, (ClassLoader)Thread.currentThread().getContextClassLoader());
            return class_;
        }
        catch (Exception exception) {
            if (exception instanceof RuntimeException) {
                throw (RuntimeException)exception;
            }
            throw this._problem(myTokenizer, "Can not locate class '" + string + "', problem: " + exception.getMessage());
        }
    }

    public JavaType parse(String string) throws IllegalArgumentException {
        MyTokenizer myTokenizer = new MyTokenizer(string.trim());
        JavaType javaType = this.parseType(myTokenizer);
        if (myTokenizer.hasMoreTokens()) {
            throw this._problem(myTokenizer, "Unexpected tokens after complete type");
        }
        return javaType;
    }

    protected JavaType parseType(MyTokenizer myTokenizer) throws IllegalArgumentException {
        if (!myTokenizer.hasMoreTokens()) {
            throw this._problem(myTokenizer, "Unexpected end-of-string");
        }
        Class<?> class_ = this.findClass(myTokenizer.nextToken(), myTokenizer);
        if (myTokenizer.hasMoreTokens()) {
            String string = myTokenizer.nextToken();
            if ("<".equals((Object)string)) {
                return this._factory._fromParameterizedClass(class_, this.parseTypes(myTokenizer));
            }
            myTokenizer.pushBack(string);
        }
        return this._factory._fromClass(class_, null);
    }

    protected List<JavaType> parseTypes(MyTokenizer myTokenizer) throws IllegalArgumentException {
        String string;
        ArrayList arrayList = new ArrayList();
        do {
            block4 : {
                block3 : {
                    if (!myTokenizer.hasMoreTokens()) break block3;
                    arrayList.add((Object)this.parseType(myTokenizer));
                    if (myTokenizer.hasMoreTokens()) break block4;
                }
                throw this._problem(myTokenizer, "Unexpected end-of-string");
            }
            string = myTokenizer.nextToken();
            if (!">".equals((Object)string)) continue;
            return arrayList;
        } while (",".equals((Object)string));
        throw this._problem(myTokenizer, "Unexpected token '" + string + "', expected ',' or '>')");
    }

    static final class MyTokenizer
    extends StringTokenizer {
        protected int _index;
        protected final String _input;
        protected String _pushbackToken;

        public MyTokenizer(String string) {
            super(string, "<,>", true);
            this._input = string;
        }

        public String getAllInput() {
            return this._input;
        }

        public String getRemainingInput() {
            return this._input.substring(this._index);
        }

        public String getUsedInput() {
            return this._input.substring(0, this._index);
        }

        public boolean hasMoreTokens() {
            return this._pushbackToken != null || super.hasMoreTokens();
        }

        /*
         * Enabled aggressive block sorting
         */
        public String nextToken() {
            String string;
            if (this._pushbackToken != null) {
                string = this._pushbackToken;
                this._pushbackToken = null;
            } else {
                string = super.nextToken();
            }
            this._index += string.length();
            return string;
        }

        public void pushBack(String string) {
            this._pushbackToken = string;
            this._index -= string.length();
        }
    }

}

