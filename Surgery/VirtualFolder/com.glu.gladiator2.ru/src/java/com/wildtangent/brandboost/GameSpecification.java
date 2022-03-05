/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Class
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.StringBuilder
 */
package com.wildtangent.brandboost;

public final class GameSpecification {
    private String a;
    private String b;
    private String c;

    public GameSpecification(String string, String string2, String string3) {
        this.a = string;
        this.b = string2;
        this.c = string3;
    }

    /*
     * Enabled aggressive block sorting
     */
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null) {
            return false;
        }
        if (this.getClass() != object.getClass()) {
            return false;
        }
        GameSpecification gameSpecification = (GameSpecification)object;
        if (this.c == null ? gameSpecification.c != null : !this.c.equals((Object)gameSpecification.c)) {
            return false;
        }
        if (this.a == null ? gameSpecification.a != null : !this.a.equals((Object)gameSpecification.a)) {
            return false;
        }
        if (this.b == null) {
            if (gameSpecification.b == null) return true;
            return false;
        }
        if (!this.b.equals((Object)gameSpecification.b)) return false;
        return true;
    }

    public String getGameName() {
        return this.c;
    }

    public String getPartnerName() {
        return this.a;
    }

    public String getSiteName() {
        return this.b;
    }

    /*
     * Enabled aggressive block sorting
     */
    public int hashCode() {
        int n2 = this.c == null ? 0 : this.c.hashCode();
        int n3 = 31 * (n2 + 31);
        int n4 = this.a == null ? 0 : this.a.hashCode();
        int n5 = 31 * (n3 + n4);
        String string = this.b;
        int n6 = 0;
        if (string == null) {
            return n5 + n6;
        }
        n6 = this.b.hashCode();
        return n5 + n6;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{GameSpecification: ");
        stringBuilder.append(this.a);
        stringBuilder.append('|');
        stringBuilder.append(this.b);
        stringBuilder.append('|');
        stringBuilder.append(this.c);
        stringBuilder.append('}');
        return stringBuilder.toString();
    }
}

