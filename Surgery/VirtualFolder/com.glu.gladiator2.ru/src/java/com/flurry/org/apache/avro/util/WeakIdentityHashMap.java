/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Object
 *  java.lang.System
 *  java.lang.UnsupportedOperationException
 *  java.lang.ref.Reference
 *  java.lang.ref.ReferenceQueue
 *  java.lang.ref.WeakReference
 *  java.util.Collection
 *  java.util.Collections
 *  java.util.HashMap
 *  java.util.HashSet
 *  java.util.Iterator
 *  java.util.Map
 *  java.util.Map$Entry
 *  java.util.Set
 */
package com.flurry.org.apache.avro.util;

import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class WeakIdentityHashMap<K, V>
implements Map<K, V> {
    private Map<WeakIdentityHashMap<K, V>, V> backingStore = new HashMap();
    private final ReferenceQueue<K> queue = new ReferenceQueue();

    private void reap() {
        WeakIdentityHashMap weakIdentityHashMap = this;
        synchronized (weakIdentityHashMap) {
            Reference reference = this.queue.poll();
            while (reference != null) {
                Reference reference2;
                IdentityWeakReference identityWeakReference = (IdentityWeakReference)reference;
                this.backingStore.remove((Object)identityWeakReference);
                reference = reference2 = this.queue.poll();
            }
            return;
        }
    }

    public void clear() {
        this.backingStore.clear();
        this.reap();
    }

    public boolean containsKey(Object object) {
        WeakIdentityHashMap.super.reap();
        return this.backingStore.containsKey((Object)(WeakIdentityHashMap)this.new IdentityWeakReference(object));
    }

    public boolean containsValue(Object object) {
        WeakIdentityHashMap.super.reap();
        return this.backingStore.containsValue(object);
    }

    public Set<Map.Entry<K, V>> entrySet() {
        this.reap();
        HashSet hashSet = new HashSet();
        for (Map.Entry entry : this.backingStore.entrySet()) {
            hashSet.add((Object)new Map.Entry<K, V>(((IdentityWeakReference)((Object)entry.getKey())).get(), entry.getValue()){
                final /* synthetic */ Object val$key;
                final /* synthetic */ Object val$value;
                {
                    this.val$key = object;
                    this.val$value = object2;
                }

                public K getKey() {
                    return (K)this.val$key;
                }

                public V getValue() {
                    return (V)this.val$value;
                }

                public V setValue(V v2) {
                    throw new UnsupportedOperationException();
                }
            });
        }
        return Collections.unmodifiableSet((Set)hashSet);
    }

    public boolean equals(Object object) {
        return this.backingStore.equals(((WeakIdentityHashMap)object).backingStore);
    }

    public V get(Object object) {
        WeakIdentityHashMap.super.reap();
        return (V)this.backingStore.get((Object)(WeakIdentityHashMap)this.new IdentityWeakReference(object));
    }

    public int hashCode() {
        this.reap();
        return this.backingStore.hashCode();
    }

    public boolean isEmpty() {
        this.reap();
        return this.backingStore.isEmpty();
    }

    public Set<K> keySet() {
        this.reap();
        HashSet hashSet = new HashSet();
        Iterator iterator = this.backingStore.keySet().iterator();
        while (iterator.hasNext()) {
            hashSet.add(((IdentityWeakReference)((Object)iterator.next())).get());
        }
        return Collections.unmodifiableSet((Set)hashSet);
    }

    public V put(K k2, V v2) {
        WeakIdentityHashMap.super.reap();
        return (V)this.backingStore.put((Object)(WeakIdentityHashMap)this.new IdentityWeakReference(k2), v2);
    }

    public void putAll(Map map) {
        throw new UnsupportedOperationException();
    }

    public V remove(Object object) {
        WeakIdentityHashMap.super.reap();
        return (V)this.backingStore.remove((Object)(WeakIdentityHashMap)this.new IdentityWeakReference(object));
    }

    public int size() {
        this.reap();
        return this.backingStore.size();
    }

    public Collection<V> values() {
        this.reap();
        return this.backingStore.values();
    }

    class IdentityWeakReference
    extends WeakReference<K> {
        int hash;

        IdentityWeakReference(Object object) {
            super(object, WeakIdentityHashMap.this.queue);
            this.hash = System.identityHashCode((Object)object);
        }

        /*
         * Enabled aggressive block sorting
         */
        public boolean equals(Object object) {
            block3 : {
                block2 : {
                    if (this == object) break block2;
                    IdentityWeakReference identityWeakReference = (IdentityWeakReference)((Object)object);
                    if (this.get() != identityWeakReference.get()) break block3;
                }
                return true;
            }
            return false;
        }

        public int hashCode() {
            return this.hash;
        }
    }

}

