/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Integer
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.StringBuffer
 */
package com.kontagent.util;

public class Base64Utils {
    public static String escape(String string2) {
        if (string2 == null) {
            return null;
        }
        StringBuffer stringBuffer = new StringBuffer();
        Base64Utils.escape(string2, stringBuffer);
        return stringBuffer.toString();
    }

    /*
     * Enabled aggressive block sorting
     */
    private static void escape(String string2, StringBuffer stringBuffer) {
        int n = 0;
        do {
            block14 : {
                char c2;
                block13 : {
                    String string3;
                    if (n >= string2.length()) {
                        return;
                    }
                    c2 = string2.charAt(n);
                    switch (c2) {
                        default: {
                            if (c2 > '' && c2 <= '\uffff') {
                                string3 = Integer.toHexString((int)c2);
                                stringBuffer.append("\\u");
                                for (int i2 = 0; i2 < 4 - string3.length(); ++i2) {
                                    stringBuffer.append('0');
                                }
                                break;
                            }
                            break block13;
                        }
                        case '\\': {
                            stringBuffer.append("\\\\");
                            break block14;
                        }
                        case '\b': {
                            stringBuffer.append("\\b");
                            break block14;
                        }
                        case '\f': {
                            stringBuffer.append("\\f");
                            break block14;
                        }
                        case '\n': {
                            stringBuffer.append("\\n");
                            break block14;
                        }
                        case '\r': {
                            stringBuffer.append("\\r");
                            break block14;
                        }
                        case '\t': {
                            stringBuffer.append("\\t");
                            break block14;
                        }
                        case '/': {
                            stringBuffer.append("\\/");
                            break block14;
                        }
                    }
                    stringBuffer.append(string3);
                    break block14;
                }
                stringBuffer.append(c2);
            }
            ++n;
        } while (true);
    }

    /*
     * Enabled aggressive block sorting
     */
    public static String unescape(String string2) {
        int n = string2.length();
        StringBuffer stringBuffer = new StringBuffer(n);
        int n2 = 0;
        while (n2 < n) {
            int n3 = n2 + 1;
            char c2 = string2.charAt(n2);
            if (c2 == '\\' && n3 < n) {
                int n4 = n3 + 1;
                c2 = string2.charAt(n3);
                if (c2 == 'u') {
                    c2 = (char)Integer.parseInt((String)string2.substring(n4, n4 + 4), (int)16);
                    n3 = n4 + 4;
                } else {
                    n3 = n4;
                }
            }
            stringBuffer.append(c2);
            n2 = n3;
        }
        return stringBuffer.toString();
    }
}

