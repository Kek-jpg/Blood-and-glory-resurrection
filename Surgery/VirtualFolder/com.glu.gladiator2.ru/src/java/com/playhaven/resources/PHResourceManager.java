/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Exception
 *  java.lang.Object
 *  java.lang.String
 *  java.util.Hashtable
 */
package com.playhaven.resources;

import com.playhaven.resources.PHResource;
import com.playhaven.resources.files.PHBadgeImageResource;
import com.playhaven.resources.files.PHCloseActiveImageResource;
import com.playhaven.resources.files.PHCloseImageResource;
import com.playhaven.src.common.PHCrashReport;
import java.util.Hashtable;

public class PHResourceManager {
    private static PHResourceManager res_manager = null;
    private boolean hasLoaded = false;
    private Hashtable<String, PHResource> resources = null;

    private PHResourceManager() {
    }

    private void loadResources() {
        if (this.resources == null && !this.hasLoaded) {
            this.registerResources();
            this.hasLoaded = true;
        }
    }

    private void registerResources() {
        this.resources = new Hashtable();
        this.resources.put((Object)"close_inactive", (Object)new PHCloseImageResource());
        this.resources.put((Object)"close_active", (Object)new PHCloseActiveImageResource());
        this.resources.put((Object)"badge_image", (Object)new PHBadgeImageResource());
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static PHResourceManager sharedResourceManager() {
        try {
            if (res_manager == null) {
                res_manager = new PHResourceManager();
                res_manager.loadResources();
            }
            do {
                return res_manager;
                break;
            } while (true);
        }
        catch (Exception exception) {
            PHCrashReport.reportCrash(exception, "PHResourceManager - sharedResourceManager", PHCrashReport.Urgency.critical);
            return res_manager;
        }
    }

    public PHResource getResource(String string2) {
        if (this.resources != null) {
            return (PHResource)this.resources.get((Object)string2);
        }
        return null;
    }

    public void registerResource(String string2, PHResource pHResource) {
        if (this.resources != null && pHResource != null) {
            this.resources.put((Object)string2, (Object)pHResource);
        }
    }
}

