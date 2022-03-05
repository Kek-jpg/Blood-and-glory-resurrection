/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Class
 *  java.lang.Comparable
 *  java.lang.Object
 *  java.lang.String
 */
package com.flurry.org.codehaus.jackson.map.type;

public final class ClassKey
implements Comparable<ClassKey> {
    private Class<?> _class;
    private String _className;
    private int _hashCode;

    public ClassKey() {
        this._class = null;
        this._className = null;
        this._hashCode = 0;
    }

    public ClassKey(Class<?> class_) {
        this._class = class_;
        this._className = class_.getName();
        this._hashCode = this._className.hashCode();
    }

    public int compareTo(ClassKey classKey) {
        return this._className.compareTo(classKey._className);
    }

    /*
     * Enabled aggressive block sorting
     */
    public boolean equals(Object object) {
        block6 : {
            block5 : {
                if (object == this) break block5;
                if (object == null) {
                    return false;
                }
                if (object.getClass() != this.getClass()) {
                    return false;
                }
                if (((ClassKey)object)._class != this._class) break block6;
            }
            return true;
        }
        return false;
    }

    public int hashCode() {
        return this._hashCode;
    }

    public void reset(Class<?> class_) {
        this._class = class_;
        this._className = class_.getName();
        this._hashCode = this._className.hashCode();
    }

    public String toString() {
        return this._className;
    }
}

