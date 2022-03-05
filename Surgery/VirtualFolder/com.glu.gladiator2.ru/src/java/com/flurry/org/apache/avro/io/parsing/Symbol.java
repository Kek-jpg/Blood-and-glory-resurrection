/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Enum
 *  java.lang.Iterable
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.System
 *  java.lang.UnsupportedOperationException
 *  java.util.ArrayList
 *  java.util.HashMap
 *  java.util.Iterator
 *  java.util.List
 *  java.util.Map
 *  java.util.NoSuchElementException
 */
package com.flurry.org.apache.avro.io.parsing;

import com.flurry.org.apache.avro.Schema;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

public abstract class Symbol {
    public static final Symbol ARRAY_END;
    public static final Symbol ARRAY_START;
    public static final Symbol BOOLEAN;
    public static final Symbol BYTES;
    public static final Symbol DEFAULT_END_ACTION;
    public static final Symbol DOUBLE;
    public static final Symbol ENUM;
    public static final Symbol FIELD_ACTION;
    public static final Symbol FIELD_END;
    public static final Symbol FIXED;
    public static final Symbol FLOAT;
    public static final Symbol INT;
    public static final Symbol ITEM_END;
    public static final Symbol LONG;
    public static final Symbol MAP_END;
    public static final Symbol MAP_KEY_MARKER;
    public static final Symbol MAP_START;
    public static final Symbol NULL;
    public static final Symbol RECORD_END;
    public static final Symbol RECORD_START;
    public static final Symbol STRING;
    public static final Symbol UNION;
    public static final Symbol UNION_END;
    public final Kind kind;
    public final Symbol[] production;

    static {
        NULL = new Terminal("null");
        BOOLEAN = new Terminal("boolean");
        INT = new Terminal("int");
        LONG = new Terminal("long");
        FLOAT = new Terminal("float");
        DOUBLE = new Terminal("double");
        STRING = new Terminal("string");
        BYTES = new Terminal("bytes");
        FIXED = new Terminal("fixed");
        ENUM = new Terminal("enum");
        UNION = new Terminal("union");
        ARRAY_START = new Terminal("array-start");
        ARRAY_END = new Terminal("array-end");
        MAP_START = new Terminal("map-start");
        MAP_END = new Terminal("map-end");
        ITEM_END = new Terminal("item-end");
        FIELD_ACTION = new Terminal("field-action");
        RECORD_START = new ImplicitAction(false, null);
        RECORD_END = new ImplicitAction(true, null);
        UNION_END = new ImplicitAction(true, null);
        FIELD_END = new ImplicitAction(true, null);
        DEFAULT_END_ACTION = new ImplicitAction(true, null);
        MAP_KEY_MARKER = new Terminal("map-key-marker");
    }

    protected Symbol(Kind kind) {
        super(kind, null);
    }

    protected Symbol(Kind kind, Symbol[] arrsymbol) {
        this.production = arrsymbol;
        this.kind = kind;
    }

    static Symbol alt(Symbol[] arrsymbol, String[] arrstring) {
        return new Alternative(arrsymbol, arrstring, null);
    }

    static Symbol error(String string) {
        return new ErrorAction(string, null);
    }

    /*
     * Enabled aggressive block sorting
     */
    static void flatten(Symbol[] arrsymbol, int n2, Symbol[] arrsymbol2, int n3, Map<Sequence, Sequence> map, Map<Sequence, List<Fixup>> map2) {
        int n4 = n2;
        int n5 = n3;
        while (n4 < arrsymbol.length) {
            Symbol symbol = arrsymbol[n4].flatten(map, map2);
            if (symbol instanceof Sequence) {
                Symbol[] arrsymbol3 = symbol.production;
                List list = (List)map2.get((Object)symbol);
                if (list == null) {
                    System.arraycopy((Object)arrsymbol3, (int)0, (Object)arrsymbol2, (int)n5, (int)arrsymbol3.length);
                } else {
                    list.add((Object)new Fixup(arrsymbol2, n5));
                }
                n5 += arrsymbol3.length;
            } else {
                int n6 = n5 + 1;
                arrsymbol2[n5] = symbol;
                n5 = n6;
            }
            ++n4;
        }
        return;
    }

