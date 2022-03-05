/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  com.flurry.org.codehaus.jackson.map.type.SimpleType
 *  java.lang.Class
 *  java.lang.IllegalArgumentException
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.StringBuilder
 *  java.lang.reflect.Modifier
 *  java.lang.reflect.ParameterizedType
 *  java.lang.reflect.Type
 *  java.lang.reflect.TypeVariable
 *  java.util.Collection
 *  java.util.Collections
 *  java.util.HashSet
 *  java.util.LinkedHashMap
 *  java.util.Map
 */
package com.flurry.org.codehaus.jackson.map.type;

import com.flurry.org.codehaus.jackson.map.type.SimpleType;
import com.flurry.org.codehaus.jackson.map.type.TypeFactory;
import com.flurry.org.codehaus.jackson.type.JavaType;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;

public class TypeBindings {
    private static final JavaType[] NO_TYPES = new JavaType[0];
    public static final JavaType UNBOUND = new SimpleType(Object.class);
    protected Map<String, JavaType> _bindings;
    protected final Class<?> _contextClass;
    protected final JavaType _contextType;
    private final TypeBindings _parentBindings;
    protected HashSet<String> _placeholders;
    protected final TypeFactory _typeFactory;

    private TypeBindings(TypeFactory typeFactory, TypeBindings typeBindings, Class<?> class_, JavaType javaType) {
        this._typeFactory = typeFactory;
        this._parentBindings = typeBindings;
        this._contextClass = class_;
        this._contextType = javaType;
    }

    public TypeBindings(TypeFactory typeFactory, JavaType javaType) {
        super(typeFactory, null, javaType.getRawClass(), javaType);
    }

    public TypeBindings(TypeFactory typeFactory, Class<?> class_) {
        super(typeFactory, null, class_, null);
    }

    public void _addPlaceholder(String string) {
        if (this._placeholders == null) {
            this._placeholders = new HashSet();
        }
        this._placeholders.add((Object)string);
    }

