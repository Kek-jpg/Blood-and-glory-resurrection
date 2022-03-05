/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.content.pm.PackageInfo
 *  android.content.pm.PackageManager
 *  android.content.pm.Signature
 *  java.io.File
 *  java.io.FileInputStream
 *  java.io.FileOutputStream
 *  java.lang.Exception
 *  java.lang.Object
 *  java.lang.String
 *  java.util.Iterator
 *  java.util.List
 */
package com.glu.plugins;

import android.app.Activity;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import com.glu.plugins.AJTGameInfo;
import com.glu.plugins.AJavaTools;
import com.unity3d.player.UnityPlayer;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Iterator;
import java.util.List;

public class AJTDebugUtil {
    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private static void CopyFolder(File file, File file2) {
        int n;
        String[] arrstring;
        int n2;
        block8 : {
            n = 0;
            try {
                int n3;
                if (file.isDirectory()) {
                    if (!file2.exists()) {
                        file2.mkdir();
                    }
                    arrstring = file.list();
                    n2 = arrstring.length;
                    break block8;
                }
                FileInputStream fileInputStream = new FileInputStream(file);
                FileOutputStream fileOutputStream = new FileOutputStream(file2);
                byte[] arrby = new byte[1024];
                while ((n3 = fileInputStream.read(arrby)) > 0) {
                    fileOutputStream.write(arrby, 0, n3);
                }
                fileInputStream.close();
                fileOutputStream.close();
                AJavaTools.Log("Copied: " + (Object)file);
                AJavaTools.Log("To " + (Object)file2);
                return;
            }
            catch (Exception exception) {
                if (!AJavaTools.DEBUG) return;
                exception.printStackTrace();
                return;
            }
        }
        while (n < n2) {
            String string2 = arrstring[n];
            AJTDebugUtil.CopyFolder(new File(file, string2), new File(file2, string2));
            ++n;
        }
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public static void ListAllSignatures() {
        AJavaTools.Log("ListAllSignatures()");
        if (!AJavaTools.TRUE_DEBUG) {
            return;
        }
        var0 = UnityPlayer.currentActivity.getPackageManager().getInstalledPackages(1).iterator();
        block2 : do {
            if (var0.hasNext() == false) return;
            var1_2 = (PackageInfo)var0.next();
            try {
                var3_6 = UnityPlayer.currentActivity.getPackageManager().getPackageInfo((String)var1_2.packageName, (int)64).signatures;
                var4_1 = var3_6.length;
                var5_3 = 0;
            }
            catch (Exception var2_5) {
                continue;
            }
            do {
                if (var5_3 < var4_1) ** break;
                continue block2;
                var6_4 = var3_6[var5_3];
                AJavaTools.Log("Signature: " + var1_2.packageName + ": " + var6_4.hashCode());
                ++var5_3;
            } while (true);
            break;
        } while (true);
    }

    /*
     * Enabled aggressive block sorting
     */
    public static void PullInternalData() {
        File file;
        File file2;
        block5 : {
            block4 : {
                AJavaTools.Log("PullInternalData()");
                if (!AJavaTools.TRUE_DEBUG) break block4;
                file = new File(UnityPlayer.currentActivity.getFilesDir().getParent());
                file2 = new File(AJTGameInfo.GetExternalFilesPath() + "/../internal");
                AJavaTools.Log("Pulling: " + (Object)file);
                AJavaTools.Log("To: " + (Object)file2);
                if (file.exists()) break block5;
            }
            return;
        }
        if (!file2.exists()) {
            file2.mkdirs();
        }
        AJTDebugUtil.CopyFolder(file, file2);
    }

    /*
     * Enabled aggressive block sorting
     */
    public static void PushInternalData() {
        File file;
        File file2;
        block3 : {
            block2 : {
                AJavaTools.Log("PushInternalData()");
                if (!AJavaTools.TRUE_DEBUG) break block2;
                file = new File(AJTGameInfo.GetExternalFilesPath() + "/../internal");
                file2 = new File(UnityPlayer.currentActivity.getFilesDir().getParent());
                AJavaTools.Log("Pushing: " + (Object)file);
                AJavaTools.Log("To: " + (Object)file2);
                if (file.exists()) break block3;
            }
            return;
        }
        AJTDebugUtil.CopyFolder(file, file2);
    }
}

