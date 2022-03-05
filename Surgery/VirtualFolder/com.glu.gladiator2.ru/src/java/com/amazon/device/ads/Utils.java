/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.content.res.Configuration
 *  android.content.res.Resources
 *  android.view.Display
 *  android.view.WindowManager
 *  java.io.IOException
 *  java.io.InputStream
 *  java.io.UnsupportedEncodingException
 *  java.lang.CharSequence
 *  java.lang.Integer
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.StringBuffer
 *  java.net.URLDecoder
 *  java.net.URLEncoder
 *  java.security.MessageDigest
 *  java.security.NoSuchAlgorithmException
 */
package com.amazon.device.ads;

import android.app.Activity;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.view.Display;
import android.view.WindowManager;
import com.amazon.device.ads.Log;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

class Utils {
    public static final String LOG_TAG = "AmazonAdRegistration";
    private static int[][] rotationArray = new int[][]{{1, 0, 9, 8}, {0, 9, 8, 1}};

    Utils() {
    }

    /*
     * Enabled aggressive block sorting
     */
    public static int determineCanonicalScreenOrientation(Activity activity) {
        int n2;
        int n3 = activity.getWindowManager().getDefaultDisplay().getOrientation();
        int n4 = activity.getResources().getConfiguration().orientation;
        boolean bl = n4 == 1 ? n3 == 0 || n3 == 2 : (n4 == 2 ? n3 == 1 || n3 == 3 : true);
        if (bl) {
            n2 = 0;
            return rotationArray[n2][n3];
        }
        n2 = 1;
        return rotationArray[n2][n3];
    }

    public static StringBuffer extractHttpResponse(InputStream inputStream) throws IOException {
        int n2;
        StringBuffer stringBuffer = new StringBuffer();
        byte[] arrby = new byte[4096];
        while ((n2 = inputStream.read(arrby)) != -1) {
            stringBuffer.append(new String(arrby, 0, n2));
        }
        return stringBuffer;
    }

    public static final String getURLDecodedString(String string) {
        if (string == null) {
            return null;
        }
        try {
            String string2 = URLDecoder.decode((String)string, (String)"UTF-8");
            return string2;
        }
        catch (UnsupportedEncodingException unsupportedEncodingException) {
            Log.d(LOG_TAG, "getURLDecodedString threw: %s", new Object[]{unsupportedEncodingException});
            return string;
        }
    }

    public static final String getURLEncodedString(String string) {
        if (string == null) {
            return null;
        }
        try {
            String string2 = URLEncoder.encode((String)string, (String)"UTF-8").replace((CharSequence)"+", (CharSequence)"%20").replace((CharSequence)"*", (CharSequence)"%2A").replace((CharSequence)"%7E", (CharSequence)"~");
            return string2;
        }
        catch (UnsupportedEncodingException unsupportedEncodingException) {
            Log.d(LOG_TAG, "getURLEncodedString threw: %s", new Object[]{unsupportedEncodingException});
            return string;
        }
    }

    public static boolean isLandscape(Activity activity) {
        int n2 = Utils.determineCanonicalScreenOrientation(activity);
        return n2 == 0 || n2 == 8;
    }

    public static boolean isPortrait(Activity activity) {
        int n2 = Utils.determineCanonicalScreenOrientation(activity);
        return n2 == 1 || n2 == 9;
    }

    public static String sha1(String string) {
        MessageDigest messageDigest = MessageDigest.getInstance((String)"SHA-1");
        messageDigest.update(string.getBytes());
        byte[] arrby = messageDigest.digest();
        StringBuffer stringBuffer = new StringBuffer();
        int n2 = 0;
        do {
            if (n2 >= arrby.length) break;
            stringBuffer.append(Integer.toHexString((int)(256 | 255 & arrby[n2])).substring(1));
            ++n2;
        } while (true);
        try {
            String string2 = stringBuffer.toString();
            return string2;
        }
        catch (NoSuchAlgorithmException noSuchAlgorithmException) {
            return "";
        }
    }
}