    protected void _resolve() {
        int n;
        this._resolveBindings((Type)this._contextClass);
        if (this._contextType != null && (n = this._contextType.containedTypeCount()) > 0) {
            if (this._bindings == null) {
                this._bindings = new LinkedHashMap();
            }
            for (int i = 0; i < n; ++i) {
                String string = this._contextType.containedTypeName(i);
                JavaType javaType = this._contextType.containedType(i);
                this._bindings.put((Object)string, (Object)javaType);
            }
        }
        if (this._bindings == null) {
            this._bindings = Collections.emptyMap();
        }
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    protected void _resolveBindings(Type var1) {
        block20 : {
            block19 : {
                block17 : {
                    block18 : {
                        block16 : {
                            if (var1 == null) {
                                return;
                            }
                            if (!(var1 instanceof ParameterizedType)) break block16;
                            var16_2 = (ParameterizedType)var1;
                            var17_3 = var16_2.getActualTypeArguments();
                            if (var17_3 == null || var17_3.length <= 0) break block17;
                            var18_4 = (Class)var16_2.getRawType();
                            var19_5 = var18_4.getTypeParameters();
                            if (var19_5.length != var17_3.length) {
                                throw new IllegalArgumentException("Strange parametrized type (in class " + var18_4.getName() + "): number of type arguments != number of type parameters (" + var17_3.length + " vs " + var19_5.length + ")");
                            }
                            var21_7 = var17_3.length;
                            break block18;
                        }
                        if (var1 instanceof Class == false) return;
                        var2_9 = (Class)var1;
                        this._resolveBindings((Type)var2_9.getDeclaringClass());
                        var3_12 = var2_9.getTypeParameters();
                        if (var3_12 == null || var3_12.length <= 0) break block19;
                        var4_13 = this._contextType;
                        var5_11 = null;
                        if (var4_13 != null) {
                            var15_15 = var2_9.isAssignableFrom(this._contextType.getRawClass());
                            var5_11 = null;
                            if (var15_15) {
                                var5_11 = this._typeFactory.findTypeParameters(this._contextType, var2_9);
                            }
                        }
                        var6_14 = 0;
                        break block20;
                    }
                    for (var20_6 = 0; var20_6 < var21_7; ++var20_6) {
                        var22_8 = var19_5[var20_6].getName();
                        if (this._bindings == null) {
                            this._bindings = new LinkedHashMap();
                        } else if (this._bindings.containsKey((Object)var22_8)) continue;
                        this._addPlaceholder(var22_8);
                        this._bindings.put((Object)var22_8, (Object)this._typeFactory._constructType(var17_3[var20_6], (TypeBindings)this));
                    }
                }
                var2_9 = (Class)var16_2.getRawType();
            }
            do {
                this._resolveBindings(var2_9.getGenericSuperclass());
                var12_18 = var2_9.getGenericInterfaces();
                var13_19 = var12_18.length;
                var14_20 = 0;
                while (var14_20 < var13_19) {
                    this._resolveBindings(var12_18[var14_20]);
                    ++var14_20;
                }
                return;
                break;
            } while (true);
        }
        do {
            block21 : {
                block23 : {
                    block22 : {
                        if (var6_14 >= var3_12.length) ** continue;
                        var7_17 = var3_12[var6_14];
                        var8_10 = var7_17.getName();
                        var9_16 = var7_17.getBounds()[0];
                        if (var9_16 == null) break block21;
                        if (this._bindings != null) break block22;
                        this._bindings = new LinkedHashMap();
                        break block23;
                    }
                    if (this._bindings.containsKey((Object)var8_10)) break block21;
                }
                this._addPlaceholder(var8_10);
                if (var5_11 != null) {
                    this._bindings.put((Object)var8_10, (Object)var5_11[var6_14]);
                } else {
                    this._bindings.put((Object)var8_10, (Object)this._typeFactory._constructType(var9_16, (TypeBindings)this));
                }
            }
            ++var6_14;
        } while (true);
    }

    public void addBinding(String string, JavaType javaType) {
        if (this._bindings == null || this._bindings.size() == 0) {
            this._bindings = new LinkedHashMap();
        }
        this._bindings.put((Object)string, (Object)javaType);
    }

    public TypeBindings childInstance() {
        return new TypeBindings(this._typeFactory, this, this._contextClass, this._contextType);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public JavaType findType(String string) {
        JavaType javaType;
        String string2;
        if (this._bindings == null) {
            this._resolve();
        }
        if ((javaType = (JavaType)this._bindings.get((Object)string)) != null) {
            return javaType;
        }
        if (this._placeholders != null && this._placeholders.contains((Object)string)) {
            return UNBOUND;
        }
        if (this._parentBindings != null) {
            return this._parentBindings.findType(string);
        }
        if (this._contextClass != null && this._contextClass.getEnclosingClass() != null && !Modifier.isStatic((int)this._contextClass.getModifiers())) {
            return UNBOUND;
        }
        if (this._contextClass != null) {
            string2 = this._contextClass.getName();
            do {
                throw new IllegalArgumentException("Type variable '" + string + "' can not be resolved (with context of class " + string2 + ")");
                break;
            } while (true);
        }
        if (this._contextType != null) {
            string2 = this._contextType.toString();
            throw new IllegalArgumentException("Type variable '" + string + "' can not be resolved (with context of class " + string2 + ")");
        }
        string2 = "UNKNOWN";
        throw new IllegalArgumentException("Type variable '" + string + "' can not be resolved (with context of class " + string2 + ")");
    }

    public int getBindingCount() {
        if (this._bindings == null) {
            this._resolve();
        }
        return this._bindings.size();
    }

    public JavaType resolveType(Class<?> class_) {
        return this._typeFactory._constructType((Type)class_, (TypeBindings)this);
    }

    public JavaType resolveType(Type type) {
        return this._typeFactory._constructType(type, (TypeBindings)this);
    }

    /*
     * Enabled aggressive block sorting
     */
    public String toString() {
        if (this._bindings == null) {
            this._resolve();
        }
        StringBuilder stringBuilder = new StringBuilder("[TypeBindings for ");
        if (this._contextType != null) {
            stringBuilder.append(this._contextType.toString());
        } else {
            stringBuilder.append(this._contextClass.getName());
        }
        stringBuilder.append(": ").append(this._bindings).append("]");
        return stringBuilder.toString();
    }

    public JavaType[] typesAsArray() {
        if (this._bindings == null) {
            this._resolve();
        }
        if (this._bindings.size() == 0) {
            return NO_TYPES;
        }
        return (JavaType[])this._bindings.values().toArray((Object[])new JavaType[this._bindings.size()]);
    }
}