    /*
     * Enabled aggressive block sorting
     */
    protected static int flattenedSize(Symbol[] arrsymbol, int n2) {
        int n3 = 0;
        int n4 = n2;
        while (n4 < arrsymbol.length) {
            n3 = arrsymbol[n4] instanceof Sequence ? (n3 += ((Sequence)arrsymbol[n4]).flattenedSize()) : ++n3;
            ++n4;
        }
        return n3;
    }

    static /* varargs */ Symbol repeat(Symbol symbol, Symbol ... arrsymbol) {
        return new Repeater(symbol, arrsymbol, null);
    }

    static Symbol resolve(Symbol symbol, Symbol symbol2) {
        return new ResolvingAction(symbol, symbol2, null);
    }

    static /* varargs */ Symbol root(Symbol ... arrsymbol) {
        return new Root(arrsymbol, null);
    }

    static /* varargs */ Symbol seq(Symbol ... arrsymbol) {
        return new Sequence(arrsymbol, null);
    }

    public Symbol flatten(Map<Sequence, Sequence> map, Map<Sequence, List<Fixup>> map2) {
        return this;
    }

    public int flattenedSize() {
        return 1;
    }

    public static class Alternative
    extends Symbol {
        public final String[] labels;
        public final Symbol[] symbols;

        private Alternative(Symbol[] arrsymbol, String[] arrstring) {
            super(Kind.ALTERNATIVE);
            this.symbols = arrsymbol;
            this.labels = arrstring;
        }

        /* synthetic */ Alternative(Symbol[] arrsymbol, String[] arrstring, 1 var3_2) {
            super(arrsymbol, arrstring);
        }

        public int findLabel(String string) {
            if (string != null) {
                for (int i2 = 0; i2 < this.labels.length; ++i2) {
                    if (!string.equals((Object)this.labels[i2])) continue;
                    return i2;
                }
            }
            return -1;
        }

        @Override
        public Alternative flatten(Map<Sequence, Sequence> map, Map<Sequence, List<Fixup>> map2) {
            Symbol[] arrsymbol = new Symbol[this.symbols.length];
            for (int i2 = 0; i2 < arrsymbol.length; ++i2) {
                arrsymbol[i2] = this.symbols[i2].flatten(map, map2);
            }
            return new Alternative(arrsymbol, this.labels);
        }

        public String getLabel(int n2) {
            return this.labels[n2];
        }

        public Symbol getSymbol(int n2) {
            return this.symbols[n2];
        }

        public int size() {
            return this.symbols.length;
        }
    }

    public static class DefaultStartAction
    extends ImplicitAction {
        public final byte[] contents;

        public DefaultStartAction(byte[] arrby) {
            super(null);
            this.contents = arrby;
        }
    }

    public static class EnumAdjustAction
    extends IntCheckAction {
        public final Object[] adjustments;

        public EnumAdjustAction(int n2, Object[] arrobject) {
            super(n2);
            this.adjustments = arrobject;
        }
    }

    public static class EnumLabelsAction
    extends IntCheckAction {
        public final List<String> symbols;

        public EnumLabelsAction(List<String> list) {
            super(list.size());
            this.symbols = list;
        }

        public int findLabel(String string) {
            if (string != null) {
                for (int i2 = 0; i2 < this.symbols.size(); ++i2) {
                    if (!string.equals(this.symbols.get(i2))) continue;
                    return i2;
                }
            }
            return -1;
        }

        public String getLabel(int n2) {
            return (String)this.symbols.get(n2);
        }
    }

    public static class ErrorAction
    extends ImplicitAction {
        public final String msg;

        private ErrorAction(String string) {
            super(null);
            this.msg = string;
        }

        /* synthetic */ ErrorAction(String string, 1 var2_2) {
            super(string);
        }
    }

    public static class FieldAdjustAction
    extends ImplicitAction {
        public final String fname;
        public final int rindex;

        public FieldAdjustAction(int n2, String string) {
            super(null);
            this.rindex = n2;
            this.fname = string;
        }
    }

