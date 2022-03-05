/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.io.ByteArrayInputStream
 *  java.io.File
 *  java.io.InputStream
 *  java.io.UnsupportedEncodingException
 *  java.lang.Exception
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.StringBuffer
 *  java.security.MessageDigest
 *  java.security.NoSuchAlgorithmException
 *  javax.xml.parsers.DocumentBuilder
 *  javax.xml.parsers.DocumentBuilderFactory
 *  org.w3c.dom.Document
 *  org.w3c.dom.Element
 *  org.w3c.dom.Node
 *  org.w3c.dom.NodeList
 */
package com.tapjoy;

import com.tapjoy.TapjoyLog;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class TapjoyUtil {
    private static final String TAPJOY_UTIL = "TapjoyUtil";

    public static String SHA1(String string2) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        return TapjoyUtil.hashAlgorithm("SHA-1", string2);
    }

    public static String SHA256(String string2) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        return TapjoyUtil.hashAlgorithm("SHA-256", string2);
    }

    public static Document buildDocument(String string2) {
        try {
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(string2.getBytes("UTF-8"));
            Document document = documentBuilderFactory.newDocumentBuilder().parse((InputStream)byteArrayInputStream);
            return document;
        }
        catch (Exception exception) {
            TapjoyLog.e(TAPJOY_UTIL, "buildDocument exception: " + exception.toString());
            return null;
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    private static String convertToHex(byte[] arrby) {
        StringBuffer stringBuffer = new StringBuffer();
        int n = 0;
        block0 : while (n < arrby.length) {
            int n2 = 15 & arrby[n] >>> 4;
            int n3 = 0;
            do {
                if (n2 >= 0 && n2 <= 9) {
                    stringBuffer.append((char)(n2 + 48));
                } else {
                    stringBuffer.append((char)(97 + (n2 - 10)));
                }
                int n4 = 15 & arrby[n];
                int n5 = n3 + 1;
                if (n3 >= 1) {
                    ++n;
                    continue block0;
                }
                n3 = n5;
                n2 = n4;
            } while (true);
            break;
        }
        return stringBuffer.toString();
    }

    public static void deleteFileOrDirectory(File file) {
        if (file.isDirectory()) {
            File[] arrfile = file.listFiles();
            int n = arrfile.length;
            for (int i2 = 0; i2 < n; ++i2) {
                TapjoyUtil.deleteFileOrDirectory(arrfile[i2]);
            }
        }
        TapjoyLog.i(TAPJOY_UTIL, "****************************************");
        TapjoyLog.i(TAPJOY_UTIL, "deleteFileOrDirectory: " + file.getAbsolutePath());
        TapjoyLog.i(TAPJOY_UTIL, "****************************************");
        file.delete();
    }

    public static String getNodeTrimValue(NodeList nodeList) {
        Element element = (Element)nodeList.item(0);
        if (element != null) {
            NodeList nodeList2 = element.getChildNodes();
            int n = nodeList2.getLength();
            String string2 = "";
            for (int i2 = 0; i2 < n; ++i2) {
                Node node = nodeList2.item(i2);
                if (node == null) continue;
                string2 = string2 + node.getNodeValue();
            }
            if (string2 != null && !string2.equals((Object)"")) {
                return string2.trim();
            }
            return null;
        }
        return null;
    }

    public static String hashAlgorithm(String string2, String string3) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        new byte[40];
        MessageDigest messageDigest = MessageDigest.getInstance((String)string2);
        messageDigest.update(string3.getBytes("iso-8859-1"), 0, string3.length());
        return TapjoyUtil.convertToHex(messageDigest.digest());
    }
}

