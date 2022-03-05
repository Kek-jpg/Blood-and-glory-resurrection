/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Class
 *  java.lang.ClassNotFoundException
 *  java.lang.IllegalAccessException
 *  java.lang.IllegalArgumentException
 *  java.lang.InstantiationException
 *  java.lang.NoSuchMethodException
 *  java.lang.Object
 *  java.lang.SecurityException
 *  java.lang.String
 *  java.lang.reflect.Constructor
 *  java.lang.reflect.InvocationTargetException
 */
package com.amazon.device.ads;

import com.amazon.device.ads.Ad;
import com.amazon.device.ads.AdBridge;
import com.amazon.device.ads.AdRenderer;
import com.amazon.device.ads.Log;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

class AdRendererFactory {
    public static final String LOG_TAG = "AdRendererFactory";

    private AdRendererFactory() {
    }

    static AdRenderer getAdRenderer(Ad.AAXCreative aAXCreative, Ad ad2, AdBridge adBridge) {
        Class class_;
        block11 : {
            if (aAXCreative == null) {
                Log.e(LOG_TAG, "NULL passed to getAdRenderer()");
                return null;
            }
            class_ = Class.forName((String)aAXCreative.getClassName());
            if (class_ != null) break block11;
            Object[] arrobject = new Object[]{aAXCreative.getClassName()};
            Log.e(LOG_TAG, "Unable to create %s class, Class.forName() returned null", arrobject);
            return null;
        }
        try {
            AdRenderer adRenderer = (AdRenderer)class_.getDeclaredConstructor(new Class[]{Ad.class, AdBridge.class}).newInstance(new Object[]{ad2, adBridge});
            return adRenderer;
        }
        catch (ClassNotFoundException classNotFoundException) {
            Object[] arrobject = new Object[]{aAXCreative.getClassName()};
            Log.e(LOG_TAG, "Couldn't find %s ad renderer class", arrobject);
            return null;
        }
        catch (SecurityException securityException) {
            Object[] arrobject = new Object[]{securityException.getLocalizedMessage(), aAXCreative.getClassName()};
            Log.e(LOG_TAG, "Security exception %s instantiating %s ad renderer class", arrobject);
            return null;
        }
        catch (NoSuchMethodException noSuchMethodException) {
            Object[] arrobject = new Object[]{noSuchMethodException.getLocalizedMessage(), aAXCreative.getClassName()};
            Log.e(LOG_TAG, "No valid constructor found: %s instantiating %s ad renderer class", arrobject);
            return null;
        }
        catch (IllegalArgumentException illegalArgumentException) {
            Object[] arrobject = new Object[]{illegalArgumentException.getLocalizedMessage(), aAXCreative.getClassName()};
            Log.e(LOG_TAG, "Illegal argument exception: %s instantiating %s ad renderer class", arrobject);
            return null;
        }
        catch (InstantiationException instantiationException) {
            Object[] arrobject = new Object[]{instantiationException.getLocalizedMessage(), aAXCreative.getClassName()};
            Log.e(LOG_TAG, "Instantiation exception: %s instantiating %s ad renderer class", arrobject);
            return null;
        }
        catch (IllegalAccessException illegalAccessException) {
            Object[] arrobject = new Object[]{illegalAccessException.getLocalizedMessage(), aAXCreative.getClassName()};
            Log.e(LOG_TAG, "Illegal access exception: %s instantiating %s ad renderer class", arrobject);
            return null;
        }
        catch (InvocationTargetException invocationTargetException) {
            Object[] arrobject = new Object[]{invocationTargetException.getLocalizedMessage(), aAXCreative.getClassName()};
            Log.e(LOG_TAG, "Invocation Target exception: %s instantiating %s ad renderer class", arrobject);
            return null;
        }
    }

    static boolean shouldCreateNewRenderer(Ad.AAXCreative aAXCreative, AdRenderer adRenderer) {
        return adRenderer == null || !adRenderer.shouldReuse() || !aAXCreative.getClassName().equals((Object)adRenderer.getClass().getName());
    }
}

