/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Class
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.StringBuilder
 */
package com.flurry.org.codehaus.jackson.map.jsontype;

public final class NamedType {
    protected final Class<?> _class;
    protected final int _hashCode;
    protected String _name;

    public NamedType(Class<?> class_) {
        super(class_, null);
    }

    public NamedType(Class<?> class_, String string) {
        this._class = class_;
        this._hashCode = class_.getName().hashCode();
        this.setName(string);
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
                if (this._class != ((NamedType)object)._class) break block6;
            }
            return true;
        }
        return false;
    }

    public String getName() {
        return this._name;
    }

    public Class<?> getType() {
        return this._class;
    }

    public boolean hasName() {
        return this._name != null;
    }

    public int hashCode() {
        return this._hashCode;
    }

    public void setName(String string) {
        if (string == null || string.length() == 0) {
            string = null;
        }
        this._name = string;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public String toString() {
        String string;
        StringBuilder stringBuilder = new StringBuilder().append("[NamedType, class ").append(this._class.getName()).append(", name: ");
        if (this._name == null) {
            string = "null";
            do {
                return stringBuilder.append(string).append("]").toString();
                break;
            } while (true);
        }
        string = "'" + this._name + "'";
        return stringBuilder.append(string).append("]").toString();
    }
}

