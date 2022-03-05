/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.text.TextUtils
 *  android.util.Log
 *  java.lang.CharSequence
 *  java.lang.IllegalArgumentException
 *  java.lang.Long
 *  java.lang.Object
 *  java.lang.RuntimeException
 *  java.lang.String
 *  java.lang.Throwable
 *  java.security.InvalidKeyException
 *  java.security.KeyFactory
 *  java.security.NoSuchAlgorithmException
 *  java.security.PublicKey
 *  java.security.SecureRandom
 *  java.security.Signature
 *  java.security.SignatureException
 *  java.security.spec.InvalidKeySpecException
 *  java.security.spec.KeySpec
 *  java.security.spec.X509EncodedKeySpec
 *  java.util.ArrayList
 *  java.util.HashSet
 *  org.json.JSONArray
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package com.glu.plugins.google;

import android.text.TextUtils;
import android.util.Log;
import com.glu.plugins.google.Base64;
import com.glu.plugins.google.Base64DecoderException;
import com.glu.plugins.google.Consts;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.ArrayList;
import java.util.HashSet;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Security {
    private static final String KEY_FACTORY_ALGORITHM = "RSA";
    private static final SecureRandom RANDOM = new SecureRandom();
    private static final String SIGNATURE_ALGORITHM = "SHA1withRSA";
    private static final String TAG = "AInAppPurchase - Security";
    private static HashSet<Long> sKnownNonces = new HashSet();

    public static long generateNonce() {
        long l = RANDOM.nextLong();
        sKnownNonces.add((Object)l);
        return l;
    }

    public static PublicKey generatePublicKey(String string2) {
        try {
            byte[] arrby = Base64.decode(string2);
            PublicKey publicKey = KeyFactory.getInstance((String)KEY_FACTORY_ALGORITHM).generatePublic((KeySpec)new X509EncodedKeySpec(arrby));
            return publicKey;
        }
        catch (NoSuchAlgorithmException noSuchAlgorithmException) {
            throw new RuntimeException((Throwable)noSuchAlgorithmException);
        }
        catch (InvalidKeySpecException invalidKeySpecException) {
            Log.e((String)TAG, (String)"Invalid key specification.");
            throw new IllegalArgumentException((Throwable)invalidKeySpecException);
        }
        catch (Base64DecoderException base64DecoderException) {
            Log.e((String)TAG, (String)"Base64 decoding failed.");
            throw new IllegalArgumentException((Throwable)base64DecoderException);
        }
    }

    public static boolean isNonceKnown(long l) {
        return sKnownNonces.contains((Object)l);
    }

    public static void removeNonce(long l) {
        sKnownNonces.remove((Object)l);
    }

    public static boolean verify(PublicKey publicKey, String string2, String string3) {
        if (Consts.DEBUG) {
            Log.i((String)TAG, (String)("signature: " + string3));
        }
        try {
            Signature signature = Signature.getInstance((String)SIGNATURE_ALGORITHM);
            signature.initVerify(publicKey);
            signature.update(string2.getBytes());
            if (!signature.verify(Base64.decode(string3))) {
                Log.e((String)TAG, (String)"Signature verification failed.");
                return false;
            }
            return true;
        }
        catch (NoSuchAlgorithmException noSuchAlgorithmException) {
            Log.e((String)TAG, (String)"NoSuchAlgorithmException.");
            return false;
        }
        catch (InvalidKeyException invalidKeyException) {
            Log.e((String)TAG, (String)"Invalid key specification.");
            return false;
        }
        catch (SignatureException signatureException) {
            Log.e((String)TAG, (String)"Signature exception.");
            return false;
        }
        catch (Base64DecoderException base64DecoderException) {
            Log.e((String)TAG, (String)"Base64 decoding failed.");
            return false;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static ArrayList<VerifiedPurchase> verifyPurchase(String string2, String string3) {
        long l;
        int n;
        boolean bl;
        JSONArray jSONArray;
        if (string2 == null) {
            Log.e((String)TAG, (String)"data is null");
            return null;
        }
        if (Consts.DEBUG) {
            Log.i((String)TAG, (String)("signedData: " + string2));
        }
        if (!TextUtils.isEmpty((CharSequence)string3)) {
            boolean bl2 = Security.verify(Security.generatePublicKey("MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAgG4fY9mB0TnsxN+MdTxRoI02vluVL7x3u78rXbJ+aG+5RKQjZHlE4qGHLqSe7T+vZt4FfE+VaHr0tQtE/B+jZhkez1PNjlFeNrVhVGC/fPVtKefWPwDAz+AawrsVQmj3h0CBfyoBm09dCg2wWDnfq42Bz6CFZFyXybu130yKMh+lPnOkeksEKRt4+xFwFlG0jaJHQO3k86juuN9OO7tWlaOrmOo2+06Zutfkc9U6hun3nVO8nEpOBW3PnSMVPE4gLdIFBfb0epojPu5EIxoS5m10QvHllq1jVIlQ5LAYusmAgJjWeM0bDUp/TEey3SaIpFbni6JeNvjkJwFO5krN0wIDAQAB"), string2, string3);
            if (!bl2 && !(bl2 = Security.verify(Security.generatePublicKey("MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAuYzhXypK930DHRHKq8ZVci5b1JnFPD8xzkiglUmyeBV8gWW8VqPHrvs7zWqB8THt78ti9ismcbLhjVufqSfZQT2wT5xiQvukmuAJn2WnGBYD2VwVJQwlXnoJ35aEASv+L34K9OTwXQUZxBK6yjc51HLEalPo5guGEsKz30frQxlR/cw1Dq/FteFGGlXRFPvSUapvOMLKI9Ep+6LI3PnY7CHI81Fx4vNntUo5aCvWKQp3YKdpgFuAdi+Smk7h1Y6B/NLOguxdzEF5uhfpFh4kpxdIibtOtQ/A/EagPiBkSXQC2ZTnCKQnpUjW0/CjKZMbjWaNzZrgXS0z/13INXT5bwIDAQAB"), string2, string3))) {
                Log.w((String)TAG, (String)"signature does not match data.");
                return null;
            }
            bl = bl2;
        } else {
            bl = false;
        }
        try {
            int n2;
            JSONObject jSONObject = new JSONObject(string2);
            l = jSONObject.optLong("nonce");
            jSONArray = jSONObject.optJSONArray("orders");
            n = jSONArray != null ? (n2 = jSONArray.length()) : 0;
        }
        catch (JSONException jSONException) {
            return null;
        }
        if (!Security.isNonceKnown(l)) {
            Log.w((String)TAG, (String)("Nonce not found: " + l));
            return null;
        }
        ArrayList arrayList = new ArrayList();
        int n3 = 0;
        do {
            if (n3 >= n) {
                Security.removeNonce(l);
                return arrayList;
            }
            try {
                JSONObject jSONObject = jSONArray.getJSONObject(n3);
                Consts.PurchaseState purchaseState = Consts.PurchaseState.valueOf(jSONObject.getInt("purchaseState"));
                String string4 = jSONObject.getString("productId");
                jSONObject.getString("packageName");
                long l2 = jSONObject.getLong("purchaseTime");
                String string5 = jSONObject.optString("orderId", "");
                boolean bl3 = jSONObject.has("notificationId");
                String string6 = null;
                if (bl3) {
                    string6 = jSONObject.getString("notificationId");
                }
                String string7 = jSONObject.optString("developerPayload", null);
                if (purchaseState != Consts.PurchaseState.PURCHASED || bl) {
                    arrayList.add((Object)new VerifiedPurchase(purchaseState, string6, string4, string5, l2, string7));
                }
            }
            catch (JSONException jSONException) {
                Log.e((String)TAG, (String)"JSON exception: ", (Throwable)jSONException);
                return null;
            }
            ++n3;
        } while (true);
    }

    public static class VerifiedPurchase {
        public String developerPayload;
        public String notificationId;
        public String orderId;
        public String productId;
        public Consts.PurchaseState purchaseState;
        public long purchaseTime;

        public VerifiedPurchase(Consts.PurchaseState purchaseState, String string2, String string3, String string4, long l, String string5) {
            this.purchaseState = purchaseState;
            this.notificationId = string2;
            this.productId = string3;
            this.orderId = string4;
            this.purchaseTime = l;
            this.developerPayload = string5;
        }
    }

}

