/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.os.AsyncTask
 *  android.os.Environment
 *  java.io.BufferedInputStream
 *  java.io.ByteArrayInputStream
 *  java.io.File
 *  java.io.FileInputStream
 *  java.io.InputStream
 *  java.lang.Exception
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.Throwable
 *  java.lang.Void
 *  java.math.BigInteger
 *  java.security.Key
 *  java.security.KeyFactory
 *  java.security.PrivateKey
 *  java.security.spec.KeySpec
 *  java.security.spec.RSAPrivateKeySpec
 *  java.util.Properties
 *  javax.crypto.Cipher
 */
package com.amazon.device.ads;

import android.os.AsyncTask;
import android.os.Environment;
import com.amazon.device.ads.Log;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.math.BigInteger;
import java.security.Key;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.KeySpec;
import java.security.spec.RSAPrivateKeySpec;
import java.util.Properties;
import javax.crypto.Cipher;

class DebugProperties {
    public static final String DEBUG_AAX_ENDPOINT = "debug.aaxEndpoint";
    public static final String DEBUG_ADID = "debug.adid";
    public static final String DEBUG_APPID = "debug.appid";
    public static final String DEBUG_ATF = "debug.atf";
    public static final String DEBUG_CHANNEL = "debug.channel";
    public static final String DEBUG_DINFO = "debug.dinfo";
    public static final String DEBUG_GEOLOC = "debug.geoloc";
    public static final String DEBUG_MD5UDID = "debug.md5udid";
    public static final String DEBUG_ON = "debug.mode";
    public static final String DEBUG_PA = "debug.pa";
    public static final String DEBUG_PK = "debug.pk";
    public static final String DEBUG_PT = "debug.pt";
    public static final String DEBUG_SHA1UDID = "debug.sha1udid";
    public static final String DEBUG_SIS_ENDPOINT = "debug.sisEndpoint";
    public static final String DEBUG_SIZE = "debug.size";
    public static final String DEBUG_SLOT = "debug.slot";
    public static final String DEBUG_SP = "debug.sp";
    public static final String DEBUG_TEST = "debug.test";
    public static final String DEBUG_UA = "debug.ua";
    public static final String DEBUG_UI = "debug.ui";
    public static final String DEBUG_VER = "debug.ver";
    private static final String FILE_PREFIX = "/com.amazon.device.ads.debug";
    private static final String LOGTAG = "DebugProperties";
    private static boolean debugModeOn_;
    private static Properties debugProperties_;
    private static final BigInteger privExponent_;
    private static final BigInteger privModulus_;

    static {
        privModulus_ = new BigInteger("22425945969293236512819607281747202268852113345956851069545419503178249900977203670147638322801582881051882957295768557918356441519366172126884608406316888515239296504501830280664879549133570276792155151832332847188532369002492210234019359186842709493620665119919750832332220777141369255943445578381285984064028865613478676828533273460580467686485184132743895959747097454385452868408957601246667523882372216446056029831689133478714597838700864119273209955182548633182248700235085802575904827859971001196599005060045450779595767759943984991630413046800554347791145167910883355627096118148593841261053098773337592734097");
        privExponent_ = new BigInteger("5599215006722084151841970702827860151139465197978118529242591197804380779249736540498127864809226859371835159226553869008622098243456195347852554241917744888762998133926842072150379542281041403163862165638226686887497980590930009552760406707269286898150890998325325890252103828011111664174475487114957696526157790937869377570600085450453371238028811033168218737171144699577236108423054506552958366535341910569552237227686862748056351625445281035713423043506793107235726047151346608576583081807969458368853010104969843563629579750936551771756389538574062221915919980316992216032119182896925094308799622409361028579777");
        debugProperties_ = new Properties();
        debugModeOn_ = false;
    }

    DebugProperties() {
    }

    static /* synthetic */ Properties access$000() {
        return debugProperties_;
    }

    static /* synthetic */ boolean access$102(boolean bl) {
        debugModeOn_ = bl;
        return bl;
    }

    protected static byte[] decrypt(byte[] arrby) {
        try {
            RSAPrivateKeySpec rSAPrivateKeySpec = new RSAPrivateKeySpec(privModulus_, privExponent_);
            PrivateKey privateKey = KeyFactory.getInstance((String)"RSA").generatePrivate((KeySpec)rSAPrivateKeySpec);
            Cipher cipher = Cipher.getInstance((String)"RSA");
            cipher.init(2, (Key)privateKey);
            byte[] arrby2 = cipher.doFinal(arrby);
            return arrby2;
        }
        catch (Exception exception) {
            Log.d(LOGTAG, "Exception " + (Object)((Object)exception) + " trying to decrypt debug file");
            return null;
        }
    }

    public static Properties getDebugProperties() {
        return debugProperties_;
    }

    public static boolean isDebugModeOn() {
        return debugModeOn_;
    }

    public static void readDebugProperties() {
        ReadDebugTask readDebugTask = new ReadDebugTask();
        Object[] arrobject = new Void[]{null};
        readDebugTask.execute(arrobject);
    }

    protected static class ReadDebugTask
    extends AsyncTask<Void, Void, Void> {
        protected ReadDebugTask() {
        }

        /*
         * Unable to fully structure code
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         * Lifted jumps to return sites
         */
        protected /* varargs */ Void doInBackground(Void ... var1) {
            if ("mounted".equals((Object)Environment.getExternalStorageState()) == false) return null;
            var2_2 = new File((Object)Environment.getExternalStorageDirectory() + "/com.amazon.device.ads.debug");
            if (var2_2.length() > Integer.MAX_VALUE) {
                return null;
            }
            var3_3 = null;
            var4_4 = 0;
            var5_5 = new BufferedInputStream((InputStream)new FileInputStream(var2_2));
            var8_6 = new byte[(int)var2_2.length()];
            while (var4_4 < var8_6.length) {
                var13_7 = var5_5.read(var8_6, var4_4, var8_6.length - var4_4);
                if (var13_7 <= 0) continue;
                var4_4 += var13_7;
            }
            var9_8 = DebugProperties.decrypt(var8_6);
            if (var9_8 != null) {
                var10_9 = new ByteArrayInputStream(var9_8);
                DebugProperties.access$000().load((InputStream)var10_9);
                if (DebugProperties.access$000().getProperty("debug.mode", "false").equals((Object)"true")) {
                    DebugProperties.access$102(true);
                }
            }
            if (var5_5 == null) return null;
            try {
                var5_5.close();
                return null;
            }
            catch (Exception var11_14) {
                return null;
            }
            catch (Throwable var6_10) {}
            ** GOTO lbl-1000
            catch (Throwable var6_12) {
                var3_3 = var5_5;
            }
lbl-1000: // 2 sources:
            {
                if (var3_3 == null) throw var6_11;
                try {
                    var3_3.close();
                    throw var6_11;
                }
                catch (Exception var7_13) {
                    return null;
                }
            }
        }
    }

}

