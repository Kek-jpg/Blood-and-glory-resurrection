/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.app.KeyguardManager
 *  android.content.Context
 *  android.content.Intent
 *  android.content.res.Resources
 *  android.net.ConnectivityManager
 *  android.net.NetworkInfo
 *  android.net.Uri
 *  android.os.AsyncTask
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.os.Handler
 *  android.os.HandlerThread
 *  android.os.Looper
 *  android.os.SystemClock
 *  android.util.DisplayMetrics
 *  android.view.Display
 *  android.view.View
 *  android.view.ViewGroup
 *  android.view.WindowManager
 *  java.io.ByteArrayInputStream
 *  java.io.ByteArrayOutputStream
 *  java.io.Closeable
 *  java.io.DataInput
 *  java.io.DataInputStream
 *  java.io.DataOutput
 *  java.io.DataOutputStream
 *  java.io.File
 *  java.io.FileOutputStream
 *  java.io.IOException
 *  java.io.InputStream
 *  java.io.OutputStream
 *  java.lang.Boolean
 *  java.lang.CharSequence
 *  java.lang.Class
 *  java.lang.ClassCastException
 *  java.lang.Exception
 *  java.lang.Integer
 *  java.lang.InterruptedException
 *  java.lang.Long
 *  java.lang.Object
 *  java.lang.Runnable
 *  java.lang.String
 *  java.lang.StringBuilder
 *  java.lang.System
 *  java.lang.Thread
 *  java.lang.Throwable
 *  java.lang.Void
 *  java.nio.ByteBuffer
 *  java.util.ArrayList
 *  java.util.Arrays
 *  java.util.Collection
 *  java.util.Collections
 *  java.util.HashMap
 *  java.util.HashSet
 *  java.util.Iterator
 *  java.util.List
 *  java.util.Map
 *  java.util.Map$Entry
 *  java.util.Set
 *  java.util.concurrent.ExecutionException
 *  java.util.regex.Matcher
 *  java.util.regex.Pattern
 *  org.apache.http.Header
 *  org.apache.http.HttpEntity
 *  org.apache.http.HttpResponse
 *  org.apache.http.StatusLine
 *  org.apache.http.client.HttpClient
 *  org.apache.http.client.methods.HttpPost
 *  org.apache.http.client.methods.HttpUriRequest
 *  org.apache.http.entity.ByteArrayEntity
 *  org.apache.http.params.BasicHttpParams
 *  org.apache.http.params.HttpConnectionParams
 *  org.apache.http.params.HttpParams
 */
package com.flurry.android;

import android.app.KeyguardManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.SystemClock;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import com.flurry.android.AdCreative;
import com.flurry.android.AdFrame;
import com.flurry.android.AdNetworkView;
import com.flurry.android.AdReportedId;
import com.flurry.android.AdReportedId$Builder;
import com.flurry.android.AdRequest;
import com.flurry.android.AdRequest$Builder;
import com.flurry.android.AdResponse;
import com.flurry.android.AdSpaceLayout;
import com.flurry.android.AdUnit;
import com.flurry.android.AdViewContainer;
import com.flurry.android.AdViewContainer$Builder;
import com.flurry.android.Callback;
import com.flurry.android.CrcMessageDigest;
import com.flurry.android.FlurryAdSize;
import com.flurry.android.FlurryAdType;
import com.flurry.android.FlurryAgent;
import com.flurry.android.GetAdAsyncTask;
import com.flurry.android.ICustomAdNetworkHandler;
import com.flurry.android.IListener;
import com.flurry.android.Location;
import com.flurry.android.Location$Builder;
import com.flurry.android.SdkAdEvent;
import com.flurry.android.SdkAdLog;
import com.flurry.android.SdkLogRequest;
import com.flurry.android.SdkLogRequest$Builder;
import com.flurry.android.SdkLogResponse;
import com.flurry.android.TestAds;
import com.flurry.android.TestAds$Builder;
import com.flurry.android.aa;
import com.flurry.android.ac;
import com.flurry.android.ad;
import com.flurry.android.ae;
import com.flurry.android.ag;
import com.flurry.android.ai;
import com.flurry.android.aj;
import com.flurry.android.an;
import com.flurry.android.ar;
import com.flurry.android.ax;
import com.flurry.android.ba;
import com.flurry.android.bb;
import com.flurry.android.bc;
import com.flurry.android.bd;
import com.flurry.android.c;
import com.flurry.android.d;
import com.flurry.android.f;
import com.flurry.android.g;
import com.flurry.android.h;
import com.flurry.android.j;
import com.flurry.android.n;
import com.flurry.android.o;
import com.flurry.android.q;
import com.flurry.android.r;
import com.flurry.android.t;
import com.flurry.android.u;
import com.flurry.android.v;
import com.flurry.android.w;
import com.flurry.android.x;
import com.flurry.android.y;
import com.flurry.org.apache.avro.io.BinaryDecoder;
import com.flurry.org.apache.avro.io.BinaryEncoder;
import com.flurry.org.apache.avro.io.Decoder;
import com.flurry.org.apache.avro.io.DecoderFactory;
import com.flurry.org.apache.avro.io.Encoder;
import com.flurry.org.apache.avro.io.EncoderFactory;
import com.flurry.org.apache.avro.specific.SpecificDatumReader;
import com.flurry.org.apache.avro.specific.SpecificDatumWriter;
import com.flurry.org.apache.avro.specific.SpecificRecordBase;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

