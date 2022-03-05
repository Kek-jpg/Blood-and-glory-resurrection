/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.io.IOException
 *  java.lang.AssertionError
 *  java.lang.Object
 */
package com.flurry.org.apache.avro.io.parsing;

import com.flurry.org.apache.avro.io.parsing.Parser;
import com.flurry.org.apache.avro.io.parsing.Symbol;
import java.io.IOException;

public class SkipParser
extends Parser {
    static final /* synthetic */ boolean $assertionsDisabled;
    private final SkipHandler skipHandler;

    /*
     * Enabled aggressive block sorting
     */
    static {
        boolean bl = !SkipParser.class.desiredAssertionStatus();
        $assertionsDisabled = bl;
    }

    public SkipParser(Symbol symbol, Parser.ActionHandler actionHandler, SkipHandler skipHandler) throws IOException {
        super(symbol, actionHandler);
        this.skipHandler = skipHandler;
    }

    public final void skipRepeater() throws IOException {
        int n2;
        int n3 = this.pos;
        Symbol[] arrsymbol = this.stack;
        this.pos = n2 = -1 + this.pos;
        Symbol symbol = arrsymbol[n2];
        if (!$assertionsDisabled && symbol.kind != Symbol.Kind.REPEATER) {
            throw new AssertionError();
        }
        this.pushProduction(symbol);
        this.skipTo(n3);
    }

    public final void skipSymbol(Symbol symbol) throws IOException {
        int n2 = this.pos;
        this.pushSymbol(symbol);
        this.skipTo(n2);
    }

    public final void skipTo(int n2) throws IOException {
        while (n2 < this.pos) {
            Symbol symbol = this.stack[-1 + this.pos];
            if (symbol.kind != Symbol.Kind.TERMINAL) {
                if (symbol.kind == Symbol.Kind.IMPLICIT_ACTION || symbol.kind == Symbol.Kind.EXPLICIT_ACTION) {
                    this.skipHandler.skipAction();
                    continue;
                }
                this.pos = -1 + this.pos;
                this.pushProduction(symbol);
                continue;
            }
            this.skipHandler.skipTopSymbol();
        }
    }

    public static interface SkipHandler {
        public void skipAction() throws IOException;

        public void skipTopSymbol() throws IOException;
    }

}