    public static final class FieldOrderAction
    extends ImplicitAction {
        public final Schema.Field[] fields;

        public FieldOrderAction(Schema.Field[] arrfield) {
            super(null);
            this.fields = arrfield;
        }
    }

    private static class Fixup {
        public final int pos;
        public final Symbol[] symbols;

        public Fixup(Symbol[] arrsymbol, int n2) {
            this.symbols = arrsymbol;
            this.pos = n2;
        }
    }

    public static class ImplicitAction
    extends Symbol {
        public final boolean isTrailing;

        private ImplicitAction() {
            this(false);
        }

        /* synthetic */ ImplicitAction(1 var1) {
        }

        private ImplicitAction(boolean bl) {
            super(Kind.IMPLICIT_ACTION);
            this.isTrailing = bl;
        }

        /* synthetic */ ImplicitAction(boolean bl, 1 var2_2) {
            super(bl);
        }
    }

    public static class IntCheckAction
    extends Symbol {
        public final int size;

        public IntCheckAction(int n2) {
            super(Kind.EXPLICIT_ACTION);
            this.size = n2;
        }
    }

    public static final class Kind
    extends Enum<Kind> {
        private static final /* synthetic */ Kind[] $VALUES;
        public static final /* enum */ Kind ALTERNATIVE;
        public static final /* enum */ Kind EXPLICIT_ACTION;
        public static final /* enum */ Kind IMPLICIT_ACTION;
        public static final /* enum */ Kind REPEATER;
        public static final /* enum */ Kind ROOT;
        public static final /* enum */ Kind SEQUENCE;
        public static final /* enum */ Kind TERMINAL;

        static {
            TERMINAL = new Kind();
            ROOT = new Kind();
            SEQUENCE = new Kind();
            REPEATER = new Kind();
            ALTERNATIVE = new Kind();
            IMPLICIT_ACTION = new Kind();
            EXPLICIT_ACTION = new Kind();
            Kind[] arrkind = new Kind[]{TERMINAL, ROOT, SEQUENCE, REPEATER, ALTERNATIVE, IMPLICIT_ACTION, EXPLICIT_ACTION};
            $VALUES = arrkind;
        }

        public static Kind valueOf(String string) {
            return (Kind)Enum.valueOf(Kind.class, (String)string);
        }

        public static Kind[] values() {
            return (Kind[])$VALUES.clone();
        }
    }

    public static class Repeater
    extends Symbol {
        public final Symbol end;

        private /* varargs */ Repeater(Symbol symbol, Symbol ... arrsymbol) {
            super(Kind.REPEATER, Repeater.makeProduction(arrsymbol));
            this.end = symbol;
            this.production[0] = this;
        }

        /* synthetic */ Repeater(Symbol symbol, Symbol[] arrsymbol, 1 var3_2) {
            super(symbol, arrsymbol);
        }

        private static Symbol[] makeProduction(Symbol[] arrsymbol) {
            Symbol[] arrsymbol2 = new Symbol[1 + arrsymbol.length];
            System.arraycopy((Object)arrsymbol, (int)0, (Object)arrsymbol2, (int)1, (int)arrsymbol.length);
            return arrsymbol2;
        }

        @Override
        public Repeater flatten(Map<Sequence, Sequence> map, Map<Sequence, List<Fixup>> map2) {
            Repeater repeater = new Repeater(this.end, new Symbol[Repeater.flattenedSize(this.production, 1)]);
            Repeater.flatten(this.production, 1, repeater.production, 1, map, map2);
            return repeater;
        }
    }

    public static class ResolvingAction
    extends ImplicitAction {
        public final Symbol reader;
        public final Symbol writer;

        private ResolvingAction(Symbol symbol, Symbol symbol2) {
            super(null);
            this.writer = symbol;
            this.reader = symbol2;
        }

        /* synthetic */ ResolvingAction(Symbol symbol, Symbol symbol2, 1 var3_2) {
            super(symbol, symbol2);
        }

        @Override
        public ResolvingAction flatten(Map<Sequence, Sequence> map, Map<Sequence, List<Fixup>> map2) {
            return new ResolvingAction(this.writer.flatten(map, map2), this.reader.flatten(map, map2));
        }
    }

