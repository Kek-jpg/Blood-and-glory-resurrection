/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.app.backup.BackupAgentHelper
 *  android.app.backup.BackupDataInput
 *  android.app.backup.BackupDataOutput
 *  android.app.backup.BackupHelper
 *  android.app.backup.FileBackupHelper
 *  android.app.backup.SharedPreferencesBackupHelper
 *  android.content.Context
 *  android.content.SharedPreferences
 *  android.content.SharedPreferences$Editor
 *  android.content.res.Resources
 *  android.os.ParcelFileDescriptor
 *  android.util.Log
 *  java.io.IOException
 *  java.io.InputStream
 *  java.lang.CharSequence
 *  java.lang.Exception
 *  java.lang.Integer
 *  java.lang.String
 *  java.lang.StringBuilder
 *  java.security.Key
 *  java.security.spec.AlgorithmParameterSpec
 *  java.util.Properties
 *  java.util.regex.Matcher
 *  java.util.regex.Pattern
 *  javax.crypto.Cipher
 *  javax.crypto.CipherInputStream
 *  javax.crypto.spec.IvParameterSpec
 *  javax.crypto.spec.SecretKeySpec
 */
package com.glu.plugins;

import android.app.backup.BackupAgentHelper;
import android.app.backup.BackupDataInput;
import android.app.backup.BackupDataOutput;
import android.app.backup.BackupHelper;
import android.app.backup.FileBackupHelper;
import android.app.backup.SharedPreferencesBackupHelper;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.ParcelFileDescriptor;
import android.util.Log;
import java.io.IOException;
import java.io.InputStream;
import java.security.Key;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class GluBackupAgent
extends BackupAgentHelper {
    public static final String KEY_DB_FILES = "BACKUP_FILES";
    public static final String KEY_DB_SHAREDPREFS = "BACKUP_SHAREDPREFS";
    private static final String SHAREDPREF_KEY_RESTORED = "restored";
    private static final String SHAREDPREF_NAME = "ajt";
    private static Properties p;

    private static void Log(String string2) {
        Log.d((String)"AJavaTools", (String)string2);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public String GetProperty(String string2) {
        GluBackupAgent.Log("GetProperty( " + string2 + " )");
        if (p != null) return p.getProperty(string2);
        GluBackupAgent.Log("Initializing properties");
        p = new Properties();
        try {
            byte[] arrby = new byte[]{-87, -52, 54, -72, 14, -98, 92, 37, -108, 125, -128, -42, 118, -93, -10, 56};
            byte[] arrby2 = new byte[]{-5, 49, 4, -65, -56, -37, 77, -37, -69, -50, 44, 56, -25, -128, -42, 126};
            Cipher cipher = Cipher.getInstance((String)"AES/CBC/PKCS5Padding");
            cipher.init(2, (Key)new SecretKeySpec(arrby, "AES"), (AlgorithmParameterSpec)new IvParameterSpec(arrby2));
            p.load((InputStream)new CipherInputStream(this.getResources().openRawResource(this.getResources().getIdentifier("raw/properties", null, this.getPackageName())), cipher));
        }
        catch (Exception exception) {
            GluBackupAgent.Log("Failed to load properties");
            return p.getProperty(string2);
        }
        return p.getProperty(string2);
    }

    public void onBackup(ParcelFileDescriptor parcelFileDescriptor, BackupDataOutput backupDataOutput, ParcelFileDescriptor parcelFileDescriptor2) throws IOException {
        GluBackupAgent.Log("GBA.onBackup()");
        super.onBackup(parcelFileDescriptor, backupDataOutput, parcelFileDescriptor2);
    }

    /*
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    public void onCreate() {
        String string2;
        GluBackupAgent.Log("GBA.onCreate()");
        String string3 = this.GetProperty(KEY_DB_SHAREDPREFS);
        if (string3 != null) {
            String string4 = string3.replace((CharSequence)"<package>", (CharSequence)this.getPackageName());
            Pattern pattern = Pattern.compile((String)"([\\w\\.-_]*)\\[(\\d*)-(\\d*)\\]([\\w\\.-_]*)");
            Matcher matcher = pattern.matcher((CharSequence)string4);
            do {
                String string5;
                if (matcher.find()) {
                    string5 = "";
                } else {
                    this.addHelper("glusp", (BackupHelper)new SharedPreferencesBackupHelper((Context)this, string4.split("\\|")));
                    break;
                }
                for (int i2 = Integer.parseInt((String)matcher.group((int)2)); i2 <= Integer.parseInt((String)matcher.group(3)); ++i2) {
                    StringBuilder stringBuilder = new StringBuilder().append(string5).append(matcher.group(1)).append(i2).append(matcher.group(4));
                    String string6 = i2 == Integer.parseInt((String)matcher.group(3)) ? "" : "|";
                    string5 = stringBuilder.append(string6).toString();
                }
                string4 = matcher.replaceFirst(string5);
                matcher = pattern.matcher((CharSequence)string4);
            } while (true);
        }
        if ((string2 = this.GetProperty(KEY_DB_FILES)) == null) return;
        String string7 = string2.replace((CharSequence)"<package>", (CharSequence)this.getPackageName());
        Pattern pattern = Pattern.compile((String)"([\\w\\.-_]*)\\[(\\d*)-(\\d*)\\]([\\w\\.-_]*)");
        Matcher matcher = pattern.matcher((CharSequence)string7);
        do {
            if (!matcher.find()) {
                this.addHelper("glufile", (BackupHelper)new FileBackupHelper((Context)this, string7.split("\\|")));
                return;
            }
            String string8 = "";
            for (int i3 = Integer.parseInt((String)matcher.group((int)2)); i3 <= Integer.parseInt((String)matcher.group(3)); ++i3) {
                StringBuilder stringBuilder = new StringBuilder().append(string8).append(matcher.group(1)).append(i3).append(matcher.group(4));
                String string9 = i3 == Integer.parseInt((String)matcher.group(3)) ? "" : "|";
                string8 = stringBuilder.append(string9).toString();
            }
            string7 = matcher.replaceFirst(string8);
            matcher = pattern.matcher((CharSequence)string7);
        } while (true);
    }

    public void onRestore(BackupDataInput backupDataInput, int n, ParcelFileDescriptor parcelFileDescriptor) throws IOException {
        GluBackupAgent.Log("GBA.onRestore()");
        super.onRestore(backupDataInput, n, parcelFileDescriptor);
        SharedPreferences.Editor editor = this.getSharedPreferences(SHAREDPREF_NAME, 0).edit();
        editor.putBoolean(SHAREDPREF_KEY_RESTORED, true);
        editor.commit();
    }
}

