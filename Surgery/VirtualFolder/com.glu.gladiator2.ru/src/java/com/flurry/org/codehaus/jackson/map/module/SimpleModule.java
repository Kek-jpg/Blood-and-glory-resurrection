/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Class
 *  java.lang.Object
 *  java.lang.String
 *  java.util.HashMap
 *  java.util.Map$Entry
 *  java.util.Set
 */
package com.flurry.org.codehaus.jackson.map.module;

import com.flurry.org.codehaus.jackson.Version;
import com.flurry.org.codehaus.jackson.map.AbstractTypeResolver;
import com.flurry.org.codehaus.jackson.map.Deserializers;
import com.flurry.org.codehaus.jackson.map.JsonDeserializer;
import com.flurry.org.codehaus.jackson.map.JsonSerializer;
import com.flurry.org.codehaus.jackson.map.KeyDeserializer;
import com.flurry.org.codehaus.jackson.map.KeyDeserializers;
import com.flurry.org.codehaus.jackson.map.Module;
import com.flurry.org.codehaus.jackson.map.Serializers;
import com.flurry.org.codehaus.jackson.map.deser.ValueInstantiator;
import com.flurry.org.codehaus.jackson.map.deser.ValueInstantiators;
import com.flurry.org.codehaus.jackson.map.module.SimpleAbstractTypeResolver;
import com.flurry.org.codehaus.jackson.map.module.SimpleDeserializers;
import com.flurry.org.codehaus.jackson.map.module.SimpleKeyDeserializers;
import com.flurry.org.codehaus.jackson.map.module.SimpleSerializers;
import com.flurry.org.codehaus.jackson.map.module.SimpleValueInstantiators;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class SimpleModule
extends Module {
    protected SimpleAbstractTypeResolver _abstractTypes = null;
    protected SimpleDeserializers _deserializers = null;
    protected SimpleKeyDeserializers _keyDeserializers = null;
    protected SimpleSerializers _keySerializers = null;
    protected HashMap<Class<?>, Class<?>> _mixins = null;
    protected final String _name;
    protected SimpleSerializers _serializers = null;
    protected SimpleValueInstantiators _valueInstantiators = null;
    protected final Version _version;

    public SimpleModule(String string, Version version) {
        this._name = string;
        this._version = version;
    }

    public <T> SimpleModule addAbstractTypeMapping(Class<T> class_, Class<? extends T> class_2) {
        if (this._abstractTypes == null) {
            this._abstractTypes = new SimpleAbstractTypeResolver();
        }
        this._abstractTypes = this._abstractTypes.addMapping(class_, class_2);
        return this;
    }

    public <T> SimpleModule addDeserializer(Class<T> class_, JsonDeserializer<? extends T> jsonDeserializer) {
        if (this._deserializers == null) {
            this._deserializers = new SimpleDeserializers();
        }
        this._deserializers.addDeserializer(class_, jsonDeserializer);
        return this;
    }

    public SimpleModule addKeyDeserializer(Class<?> class_, KeyDeserializer keyDeserializer) {
        if (this._keyDeserializers == null) {
            this._keyDeserializers = new SimpleKeyDeserializers();
        }
        this._keyDeserializers.addDeserializer(class_, keyDeserializer);
        return this;
    }

    public <T> SimpleModule addKeySerializer(Class<? extends T> class_, JsonSerializer<T> jsonSerializer) {
        if (this._keySerializers == null) {
            this._keySerializers = new SimpleSerializers();
        }
        this._keySerializers.addSerializer(class_, jsonSerializer);
        return this;
    }

    public SimpleModule addSerializer(JsonSerializer<?> jsonSerializer) {
        if (this._serializers == null) {
            this._serializers = new SimpleSerializers();
        }
        this._serializers.addSerializer(jsonSerializer);
        return this;
    }

    public <T> SimpleModule addSerializer(Class<? extends T> class_, JsonSerializer<T> jsonSerializer) {
        if (this._serializers == null) {
            this._serializers = new SimpleSerializers();
        }
        this._serializers.addSerializer(class_, jsonSerializer);
        return this;
    }

    public SimpleModule addValueInstantiator(Class<?> class_, ValueInstantiator valueInstantiator) {
        if (this._valueInstantiators == null) {
            this._valueInstantiators = new SimpleValueInstantiators();
        }
        this._valueInstantiators = this._valueInstantiators.addValueInstantiator(class_, valueInstantiator);
        return this;
    }

    @Override
    public String getModuleName() {
        return this._name;
    }

    public void setAbstractTypes(SimpleAbstractTypeResolver simpleAbstractTypeResolver) {
        this._abstractTypes = simpleAbstractTypeResolver;
    }

    public void setDeserializers(SimpleDeserializers simpleDeserializers) {
        this._deserializers = simpleDeserializers;
    }

    public void setKeyDeserializers(SimpleKeyDeserializers simpleKeyDeserializers) {
        this._keyDeserializers = simpleKeyDeserializers;
    }

    public void setKeySerializers(SimpleSerializers simpleSerializers) {
        this._keySerializers = simpleSerializers;
    }

    public SimpleModule setMixInAnnotation(Class<?> class_, Class<?> class_2) {
        if (this._mixins == null) {
            this._mixins = new HashMap();
        }
        this._mixins.put(class_, class_2);
        return this;
    }

    public void setSerializers(SimpleSerializers simpleSerializers) {
        this._serializers = simpleSerializers;
    }

    public void setValueInstantiators(SimpleValueInstantiators simpleValueInstantiators) {
        this._valueInstantiators = simpleValueInstantiators;
    }

    @Override
    public void setupModule(Module.SetupContext setupContext) {
        if (this._serializers != null) {
            setupContext.addSerializers(this._serializers);
        }
        if (this._deserializers != null) {
            setupContext.addDeserializers(this._deserializers);
        }
        if (this._keySerializers != null) {
            setupContext.addKeySerializers(this._keySerializers);
        }
        if (this._keyDeserializers != null) {
            setupContext.addKeyDeserializers(this._keyDeserializers);
        }
        if (this._abstractTypes != null) {
            setupContext.addAbstractTypeResolver(this._abstractTypes);
        }
        if (this._valueInstantiators != null) {
            setupContext.addValueInstantiators(this._valueInstantiators);
        }
        if (this._mixins != null) {
            for (Map.Entry entry : this._mixins.entrySet()) {
                setupContext.setMixInAnnotations((Class)entry.getKey(), (Class)entry.getValue());
            }
        }
    }

    @Override
    public Version version() {
        return this._version;
    }
}