final class be
implements ai {
    static String a = "FlurryAgent";
    static int b = 5;
    private static List<Integer> e;
    private static DecoderFactory f;
    private static v g;
    private long A;
    private long B;
    private long C;
    private Map<Integer, ByteBuffer> D;
    private String E;
    private Handler F;
    private ICustomAdNetworkHandler G;
    private IListener H;
    private volatile boolean I;
    private volatile List<bb> J = new ArrayList();
    private volatile Map<String, bb> K = new HashMap();
    private volatile List<bb> L = new ArrayList();
    private volatile List<String> M = new ArrayList();
    private volatile boolean N = false;
    private Map<String, String> O;
    w c;
    bd d;
    private ax h;
    private r i;
    private Display j;
    private boolean k = false;
    private File l = null;
    private String m;
    private String n;
    private String o;
    private String p;
    private boolean q = true;
    private boolean r = false;
    private volatile String s = null;
    private volatile String t = null;
    private volatile float u;
    private volatile float v;
    private volatile Map<String, String> w;
    private bb x;
    private AdUnit y;
    private String z;

    be() {
        HandlerThread handlerThread = new HandlerThread("FlurryAdThread");
        handlerThread.start();
        this.F = new Handler(handlerThread.getLooper());
        this.c = new w();
        Object[] arrobject = new Integer[]{0, 1, 2, 3, 4, 5};
        e = Arrays.asList((Object[])arrobject);
        f = new DecoderFactory();
        g = new v(this);
        this.i = new r(this);
    }

    private static int a(byte[] arrby) {
        CrcMessageDigest crcMessageDigest = new CrcMessageDigest();
        crcMessageDigest.update(arrby);
        return crcMessageDigest.getChecksum();
    }

    private an a(String string, boolean bl, Map<String, String> map) {
        void var6_4 = this;
        synchronized (var6_4) {
            an an2 = new an(string, bl, this.d(), map);
            return an2;
        }
    }

    private static <A extends SpecificRecordBase> A a(byte[] arrby, Class<A> class_) {
        SpecificRecordBase specificRecordBase;
        BinaryDecoder binaryDecoder = f.binaryDecoder((InputStream)new ByteArrayInputStream(arrby), null);
        try {
            specificRecordBase = new SpecificDatumReader<A>(class_).read(null, binaryDecoder);
        }
        catch (ClassCastException classCastException) {
            "ClassCastException in parseAvroBinary:" + classCastException.getMessage();
            return null;
        }
        return (A)specificRecordBase;
    }

    static /* synthetic */ File a(be be2) {
        return be2.l;
    }

    private String a(bb bb2, AdUnit adUnit, u u2, String string) {
        Pattern pattern = Pattern.compile((String)".*?(%\\{\\w+\\}).*?");
        Matcher matcher = pattern.matcher((CharSequence)string);
        while (matcher.matches()) {
            string = this.i.a(bb2, adUnit, string, matcher.group(1));
            matcher = pattern.matcher((CharSequence)string);
        }
        return string;
    }

    private void a(SdkLogResponse sdkLogResponse) {
        void var4_2 = this;
        synchronized (var4_2) {
            if (sdkLogResponse.a().toString().equals((Object)"success")) {
                this.J.removeAll(this.L);
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void a(List<bb> list, DataOutputStream dataOutputStream) {
        void var8_3 = this;
        synchronized (var8_3) {
            int n2 = list.size();
            int n3 = 0;
            while (n3 < n2) {
                try {
                    ((bb)list.get(n3)).a((DataOutput)dataOutputStream);
                }
                catch (IOException iOException) {
                    bc.a(a, "unable to convert adLog to byte[]: " + ((bb)list.get(n3)).b());
                }
                ++n3;
            }
            return;
        }
    }

    static boolean a(Context context, String string, String string2) {
        Intent intent = new Intent(string2);
        intent.setData(Uri.parse((String)string));
        return y.a(context, intent);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private boolean a(byte[] arrby, String string) {
        byte[] arrby2;
        String string2;
        if (string == null) {
            return false;
        }
        if (string.equals((Object)"/v3/getAds.do")) {
            StringBuilder stringBuilder = new StringBuilder();
            String string3 = this.s != null ? this.s : (FlurryAgent.getUseHttps() ? "https://ads.flurry.com" : "http://ads.flurry.com");
            string2 = stringBuilder.append(string3).append(string).toString();
        } else {
            StringBuilder stringBuilder = new StringBuilder();
            String string4 = this.t != null ? this.t : (FlurryAgent.getUseHttps() ? "https://adlog.flurry.com" : "http://adlog.flurry.com");
            string2 = stringBuilder.append(string4).append(string).toString();
        }
        if ((arrby2 = be.super.b(arrby, string2)) == null) return true;
        try {
            if (string.equals((Object)"/v3/getAds.do")) {
                be.super.c(arrby2);
                return true;
            }
            be.super.b(arrby2);
            return true;
        }
        catch (IOException iOException) {
            bc.b(a, "IOException: " + iOException.toString());
            return true;
        }
    }

    private static byte[] a(InputStream inputStream) {
        int n2;
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] arrby = new byte[128];
        while ((n2 = inputStream.read(arrby)) != -1) {
            byteArrayOutputStream.write(arrby, 0, n2);
        }
        return byteArrayOutputStream.toByteArray();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private byte[] a(String string, int n2, int n3, boolean bl, FlurryAdSize flurryAdSize) {
        List<AdReportedId> list = this.q();
        AdRequest adRequest = AdRequest.a().setApiKey(this.z).setAdSpaceName("").setBindings(e).setAdReportedIds(list).setLocation(Location.a().setLat(this.u).setLon(this.v).build()).setTestDevice(this.N).setAgentVersion(Integer.toString((int)FlurryAgent.getAgentVersion())).setSessionId(this.A).setAdViewContainer(AdViewContainer.a().setScreenHeight(this.j.getHeight()).setScreenWidth(this.j.getWidth()).setViewHeight(n3).setViewWidth(n2).build()).setLocale(this.m).setTimezone(this.n).setOsVersion(this.o).setDevicePlatform(this.p).build();
        if (bl) {
            adRequest.a(bl);
        } else {
            adRequest.a(string);
        }
        if (flurryAdSize != null) {
            adRequest.a(TestAds.a().setAdspacePlacement(flurryAdSize.a()).build());
        }
        if (this.O != null) {
            HashMap hashMap = new HashMap();
            for (Map.Entry entry : this.O.entrySet()) {
                hashMap.put(entry.getKey(), entry.getValue());
            }
            adRequest.a((Map<CharSequence, CharSequence>)hashMap);
        }
        "Got ad request  " + adRequest;
        SpecificDatumWriter<AdRequest> specificDatumWriter = new SpecificDatumWriter<AdRequest>(AdRequest.class);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        BinaryEncoder binaryEncoder = EncoderFactory.get().directBinaryEncoder((OutputStream)byteArrayOutputStream, null);
        try {
            specificDatumWriter.write(adRequest, binaryEncoder);
            binaryEncoder.flush();
        }
        catch (IOException iOException) {
            iOException.getMessage();
            return new byte[0];
        }
        return byteArrayOutputStream.toByteArray();
    }

    /*
     * Enabled aggressive block sorting
     */
    private void b(byte[] arrby) {
        SdkLogResponse sdkLogResponse = be.a(arrby, SdkLogResponse.class);
        if (sdkLogResponse == null) return;
        {
            "got an AdLogResponse:" + sdkLogResponse;
            if (sdkLogResponse.a().toString().equals((Object)"success")) {
                be.super.a(sdkLogResponse);
                return;
            } else {
                for (CharSequence charSequence : sdkLogResponse.b()) {
                    bc.b(a, charSequence.toString());
                }
            }
        }
    }

    static /* synthetic */ boolean b(be be2) {
        return be2.k;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private byte[] b(byte[] arrby, String string) {
        int n2;
        block6 : {
            HttpResponse httpResponse;
            ByteArrayEntity byteArrayEntity = new ByteArrayEntity(arrby);
            byteArrayEntity.setContentType("avro/binary");
            HttpPost httpPost = new HttpPost(string);
            httpPost.setEntity((HttpEntity)byteArrayEntity);
            httpPost.setHeader("accept", "avro/binary");
            httpPost.setHeader("FM-Checksum", Integer.toString((int)be.a(arrby)));
            BasicHttpParams basicHttpParams = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout((HttpParams)basicHttpParams, (int)10000);
            HttpConnectionParams.setSoTimeout((HttpParams)basicHttpParams, (int)15000);
            httpPost.getParams().setBooleanParameter("http.protocol.expect-continue", false);
            HttpClient httpClient = this.h.a((HttpParams)basicHttpParams);
            try {
                httpResponse = httpClient.execute((HttpUriRequest)httpPost);
                n2 = httpResponse.getStatusLine().getStatusCode();
                if (n2 != 200) break block6;
            }
            catch (IOException iOException) {
                bc.b(a, "IOException: " + iOException.toString());
                return null;
            }
            if (httpResponse.getEntity() == null || httpResponse.getEntity().getContentLength() == 0L) break block6;
            bc.c(a, "Request successful");
            byte[] arrby2 = be.a(httpResponse.getEntity().getContent());
            byteArrayEntity.consumeContent();
            String string2 = Integer.toString((int)be.a(arrby2));
            if (!httpResponse.containsHeader("FM-Checksum")) return arrby2;
            if (!httpResponse.getFirstHeader("FM-Checksum").getValue().equals((Object)string2)) return null;
            return arrby2;
        }
        bc.b(a, "Request failed with HTTP " + n2);
        do {
            return null;
            break;
        } while (true);
    }

    static /* synthetic */ ax c(be be2) {
        return be2.h;
    }

    /*
     * Enabled aggressive block sorting
     */
    private void c(byte[] arrby) {
        AdResponse adResponse = be.a(arrby, AdResponse.class);
        if (adResponse == null) return;
        {
            "got an AdResponse:" + adResponse;
            if (adResponse.b() != null && adResponse.b().size() > 0) {
                bc.b(a, "Ad request returned errors: ");
                for (CharSequence charSequence : adResponse.b()) {
                    bc.b(a, charSequence.toString());
                }
                return;
            } else if (adResponse.a() != null && adResponse.a().size() < 1) {
                bc.d(a, "Ad request successful but server delivered no ad units.");
                return;
            } else {
                if (!be.super.o()) return;
                {
                    this.c.a(adResponse);
                    return;
                }
            }
        }
    }

    static /* synthetic */ boolean c(Context context) {
        return be.d(context);
    }

    /*
     * Enabled aggressive block sorting
     */
    private static boolean d(Context context) {
        if (context.checkCallingOrSelfPermission("android.permission.ACCESS_NETWORK_STATE") != 0) {
            return true;
        }
        NetworkInfo networkInfo = ((ConnectivityManager)context.getSystemService("connectivity")).getActiveNetworkInfo();
        if (networkInfo != null) {
            if (networkInfo.isConnected()) return true;
            if (networkInfo.isRoaming()) {
                return true;
            }
        }
        boolean bl = false;
        if (bl) return bl;
        bc.d(a, "No connectivity found.");
        return bl;
    }

    private boolean o() {
        if (!this.I) {
            bc.d(a, "Flurry Ads not initialized");
        }
        if (this.E == null) {
            bc.d(a, "Cannot identify UDID.");
        }
        return this.I;
    }

    private byte[] p() {
        List<AdReportedId> list = this.q();
        v v2 = g;
        List<bb> list2 = this.J;
        ArrayList arrayList = new ArrayList();
        for (bb bb2 : list2) {
            SdkAdLog sdkAdLog = new SdkAdLog();
            sdkAdLog.a(bb2.c());
            sdkAdLog.a(bb2.b());
            ArrayList arrayList2 = new ArrayList();
            for (an an2 : bb2.d()) {
                if (!an2.b()) continue;
                SdkAdEvent sdkAdEvent = new SdkAdEvent();
                sdkAdEvent.a(an2.a());
                sdkAdEvent.a(an2.c());
                Map<String, String> map = an2.d();
                HashMap hashMap = new HashMap();
                for (Map.Entry entry : map.entrySet()) {
                    hashMap.put(entry.getKey(), entry.getValue());
                }
                sdkAdEvent.a((Map<CharSequence, CharSequence>)hashMap);
                arrayList2.add((Object)sdkAdEvent);
            }
            sdkAdLog.a((List<SdkAdEvent>)arrayList2);
            arrayList.add((Object)sdkAdLog);
        }
        v2.a.L = list2;
        if (arrayList.size() == 0) {
            return null;
        }
        SdkLogRequest sdkLogRequest = SdkLogRequest.a().setApiKey(this.z).setAdReportedIds(list).setSdkAdLogs((List<SdkAdLog>)arrayList).setTestDevice(false).setAgentTimestamp(System.currentTimeMillis()).build();
        "Got ad log request:" + sdkLogRequest.toString();
        SpecificDatumWriter<SdkLogRequest> specificDatumWriter = new SpecificDatumWriter<SdkLogRequest>(SdkLogRequest.class);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        BinaryEncoder binaryEncoder = EncoderFactory.get().directBinaryEncoder((OutputStream)byteArrayOutputStream, null);
        try {
            specificDatumWriter.write(sdkLogRequest, binaryEncoder);
            binaryEncoder.flush();
        }
        catch (IOException iOException) {
            iOException.getMessage();
            return null;
        }
        return byteArrayOutputStream.toByteArray();
    }

    private List<AdReportedId> q() {
        ArrayList arrayList = new ArrayList();
        ByteBuffer byteBuffer = ByteBuffer.wrap((byte[])this.E.getBytes());
        arrayList.add((Object)AdReportedId.a().setId(byteBuffer).setType(0).build());
        for (Map.Entry entry : this.D.entrySet()) {
            arrayList.add((Object)AdReportedId.a().setId((ByteBuffer)entry.getValue()).setType((Integer)entry.getKey()).build());
        }
        return arrayList;
    }

    final bb a(bb bb2, String string, boolean bl, Map<String, String> map) {
        void var7_5 = this;
        synchronized (var7_5) {
            if (!this.J.contains((Object)bb2)) {
                this.J.add((Object)bb2);
            }
            bb2.a(be.super.a(string, bl, map));
            return bb2;
        }
    }

    final bb a(String string, String string2, boolean bl, Map<String, String> map) {
        bb bb2 = (bb)this.K.get((Object)string);
        if (bb2 == null) {
            bb2 = y.a((be)this, string);
        }
        return this.a(bb2, string2, true, map);
    }

    /*
     * Enabled aggressive block sorting
     */
    final q a(Context context, AdUnit adUnit) {
        boolean bl;
        o o2;
        List<AdFrame> list = adUnit.c();
        if (list.size() <= 0) {
            return new q(null, false, false);
        }
        AdFrame adFrame = (AdFrame)list.get(0);
        int n2 = adFrame.a();
        String string = adFrame.c().toString();
        String string2 = adFrame.c().toString();
        String string3 = adFrame.d().d().toString();
        bb bb2 = this.a(adFrame.f().toString(), "requested", true, null);
        if (this.H != null) {
            IListener iListener = this.H;
            String string4 = adUnit.a().toString();
            int n3 = adFrame.a();
            String string5 = adFrame.d().d().toString();
            FlurryAdType flurryAdType = n3 == 3 ? FlurryAdType.VIDEO_TAKEOVER : (string5.equals((Object)"takeover") ? FlurryAdType.WEB_TAKEOVER : FlurryAdType.WEB_BANNER);
            if (!iListener.shouldDisplayAd(string4, flurryAdType)) {
                return new q(null, false, false);
            }
        }
        "Processing ad request for binding: " + n2 + ", networkType: " + string + ", format: " + string3;
        if (adUnit.d() == 1 || n2 == 2 || n2 == 1 || n2 == 3) {
            if (string3.equals((Object)"takeover")) {
                this.x = bb2;
                this.y = adUnit;
                "opening takeover activity, display: " + string2 + ", content: " + string;
                bl = true;
                o2 = null;
            } else {
                aj aj2 = new aj(context, (be)this, bb2, adUnit);
                ((o)aj2).initLayout(context);
                o2 = aj2;
                bl = false;
            }
        } else if (n2 == 4) {
            AdSpaceLayout adSpaceLayout = adFrame.d();
            AdCreative adCreative = new AdCreative(adSpaceLayout.b(), adSpaceLayout.a(), adSpaceLayout.d().toString(), adSpaceLayout.c().toString(), adSpaceLayout.e().toString());
            if (string.equalsIgnoreCase("Admob")) {
                "Retrieving BannerView for:" + string;
                o2 = new x(context, (be)this, bb2, adCreative);
                o2.initLayout(context);
                o2.d = 0;
                o2.c = adUnit;
                bl = false;
            } else if (string.equalsIgnoreCase("Millennial Media")) {
                "Retrieving BannerView for:" + string;
                o2 = new ag(context, (be)this, bb2, adCreative);
                o2.initLayout(context);
                o2.d = 0;
                o2.c = adUnit;
                bl = false;
            } else if (string.equalsIgnoreCase("InMobi")) {
                "Retrieving BannerView for:" + string;
                o2 = new aa(context, (be)this, bb2, adCreative);
                o2.initLayout(context);
                o2.d = 0;
                o2.c = adUnit;
                bl = false;
            } else if (string.equalsIgnoreCase("Mobclix")) {
                "Retrieving BannerView for:" + string;
                o2 = new ac(context, (be)this, bb2, adCreative);
                o2.initLayout(context);
                o2.d = 0;
                o2.c = adUnit;
                bl = false;
            } else if (string.equalsIgnoreCase("Jumptap")) {
                "Retrieving BannerView for:" + string;
                o2 = new g(context, (be)this, bb2, adCreative);
                o2.initLayout(context);
                o2.d = 0;
                o2.c = adUnit;
                bl = false;
            } else {
                ICustomAdNetworkHandler iCustomAdNetworkHandler = this.G;
                if (iCustomAdNetworkHandler != null) {
                    AdNetworkView adNetworkView = iCustomAdNetworkHandler.getAdFromNetwork(context, adCreative, string);
                    if (adNetworkView != null) {
                        adNetworkView.a = this;
                        adNetworkView.b = bb2;
                        adNetworkView.initLayout(context);
                        adNetworkView.d = 0;
                        adNetworkView.c = adUnit;
                        o2 = adNetworkView;
                        bl = false;
                    } else {
                        bc.d(a, "CustomAdNetworkHandler returned null banner view");
                        o2 = adNetworkView;
                        bl = false;
                    }
                } else {
                    bc.d(a, "No CustomAdNetworkHandler set");
                    bl = false;
                    o2 = null;
                }
            }
        } else {
            "Do not support binding: " + n2;
            bl = false;
            o2 = null;
        }
        y.e(string);
        return new q(o2, bl, true);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    final q a(Context context, String string, int n2, int n3, boolean bl, FlurryAdSize flurryAdSize, long l2) {
        Collections.emptyMap();
        q q2 = new q(null, false, false);
        if (this.o()) {
            long l3 = System.currentTimeMillis();
            boolean bl2 = false;
            q q3 = q2;
            do {
                if (!y.a(l3 + l2)) {
                    return q3;
                }
                AdUnit adUnit = this.d(string);
                if (adUnit != null) {
                    q q4 = this.a(context, adUnit);
                    if (!this.c.b(string) && !bl2) {
                        this.a(string, n2, n3, false, null, 1L);
                        bl2 = true;
                        q2 = q4;
                    } else {
                        q2 = q4;
                    }
                } else {
                    AdUnit adUnit2;
                    if (!bl2) {
                        this.a(string, n2, n3, false, null, l2);
                        bl2 = true;
                    }
                    q2 = (adUnit2 = this.d(string)) != null ? this.a(context, adUnit2) : q3;
                }
                if (q2.c()) break;
                try {
                    Thread.sleep((long)(l2 / 10L));
                    q3 = q2;
                }
                catch (InterruptedException interruptedException) {
                    bc.b(a, "Ad Request thread interrupted.");
                    return q2;
                }
            } while (true);
        }
        return q2;
    }

    final void a() {
        this.d.a((Context)null);
    }

    final void a(float f2, float f3) {
        this.u = f2;
        this.v = f3;
    }

    final void a(Context context) {
        this.d.a(context);
        this.d.b();
    }

    final void a(Context context, long l2, long l3) {
        this.A = l2;
        this.B = l3;
        this.C = 0L;
        this.d.a(context);
        this.d.a();
        this.r = false;
    }

    /*
     * Enabled aggressive block sorting
     */
    final void a(Context context, ViewGroup viewGroup) {
        t t2;
        if (!be.super.o() || (t2 = this.d.a(viewGroup)) == null) {
            return;
        }
        viewGroup.removeView((View)t2);
        this.d.a(context, t2);
    }

    final void a(Context context, n n2) {
        if (!this.I) {
            this.z = n2.a;
            this.d = new bd(context);
            this.I = true;
        }
        this.h = n2.b;
        this.m = n2.d;
        this.n = n2.e;
        this.o = Build.VERSION.SDK;
        this.p = Build.DEVICE;
        this.j = ((WindowManager)context.getSystemService("window")).getDefaultDisplay();
        this.l = context.getFileStreamPath(".flurryadlog." + Integer.toString((int)this.z.hashCode(), (int)16));
        String string = context.getPackageName();
        this.q = be.a(context, "market://details?id=" + string, "android.intent.action.VIEW");
        this.w = new HashMap();
    }

    final void a(Context context, String string) {
        if (string.startsWith("market://details?id=")) {
            String string2 = string.substring("market://details?id=".length());
            if (this.q) {
                try {
                    y.a(context, string);
                    return;
                }
                catch (Exception exception) {
                    bc.b(a, "Cannot launch Google Play url " + string, exception);
                    return;
                }
            }
            y.a(context, "https://market.android.com/details?id=" + string2);
            return;
        }
        bc.d(a, "Unexpected Google Play url scheme: " + string);
    }

    final void a(ICustomAdNetworkHandler iCustomAdNetworkHandler) {
        if (iCustomAdNetworkHandler != null) {
            this.G = iCustomAdNetworkHandler;
        }
    }

    final void a(IListener iListener) {
        if (iListener != null) {
            this.H = iListener;
        }
    }

    /*
     * Unable to fully structure code
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public final void a(ba var1, ai var2_3, int var3_2) {
        var4_4 = (AdFrame)var1.c.c().get(var1.e);
        var5_5 = new ArrayList();
        var6_6 = var4_4.e();
        var7_7 = var1.a;
        for (Callback var20_20 : var6_6) {
            if (!var20_20.a().toString().equals((Object)var7_7)) continue;
            for (CharSequence var22_11 : var20_20.b()) {
                var23_13 = new HashMap();
                var24_16 = var22_11.toString();
                var25_17 = var24_16.indexOf(63);
                if (var25_17 != -1) {
                    var26_14 = var24_16.substring(0, var25_17);
                    for (String var31_18 : var24_16.substring(var25_17 + 1).split("&")) {
                        var32_15 = var31_18.indexOf(61);
                        if (var32_15 == -1) continue;
                        var23_13.put((Object)var31_18.substring(0, var32_15), (Object)var31_18.substring(var32_15 + 1));
                    }
                } else {
                    var26_14 = var24_16;
                }
                var5_5.add((Object)new u(var26_14, (Map<String, String>)var23_13, var1));
            }
        }
        if (!var1.a.equals((Object)"adWillClose") && !var1.a.equals((Object)"clicked")) ** GOTO lbl35
        if (this.H != null) {
            this.H.onAdClosed(var1.c.a().toString());
        }
        var9_21 = new HashSet();
        var9_21.addAll((Collection)Arrays.asList((Object[])new String[]{"closeAd", "processRedirect", "nextFrame", "nextAdUnit", "notifyUser"}));
        var11_22 = var5_5.iterator();
        while (var11_22.hasNext()) {
            if (!var9_21.contains((Object)((u)var11_22.next()).a)) continue;
            var12_23 = true;
lbl32: // 2 sources:
            do {
                if (!var12_23) {
                    var5_5.add((Object)new u("closeAd", (Map<String, String>)Collections.emptyMap(), var1));
                }
lbl35: // 4 sources:
                if (var1.a.equals((Object)"renderFailed") && this.H != null) {
                    this.H.onRenderFailed(var1.c.a().toString());
                }
                var13_24 = null;
                for (u var17_26 : var5_5) {
                    if (var17_26.a.equals((Object)"logEvent")) {
                        var17_26.b.put((Object)"__sendToServer", (Object)"true");
                        var13_24 = var17_26;
                    }
                    var2_3.a(var17_26, (be)this, var3_2 + 1);
                }
                if (var13_24 != null) return;
                var15_27 = new HashMap();
                var15_27.put((Object)"__sendToServer", (Object)"false");
                var2_3.a(new u("logEvent", (Map<String, String>)var15_27, var1), (be)this, var3_2 + 1);
                return;
                break;
            } while (true);
        }
        var12_23 = false;
        ** while (true)
    }

    final void a(bb bb2) {
        void var5_2 = this;
        synchronized (var5_2) {
            if (this.J.size() < 32767) {
                this.J.add((Object)bb2);
                this.K.put((Object)bb2.b(), (Object)bb2);
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    @Override
    public final void a(u u2, be be2, int n2) {
        String string = u2.a;
        Context context = u2.c.b;
        bb bb2 = u2.c.d;
        AdUnit adUnit = u2.c.c;
        if (n2 > 10) {
            "Maximum depth for event/action loop exceeded when performing action:" + string + "," + u2.b + ",triggered by:" + u2.c.a;
            return;
        }
        if (string.equals((Object)"processRedirect")) {
            String string2;
            if (!u2.b.containsKey((Object)"url")) return;
            if (this.H != null) {
                this.H.onApplicationExit(adUnit.a().toString());
            }
            String string3 = be.super.a(bb2, adUnit, u2, y.c((String)u2.b.get((Object)"url")));
            ae ae2 = new ae((be)this, context, string3);
            try {
                string2 = (String)ae2.execute((Object[])new Void[0]).get();
            }
            catch (InterruptedException interruptedException) {
                interruptedException.printStackTrace();
                string2 = "";
            }
            catch (ExecutionException executionException) {
                executionException.printStackTrace();
                string2 = "";
            }
            if (string2 != null) {
                if (!be.super.o()) return;
                this.F.post((Runnable)new c((be)this, string2, context, true));
                return;
            }
            bc.d(a, "Redirect URL could not be found for: " + string3);
            return;
        }
        if (string.equals((Object)"verifyUrl")) {
            if (!u2.b.containsKey((Object)"url")) return;
            String string4 = be.a(context, be.super.a(bb2, adUnit, u2, (String)u2.b.get((Object)"url")), "android.intent.action.VIEW") ? "urlVerified" : "urlNotVerified";
            Collections.emptyMap();
            be2.a(new ba(string4, u2.c.b, adUnit, bb2, u2.c.e), (ai)this, n2 + 1);
            return;
        }
        if (!string.equals((Object)"sendUrlAsync")) {
            if (!string.equals((Object)"sendAdLogs")) return;
            this.j();
            return;
        }
        if (!u2.b.containsKey((Object)"url")) return;
        if (this.H != null) {
            this.H.onApplicationExit(adUnit.a().toString());
        }
        d d2 = new d((be)this, context, be.super.a(bb2, adUnit, u2, (String)u2.b.get((Object)"url")));
        this.F.post((Runnable)d2);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    final void a(DataInputStream dataInputStream) {
        void var4_2 = this;
        synchronized (var4_2) {
            do {
                if (dataInputStream.readUnsignedShort() == 0) {
                    this.k = true;
                    return;
                }
                this.J.add((Object)new bb((DataInput)dataInputStream));
            } while (true);
        }
    }

    final void a(String string) {
        this.E = string;
    }

    final void a(String string, int n2, int n3, boolean bl, FlurryAdSize flurryAdSize, long l2) {
        block5 : {
            block4 : {
                if (this.M.contains((Object)string)) break block4;
                if (string != null) break block5;
                bc.a(a, "ad space name should not be null");
            }
            return;
        }
        if (l2 > 0L) {
            h h2 = new h(this, string, n2, n3, bl, flurryAdSize);
            this.F.post((Runnable)h2);
            return;
        }
        this.a(string, n2, n3, bl, flurryAdSize, "/v3/getAds.do");
    }

    final void a(String string, int n2, int n3, boolean bl, FlurryAdSize flurryAdSize, String string2) {
        this.M.add((Object)string);
        byte[] arrby = this.a(string, n2, n3, bl, flurryAdSize);
        if (arrby != null) {
            this.a(arrby, string2);
        }
        this.M.remove((Object)string);
    }

    final void a(Map<Integer, ByteBuffer> map) {
        this.D = map;
    }

    final void a(boolean bl) {
        this.N = bl;
    }

    final boolean a(Context context, String string, FlurryAdSize flurryAdSize, long l2) {
        boolean bl = false;
        long l3 = System.currentTimeMillis();
        while (y.a(l3 + l2)) {
            if (this.c.b(string)) {
                return true;
            }
            if (bl) continue;
            this.a(string, y.a(context, context.getResources().getDisplayMetrics().widthPixels), y.a(context, context.getResources().getDisplayMetrics().heightPixels), false, flurryAdSize, l2);
            bl = true;
        }
        return false;
    }

    /*
     * Enabled aggressive block sorting
     */
    final boolean a(Context context, String string, FlurryAdSize flurryAdSize, ViewGroup viewGroup, long l2) {
        if (!this.o()) {
            return false;
        }
        if (l2 < 1L) {
            boolean bl = this.c.b(string);
            if (be.d(context) && !((KeyguardManager)context.getSystemService("keyguard")).inKeyguardRestrictedInputMode()) {
                new GetAdAsyncTask(context, string, flurryAdSize, viewGroup, this).execute((Object[])new Void[0]);
            }
            return bl;
        }
        t t2 = this.d.a(viewGroup);
        t t3 = this.d.a(context, (View)viewGroup, string);
        if (t3 == null) {
            t3 = this.d.a(this, context, viewGroup, string);
            this.d.b(context, t3);
        }
        t t4 = t3;
        for (t t5 : this.d.a(context, viewGroup, string)) {
            if (t5.equals((Object)t2)) continue;
            this.d.a(context, t5);
            t5.c().removeView((View)t5);
        }
        ar ar2 = this.d.a(t4, l2);
        if (ar2.a()) {
            if (!t4.equals((Object)t2)) {
                if (t2 != null) {
                    this.d.a(context, t2);
                    viewGroup.removeView((View)t2);
                }
                viewGroup.addView((View)t4);
            }
            if (t4.a() > 0 && !t4.b()) {
                this.d.a(t4);
            }
        }
        boolean bl = ar2.a() || ar2.b();
        bc.a(a, "Ad is being returned: " + bl);
        return bl;
    }

    final long b() {
        return this.A;
    }

    final void b(Context context) {
        bc.c(a, "Init'ing ads.");
        if (!this.r) {
            DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
            this.a("", displayMetrics.widthPixels, displayMetrics.heightPixels, true, null, 1L);
            this.r = true;
            return;
        }
        bc.c(a, "Ads already init'ed, will not init them again this session");
    }

    final void b(String string) {
        this.s = string;
    }

    final void b(Map<String, String> map) {
        if (map != null) {
            this.O = map;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    final boolean b(Context context, String string) {
        int n2 = 0;
        while (n2 < 5) {
            if (be.d(context)) {
                HttpResponse httpResponse = y.a(this.h, string, 10000, 15000, true);
                if (httpResponse != null && httpResponse.getStatusLine().getStatusCode() == 200) {
                    "URL hit succeeded for: " + string;
                    return true;
                }
            } else {
                try {
                    Thread.sleep((long)100L);
                }
                catch (InterruptedException interruptedException) {
                    interruptedException.getMessage();
                }
            }
            ++n2;
        }
        return false;
    }

    final String c() {
        return this.z;
    }

    final void c(String string) {
        this.t = string;
    }

    final void c(Map<String, String> map) {
        if (map != null && map.size() > 0) {
            for (Map.Entry entry : map.entrySet()) {
                this.w.put(entry.getKey(), entry.getValue());
            }
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    final long d() {
        be be2 = this;
        synchronized (be2) {
            long l2;
            block5 : {
                l2 = SystemClock.elapsedRealtime() - this.B;
                if (l2 <= this.C) break block5;
                do {
                    this.C = l2;
                    return this.C;
                    break;
                } while (true);
            }
            this.C = l2 = 1L + this.C;
            this.C = l2;
            return this.C;
        }
    }

    final AdUnit d(String string) {
        return this.c.a(string);
    }

    final String e() {
        return this.E;
    }

    final void e(String string) {
        byte[] arrby = be.super.p();
        if (arrby != null) {
            be.super.a(arrby, string);
        }
    }

    final void f() {
        this.O = null;
    }

    final boolean g() {
        return this.I;
    }

    final void h() {
        this.w.clear();
    }

    final Map<String, String> i() {
        return this.w;
    }

    final void j() {
        be be2 = this;
        synchronized (be2) {
            f f2 = new f(this);
            this.F.post((Runnable)f2);
            return;
        }
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    final void k() {
        block14 : {
            block13 : {
                var7_1 = this;
                // MONITORENTER : var7_1
                var6_2 = ad.a(this.l);
                if (var6_2) break block13;
                y.a(null);
                // MONITOREXIT : var7_1
                return;
            }
            var2_3 = new DataOutputStream((OutputStream)new FileOutputStream(this.l));
            try {
                var2_3.writeShort(46586);
                this.a(this.J, var2_3);
                var2_3.writeShort(0);
            }
            catch (Throwable var4_6) {
                ** continue;
            }
            y.a((Closeable)var2_3);
            return;
            catch (Throwable var4_4) {
                block15 : {
                    var2_3 = null;
                    break block15;
                    catch (Throwable var1_9) {
                        var2_3 = null;
                        break block14;
                    }
                }
lbl25: // 2 sources:
                do {
                    try {
                        bc.b(be.a, "", (Throwable)var4_5);
                    }
                    catch (Throwable var1_8) {
                        break;
                    }
                    y.a(var2_3);
                    return;
                    break;
                } while (true);
            }
        }
        y.a(var2_3);
        throw var1_7;
    }

    final void l() {
        be be2 = this;
        synchronized (be2) {
            new j(this).execute((Object[])new Void[0]);
            return;
        }
    }

    final bb m() {
        return this.x;
    }

    final AdUnit n() {
        return this.y;
    }
}

