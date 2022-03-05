/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Boolean
 *  java.lang.CharSequence
 *  java.lang.Exception
 *  java.lang.Integer
 *  java.lang.Long
 *  java.lang.Object
 *  java.lang.Throwable
 *  java.util.List
 *  java.util.Map
 */
package com.flurry.android;

import com.flurry.android.AdReportedId;
import com.flurry.android.AdRequest;
import com.flurry.android.AdViewContainer;
import com.flurry.android.Location;
import com.flurry.android.TestAds;
import com.flurry.org.apache.avro.AvroRuntimeException;
import com.flurry.org.apache.avro.Schema;
import com.flurry.org.apache.avro.data.RecordBuilder;
import com.flurry.org.apache.avro.specific.SpecificRecordBuilderBase;
import java.util.List;
import java.util.Map;

public class AdRequest$Builder
extends SpecificRecordBuilderBase<AdRequest>
implements RecordBuilder<AdRequest> {
    private CharSequence a;
    private CharSequence b;
    private CharSequence c;
    private long d;
    private List<AdReportedId> e;
    private Location f;
    private boolean g;
    private List<Integer> h;
    private AdViewContainer i;
    private CharSequence j;
    private CharSequence k;
    private CharSequence l;
    private CharSequence m;
    private TestAds n;
    private Map<CharSequence, CharSequence> o;
    private boolean p;

    /* synthetic */ AdRequest$Builder() {
        this(0);
    }

    private AdRequest$Builder(byte by) {
        super(AdRequest.SCHEMA$);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public AdRequest build() {
        try {
            boolean bl;
            AdRequest adRequest = new AdRequest();
            CharSequence charSequence = this.fieldSetFlags()[0] ? this.a : (CharSequence)this.defaultValue(this.fields()[0]);
            adRequest.a = charSequence;
            CharSequence charSequence2 = this.fieldSetFlags()[1] ? this.b : (CharSequence)this.defaultValue(this.fields()[1]);
            adRequest.b = charSequence2;
            CharSequence charSequence3 = this.fieldSetFlags()[2] ? this.c : (CharSequence)this.defaultValue(this.fields()[2]);
            adRequest.c = charSequence3;
            long l2 = this.fieldSetFlags()[3] ? this.d : (Long)this.defaultValue(this.fields()[3]);
            adRequest.d = l2;
            List list = this.fieldSetFlags()[4] ? this.e : (List)this.defaultValue(this.fields()[4]);
            adRequest.e = list;
            Location location = this.fieldSetFlags()[5] ? this.f : (Location)this.defaultValue(this.fields()[5]);
            adRequest.f = location;
            boolean bl2 = this.fieldSetFlags()[6] ? this.g : (Boolean)this.defaultValue(this.fields()[6]);
            adRequest.g = bl2;
            List list2 = this.fieldSetFlags()[7] ? this.h : (List)this.defaultValue(this.fields()[7]);
            adRequest.h = list2;
            AdViewContainer adViewContainer = this.fieldSetFlags()[8] ? this.i : (AdViewContainer)this.defaultValue(this.fields()[8]);
            adRequest.i = adViewContainer;
            CharSequence charSequence4 = this.fieldSetFlags()[9] ? this.j : (CharSequence)this.defaultValue(this.fields()[9]);
            adRequest.j = charSequence4;
            CharSequence charSequence5 = this.fieldSetFlags()[10] ? this.k : (CharSequence)this.defaultValue(this.fields()[10]);
            adRequest.k = charSequence5;
            CharSequence charSequence6 = this.fieldSetFlags()[11] ? this.l : (CharSequence)this.defaultValue(this.fields()[11]);
            adRequest.l = charSequence6;
            CharSequence charSequence7 = this.fieldSetFlags()[12] ? this.m : (CharSequence)this.defaultValue(this.fields()[12]);
            adRequest.m = charSequence7;
            TestAds testAds = this.fieldSetFlags()[13] ? this.n : (TestAds)this.defaultValue(this.fields()[13]);
            adRequest.n = testAds;
            Map map = this.fieldSetFlags()[14] ? this.o : (Map)this.defaultValue(this.fields()[14]);
            adRequest.o = map;
            boolean bl3 = this.fieldSetFlags()[15] ? this.p : (bl = ((Boolean)this.defaultValue(this.fields()[15])).booleanValue());
            adRequest.p = bl3;
            return adRequest;
        }
        catch (Exception exception) {
            throw new AvroRuntimeException(exception);
        }
    }

    public AdRequest$Builder clearAdReportedIds() {
        this.e = null;
        this.fieldSetFlags()[4] = false;
        return this;
    }

    public AdRequest$Builder clearAdSpaceName() {
        this.c = null;
        this.fieldSetFlags()[2] = false;
        return this;
    }

    public AdRequest$Builder clearAdViewContainer() {
        this.i = null;
        this.fieldSetFlags()[8] = false;
        return this;
    }

    public AdRequest$Builder clearAgentVersion() {
        this.b = null;
        this.fieldSetFlags()[1] = false;
        return this;
    }

    public AdRequest$Builder clearApiKey() {
        this.a = null;
        this.fieldSetFlags()[0] = false;
        return this;
    }

    public AdRequest$Builder clearBindings() {
        this.h = null;
        this.fieldSetFlags()[7] = false;
        return this;
    }

    public AdRequest$Builder clearDevicePlatform() {
        this.m = null;
        this.fieldSetFlags()[12] = false;
        return this;
    }

    public AdRequest$Builder clearKeywords() {
        this.o = null;
        this.fieldSetFlags()[14] = false;
        return this;
    }

    public AdRequest$Builder clearLocale() {
        this.j = null;
        this.fieldSetFlags()[9] = false;
        return this;
    }

    public AdRequest$Builder clearLocation() {
        this.f = null;
        this.fieldSetFlags()[5] = false;
        return this;
    }

    public AdRequest$Builder clearOsVersion() {
        this.l = null;
        this.fieldSetFlags()[11] = false;
        return this;
    }

    public AdRequest$Builder clearRefresh() {
        this.fieldSetFlags()[15] = false;
        return this;
    }

    public AdRequest$Builder clearSessionId() {
        this.fieldSetFlags()[3] = false;
        return this;
    }

    public AdRequest$Builder clearTestAds() {
        this.n = null;
        this.fieldSetFlags()[13] = false;
        return this;
    }

    public AdRequest$Builder clearTestDevice() {
        this.fieldSetFlags()[6] = false;
        return this;
    }

    public AdRequest$Builder clearTimezone() {
        this.k = null;
        this.fieldSetFlags()[10] = false;
        return this;
    }

    public List<AdReportedId> getAdReportedIds() {
        return this.e;
    }

    public CharSequence getAdSpaceName() {
        return this.c;
    }

    public AdViewContainer getAdViewContainer() {
        return this.i;
    }

    public CharSequence getAgentVersion() {
        return this.b;
    }

    public CharSequence getApiKey() {
        return this.a;
    }

    public List<Integer> getBindings() {
        return this.h;
    }

    public CharSequence getDevicePlatform() {
        return this.m;
    }

    public Map<CharSequence, CharSequence> getKeywords() {
        return this.o;
    }

    public CharSequence getLocale() {
        return this.j;
    }

    public Location getLocation() {
        return this.f;
    }

    public CharSequence getOsVersion() {
        return this.l;
    }

    public Boolean getRefresh() {
        return this.p;
    }

    public Long getSessionId() {
        return this.d;
    }

    public TestAds getTestAds() {
        return this.n;
    }

    public Boolean getTestDevice() {
        return this.g;
    }

    public CharSequence getTimezone() {
        return this.k;
    }

    public boolean hasAdReportedIds() {
        return this.fieldSetFlags()[4];
    }

    public boolean hasAdSpaceName() {
        return this.fieldSetFlags()[2];
    }

    public boolean hasAdViewContainer() {
        return this.fieldSetFlags()[8];
    }

    public boolean hasAgentVersion() {
        return this.fieldSetFlags()[1];
    }

    public boolean hasApiKey() {
        return this.fieldSetFlags()[0];
    }

    public boolean hasBindings() {
        return this.fieldSetFlags()[7];
    }

    public boolean hasDevicePlatform() {
        return this.fieldSetFlags()[12];
    }

    public boolean hasKeywords() {
        return this.fieldSetFlags()[14];
    }

    public boolean hasLocale() {
        return this.fieldSetFlags()[9];
    }

    public boolean hasLocation() {
        return this.fieldSetFlags()[5];
    }

    public boolean hasOsVersion() {
        return this.fieldSetFlags()[11];
    }

    public boolean hasRefresh() {
        return this.fieldSetFlags()[15];
    }

    public boolean hasSessionId() {
        return this.fieldSetFlags()[3];
    }

    public boolean hasTestAds() {
        return this.fieldSetFlags()[13];
    }

    public boolean hasTestDevice() {
        return this.fieldSetFlags()[6];
    }

    public boolean hasTimezone() {
        return this.fieldSetFlags()[10];
    }

    public AdRequest$Builder setAdReportedIds(List<AdReportedId> list) {
        this.validate(this.fields()[4], list);
        this.e = list;
        this.fieldSetFlags()[4] = true;
        return this;
    }

    public AdRequest$Builder setAdSpaceName(CharSequence charSequence) {
        this.validate(this.fields()[2], (Object)charSequence);
        this.c = charSequence;
        this.fieldSetFlags()[2] = true;
        return this;
    }

    public AdRequest$Builder setAdViewContainer(AdViewContainer adViewContainer) {
        this.validate(this.fields()[8], adViewContainer);
        this.i = adViewContainer;
        this.fieldSetFlags()[8] = true;
        return this;
    }

    public AdRequest$Builder setAgentVersion(CharSequence charSequence) {
        this.validate(this.fields()[1], (Object)charSequence);
        this.b = charSequence;
        this.fieldSetFlags()[1] = true;
        return this;
    }

    public AdRequest$Builder setApiKey(CharSequence charSequence) {
        this.validate(this.fields()[0], (Object)charSequence);
        this.a = charSequence;
        this.fieldSetFlags()[0] = true;
        return this;
    }

    public AdRequest$Builder setBindings(List<Integer> list) {
        this.validate(this.fields()[7], list);
        this.h = list;
        this.fieldSetFlags()[7] = true;
        return this;
    }

    public AdRequest$Builder setDevicePlatform(CharSequence charSequence) {
        this.validate(this.fields()[12], (Object)charSequence);
        this.m = charSequence;
        this.fieldSetFlags()[12] = true;
        return this;
    }

    public AdRequest$Builder setKeywords(Map<CharSequence, CharSequence> map) {
        this.validate(this.fields()[14], map);
        this.o = map;
        this.fieldSetFlags()[14] = true;
        return this;
    }

    public AdRequest$Builder setLocale(CharSequence charSequence) {
        this.validate(this.fields()[9], (Object)charSequence);
        this.j = charSequence;
        this.fieldSetFlags()[9] = true;
        return this;
    }

    public AdRequest$Builder setLocation(Location location) {
        this.validate(this.fields()[5], location);
        this.f = location;
        this.fieldSetFlags()[5] = true;
        return this;
    }

    public AdRequest$Builder setOsVersion(CharSequence charSequence) {
        this.validate(this.fields()[11], (Object)charSequence);
        this.l = charSequence;
        this.fieldSetFlags()[11] = true;
        return this;
    }

    public AdRequest$Builder setRefresh(boolean bl) {
        this.validate(this.fields()[15], bl);
        this.p = bl;
        this.fieldSetFlags()[15] = true;
        return this;
    }

    public AdRequest$Builder setSessionId(long l2) {
        this.validate(this.fields()[3], l2);
        this.d = l2;
        this.fieldSetFlags()[3] = true;
        return this;
    }

    public AdRequest$Builder setTestAds(TestAds testAds) {
        this.validate(this.fields()[13], testAds);
        this.n = testAds;
        this.fieldSetFlags()[13] = true;
        return this;
    }

    public AdRequest$Builder setTestDevice(boolean bl) {
        this.validate(this.fields()[6], bl);
        this.g = bl;
        this.fieldSetFlags()[6] = true;
        return this;
    }

    public AdRequest$Builder setTimezone(CharSequence charSequence) {
        this.validate(this.fields()[10], (Object)charSequence);
        this.k = charSequence;
        this.fieldSetFlags()[10] = true;
        return this;
    }
}