    protected static class Root
    extends Symbol {
        private /* varargs */ Root(Symbol ... arrsymbol) {
            super(Kind.ROOT, Root.makeProduction(arrsymbol));
            this.production[0] = this;
        }

        /* synthetic */ Root(Symbol[] arrsymbol, 1 var2_2) {
            super(arrsymbol);
        }

        private static Symbol[] makeProduction(Symbol[] arrsymbol) {
            Symbol[] arrsymbol2 = new Symbol[1 + Root.flattenedSize(arrsymbol, 0)];
            Root.flatten(arrsymbol, 0, arrsymbol2, 1, (Map<Sequence, Sequence>)new HashMap(), (Map<Sequence, List<Fixup>>)new HashMap());
            return arrsymbol2;
        }
    }

    protected static class Sequence
    extends Symbol
    implements Iterable<Symbol> {
        private Sequence(Symbol[] arrsymbol) {
            super(Kind.SEQUENCE, arrsymbol);
        }

        /* synthetic */ Sequence(Symbol[] arrsymbol, com.flurry.org.apache.avro.io.parsing.Symbol$1 var2_2) {
            super(arrsymbol);
        }

        @Override
        public Sequence flatten(Map<Sequence, Sequence> map, Map<Sequence, List<Fixup>> map2) {
            Sequence sequence = (Sequence)map.get((Object)this);
            if (sequence == null) {
                sequence = new Sequence(new Symbol[this.flattenedSize()]);
                map.put((Object)this, (Object)sequence);
                ArrayList arrayList = new ArrayList();
                map2.put((Object)sequence, (Object)arrayList);
                Sequence.flatten(this.production, 0, sequence.production, 0, map, map2);
                for (Fixup fixup : arrayList) {
                    System.arraycopy((Object)sequence.production, (int)0, (Object)fixup.symbols, (int)fixup.pos, (int)sequence.production.length);
                }
                map2.remove((Object)sequence);
            }
            return sequence;
        }

        @Override
        public final int flattenedSize() {
            return Sequence.flattenedSize(this.production, 0);
        }

        public Symbol get(int n2) {
            return this.production[n2];
        }

        public Iterator<Symbol> iterator() {
            return new Iterator<Symbol>(){
                private int pos;
                {
                    this.pos = Sequence.this.production.length;
                }

                public boolean hasNext() {
                    return this.pos > 0;
                }

                public Symbol next() {
                    if (this.pos > 0) {
                        int n2;
                        Symbol[] arrsymbol = Sequence.this.production;
                        this.pos = n2 = -1 + this.pos;
                        return arrsymbol[n2];
                    }
                    throw new NoSuchElementException();
                }

                public void remove() {
                    throw new UnsupportedOperationException();
                }
            };
        }

        public int size() {
            return this.production.length;
        }

    }

    public static class SkipAction
    extends ImplicitAction {
        public final Symbol symToSkip;

        public SkipAction(Symbol symbol) {
            super(true, null);
            this.symToSkip = symbol;
        }

        @Override
        public SkipAction flatten(Map<Sequence, Sequence> map, Map<Sequence, List<Fixup>> map2) {
            return new SkipAction(this.symToSkip.flatten(map, map2));
        }
    }

    private static class Terminal
    extends Symbol {
        private final String printName;

        public Terminal(String string) {
            super(Kind.TERMINAL);
            this.printName = string;
        }

        public String toString() {
            return this.printName;
        }
    }

    public static class UnionAdjustAction
    extends ImplicitAction {
        public final int rindex;
        public final Symbol symToParse;

        public UnionAdjustAction(int n2, Symbol symbol) {
            super(null);
            this.rindex = n2;
            this.symToParse = symbol;
        }

        @Override
        public UnionAdjustAction flatten(Map<Sequence, Sequence> map, Map<Sequence, List<Fixup>> map2) {
            return new UnionAdjustAction(this.rindex, this.symToParse.flatten(map, map2));
        }
    }

    public static class WriterUnionAction
    extends ImplicitAction {
        public WriterUnionAction() {
            super(null);
        }
    }

}

