/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.telephony.TelephonyManager
 *  java.io.BufferedReader
 *  java.io.IOException
 *  java.io.InputStream
 *  java.io.InputStreamReader
 *  java.io.Reader
 *  java.lang.Integer
 *  java.lang.Object
 *  java.lang.RuntimeException
 *  java.lang.String
 *  java.lang.StringBuilder
 *  java.lang.Throwable
 *  java.net.HttpURLConnection
 *  java.net.InetAddress
 *  java.net.MalformedURLException
 *  java.net.NetworkInterface
 *  java.net.SocketException
 *  java.net.URL
 *  java.net.URLConnection
 *  java.util.Enumeration
 */
package com.kontagent.util;

import android.content.Context;
import android.telephony.TelephonyManager;
import com.kontagent.Kontagent;
import com.kontagent.KontagentLog;
import com.kontagent.util.NetworkConnectivityError;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Enumeration;

public class NetworkUtil {
    public static String carrierName(Context context) {
        return ((TelephonyManager)context.getSystemService("phone")).getNetworkOperatorName();
    }

    public static String getLocalIpAddress() {
        try {
            Enumeration enumeration = NetworkInterface.getNetworkInterfaces();
            while (enumeration.hasMoreElements()) {
                Enumeration enumeration2 = ((NetworkInterface)enumeration.nextElement()).getInetAddresses();
                while (enumeration2.hasMoreElements()) {
                    InetAddress inetAddress = (InetAddress)enumeration2.nextElement();
                    if (inetAddress.isLoopbackAddress()) continue;
                    String string2 = inetAddress.getHostAddress().toString();
                    KontagentLog.d("Current ip address is " + string2);
                    String string3 = inetAddress.getHostAddress().toString();
                    return string3;
                }
            }
        }
        catch (SocketException socketException) {
            KontagentLog.e(socketException.toString(), socketException);
        }
        return null;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static String httpGet(String string2) throws NetworkConnectivityError {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            HttpURLConnection httpURLConnection = (HttpURLConnection)new URL(string2).openConnection();
            httpURLConnection.setConnectTimeout(5000);
            if (httpURLConnection.getResponseCode() == 200) {
                String string3;
                BufferedReader bufferedReader = new BufferedReader((Reader)new InputStreamReader(httpURLConnection.getInputStream()), 8192);
                while ((string3 = bufferedReader.readLine()) != null) {
                    stringBuilder.append(string3);
                }
                bufferedReader.close();
            }
            httpURLConnection.disconnect();
            return stringBuilder.toString();
        }
        catch (MalformedURLException malformedURLException) {
            KontagentLog.e("Problem connecting to url " + string2, malformedURLException);
            if (!Kontagent.debugEnabled().booleanValue()) return stringBuilder.toString();
            throw new RuntimeException((Throwable)malformedURLException);
        }
        catch (IOException iOException) {
            KontagentLog.e("Problem connecting to url " + string2, iOException);
            throw new NetworkConnectivityError(iOException);
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private static int httpGetStatusCode(String string2) throws NetworkConnectivityError {
        try {
            HttpURLConnection httpURLConnection = (HttpURLConnection)new URL(string2).openConnection();
            httpURLConnection.setConnectTimeout(5000);
            KontagentLog.d("Connection to " + string2 + "returned response code " + Integer.toString((int)httpURLConnection.getResponseCode()));
            return httpURLConnection.getResponseCode();
        }
        catch (MalformedURLException malformedURLException) {
            KontagentLog.d("Problem connecting to url " + string2);
            do {
                return 0;
                break;
            } while (true);
        }
        catch (IOException iOException) {
            KontagentLog.d("Problem connecting to url " + string2);
            return 0;
        }
    }

    public static boolean isOnline() {
        try {
            int n = NetworkUtil.httpGetStatusCode("http://api.geo.kontagent.net/api/v0/ping/");
            boolean bl = false;
            if (n == 404) {
                bl = true;
            }
            return bl;
        }
        catch (NetworkConnectivityError networkConnectivityError) {
            KontagentLog.e("isOnline check threw exceoption", (Throwable)networkConnectivityError);
            return false;
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    public static boolean isValidCarrierName(Context context) {
        String string2 = NetworkUtil.carrierName(context);
        if (string2 == null) {
            KontagentLog.d("Carrier name is null");
            return false;
        } else {
            KontagentLog.d("Carrier name is " + string2);
            if (string2.equals((Object)"")) return false;
            return true;
        }
    }

    public static boolean isValidIpAddress() {
        return NetworkUtil.getLocalIpAddress() != null && !NetworkUtil.getLocalIpAddress().equals((Object)"");
    }
}

