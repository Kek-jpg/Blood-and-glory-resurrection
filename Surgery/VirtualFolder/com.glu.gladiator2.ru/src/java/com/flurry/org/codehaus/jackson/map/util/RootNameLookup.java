/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Class
 *  java.lang.Object
 *  java.lang.String
 */
package com.flurry.org.codehaus.jackson.map.util;

import com.flurry.org.codehaus.jackson.io.SerializedString;
import com.flurry.org.codehaus.jackson.map.AnnotationIntrospector;
import com.flurry.org.codehaus.jackson.map.BeanDescription;
import com.flurry.org.codehaus.jackson.map.MapperConfig;
import com.flurry.org.codehaus.jackson.map.introspect.AnnotatedClass;
import com.flurry.org.codehaus.jackson.map.introspect.BasicBeanDescription;
import com.flurry.org.codehaus.jackson.map.type.ClassKey;
import com.flurry.org.codehaus.jackson.map.util.LRUMap;
import com.flurry.org.codehaus.jackson.type.JavaType;

public class RootNameLookup {
    protected LRUMap<ClassKey, SerializedString> _rootNames;

    public SerializedString findRootName(JavaType javaType, MapperConfig<?> mapperConfig) {
        return this.findRootName(javaType.getRawClass(), mapperConfig);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public SerializedString findRootName(Class<?> class_, MapperConfig<?> mapperConfig) {
        void var9_3 = this;
        synchronized (var9_3) {
            SerializedString serializedString;
            block8 : {
                ClassKey classKey;
                block7 : {
                    block6 : {
                        classKey = new ClassKey(class_);
                        if (this._rootNames != null) break block6;
                        this._rootNames = new LRUMap(20, 200);
                        break block7;
                    }
                    serializedString = (SerializedString)this._rootNames.get((Object)classKey);
                    if (serializedString != null) break block8;
                }
                BasicBeanDescription basicBeanDescription = (BasicBeanDescription)mapperConfig.introspectClassAnnotations(class_);
                String string = mapperConfig.getAnnotationIntrospector().findRootName(basicBeanDescription.getClassInfo());
                if (string == null) {
                    string = class_.getSimpleName();
                }
                serializedString = new SerializedString(string);
                this._rootNames.put((Object)classKey, (Object)serializedString);
            }
            return serializedString;
        }
    }
}

