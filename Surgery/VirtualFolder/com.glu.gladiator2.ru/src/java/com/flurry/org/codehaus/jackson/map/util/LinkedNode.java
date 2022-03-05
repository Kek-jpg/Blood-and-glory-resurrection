/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Object
 */
package com.flurry.org.codehaus.jackson.map.util;

public final class LinkedNode<T> {
    final LinkedNode<T> _next;
    final T _value;

    public LinkedNode(T t, LinkedNode<T> linkedNode) {
        this._value = t;
        this._next = linkedNode;
    }

    public static <ST> boolean contains(LinkedNode<ST> linkedNode, ST ST) {
        while (linkedNode != null) {
            if (linkedNode.value() == ST) {
                return true;
            }
            linkedNode = linkedNode.next();
        }
        return false;
    }

    public LinkedNode<T> next() {
        return this._next;
    }

    public T value() {
        return this._value;
    }
}

