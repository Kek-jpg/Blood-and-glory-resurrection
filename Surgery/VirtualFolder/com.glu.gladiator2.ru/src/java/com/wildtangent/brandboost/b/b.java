/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.util.Pair
 *  android.view.animation.Animation
 *  android.view.animation.AnimationSet
 *  android.view.animation.TranslateAnimation
 *  java.lang.Integer
 *  java.lang.Object
 */
package com.wildtangent.brandboost.b;

import android.util.Pair;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import com.wildtangent.brandboost.BrandBoostAPI;
import com.wildtangent.brandboost.b.m;

public class b {
    public static AnimationSet a(int n2, int n3, BrandBoostAPI.Position position, m m2) {
        Pair<Integer, Integer> pair;
        Pair<Integer, Integer> pair2 = b.c(n2, n3, position, m2);
        if (pair2 != null) {
            // empty if block
        }
        if ((pair = b.b(n2, n3, position, m2)) != null) {
            // empty if block
        }
        TranslateAnimation translateAnimation = new TranslateAnimation(0, (float)((Integer)pair.first).intValue(), 0, (float)((Integer)pair2.first).intValue(), 0, (float)((Integer)pair.second).intValue(), 0, (float)((Integer)pair2.second).intValue());
        translateAnimation.setDuration(600L);
        AnimationSet animationSet = new AnimationSet(false);
        animationSet.addAnimation((Animation)translateAnimation);
        return animationSet;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private static Pair<Integer, Integer> b(int n2, int n3, BrandBoostAPI.Position position, m m2) {
        int n4;
        int n5 = m2.f();
        int n6 = m2.g();
        int n7 = 0;
        if (position == BrandBoostAPI.Position.North || position == BrandBoostAPI.Position.Center) {
            n4 = -(n3 + n6);
            do {
                return new Pair((Object)new Integer(n7), (Object)new Integer(n4));
                break;
            } while (true);
        }
        if (position == BrandBoostAPI.Position.Northeast) {
            n7 = n2 + n5;
            n4 = -(n3 + n6);
            return new Pair((Object)new Integer(n7), (Object)new Integer(n4));
        }
        if (position == BrandBoostAPI.Position.East) {
            n7 = n2 + n5;
            n4 = 0;
            return new Pair((Object)new Integer(n7), (Object)new Integer(n4));
        }
        if (position == BrandBoostAPI.Position.Southeast) {
            n7 = n2 + n5;
            n4 = n3 + n6;
            return new Pair((Object)new Integer(n7), (Object)new Integer(n4));
        }
        if (position == BrandBoostAPI.Position.South) {
            n4 = n3 + n6;
            n7 = 0;
            return new Pair((Object)new Integer(n7), (Object)new Integer(n4));
        }
        if (position == BrandBoostAPI.Position.Southwest) {
            n7 = -(n2 + n5);
            n4 = n3 + n6;
            return new Pair((Object)new Integer(n7), (Object)new Integer(n4));
        }
        if (position == BrandBoostAPI.Position.West) {
            n7 = -(n2 + n5);
            n4 = 0;
            return new Pair((Object)new Integer(n7), (Object)new Integer(n4));
        }
        if (position != BrandBoostAPI.Position.Northwest) return null;
        n7 = -(n2 + n5);
        n4 = -(n3 + n6);
        return new Pair((Object)new Integer(n7), (Object)new Integer(n4));
    }

    private static Pair<Integer, Integer> c(int n2, int n3, BrandBoostAPI.Position position, m m2) {
        return new Pair((Object)new Integer(0), (Object)new Integer(0));
    }
}

