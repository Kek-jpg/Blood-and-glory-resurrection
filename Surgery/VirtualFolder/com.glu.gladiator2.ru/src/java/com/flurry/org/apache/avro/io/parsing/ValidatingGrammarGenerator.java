/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.NoSuchFieldError
 *  java.lang.Object
 *  java.lang.RuntimeException
 *  java.lang.String
 *  java.util.HashMap
 *  java.util.Iterator
 *  java.util.List
 *  java.util.Map
 */
package com.flurry.org.apache.avro.io.parsing;

import com.flurry.org.apache.avro.Schema;
import com.flurry.org.apache.avro.io.parsing.Symbol;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class ValidatingGrammarGenerator {
    public Symbol generate(Schema schema) {
        Symbol[] arrsymbol = new Symbol[]{this.generate(schema, (Map<LitS, Symbol>)new HashMap())};
        return Symbol.root(arrsymbol);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public Symbol generate(Schema schema, Map<LitS, Symbol> map) {
        switch (1.$SwitchMap$org$apache$avro$Schema$Type[schema.getType().ordinal()]) {
            default: {
                throw new RuntimeException("Unexpected schema type");
            }
            case 1: {
                return Symbol.NULL;
            }
            case 2: {
                return Symbol.BOOLEAN;
            }
            case 3: {
                return Symbol.INT;
            }
            case 4: {
                return Symbol.LONG;
            }
            case 5: {
                return Symbol.FLOAT;
            }
            case 6: {
                return Symbol.DOUBLE;
            }
            case 7: {
                return Symbol.STRING;
            }
            case 8: {
                return Symbol.BYTES;
            }
            case 9: {
                Symbol[] arrsymbol = new Symbol[]{new Symbol.IntCheckAction(schema.getFixedSize()), Symbol.FIXED};
                return Symbol.seq(arrsymbol);
            }
            case 10: {
                Symbol[] arrsymbol = new Symbol[]{new Symbol.IntCheckAction(schema.getEnumSymbols().size()), Symbol.ENUM};
                return Symbol.seq(arrsymbol);
            }
            case 11: {
                Symbol[] arrsymbol = new Symbol[2];
                Symbol symbol = Symbol.ARRAY_END;
                Symbol[] arrsymbol2 = new Symbol[]{this.generate(schema.getElementType(), map)};
                arrsymbol[0] = Symbol.repeat(symbol, arrsymbol2);
                arrsymbol[1] = Symbol.ARRAY_START;
                return Symbol.seq(arrsymbol);
            }
            case 12: {
                Symbol[] arrsymbol = new Symbol[2];
                Symbol symbol = Symbol.MAP_END;
                Symbol[] arrsymbol3 = new Symbol[]{this.generate(schema.getValueType(), map), Symbol.STRING};
                arrsymbol[0] = Symbol.repeat(symbol, arrsymbol3);
                arrsymbol[1] = Symbol.MAP_START;
                return Symbol.seq(arrsymbol);
            }
            case 13: {
                LitS litS = new LitS(schema);
                Symbol symbol = (Symbol)map.get((Object)litS);
                if (symbol != null) return symbol;
                Symbol[] arrsymbol = new Symbol[schema.getFields().size()];
                symbol = Symbol.seq(arrsymbol);
                map.put((Object)litS, (Object)symbol);
                int n2 = arrsymbol.length;
                Iterator iterator = schema.getFields().iterator();
                while (iterator.hasNext()) {
                    Schema.Field field = (Schema.Field)iterator.next();
                    arrsymbol[--n2] = this.generate(field.schema(), map);
                }
                return symbol;
            }
            case 14: 
        }
        List<Schema> list = schema.getTypes();
        Symbol[] arrsymbol = new Symbol[list.size()];
        String[] arrstring = new String[list.size()];
        int n3 = 0;
        for (Schema schema2 : schema.getTypes()) {
            arrsymbol[n3] = this.generate(schema2, map);
            arrstring[n3] = schema2.getFullName();
            ++n3;
        }
        Symbol[] arrsymbol4 = new Symbol[]{Symbol.alt(arrsymbol, arrstring), Symbol.UNION};
        return Symbol.seq(arrsymbol4);
    }

    static class LitS {
        public final Schema actual;

        public LitS(Schema schema) {
            this.actual = schema;
        }

        /*
         * Enabled aggressive block sorting
         */
        public boolean equals(Object object) {
            return object instanceof LitS && this.actual == ((LitS)object).actual;
        }

        public int hashCode() {
            return this.actual.hashCode();
        }
    }

}

