/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.io.IOException
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.System
 */
package com.flurry.org.apache.avro.io.parsing;

import com.flurry.org.apache.avro.AvroTypeException;
import com.flurry.org.apache.avro.io.parsing.Symbol;
import java.io.IOException;

public class Parser {
    protected int pos;
    protected Symbol[] stack;
    protected final ActionHandler symbolHandler;

    public Parser(Symbol symbol, ActionHandler actionHandler) throws IOException {
        this.symbolHandler = actionHandler;
        this.stack = new Symbol[5];
        this.stack[0] = symbol;
        this.pos = 1;
    }

    private void expandStack() {
        Symbol[] arrsymbol = new Symbol[this.stack.length + java.lang.Math.max((int)this.stack.length, (int)1024)];
        System.arraycopy((Object)this.stack, (int)0, (Object)arrsymbol, (int)0, (int)this.stack.length);
        this.stack = arrsymbol;
    }

    public final Symbol advance(Symbol symbol) throws IOException {
        do {
            int n2;
            Symbol[] arrsymbol = this.stack;
            this.pos = n2 = -1 + this.pos;
            Symbol symbol2 = arrsymbol[n2];
            if (symbol2 == symbol) {
                return symbol2;
            }
            Symbol.Kind kind = symbol2.kind;
            if (kind == Symbol.Kind.IMPLICIT_ACTION) {
                Symbol symbol3 = this.symbolHandler.doAction(symbol, symbol2);
                if (symbol3 == null) continue;
                return symbol3;
            }
            if (kind == Symbol.Kind.TERMINAL) {
                throw new AvroTypeException("Attempt to process a " + symbol + " when a " + symbol2 + " was expected.");
            }
            if (kind == Symbol.Kind.REPEATER && symbol == ((Symbol.Repeater)symbol2).end) {
                return symbol;
            }
            this.pushProduction(symbol2);
        } while (true);
    }

    public int depth() {
        return this.pos;
    }

    public Symbol popSymbol() {
        int n2;
        Symbol[] arrsymbol = this.stack;
        this.pos = n2 = -1 + this.pos;
        return arrsymbol[n2];
    }

    /*
     * Enabled aggressive block sorting
     */
    public final void processImplicitActions() throws IOException {
        do {
            Symbol symbol;
            block6 : {
                block5 : {
                    if (this.pos <= 1) break block5;
                    symbol = this.stack[-1 + this.pos];
                    if (symbol.kind == Symbol.Kind.IMPLICIT_ACTION) {
                        this.pos = -1 + this.pos;
                        this.symbolHandler.doAction(null, symbol);
                        continue;
                    }
                    if (symbol.kind != Symbol.Kind.TERMINAL) break block6;
                }
                return;
            }
            this.pos = -1 + this.pos;
            this.pushProduction(symbol);
        } while (true);
    }

    /*
     * Enabled aggressive block sorting
     */
    public final void processTrailingImplicitActions() throws IOException {
        do {
            Symbol symbol;
            block4 : {
                block3 : {
                    if (this.pos < 1) break block3;
                    symbol = this.stack[-1 + this.pos];
                    if (symbol.kind == Symbol.Kind.IMPLICIT_ACTION && ((Symbol.ImplicitAction)symbol).isTrailing) break block4;
                }
                return;
            }
            this.pos = -1 + this.pos;
            this.symbolHandler.doAction(null, symbol);
        } while (true);
    }

    public final void pushProduction(Symbol symbol) {
        Symbol[] arrsymbol = symbol.production;
        do {
            if (this.pos + arrsymbol.length <= this.stack.length) {
                System.arraycopy((Object)arrsymbol, (int)0, (Object)this.stack, (int)this.pos, (int)arrsymbol.length);
                this.pos += arrsymbol.length;
                return;
            }
            Parser.super.expandStack();
        } while (true);
    }

    public void pushSymbol(Symbol symbol) {
        if (this.pos == this.stack.length) {
            Parser.super.expandStack();
        }
        Symbol[] arrsymbol = this.stack;
        int n2 = this.pos;
        this.pos = n2 + 1;
        arrsymbol[n2] = symbol;
    }

    public void reset() {
        this.pos = 1;
    }

    public Symbol topSymbol() {
        return this.stack[-1 + this.pos];
    }

    public static interface ActionHandler {
        public Symbol doAction(Symbol var1, Symbol var2) throws IOException;
    }

}

