/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Class
 *  java.lang.Exception
 *  java.lang.Object
 */
package com.flurry.org.codehaus.jackson.map.ser;

import com.flurry.org.codehaus.jackson.JsonGenerator;
import com.flurry.org.codehaus.jackson.map.JsonSerializer;
import com.flurry.org.codehaus.jackson.map.SerializerProvider;
import com.flurry.org.codehaus.jackson.map.ser.BeanPropertyWriter;

public abstract class FilteredBeanPropertyWriter {
    public static BeanPropertyWriter constructViewBased(BeanPropertyWriter beanPropertyWriter, Class<?>[] arrclass) {
        if (arrclass.length == 1) {
            return new SingleView(beanPropertyWriter, arrclass[0]);
        }
        return new MultiView(beanPropertyWriter, arrclass);
    }

    private static final class MultiView
    extends BeanPropertyWriter {
        protected final BeanPropertyWriter _delegate;
        protected final Class<?>[] _views;

        protected MultiView(BeanPropertyWriter beanPropertyWriter, Class<?>[] arrclass) {
            super(beanPropertyWriter);
            this._delegate = beanPropertyWriter;
            this._views = arrclass;
        }

        @Override
        public void serializeAsField(Object object, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws Exception {
            Class<?> class_ = serializerProvider.getSerializationView();
            if (class_ != null) {
                int n = 0;
                int n2 = this._views.length;
                do {
                    if (n >= n2 || this._views[n].isAssignableFrom(class_)) {
                        if (n != n2) break;
                        return;
                    }
                    ++n;
                } while (true);
            }
            this._delegate.serializeAsField(object, jsonGenerator, serializerProvider);
        }

        @Override
        public BeanPropertyWriter withSerializer(JsonSerializer<Object> jsonSerializer) {
            return new MultiView(this._delegate.withSerializer(jsonSerializer), this._views);
        }
    }

    private static final class SingleView
    extends BeanPropertyWriter {
        protected final BeanPropertyWriter _delegate;
        protected final Class<?> _view;

        protected SingleView(BeanPropertyWriter beanPropertyWriter, Class<?> class_) {
            super(beanPropertyWriter);
            this._delegate = beanPropertyWriter;
            this._view = class_;
        }

        @Override
        public void serializeAsField(Object object, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws Exception {
            Class<?> class_ = serializerProvider.getSerializationView();
            if (class_ == null || this._view.isAssignableFrom(class_)) {
                this._delegate.serializeAsField(object, jsonGenerator, serializerProvider);
            }
        }

        @Override
        public BeanPropertyWriter withSerializer(JsonSerializer<Object> jsonSerializer) {
            return new SingleView(this._delegate.withSerializer(jsonSerializer), this._view);
        }
    }

}

