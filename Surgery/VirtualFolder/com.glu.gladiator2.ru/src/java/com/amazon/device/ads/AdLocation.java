/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.location.Location
 *  android.location.LocationManager
 *  java.lang.Class
 *  java.lang.Enum
 *  java.lang.IllegalArgumentException
 *  java.lang.Math
 *  java.lang.Object
 *  java.lang.SecurityException
 *  java.lang.String
 */
package com.amazon.device.ads;

import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import com.amazon.device.ads.InternalAdRegistration;
import com.amazon.device.ads.Log;

class AdLocation {
    private static final String LOG_TAG = "AdLocation";
    private static final float MAX_DISTANCE_IN_KILOMETERS = 3.0f;
    private static AdLocation instance_ = null;
    private int arcminutePrecision_ = 6;
    private LocationAwareness locationAwareness_ = LocationAwareness.LOCATION_AWARENESS_TRUNCATED;

    private AdLocation() {
    }

    public static AdLocation getInstance() {
        Class<AdLocation> class_ = AdLocation.class;
        synchronized (AdLocation.class) {
            if (instance_ == null) {
                instance_ = new AdLocation();
            }
            AdLocation adLocation = instance_;
            // ** MonitorExit[var2] (shouldn't be in output)
            return adLocation;
        }
    }

    private static double roundToArcminutes(double d2) {
        return (double)Math.round((double)(d2 * 60.0)) / 60.0;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public Location getLocation() {
        Location location;
        Location location2;
        Location location3;
        if (this.locationAwareness_ == LocationAwareness.LOCATION_AWARENESS_DISABLED) {
            return null;
        }
        LocationManager locationManager = (LocationManager)InternalAdRegistration.getInstance().getContext().getSystemService("location");
        try {
            Location location4;
            location2 = location4 = locationManager.getLastKnownLocation("gps");
        }
        catch (SecurityException securityException) {
            Log.d(LOG_TAG, "Failed to retrieve GPS location: No permissions to access GPS");
            location2 = null;
        }
        catch (IllegalArgumentException illegalArgumentException) {
            Log.d(LOG_TAG, "Failed to retrieve GPS location: No GPS found");
            location2 = null;
        }
        try {
            Location location5;
            location3 = location5 = locationManager.getLastKnownLocation("network");
        }
        catch (SecurityException securityException) {
            Log.d(LOG_TAG, "Failed to retrieve network location: No permissions to access network location");
            location3 = null;
        }
        catch (IllegalArgumentException illegalArgumentException) {
            Log.d(LOG_TAG, "Failed to retrieve network location: No network provider found");
            location3 = null;
        }
        if (location2 == null && location3 == null) {
            return null;
        }
        if (location2 != null && location3 != null) {
            if (location2.distanceTo(location3) / 1000.0f <= 3.0f) {
                float f2;
                float f3 = location2.hasAccuracy() ? location2.getAccuracy() : Float.MAX_VALUE;
                if (f3 < (f2 = location3.hasAccuracy() ? location3.getAccuracy() : Float.MAX_VALUE)) {
                    Log.d(LOG_TAG, "Setting lat/long using GPS determined by distance");
                    location = location2;
                } else {
                    Log.d(LOG_TAG, "Setting lat/long using network determined by distance");
                    location = location3;
                }
            } else if (location2.getTime() > location3.getTime()) {
                Log.d(LOG_TAG, "Setting lat/long using GPS");
                location = location2;
            } else {
                Log.d(LOG_TAG, "Setting lat/long using network");
                location = location3;
            }
        } else if (location2 != null) {
            Log.d(LOG_TAG, "Setting lat/long using GPS, not network");
            location = location2;
        } else {
            Log.d(LOG_TAG, "Setting lat/long using network location, not GPS");
            location = location3;
        }
        if (this.locationAwareness_ == LocationAwareness.LOCATION_AWARENESS_TRUNCATED) {
            location.setLatitude((double)Math.round((double)(AdLocation.roundToArcminutes(location.getLatitude()) * Math.pow((double)10.0, (double)this.arcminutePrecision_))) / Math.pow((double)10.0, (double)this.arcminutePrecision_));
            location.setLongitude((double)Math.round((double)(AdLocation.roundToArcminutes(location.getLongitude()) * Math.pow((double)10.0, (double)this.arcminutePrecision_))) / Math.pow((double)10.0, (double)this.arcminutePrecision_));
        }
        return location;
    }

    private static final class LocationAwareness
    extends Enum<LocationAwareness> {
        private static final /* synthetic */ LocationAwareness[] $VALUES;
        public static final /* enum */ LocationAwareness LOCATION_AWARENESS_DISABLED;
        public static final /* enum */ LocationAwareness LOCATION_AWARENESS_NORMAL;
        public static final /* enum */ LocationAwareness LOCATION_AWARENESS_TRUNCATED;

        static {
            LOCATION_AWARENESS_NORMAL = new LocationAwareness();
            LOCATION_AWARENESS_TRUNCATED = new LocationAwareness();
            LOCATION_AWARENESS_DISABLED = new LocationAwareness();
            LocationAwareness[] arrlocationAwareness = new LocationAwareness[]{LOCATION_AWARENESS_NORMAL, LOCATION_AWARENESS_TRUNCATED, LOCATION_AWARENESS_DISABLED};
            $VALUES = arrlocationAwareness;
        }

        public static LocationAwareness valueOf(String string) {
            return (LocationAwareness)Enum.valueOf(LocationAwareness.class, (String)string);
        }

        public static LocationAwareness[] values() {
            return (LocationAwareness[])$VALUES.clone();
        }
    }

}

