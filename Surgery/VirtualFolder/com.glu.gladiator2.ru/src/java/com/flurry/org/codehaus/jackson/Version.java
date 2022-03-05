/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Class
 *  java.lang.Comparable
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.StringBuilder
 */
package com.flurry.org.codehaus.jackson;

public class Version
implements Comparable<Version> {
    private static final Version UNKNOWN_VERSION = new Version(0, 0, 0, null);
    protected final int _majorVersion;
    protected final int _minorVersion;
    protected final int _patchLevel;
    protected final String _snapshotInfo;

    public Version(int n2, int n3, int n4, String string) {
        this._majorVersion = n2;
        this._minorVersion = n3;
        this._patchLevel = n4;
        this._snapshotInfo = string;
    }

    public static Version unknownVersion() {
        return UNKNOWN_VERSION;
    }

    public int compareTo(Version version) {
        int n2 = this._majorVersion - version._majorVersion;
        if (n2 == 0 && (n2 = this._minorVersion - version._minorVersion) == 0) {
            n2 = this._patchLevel - version._patchLevel;
        }
        return n2;
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
                Version version = (Version)object;
                if (version._majorVersion != this._majorVersion || version._minorVersion != this._minorVersion || version._patchLevel != this._patchLevel) break block6;
            }
            return true;
        }
        return false;
    }

    public int getMajorVersion() {
        return this._majorVersion;
    }

    public int getMinorVersion() {
        return this._minorVersion;
    }

    public int getPatchLevel() {
        return this._patchLevel;
    }

    public int hashCode() {
        return this._majorVersion + this._minorVersion + this._patchLevel;
    }

    public boolean isSnapshot() {
        return this._snapshotInfo != null && this._snapshotInfo.length() > 0;
    }

    public boolean isUknownVersion() {
        return this == UNKNOWN_VERSION;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this._majorVersion).append('.');
        stringBuilder.append(this._minorVersion).append('.');
        stringBuilder.append(this._patchLevel);
        if (this.isSnapshot()) {
            stringBuilder.append('-').append(this._snapshotInfo);
        }
        return stringBuilder.toString();
    }
}

